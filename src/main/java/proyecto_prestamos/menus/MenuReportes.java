package proyecto_prestamos.menus;

import proyecto_prestamos.servicio.PrestamoService;
import proyecto_prestamos.servicio.ReporteService;

public class MenuReportes extends MenuBase {
    private final ReporteService reportes = new ReporteService();

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
            case "3" -> reportes.vencidos(servicio.listarMisPrestamos())
                    .forEach(p -> System.out.println(p));
            case "4" -> reportes.clientesMorosos(servicio.listarMisPrestamos())
                    .forEach(id -> System.out.println("Cliente id: " + id));
            case "5" -> System.out.println("Cartera pendiente: "
                    + reportes.carteraPendiente(servicio.listarMisPrestamos()));
            default -> System.out.println("Opcion invalida");
        }
    }  
}