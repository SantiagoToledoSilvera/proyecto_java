package proyecto_prestamos.menus;

import java.util.Scanner;

public class Entrada {

    private final Scanner sc;

    public Entrada(Scanner sc) {
        this.sc = sc;
    }

    public String texto(String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine().trim();
    }

    public int entero(String mensaje) {
        while (true) {
            try {
                return Integer.parseInt(texto(mensaje));
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un numero entero valido");
            }
        }
    }

    public double decimal(String mensaje) {
        while (true) {
            try {
                return Double.parseDouble(texto(mensaje));
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un numero valido");
            }
        }
    }
    
    public String fecha(String mensaje) {
    while (true) {
        String f = texto(mensaje);
        try {
            java.time.LocalDate.parse(f); 
            return f;
        } catch (java.time.format.DateTimeParseException e) {
            System.out.println("Formato invalido. Use aaaa-mm-dd (ej: 1990-05-10)");
        }
    }
}
    
}