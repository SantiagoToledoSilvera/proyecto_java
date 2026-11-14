package proyecto_prestamos.servicio;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import proyecto_prestamos.modelos.EstadoPrestamos;
import proyecto_prestamos.modelos.Prestamo;
import proyecto_prestamos.modelos.Pago;

public class ReporteService {
    private final CalculoInteres calculo;
    
    public ReporteService(CalculoInteres calculo) {
        this.calculo = calculo;
    }
    
    public boolean estaEnMora(Prestamo p) {
        if (p.getEstado() != EstadoPrestamos.ACTIVO) return false;

        double total = calculo.calcularMontoTotal(p.getMonto(), p.getInteres(), p.getCuotas());
        double cuota = calculo.calcularCuota(total, p.getCuotas());

        long meses = ChronoUnit.MONTHS.between(p.getFechaInicio(), LocalDate.now());
        meses = Math.min(meses, p.getCuotas());

        double deberiaHaberPagado = meses * cuota;
        double pagado = total - p.getSaldoPendiente();

        return pagado < deberiaHaberPagado - 0.01;
    }

    public List<Prestamo> activos(List<Prestamo> lista) {
        return lista.stream()
                .filter(p -> p.getEstado() == EstadoPrestamos.ACTIVO)
                .collect(Collectors.toList());
    }

    public List<Prestamo> solicitudesPendientes(List<Prestamo> lista) {      
        return lista.stream()
                .filter(p -> p.getEstado() == EstadoPrestamos.SOLICITADO)
                .collect(Collectors.toList());
    }

    public List<Prestamo> vencidos(List<Prestamo> lista) {
        return lista.stream()
                .filter(p -> estaEnMora(p))
                .collect(Collectors.toList());
    }

    public List<Integer> clientesMorosos(List<Prestamo> lista) {
        return vencidos(lista).stream()
                .map(p -> p.getClienteId())
                .distinct()
                .collect(Collectors.toList());
    }

    public double mostrarResumen(List<Prestamo> lista) {
        return lista.stream()
                .filter(p -> p.getEstado() == EstadoPrestamos.ACTIVO)
                .mapToDouble(p -> p.getSaldoPendiente())
                .sum();
    }

    public Map<Integer, List<Prestamo>> porCliente(List<Prestamo> lista) {
        return lista.stream()
                .collect(Collectors.groupingBy(p -> p.getClienteId()));
    }
    
        // Total pagado por cada prestamo
    public Map<Integer, Double> totalPagadoPorPrestamo(List<Pago> pagos) {
        return pagos.stream()
                .collect(Collectors.groupingBy(
                        p -> p.getPrestamoId(),
                        Collectors.summingDouble(p -> p.getMonto())));
    }

    // Todos los pagos de un cliente (cruza pagos con sus prestamos)
    public List<Pago> pagosDeCliente(int clienteId, List<Prestamo> prestamos, List<Pago> pagos) {
        List<Integer> idsPrestamos = prestamos.stream()
                .filter(p -> p.getClienteId() == clienteId)
                .map(p -> p.getId())
                .collect(Collectors.toList());

        return pagos.stream()
                .filter(pg -> idsPrestamos.contains(pg.getPrestamoId()))
                .collect(Collectors.toList());
    }

    // Pagos realizados en un rango de fechas
    public List<Pago> pagosEntre(LocalDate desde, LocalDate hasta, List<Pago> pagos) {
        return pagos.stream()
                .filter(pg -> !pg.getFechaPago().isBefore(desde)
                           && !pg.getFechaPago().isAfter(hasta))
                .collect(Collectors.toList());
    }
    
}