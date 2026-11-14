package proyecto_prestamos.servicio;

import java.time.LocalDate;
import java.util.List;
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
        if (interes < 0) {
            throw new NegocioException("Los intereses no pueden ser negativos");
        }

        Prestamo p = new Prestamo(0, clienteId, 0, monto, interes, cuotas,
                LocalDate.now(), EstadoPrestamos.SOLICITADO, 0);
        prestamoDAO.insertar(p);
    }

    public void aprobar(int prestamoId) {
        exigirAdmin();

        Prestamo p = prestamoDAO.buscarPorId(prestamoId);
        if (p == null) {
            throw new NegocioException("El prestamo no existe");
        }
        if (p.getEstado() != EstadoPrestamos.SOLICITADO) {
            throw new NegocioException("Las solicitudes solo se pueden aprobar");
        }

        double total = calculo.calcularMontoTotal(p.getMonto(), p.getInteres(), p.getCuotas());

        p.setSaldoPendiente(total);
        p.setEstado(EstadoPrestamos.ACTIVO);
        p.setEmpleadoId(SesionActual.getInstancia().getPersona().getId());
        p.setFechaInicio(LocalDate.now());
        prestamoDAO.actualizar(p);
    }
    
    public void rechazar(int prestamoId) {
        exigirAdmin();

        Prestamo p = prestamoDAO.buscarPorId(prestamoId);
        if (p == null) {
            throw new NegocioException("El prestamo no existe");
        }
        if (p.getEstado() != EstadoPrestamos.SOLICITADO) {
            throw new NegocioException("Las solicitudes solo se pueden rechazar");
        }


        p.setEstado(EstadoPrestamos.RECHAZADO);
        p.setEmpleadoId(SesionActual.getInstancia().getPersona().getId());
        prestamoDAO.actualizar(p);
    }
    
        public void registrarPago(int prestamoId, double monto) {
        exigirSesion();
        SesionActual s = SesionActual.getInstancia();

        Prestamo p = prestamoDAO.buscarPorId(prestamoId);
        if (p == null) {
            throw new NegocioException("El prestamo no existe");
        }
        if (!s.esAdmin() && p.getClienteId() != s.getPersona().getId()) {
            throw new NegocioException("No se puede pagar prestamos ajenos");
        }
        if (p.getEstado() != EstadoPrestamos.ACTIVO) {
            throw new NegocioException("Solo puede pagar prestamos activos");
        }
        if (monto <= 0) {
            throw new NegocioException("El monto debe ser mayor a 0");
        }
        if (monto > p.getSaldoPendiente()) {
            throw new NegocioException("El pago supera el saldo pendiente");
        }

        double total = calculo.calcularMontoTotal(p.getMonto(), p.getInteres(), p.getCuotas());
        double cuota = calculo.calcularCuota(total, p.getCuotas());
        boolean liquidaDeuda = monto >= p.getSaldoPendiente();
        if (monto < cuota && !liquidaDeuda) {
            throw new NegocioException("El pago minimo es la cuota mensual: " + cuota);
        }

        pagoDAO.insertar(new Pago(0, prestamoId, LocalDate.now(), monto));

        double nuevoSaldo = Math.round((p.getSaldoPendiente() - monto) * 100) / 100.0;
        p.setSaldoPendiente(nuevoSaldo);

        if (nuevoSaldo <= 0) {
            p.setEstado(EstadoPrestamos.PAGADO);
        }

        prestamoDAO.actualizar(p);
        
    }
    
    public java.util.List<Prestamo> filtrarPrestamosActivos() {
        exigirSesion();
        SesionActual s = SesionActual.getInstancia();
        if (s.esAdmin()) {
            return prestamoDAO.listar();
        }
        return prestamoDAO.listarPorCliente(s.getPersona().getId());
    }
    
    public java.util.List<Prestamo> filtrarPrestamosVencidos() {
        exigirSesion();
        SesionActual s = SesionActual.getInstancia();
        if (s.esAdmin()) {
            return prestamoDAO.listar();
        }
        return prestamoDAO.listarPorCliente(s.getPersona().getId());
    }

    public java.util.List<Pago> historialPagos(int prestamoId) {
        exigirSesion();
        Prestamo p = prestamoDAO.buscarPorId(prestamoId);
        if (p == null) {
            throw new NegocioException("El prestamo no existe");
        }
        SesionActual s = SesionActual.getInstancia();
        if (!s.esAdmin() && p.getClienteId() != s.getPersona().getId()) {
            throw new NegocioException("No puede ver pagos de prestamos ajenos");
        }
        return pagoDAO.listarPorPrestamo(prestamoId);
    }
    
    public List<Pago> historialGeneral() {
        exigirAdmin();
        return pagoDAO.listar();
    }
    
}
