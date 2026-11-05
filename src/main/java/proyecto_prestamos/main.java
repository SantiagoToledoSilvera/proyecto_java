package proyecto_prestamos;

import java.util.Scanner;

public class main {
    public static void main(String[] args) {
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