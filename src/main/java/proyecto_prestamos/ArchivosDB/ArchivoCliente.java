package proyecto_prestamos.ArchivosDB;

import proyecto_prestamos.modelos.Cliente;

public class ArchivoCliente extends ArchivoCsv<Cliente> {
    public ArchivoCliente() { super("clientes.csv"); }

    @Override protected String encabezado() {
        return "id;nombre;documento;correo;fecha_nacimiento;telefono";
    }
    @Override protected String aLinea(Cliente c) {
        return c.getId() + ";" + c.getNombre() + ";" + c.getDocumento() + ";"
             + c.getCorreo() + ";" + c.getFNacim() + ";" + c.getTelefono();
    }
    @Override protected Cliente deLinea(String[] c) {
        return new Cliente(Integer.parseInt(c[0]), c[1], c[2], c[3], c[4], c[5]);
    }
}