package proyecto_prestamos.ArchivosDB;

import java.time.LocalDate;
import proyecto_prestamos.modelos.EstadoPrestamos;
import proyecto_prestamos.modelos.Prestamo;

public class ArchivoPrestamo extends ArchivoCsv<Prestamo> {
    public ArchivoPrestamo() { super("prestamos.csv"); }

    @Override protected String encabezado() {
        return "id;cliente_id;empleado_id;monto;interes;cuotas;fecha_inicio;estado;saldo_pendiente";
    }
    @Override protected String aLinea(Prestamo p) {
        return p.getId() + ";" + p.getClienteId() + ";" + p.getEmpleadoId() + ";"
             + p.getMonto() + ";" + p.getInteres() + ";" + p.getCuotas() + ";"
             + p.getFechaInicio() + ";" + p.getEstado().name() + ";" + p.getSaldoPendiente();
    }
    @Override protected Prestamo deLinea(String[] c) {
        return new Prestamo(Integer.parseInt(c[0]), Integer.parseInt(c[1]),
                Integer.parseInt(c[2]), Double.parseDouble(c[3]), Double.parseDouble(c[4]),
                Integer.parseInt(c[5]), LocalDate.parse(c[6]),
                EstadoPrestamos.valueOf(c[7]), Double.parseDouble(c[8]));
    }
}