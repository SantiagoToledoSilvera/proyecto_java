package proyecto_prestamos.dao;

import java.sql.*;
import proyecto_prestamos.conexion.Conexion;
import proyecto_prestamos.modelos.Cliente;
import java.util.ArrayList;
import java.util.List;

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
    
    public List<Cliente> listar() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM clientes";
        Connection con = Conexion.getInstancia().getConexion();

        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(construirCliente(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar: " + e.getMessage());
        }
        return lista;
    }
    
    public Cliente buscarPorId(int id) {
        String sql = "SELECT * FROM clientes WHERE id = ?";
        Connection con = Conexion.getInstancia().getConexion();

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return construirCliente(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar: " + e.getMessage());
        }
        return null; 
    }
    
     public void actualizar(Cliente c) {
        String sql = "UPDATE clientes SET nombre = ?, documento = ?, correo = ?, fecha_nacimiento = ?, telefono = ? WHERE id = ?";
        Connection con = Conexion.getInstancia().getConexion();

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getDocumento());
            ps.setString(3, c.getCorreo());
            ps.setDate(4, Date.valueOf(c.getFNacim()));
            ps.setString(5, c.getTelefono());
            ps.setInt(6, c.getId());

            int filas = ps.executeUpdate();
            if (filas == 0) {
                System.out.println("No existe un cliente con ese id");
            } else {
                System.out.println("Cliente actualizado con exito");
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar: " + e.getMessage());
        }
    }
    
    public void eliminar(int id) {
        String sql = "DELETE FROM clientes WHERE id = ?";
        Connection con = Conexion.getInstancia().getConexion();

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);

            int filas = ps.executeUpdate();
            if (filas == 0) {
                System.out.println("No existe un cliente con ese id");
            } else {
                System.out.println("Cliente eliminado con exito");
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar (¿tiene prestamos asociados?): " + e.getMessage());
        }
    }
     
    private Cliente construirCliente(ResultSet rs) throws SQLException {
        Date fecha = rs.getDate("fecha_nacimiento");
        String fNacim = (fecha != null) ? fecha.toString() : "";

        return new Cliente(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("documento"),
                rs.getString("correo"),
                fNacim,
                rs.getString("telefono")
        );
    }   
        
    public Cliente buscarPorNombreYDocumento(String nombre, String documento) {
        String sql = "SELECT * FROM clientes WHERE nombre = ? AND documento = ?";
        Connection con = Conexion.getInstancia().getConexion();

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, documento);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return construirCliente(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error en la busqueda: " + e.getMessage());
        }
        return null;
    } 
}
