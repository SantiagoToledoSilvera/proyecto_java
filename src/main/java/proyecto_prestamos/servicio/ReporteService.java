package proyecto_prestamos.servicio;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import proyecto_prestamos.modelos.EstadoPrestamos;
import proyecto_prestamos.modelos.Prestamo;

public class ReporteService {

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
                .filter(p -> p.getEstado() == EstadoPrestamos.ACTIVO)
                .filter(p -> p.getFechaInicio().plusMonths(p.getCuotas())
                        .isBefore(LocalDate.now()))
                .collect(Collectors.toList());
    }

    public List<Integer> clientesMorosos(List<Prestamo> lista) {
        return vencidos(lista).stream()
                .map(p -> p.getClienteId())
                .distinct()
                .collect(Collectors.toList());
    }

    public double carteraPendiente(List<Prestamo> lista) {
        return lista.stream()
                .filter(p -> p.getEstado() == EstadoPrestamos.ACTIVO)
                .mapToDouble(p -> p.getSaldoPendiente())
                .sum();
    }

    public Map<Integer, List<Prestamo>> porCliente(List<Prestamo> lista) {
        return lista.stream()
                .collect(Collectors.groupingBy(p -> p.getClienteId()));
    }
}