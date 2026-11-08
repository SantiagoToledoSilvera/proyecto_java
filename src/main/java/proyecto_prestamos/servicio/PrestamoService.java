package proyecto_prestamos.servicio;

import java.time.LocalDate;
import proyecto_prestamos.dao.PagoDAO;
import proyecto_prestamos.dao.PrestamoDAO;
import proyecto_prestamos.modelos.EstadoPrestamos;
import proyecto_prestamos.modelos.Pago;
import proyecto_prestamos.modelos.Prestamo;

public class PrestamoService {

    private final PrestamoDAO prestamoDAO = new PrestamoDAO();
    private final PagoDAO pagoDAO = new PagoDAO();
    private final CalculoInteres calculo;

    public PrestamoService(CalculoInteres calculo) {
        this.calculo = calculo;
    }

    private void exigirSesion() {
        if (!SesionActual.getInstancia().haySesion()) {
            throw new NegocioException("Debe iniciar sesion");
        }
    }

    private void exigirAdmin() {
        exigirSesion();
        if (!SesionActual.getInstancia().esAdmin()) {
            throw new NegocioException("Accion solo para administradores");
        }
    }

    public void solicitar(int clienteId, double monto, double interes, int cuotas) {
        exigirSesion();
        SesionActual s = SesionActual.getInstancia();

        if (!s.esAdmin() && s.getPersona().getId() != clienteId) {
            throw new NegocioException("Solo puede solicitar prestamos a su nombre");
        }
        if (monto <= 0) {
            throw new NegocioException("El monto debe ser mayor a 0");
        }
        if (cuotas <= 0) {
            throw new NegocioException("Las cuotas deben ser mayores a 0");
        }
        if (interes <= 0) {
            throw new NegocioException("Los intereses no pueden ser negativos");
        }

        Prestamo p = new Prestamo(0, clienteId, 0, monto, interes, cuotas,
                LocalDate.now(), EstadoPrestamos.SOLICITADO, 0);
        prestamoDAO.insertar(p);
    }
}