package proyecto_prestamos.servicio;

import proyecto_prestamos.modelos.Persona;
import proyecto_prestamos.modelos.Rol;

public class SesionActual {
    private static SesionActual instancia;
    private Persona persona;
    private Rol rol;

    private SesionActual() {}

    public static SesionActual getInstancia() {
        if (instancia == null) {
            instancia = new SesionActual();
        }
        return instancia;
    }

    public void iniciar(Persona persona, Rol rol) {
        this.persona = persona;
        this.rol = rol;
    }
    
    public void cerrar() { 
        this.persona = null;
        this.rol = null;
    }
    
    public boolean esAdmin() {
        return rol == Rol.ADMIN;
    }
    
    public boolean haySesion() {
    return persona != null;
    }

    public Rol getRol() {
        return rol;
    }
    
    public Persona getPersona() { 
        return persona; }
}