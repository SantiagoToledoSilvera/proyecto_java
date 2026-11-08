package proyecto_prestamos.servicio;

import proyecto_prestamos.dao.ClienteDAO;
import proyecto_prestamos.dao.EmpleadoDAO;
import proyecto_prestamos.modelos.Cliente;
import proyecto_prestamos.modelos.Empleado;
import proyecto_prestamos.modelos.Rol;

public class AuthService {

    private final EmpleadoDAO empleadoDAO = new EmpleadoDAO();
    private final ClienteDAO clienteDAO = new ClienteDAO();

    public boolean login(String nombre, String documento) {
        Empleado e = empleadoDAO.buscarPorNombreYDocumento(nombre, documento);
        if (e != null) {
            SesionActual.getInstancia().iniciar(e, Rol.ADMIN);
            return true;
        }

        Cliente c = clienteDAO.buscarPorNombreYDocumento(nombre, documento);
        if (c != null) {
            SesionActual.getInstancia().iniciar(c, Rol.CLIENTE);
            return true;
        }
        return false;
    }

    public void logout() {
        SesionActual.getInstancia().cerrar();
    }
}