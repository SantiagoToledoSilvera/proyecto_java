package proyecto_prestamos.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static Conexion instancia;
    private Connection con;

    private static final String URL = "jdbc:mysql://localhost:3306/crediya_db";
    private static final String USER = "root";
    private static final String PASSWORD = "Voltito";

    private Conexion() {                    
        try {
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to database crediya");
        } catch (SQLException e) {
            System.out.println("Error de conexion " + e.getMessage());
        }
    }

    public static Conexion getInstancia() {
        if (instancia == null) {
            instancia = new Conexion();
        }
        return instancia;
}

    public Connection getConexion() {
        return con; 
    }
}

