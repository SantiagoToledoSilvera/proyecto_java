package proyecto_prestamos.menus;

import proyecto_prestamos.servicio.PrestamoService;
import proyecto_prestamos.servicio.SesionActual;

public class MenuCliente extends MenuBase {

    public MenuCliente(Entrada in, PrestamoService servicio) {
        super(in, servicio);
    }

    @Override
    protected String titulo() { return "MENU CLIENTE"; }

    @Override
    protected String opciones() {
        return """
               1. Solicitar prestamo
               2. Pagar prestamo
               3. Ver mis prestamos
               4. Ver historial de pagos
               0. Cerrar sesion
               """;
    }

    @Override
    protected void ejecutar(String op) {
        switch (op) {
            case "1" -> solicitar();
            case "2" -> pagar();          
            case "3" -> verPrestamos();     
            case "4" -> verHistorial();
            default -> System.out.println("Opcion invalida");
        }
    }
    

    private void solicitar() {
        int miId = SesionActual.getInstancia().getPersona().getId();
        double monto = in.decimal("Monto: ");
        double interes = in.decimal("Interes mensual (%): ");
        int cuotas = in.entero("Numero de cuotas (meses): ");
        servicio.solicitar(miId, monto, interes, cuotas);
    }
}