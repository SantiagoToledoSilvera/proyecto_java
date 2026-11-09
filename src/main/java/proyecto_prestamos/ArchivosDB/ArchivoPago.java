package proyecto_prestamos.ArchivosDB;

import java.time.LocalDate;
import proyecto_prestamos.modelos.Pago;

public class ArchivoPago extends ArchivoCsv<Pago> {
    public ArchivoPago() { super("pagos.csv"); }

    @Override protected String encabezado() {
        return "id;prestamo_id;fecha_pago;monto";
    }
    @Override protected String aLinea(Pago p) {
        return p.getId() + ";" + p.getPrestamoId() + ";" + p.getFechaPago() + ";" + p.getMonto();
    }
    @Override protected Pago deLinea(String[] c) {
        return new Pago(Integer.parseInt(c[0]), Integer.parseInt(c[1]),
                LocalDate.parse(c[2]), Double.parseDouble(c[3]));
    }
}