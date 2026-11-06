package proyecto_prestamos.DAO;

import java.sql.*;
import proyecto_prestamos.conexion.Conexion;
import proyecto_prestamos.modelos.Cliente;

public class ClienteDAO {
    public void insertar(Cliente c) {
        String sql = "INSERT INTO clientes (nombre, documento, correo, fecha_nacimiento, telefono)"
                + " VALUES (?, ?, ?, ?, ?)";
        
        Connection con = Conexion.getInstancia().getConexion();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getDocumento());
            ps.setString(3, c.getCorreo());
            ps.setDate(4, java.sql.Date.valueOf(c.getFNacim()));
            ps.setString(5, c.getTelefono());
            ps.executeUpdate();
            System.out.println("Cliente registrado con exito");
        } catch (SQLException e) {
            System.out.println("Error al registrar: " + e.getMessage());
        }
    }
}
