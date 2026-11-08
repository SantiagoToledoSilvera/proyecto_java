package proyecto_prestamos.dao;

import java.sql.*;
import proyecto_prestamos.conexion.Conexion;
import java.util.ArrayList;
import java.util.List;
import proyecto_prestamos.modelos.Pago;

public class PagoDAO {
    
    public void insertar(Pago p) {
        String sql = "INSERT INTO pagos (prestamo_id, fecha_pago, monto)"
                + " VALUES (?, ?, ?)";
        
        Connection con = Conexion.getInstancia().getConexion();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, p.getPrestamoId());
            ps.setDate(2, Date.valueOf(p.getFechaPago()));
            ps.setDouble(3, p.getMonto());

            ps.executeUpdate();
            System.out.println("Pago guardado exitosamente");
        } catch (SQLException e) {
            System.out.println("Error al completar el pago: " + e.getMessage());
        }
    }
    
    public List<Pago> listar() {
        List<Pago> lista = new ArrayList<>();
        String sql = "SELECT * FROM pagos ORDER BY fecha_pago";
        Connection con = Conexion.getInstancia().getConexion();

        try (PreparedStatement ps = con.prepareStatement(sql)) {       
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(construirPago(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al listar pagos: " + e.getMessage());
        }
        return lista;
    }
    
    public List<Pago> listarPorPrestamo(int prestamoId) {
        List<Pago> lista = new ArrayList<>();
        String sql = "SELECT * FROM pagos WHERE prestamo_id = ? ORDER BY fecha_pago";
        Connection con = Conexion.getInstancia().getConexion();

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, prestamoId);   
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(construirPago(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al listar pagos: " + e.getMessage());
        }
        return lista;
    }
    
    private Pago construirPago(ResultSet rs) throws SQLException {

        return new Pago(
                rs.getInt("id"),
                rs.getInt("prestamo_id"),
                rs.getDate("fecha_pago").toLocalDate(),
                rs.getDouble("monto")
        );
    } 
}