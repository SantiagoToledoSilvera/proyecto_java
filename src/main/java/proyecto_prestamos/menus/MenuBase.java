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
            System.out.println("\n===== " + titulo() + " =====");
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
        System.out.println("\n--- LISTADO DE PRESTAMOS ---");
        for (Prestamo p : lista) {
            System.out.println("ID: " + p.getId() 
                + " | Cliente: " + p.getClienteId() 
                + " | Monto: $" + p.getMonto() 
                + " | Cuotas: " + p.getCuotas() 
                + " | Estado: " + p.getEstado()
                + " | Saldo: $" + p.getSaldoPendiente());
        }
        System.out.println("----------------------------\n");
    }
    
    protected void verHistorial() {
        int id = in.entero("Id del prestamo: ");
        List<Pago> lista = servicio.historialPagos(id);
        if (lista.isEmpty()) {
            System.out.println("No hay pagos registrados");
            return;
        }
        System.out.println("\n--- HISTORIAL DE PAGOS ---");
        for (Pago p : lista) {
            System.out.println("ID Pago: " + p.getId() 
                + " | Prestamo ID: " + p.getPrestamoId() 
                + " | Fecha: " + p.getFechaPago() 
                + " | Monto: $" + p.getMonto());
        }
        System.out.println("--------------------------\n");
    }   
    protected MenuBase(Entrada in) {
        this(in, null);
    }  
}