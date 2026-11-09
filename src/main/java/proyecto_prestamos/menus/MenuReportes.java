package proyecto_prestamos.menus;

import java.time.LocalDate;
import java.util.List;
import proyecto_prestamos.modelos.Pago;
import proyecto_prestamos.modelos.Prestamo;
import proyecto_prestamos.servicio.InteresCompuesto;
import proyecto_prestamos.servicio.PrestamoService;
import proyecto_prestamos.servicio.ReporteService;

public class MenuReportes extends MenuBase {
    private final ReporteService reportes = new ReporteService(new InteresCompuesto());

    public MenuReportes(Entrada in, PrestamoService servicio) {
        super(in, servicio);
    }

    @Override
    protected String titulo() { return "MENU DE REPORTES"; }

    @Override
    protected String opciones() {
        return """
               1. Prestamos activos
               2. Solicitudes pendientes
               3. Prestamos vencidos
               4. Clientes morosos
               5. Cartera pendiente total
               6. Total pagado por prestamo
               7. Pagos de un cliente
               8. Pagos entre fechas
               0. Volver
               """;
    }

    @Override
    protected void ejecutar(String op) {
        switch (op) {
            case "1" -> reportes.activos(servicio.listarMisPrestamos())
                .forEach(p -> System.out.println(p));
            case "2" -> reportes.solicitudesPendientes(servicio.listarMisPrestamos())
                    .forEach(p -> System.out.println(p));
            case "3" -> {
                List<Prestamo> vencidos = reportes.vencidos(servicio.listarMisPrestamos());
                if (vencidos.isEmpty()) {
                    System.out.println("No hay prestamos vencidos");
                } else {
                    vencidos.forEach(p -> System.out.println(p));
                }
            }
            case "4" -> {
                List<Integer> morosos = reportes.clientesMorosos(servicio.listarMisPrestamos());
                if (morosos.isEmpty()) {
                    System.out.println("No hay clientes morosos");
                } else {
                    morosos.forEach(id -> System.out.println("Cliente id: " + id));
                }
            }
            case "5" -> System.out.println("Cartera pendiente: "
                    + reportes.carteraPendiente(servicio.listarMisPrestamos()));
            case "6" -> {
                var totales = reportes.totalPagadoPorPrestamo(servicio.historialGeneral());
                if (totales.isEmpty()) {
                    System.out.println("No hay pagos registrados");
                } else {
                    totales.forEach((id, total) ->
                            System.out.println("Prestamo " + id + ": pagado " + total));
                }
            }
            case "7" -> {
                int clienteId = in.entero("Id del cliente: ");
                List<Pago> pagos = reportes.pagosDeCliente(
                        clienteId, servicio.listarMisPrestamos(), servicio.historialGeneral());
                if (pagos.isEmpty()) {
                    System.out.println("Ese cliente no tiene pagos");
                } else {
                    pagos.forEach(pg -> System.out.println(pg));
                }
            }
            case "8" -> {
                LocalDate desde = LocalDate.parse(in.fecha("Desde (aaaa-mm-dd): "));
                LocalDate hasta = LocalDate.parse(in.fecha("Hasta (aaaa-mm-dd): "));
                List<Pago> pagos = reportes.pagosEntre(desde, hasta, servicio.historialGeneral());
                if (pagos.isEmpty()) {
                    System.out.println("No hay pagos en ese rango");
                } else {
                    pagos.forEach(pg -> System.out.println(pg));
                }
            }
            default -> System.out.println("Opcion invalida");
        }
    }  
}