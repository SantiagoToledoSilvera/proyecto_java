package proyecto_prestamos;

import java.util.Scanner;
import proyecto_prestamos.servicio.AuthService;
import proyecto_prestamos.servicio.InteresCompuesto;
import proyecto_prestamos.servicio.PrestamoService;
import proyecto_prestamos.servicio.SesionActual;
import proyecto_prestamos.servicio.NegocioException;


public class main {
    public static void main(String[] args) {
        
        
        /** Comando de prueba - Borrar al terminar **/
        AuthService authTest = new AuthService();
        PrestamoService ps = new PrestamoService(new InteresCompuesto());

        try {
            authTest.login("Admin Principal", "1000");
            ps.solicitar(1, 500000, 2, 6);     // debe registrar con éxito
            ps.solicitar(1, -5, 2, 6);         // debe lanzar el error del monto
        } catch (NegocioException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            authTest.logout();
        }
        /** ___________________________ **/
        
        Scanner sc = new Scanner(System.in);
        AuthService auth = new AuthService();
        String op; 
        
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
                        if (SesionActual.getInstancia().esAdmin()) {
                            System.out.println("Menu administrador (pendiente)");
                        } else {
                            System.out.println("Menu cliente (pendiente)");
                        }
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