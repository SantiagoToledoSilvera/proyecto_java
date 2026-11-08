package proyecto_prestamos.dao;

import java.sql.*;
import proyecto_prestamos.conexion.Conexion;
import proyecto_prestamos.modelos.Empleado;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAO {
    public void insertar(Empleado e) {
        String sql = "INSERT INTO empleados (nombre, documento, correo, fecha_nacimiento, rol, salario)"
                + " VALUES (?, ?, ?, ?, ?, ?)";
        
        Connection con = Conexion.getInstancia().getConexion();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, e.getNombre());
            ps.setString(2, e.getDocumento());
            ps.setString(3, e.getCorreo());
            ps.setDate(4, java.sql.Date.valueOf(e.getFNacim()));
            ps.setString(5, e.getRol());
            ps.setDouble(6, e.getSalario());
            ps.executeUpdate();
            System.out.println("Empleado registrado con exito");
        } catch (SQLException x) {
            System.out.println("Error al registrar: " + x.getMessage());
        }
    }
    
    public List<Empleado> listar() {
        List<Empleado> lista = new ArrayList<>();
        String sql = "SELECT * FROM empleados";
        Connection con = Conexion.getInstancia().getConexion();

        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(construirEmpleado(rs));
            }
        } catch (SQLException x) {
            System.out.println("Error al listar: " + x.getMessage());
        }
        return lista;
    }
    
    public Empleado buscarPorId(int id) {
        String sql = "SELECT * FROM empleados WHERE id = ?";
        Connection con = Conexion.getInstancia().getConexion();

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return construirEmpleado(rs);
                }
            }
        } catch (SQLException x) {
            System.out.println("Error al buscar: " + x.getMessage());
        }
        return null; 
    }
    
    public void actualizar(Empleado e) {
        String sql = "UPDATE empleados SET nombre = ?, documento = ?, correo = ?, fecha_nacimiento = ?, rol = ?, salario = ? WHERE id = ?";
        Connection con = Conexion.getInstancia().getConexion();

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, e.getNombre());
            ps.setString(2, e.getDocumento());
            ps.setString(3, e.getCorreo());
            ps.setDate(4, Date.valueOf(e.getFNacim()));
            ps.setString(5, e.getRol());
            ps.setDouble(6, e.getSalario());
            ps.setInt(7, e.getId());

            int filas = ps.executeUpdate();
            if (filas == 0) {
                System.out.println("No existe un empleado con ese id");
            } else {
                System.out.println("Empleado actualizado con éxito");
            }
        } catch (SQLException x) {
            System.out.println("Error al actualizar: " + x.getMessage());
        }
    }
    
    public void eliminar(int id) {
        String sql = "DELETE FROM empleados WHERE id = ?";
        Connection con = Conexion.getInstancia().getConexion();

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);

            int filas = ps.executeUpdate();
            if (filas == 0) {
                System.out.println("No existe un empleado con ese id");
            } else {
                System.out.println("Empleado eliminado con éxito");
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar este empleado: " + e.getMessage());
        }
    }
    
    private Empleado construirEmpleado(ResultSet rs) throws SQLException {
        Date fecha = rs.getDate("fecha_nacimiento");
        String fNacim = (fecha != null) ? fecha.toString() : "";

        return new Empleado(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("documento"),
                rs.getString("correo"),
                fNacim,
                rs.getString("rol"),
                rs.getDouble("salario")
        );
    }   
}