package proyecto_prestamos.menus;

import proyecto_prestamos.servicio.PrestamoService;

public class MenuAdmin extends MenuBase {

    public MenuAdmin(Entrada in, PrestamoService servicio) {
        super(in, servicio);
    }

    @Override
    protected String titulo() { return "MENU ADMINISTRADOR"; }

    @Override
    protected String opciones() {
        return """
               1. Ver todos los prestamos
               2. Aprobar solicitud
               3. Rechazar solicitud
               4. Registrar pago
               5. Ver historial de pagos
               6. Gestionar Clientes
               7. Gestionar Empleado
               8. EXAMEN <----------------------
               9. Archivos (respaldo)
               0. Cerrar sesion
               """;
    }

    @Override
    protected void ejecutar(String op) {
        switch (op) {
            case "1" -> verPrestamos();
            case "2" -> {
                servicio.aprobar(in.entero("Id del prestamo: "));
                System.out.println("Solicitud aprobada");
            }
            case "3" -> {
                servicio.rechazar(in.entero("Id del prestamo: "));
                System.out.println("Solicitud rechazada");
            }
            case "4" -> pagar();
            case "5" -> verHistorial();
            case "6" -> new GestionCliente(in).mostrar();
            case "7" -> new GestionEmpleado(in).mostrar();
            case "8" -> new GestorPrestamos(in, servicio).mostrar();
            case "9" -> new MenuArchivos(in, servicio).mostrar();
            default -> System.out.println("Opcion invalida");
        }
    }  
}