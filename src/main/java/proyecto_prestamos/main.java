package proyecto_prestamos;

import java.util.Scanner;
import proyecto_prestamos.modelos.Cliente;
import proyecto_prestamos.modelos.Empleado;

public class main {
    public static void main(String[] args) {
        
        
        /** Comando de prueba consulta **/
        Cliente c = new Cliente(1, "Juan Carlos Bodoque", "123456", "jucabo@gmail.com", "01/11/2007", "3665485641");
        System.out.println(c);
        
        Empleado e = new Empleado(17, "Karla Vargas", "987654", "karlauwu@gmail.com", "23/05/1999", "Gerente", 2500);
        System.out.println(e);
         
        
        
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