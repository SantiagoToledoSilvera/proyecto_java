package proyecto_prestamos.servicio;

public class InteresCompuesto implements CalculoInteres {
    
    @Override
    public double calcularMontoTotal(double capital, double tasaMensual, int cuotas) {
        double tasaDec = tasaMensual / 100;
        double factor = 1 + tasaDec;
        double montoTotal = capital * Math.pow(factor, cuotas);
        return Math.round(montoTotal * 100) / 100.0;
    }
    
    @Override
    public double calcularCuota(double montoTotal, int cuotas) {
        return Math.round((montoTotal / cuotas) * 100) / 100.0;
    }
}
