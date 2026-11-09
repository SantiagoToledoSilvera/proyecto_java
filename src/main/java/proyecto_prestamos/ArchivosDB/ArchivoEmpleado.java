package proyecto_prestamos.ArchivosDB;

import proyecto_prestamos.modelos.Empleado;

public class ArchivoEmpleado extends ArchivoCsv<Empleado> {
    public ArchivoEmpleado() { super("empleados.csv"); }

    @Override protected String encabezado() {
        return "id;nombre;documento;correo;fecha_nacimiento;rol;salario";
    }
    @Override protected String aLinea(Empleado e) {
        return e.getId() + ";" + e.getNombre() + ";" + e.getDocumento() + ";"
             + e.getCorreo() + ";" + e.getFNacim() + ";" + e.getRol() + ";" + e.getSalario();
    }
    @Override protected Empleado deLinea(String[] c) {
        return new Empleado(Integer.parseInt(c[0]), c[1], c[2], c[3], c[4], c[5],
                Double.parseDouble(c[6]));
    }
}