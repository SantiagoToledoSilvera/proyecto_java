package proyecto_prestamos.menus;

import java.time.LocalDate;
import java.util.List;
import proyecto_prestamos.modelos.Pago;
import proyecto_prestamos.modelos.Prestamo;
import proyecto_prestamos.servicio.InteresCompuesto;
import proyecto_prestamos.servicio.PrestamoService;
import proyecto_prestamos.servicio.ReporteService;

public class GestorPrestamos extends MenuBase {
    private final ReporteService reportes = new ReporteService(new InteresCompuesto());

    public GestorPrestamos(Entrada in, PrestamoService servicio) {
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
               5. Mostrar resumen
               6. Total pagado por prestamo
               7. Pagos de un cliente
               8. Pagos entre fechas
               0. Volver
               """;
    }

    @Override
    protected void ejecutar(String op) {
        switch (op) {
            case "1" -> {
                List<Prestamo> lista = reportes.activos(servicio.filtrarPrestamosActivos());
                if (lista.isEmpty()) {
                    System.out.println("No hay prestamos activos");
                } else {
                    System.out.println("\n--- PRESTAMOS ACTIVOS ---");
                    for (Prestamo p : lista) {
                        System.out.println("ID: " + p.getId() 
                            + " | Cliente: " + p.getClienteId() 
                            + " | Monto: $" + p.getMonto() 
                            + " | Estado: " + p.getEstado()
                            + " | Saldo: $" + p.getSaldoPendiente());
                    }
                    System.out.println("-------------------------\n");
                }
            }
            case "2" -> {
                List<Prestamo> lista = reportes.solicitudesPendientes(servicio.filtrarPrestamosActivos());
                if (lista.isEmpty()) {
                    System.out.println("No hay solicitudes pendientes");
                } else {
                    System.out.println("\n--- SOLICITUDES PENDIENTES ---");
                    for (Prestamo p : lista) {
                        System.out.println("ID: " + p.getId() 
                            + " | Cliente: " + p.getClienteId() 
                            + " | Monto: $" + p.getMonto() 
                            + " | Estado: " + p.getEstado());
                    }
                    System.out.println("------------------------------\n");
                }
            }
            case "3" -> {
                List<Prestamo> vencidos = reportes.vencidos(servicio.filtrarPrestamosVencidos());
                if (vencidos.isEmpty()) {
                    System.out.println("No hay prestamos vencidos");
                } else {
                    System.out.println("\n--- PRESTAMOS VENCIDOS ---");
                    for (Prestamo p : vencidos) {
                        System.out.println("ID: " + p.getId() 
                            + " | Cliente: " + p.getClienteId() 
                            + " | Monto: $" + p.getMonto() 
                            + " | Saldo: $" + p.getSaldoPendiente());
                    }
                    System.out.println("--------------------------\n");
                }
            }
            case "4" -> {
                List<Integer> morosos = reportes.clientesMorosos(servicio.filtrarPrestamosActivos());
                if (morosos.isEmpty()) {
                    System.out.println("No hay clientes morosos");
                } else {
                    System.out.println("\n--- CLIENTES MOROSOS ---");
                    for (Integer id : morosos) {
                        System.out.println("Cliente ID: " + id);
                    }
                    System.out.println("------------------------\n");
                }
            }
            case "5" -> System.out.println(String.format("Cartera pendiente: $%,.2f", 
                    reportes.mostrarResumen(servicio.filtrarPrestamosActivos())));
            case "6" -> {               /** totalPagadoPorPrestamo  **/
                var totales = reportes.totalPagadoPorPrestamo(servicio.historialGeneral());
                if (totales.isEmpty()) {
                    System.out.println("No hay pagos registrados");
                } else {
                    System.out.println("\n--- TOTAL PAGADO POR PRESTAMO ---");
                    totales.forEach((id, total) ->
                            System.out.println("Prestamo ID: " + id + " | Total Pagado: $" + total));
                    System.out.println("-----------------------------------\n");
                }
            }
            case "7" -> {
                int clienteId = in.entero("Id del cliente: ");
                List<Pago> pagos = reportes.pagosDeCliente(
                        clienteId, servicio.filtrarPrestamosActivos(), servicio.historialGeneral());
                if (pagos.isEmpty()) {
                    System.out.println("Ese cliente no tiene pagos");
                } else {
                    System.out.println("\n--- PAGOS DEL CLIENTE ---");
                    for (Pago pg : pagos) {
                        System.out.println("ID Pago: " + pg.getId() 
                            + " | Prestamo ID: " + pg.getPrestamoId() 
                            + " | Fecha: " + pg.getFechaPago() 
                            + " | Monto: $" + pg.getMonto());
                    }
                    System.out.println("-------------------------\n");
                }
            }
            case "8" -> {
                LocalDate desde = LocalDate.parse(in.fecha("Desde (aaaa-mm-dd): "));
                LocalDate hasta = LocalDate.parse(in.fecha("Hasta (aaaa-mm-dd): "));
                List<Pago> pagos = reportes.pagosEntre(desde, hasta, servicio.historialGeneral());
                if (pagos.isEmpty()) {
                    System.out.println("No hay pagos en ese rango");
                } else {
                    System.out.println("\n--- PAGOS ENTRE FECHAS ---");
                    for (Pago pg : pagos) {
                        System.out.println("ID Pago: " + pg.getId() 
                            + " | Prestamo ID: " + pg.getPrestamoId() 
                            + " | Fecha: " + pg.getFechaPago() 
                            + " | Monto: $" + pg.getMonto());
                    }
                    System.out.println("--------------------------\n");
                }
            }
            default -> System.out.println("Opcion invalida");
        }
    }  
}