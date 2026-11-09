package proyecto_prestamos.menus;

import proyecto_prestamos.servicio.PrestamoService;
import proyecto_prestamos.servicio.RespaldoService;

public class MenuArchivos extends MenuBase {

    private final RespaldoService respaldo = new RespaldoService();

    public MenuArchivos(Entrada in, PrestamoService servicio) {
        super(in, servicio);
    }

    @Override
    protected String titulo() { return "RESPALDO EN ARCHIVOS"; }

    @Override
    protected String opciones() {
        return """
               1. Exportar todo (clientes, empleados, prestamos, pagos)
               2. Cargar todo desde archivos
               3. Exportar clientes
               4. Exportar empleados
               5. Exportar prestamos
               6. Exportar pagos
               7. Cargar clientes
               8. Cargar empleados
               9. Cargar prestamos
               10. Cargar pagos
               0. Volver
               """;
    }

    @Override
    protected void ejecutar(String op) {
        switch (op) {
            case "1" -> System.out.println("Exportado -> " + respaldo.exportarTodo());
            case "2" -> System.out.println("Cargado -> " + respaldo.cargarTodo());
            case "3" -> System.out.println("Clientes exportados: " + respaldo.exportarClientes());
            case "4" -> System.out.println("Empleados exportados: " + respaldo.exportarEmpleados());
            case "5" -> System.out.println("Prestamos exportados: " + respaldo.exportarPrestamos());
            case "6" -> System.out.println("Pagos exportados: " + respaldo.exportarPagos());
            case "7" -> System.out.println("Clientes cargados: " + respaldo.cargarClientes());
            case "8" -> System.out.println("Empleados cargados: " + respaldo.cargarEmpleados());
            case "9" -> System.out.println("Prestamos cargados: " + respaldo.cargarPrestamos());
            case "10" -> System.out.println("Pagos cargados: " + respaldo.cargarPagos());
            default -> System.out.println("Opcion invalida");
        }
    }
}