package proyecto_prestamos;

import java.util.Scanner;
import proyecto_prestamos.modelos.Prestamo;
import java.time.LocalDate;
import proyecto_prestamos.dao.PagoDAO;
import proyecto_prestamos.dao.PrestamoDAO;
import proyecto_prestamos.modelos.EstadoPrestamos;
import proyecto_prestamos.modelos.Pago;

public class main {
    public static void main(String[] args) {
        
        
        /** Comando de prueba consulta - Borrar al terminar programa **/
            PagoDAO dao = new PagoDAO();
            dao.insertar(new Pago(0, 22, LocalDate.now(), 105686.82));
            System.out.println(dao.listarPorPrestamo(22));
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