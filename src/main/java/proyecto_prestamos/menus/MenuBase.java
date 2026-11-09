package proyecto_prestamos.menus;

import proyecto_prestamos.modelos.Prestamo;
import proyecto_prestamos.servicio.NegocioException;
import proyecto_prestamos.servicio.PrestamoService;
import java.util.List;
import proyecto_prestamos.modelos.Pago;

public abstract class MenuBase {

    protected final Entrada in;
    protected final PrestamoService servicio;

    protected MenuBase(Entrada in, PrestamoService servicio) {
        this.in = in;
        this.servicio = servicio;
    }

    protected abstract String titulo();
    protected abstract String opciones();
    protected abstract void ejecutar(String op);   

    public final void mostrar() {
        String op;
        do {
            System.out.println("===== " + titulo() + " =====");
            System.out.println(opciones());
            op = in.texto("Opcion: ");

            try {
                if (!op.equals("0")) {
                    ejecutar(op);
                }
            } catch (NegocioException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (!op.equals("0"));
        System.out.println("Volviendo...");
    }

    protected void pagar() {
        int id = in.entero("Id del prestamo: ");
        double monto = in.decimal("Monto a pagar: ");
        servicio.registrarPago(id, monto);
        System.out.println("Pago registrado");
    }

    protected void verPrestamos() {
        List<Prestamo> lista = servicio.listarMisPrestamos();
        if (lista.isEmpty()) {
            System.out.println("No hay prestamos");
            return;
        }
        for (Prestamo p : lista) {
            System.out.println(p);
        }
    }
    
    protected void verHistorial() {
        int id = in.entero("Id del prestamo: ");
        List<Pago> lista = servicio.historialPagos(id);
        if (lista.isEmpty()) {
            System.out.println("No hay pagos registrados");
            return;
        }
        for (Pago p : lista) {
            System.out.println(p);
        }
    }   
    protected MenuBase(Entrada in) {
        this(in, null);
    }  
}