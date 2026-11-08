package proyecto_prestamos.modelos;

import java.time.LocalDate;

public class Prestamo {
    
    private int id;
    private int clienteId;
    private int empleadoId;
    private double monto;
    private double interes;
    private int cuotas;
    private LocalDate fechaInicio;
    private EstadoPrestamos estado;
    private double saldoPendiente;  
    
    public Prestamo(int id, int clienteId, int empleadoId, double monto, double interes, int cuotas, LocalDate fechaInicio, EstadoPrestamos estado, double saldoPendiente) {
        this.id = id;
        this.clienteId = clienteId;
        this.empleadoId = empleadoId;
        this.monto = monto;
        this.interes = interes;
        this.cuotas = cuotas;
        this.fechaInicio = fechaInicio;
        this.estado = estado;
        this.saldoPendiente = saldoPendiente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public int getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(int empleadoId) {
        this.empleadoId = empleadoId;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public double getInteres() {
        return interes;
    }

    public void setInteres(double interes) {
        this.interes = interes;
    }

    public int getCuotas() {
        return cuotas;
    }

    public void setCuotas(int cuotas) {
        this.cuotas = cuotas;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public EstadoPrestamos getEstado() {
        return estado;
    }

    public void setEstado(EstadoPrestamos estado) {
        this.estado = estado;
    }

    public double getSaldoPendiente() {
        return saldoPendiente;
    }

    public void setSaldoPendiente(double saldoPendiente) {
        this.saldoPendiente = saldoPendiente;
    }

    @Override
    public String toString() {
        return "Prestamo{" + "id=" + id + ", clienteId=" + clienteId + ", empleadoId=" + empleadoId + ", monto=" + monto + ", interes=" + interes + ", cuotas=" + cuotas + ", fechaInicio=" + fechaInicio + ", estado=" + estado + ", saldoPendiente=" + saldoPendiente + '}';
    }
}