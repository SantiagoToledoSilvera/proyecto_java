package proyecto_prestamos.servicio;

public class NegocioException extends RuntimeException {
   
    public NegocioException(String mensaje) {
        super(mensaje);
    }
}

