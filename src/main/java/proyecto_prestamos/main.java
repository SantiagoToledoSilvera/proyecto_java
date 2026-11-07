package proyecto_prestamos;

import java.sql.Connection;
import java.util.Scanner;
import proyecto_prestamos.modelos.Cliente;
import proyecto_prestamos.modelos.Empleado;
import proyecto_prestamos.conexion.Conexion;
import proyecto_prestamos.dao.ClienteDAO;
import java.util.List;

public class main {
    public static void main(String[] args) {
        
        
        /** Comando de prueba consulta - Borrar al terminar programa **/
        ClienteDAO dao = new ClienteDAO();
        
        Cliente c = dao.buscarPorId(1);
        System.out.println(c);
        
        c.setTelefono("3109998888");
        dao.actualizar(c);
        System.out.println(dao.buscarPorId(1));
        
        dao.eliminar(2);
        System.out.println(dao.listar());
        
         
        /** ___________________________ **/
        
        Scanner sc = new Scanner(System.in);
        String op; 
        
        do {
            System.out.println("""
                                _________________ Bienvenido a CrediYa _________________ 
                               - Como desea ingresar? 
                               1. Usuario.
                               2. Administrador.
                               0. Cerrar el programa.
                               """);
            op = sc.nextLine();

            switch (op) {
                case "1" -> {}
                case "2" -> {}
                case "0" -> System.out.println("Gracias por ser cliente crediYa. Vuelve pronto.\n");
                default -> System.out.println("La opcion elegida es invalidad. Intente nuevamente.\n");
            }            
        } while (!op.equals("0"));
    
        sc.close();
        
    }
}