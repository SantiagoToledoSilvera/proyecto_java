package proyecto_prestamos.servicio;

public interface CalculoInteres {
    double calcularMontoTotal(double capital, double tasaMensual, int cuotas);
    double calcularCuota(double montoTotal, int cuotas);
}
