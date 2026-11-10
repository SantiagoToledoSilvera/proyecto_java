package proyecto_prestamos.menus;

import java.util.List;
import proyecto_prestamos.dao.ClienteDAO;
import proyecto_prestamos.modelos.Cliente;

public class GestionCliente extends MenuBase {

    private final ClienteDAO dao = new ClienteDAO();

    public GestionCliente(Entrada in) {
        super(in);
    }

    @Override
    protected String titulo() { return "GESTION DE CLIENTES"; }

    @Override
    protected String opciones() {
        return """
               1. Registrar cliente
               2. Listar clientes
               3. Actualizar cliente
               4. Eliminar cliente
               0. Volver
               """;
    }

    @Override
    protected void ejecutar(String op) {
        switch (op) {
            case "1" -> registrar();
            case "2" -> listar();
            case "3" -> actualizar();
            case "4" -> eliminar();
            default -> System.out.println("Opcion invalida");
        }
    }

    private void registrar() {
        String nombre = in.texto("Nombre: ");
        String documento = in.texto("Documento: ");
        String correo = in.texto("Correo: ");
        String fNacim = in.fecha("Fecha de nacimiento (aaaa-mm-dd): ");
        String telefono = in.texto("Telefono: ");
        dao.insertar(new Cliente(0, nombre, documento, correo, fNacim, telefono));
    }

    private void listar() {
        List<Cliente> lista = dao.listar();
        if (lista.isEmpty()) {
            System.out.println("No hay clientes");
            return;
        }
       System.out.println("\n--- LISTADO DE CLIENTES ---");
        for (Cliente c : lista) {
            System.out.println("ID: " + c.getId() 
                + " | Nombre: " + c.getNombre() 
                + " | Documento: " + c.getDocumento() 
                + " | Correo: " + c.getCorreo() 
                + " | Tel: " + c.getTelefono());
        }
        System.out.println("---------------------------\n");
    }

    private void actualizar() {
        int id = in.entero("Id del cliente: ");
        Cliente c = dao.buscarPorId(id);
        if (c == null) {
            System.out.println("No existe un cliente con ese id");
            return;
        }
        String nombre = in.texto("Nuevo nombre(si no ingresa valor se mantendra el original): ");
        if (!nombre.isEmpty()) c.setNombre(nombre);
        String correo = in.texto("Correo del cliente nuevo: ");
        if (!correo.isEmpty()) c.setCorreo(correo);
        String telefono = in.texto("Telefono del cliente nuevo: ");
        if (!telefono.isEmpty()) c.setTelefono(telefono);

        dao.actualizar(c);
    }

    private void eliminar() {
        int id = in.entero("Id del cliente a eliminar: ");
        dao.eliminar(id);
    }
}