package proyecto_prestamos;

import java.util.List;
import java.util.Scanner;
import proyecto_prestamos.dao.PrestamoDAO;
import proyecto_prestamos.servicio.AuthService;
import proyecto_prestamos.servicio.InteresCompuesto;
import proyecto_prestamos.servicio.PrestamoService;
import proyecto_prestamos.servicio.SesionActual;
import proyecto_prestamos.menus.*;
import proyecto_prestamos.modelos.Prestamo;
import proyecto_prestamos.servicio.ReporteService;


public class main {
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        AuthService auth = new AuthService();
        String op; 
        Entrada in = new Entrada(sc);
        PrestamoService servicio = new PrestamoService(new InteresCompuesto());
        
        /** _________________ **/
         
         
         /** __________________ **/
        
        do {
            System.out.println("""
                                _________________ Bienvenido a CrediYa _________________ 
                               - Como desea ingresar? 
                               1. Iniciar sesion.
                               0. Cerrar el programa.
                               """);
            op = sc.nextLine();
            
            switch (op) {
                case "1" -> {
                    System.out.print("Nombre: ");
                    String nombre = sc.nextLine();
                    System.out.print("Documento: ");
                    String documento = sc.nextLine();

                    if (auth.login(nombre, documento)) {
                        MenuBase menu = SesionActual.getInstancia().esAdmin()
                                ? new MenuAdmin(in, servicio)
                                : new MenuCliente(in, servicio);
                        menu.mostrar();
                        auth.logout();
                    } else {
                        System.out.println("Credenciales invalidas");
                    }
                }
                case "0" -> System.out.println("Gracias por ser cliente CrediYa. Vuelve pronto.");
                default -> System.out.println("La opcion elegida es invalida. Intente nuevamente.");
            }
        } while (!op.equals("0"));
        sc.close();
    }
}