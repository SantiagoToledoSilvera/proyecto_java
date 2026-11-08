package proyecto_prestamos.dao;

import java.sql.*;
import proyecto_prestamos.conexion.Conexion;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import proyecto_prestamos.modelos.Prestamo;
import proyecto_prestamos.modelos.EstadoPrestamos;

public class PrestamoDAO {

    public void insertar(Prestamo p) {
        String sql = "INSERT INTO prestamos (cliente_id, empleado_id, monto, interes, cuotas, fecha_inicio, estado, saldo_pendiente)" 
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        Connection con = Conexion.getInstancia().getConexion();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, p.getClienteId());
            if (p.getEmpleadoId() == 0) {
                ps.setNull(2, Types.INTEGER);
            } else {
                ps.setInt(2, p.getEmpleadoId());
            }
            ps.setDouble(3, p.getMonto());
            ps.setDouble(4, p.getInteres());
            ps.setInt(5, p.getCuotas());
            ps.setDate(6, Date.valueOf(p.getFechaInicio()));
            ps.setString(7, p.getEstado().name());
            ps.setDouble(8, p.getSaldoPendiente());
            ps.executeUpdate();
            System.out.println("Prestamo registrado con exito");
        } catch (SQLException x) {
            System.out.println("Error al crear el prestamo: " + x.getMessage());
        }
    }
    
    public List<Prestamo> listar() {
        List<Prestamo> lista = new ArrayList<>();
        String sql = "SELECT * FROM prestamos";
        Connection con = Conexion.getInstancia().getConexion();

        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(construirPrestamo(rs));
            }
        } catch (SQLException x) {
            System.out.println("Error al mostrar prestamos: " + x.getMessage());
        }
        return lista;
    }
    
    public Prestamo buscarPorId(int id) {
        String sql = "SELECT * FROM prestamos WHERE id = ?";
        Connection con = Conexion.getInstancia().getConexion();

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return construirPrestamo(rs);
                }
            }
        } catch (SQLException x) {
            System.out.println("Error al buscar prestamo: " + x.getMessage());
        }
        return null; 
    }
    
     public void actualizar(Prestamo p) {
        String sql = "UPDATE prestamos SET cliente_id = ?, empleado_id = ?, monto = ?, interes = ?, "
                + "cuotas = ?, fecha_inicio = ?, estado = ?, saldo_pendiente = ? WHERE id = ?";
        Connection con = Conexion.getInstancia().getConexion();

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, p.getClienteId());
            if (p.getEmpleadoId() == 0) {
                ps.setNull(2, Types.INTEGER);
            } else {
                ps.setInt(2, p.getEmpleadoId());
            }
            ps.setDouble(3, p.getMonto());
            ps.setDouble(4, p.getInteres());
            ps.setInt(5, p.getCuotas());
            ps.setDate(6, Date.valueOf(p.getFechaInicio()));
            ps.setString(7, p.getEstado().name());
            ps.setDouble(8, p.getSaldoPendiente());
            ps.setInt(9, p.getId());

            int filas = ps.executeUpdate();
            if (filas == 0) {
                System.out.println("No existe un préstamo con ese id");
            } else {
                System.out.println("Préstamo actualizado con éxito");
            }
        } catch (SQLException x) {
            System.out.println("Error al actualizar: " + x.getMessage());
        }
    }
    
    public void eliminar(int id) {
        String sql = "DELETE FROM prestamos WHERE id = ?";
        Connection con = Conexion.getInstancia().getConexion();

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);

            int filas = ps.executeUpdate();
            if (filas == 0) {
                System.out.println("No existe un prestamo con ese id");
            } else {
                System.out.println("Prestamo eliminado con exito");
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar este prestamo: " + e.getMessage());
        }
    }
    
    private Prestamo construirPrestamo(ResultSet rs) throws SQLException {
        LocalDate fecha = rs.getDate("fecha_inicio").toLocalDate();
        EstadoPrestamos estado = EstadoPrestamos.valueOf(rs.getString("estado"));

        return new Prestamo(
            rs.getInt("id"),
            rs.getInt("cliente_id"),
            rs.getInt("empleado_id"),
            rs.getDouble("monto"),
            rs.getDouble("interes"),
            rs.getInt("cuotas"),
            fecha,
            estado,
            rs.getDouble("saldo_pendiente")
        );
    }
    
    public List<Prestamo> listarPorCliente(int clienteId) {
    List<Prestamo> lista = new ArrayList<>();
    String sql = "SELECT * FROM prestamos WHERE cliente_id = ?";
    Connection con = Conexion.getInstancia().getConexion();

    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, clienteId);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(construirPrestamo(rs));
            }
        }
    } catch (SQLException x) {
        System.out.println("Error al listar prestamos del cliente: " + x.getMessage());
    }
    return lista;
}
    
}