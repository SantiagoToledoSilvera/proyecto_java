package proyecto_prestamos;

/** borrar al terminar **/

import proyecto_prestamos.conexion.conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class consulta {
    public static void main(String[] args) {
        String sql = "SELECT * FROM prestamos";
        
        try (Connection con = conexion.conectar();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        System.out.println(rs.getInt("id") + " - " + rs.getString("monto") + " - " + rs.getString("interes"));
                    }
        } catch (SQLException e) {
        e.printStackTrace();
        }
    }
}