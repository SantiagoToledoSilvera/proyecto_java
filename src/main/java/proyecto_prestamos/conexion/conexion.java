package proyecto_prestamos.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexion {
    
    private static final String URL = "jdbc:mysql://localhost:3306/crediya_db";
    
    private static final String USER = "root";
    private static final String PASSWORD = "Voltito";
    public static Connection conectar() {
    
        Connection conexion = null;
        try {
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexion exitosa.");
        } catch (SQLException e) {
            System.out.println("Error de conexion: " + e.getMessage());
        }
        return conexion;
    }
}
