package proyecto_prestamos.menus;

import java.util.List;
import proyecto_prestamos.dao.EmpleadoDAO;
import proyecto_prestamos.modelos.Empleado;
import proyecto_prestamos.servicio.SesionActual;

public class GestionEmpleado extends MenuBase {

    private final EmpleadoDAO dao = new EmpleadoDAO();

    public GestionEmpleado(Entrada in) {
        super(in);
    }

    @Override
    protected String titulo() { return "GESTION DE EMPLEADOS"; }

    @Override
    protected String opciones() {
        return """
               1. Registrar empleado
               2. Listar empleados
               3. Actualizar empleado
               4. Eliminar empleado
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
        String rol = in.texto("Rol: ");
        double salario = in.decimal("Salario: ");
        System.out.println("Este empleado tendra acceso de administrador");
        dao.insertar(new Empleado(0, nombre, documento, correo, fNacim, rol, salario));
    }

    private void listar() {
        List<Empleado> lista = dao.listar();
        if (lista.isEmpty()) {
            System.out.println("No hay empleados");
            return;
        }
        for (Empleado c : lista) {
            System.out.println(c);
        }
    }

    private void actualizar() {
        int id = in.entero("Id del empleado: ");
        Empleado c = dao.buscarPorId(id);
        if (c == null) {
            System.out.println("No existe un empleado con ese id");
            return;
        }
        String nombre = in.texto("Nuevo nombre(si no ingresa valor se mantendra el original): ");
        if (!nombre.isEmpty()) c.setNombre(nombre);
        String correo = in.texto("Correo del cliente nuevo: ");
        if (!correo.isEmpty()) c.setCorreo(correo);
        String rol = in.texto("Rol nuevo del empleado: ");
        if (!rol.isEmpty()) c.setRol(rol);
        String salarioTxt = in.texto("Nuevo salario (Enter = igual): ");
            if (!salarioTxt.isEmpty()) {
                try {
                    c.setSalario(Double.parseDouble(salarioTxt));
                } catch (NumberFormatException e) {
                    System.out.println("Salario invalido, se mantiene el anterior");
                }
            }

        dao.actualizar(c);
    }

    private void eliminar() {
        int id = in.entero("Id del empleado a eliminar: ");
        
        if (id == SesionActual.getInstancia().getPersona().getId()) {
            System.out.println("No puede eliminar su propia cuenta");
            return;
        }
        
        dao.eliminar(id);
    }
}