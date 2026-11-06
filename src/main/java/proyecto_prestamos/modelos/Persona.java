package proyecto_prestamos.modelos;

public abstract class Persona {
    
    private int id;
    private String nombre;
    private String documento;
    private String correo;
    private String fNacim;

    public Persona(int id, String nombre, String documento, String correo, String fNacim) {
        this.id = id;
        this.nombre = nombre;
        this.documento = documento;
        this.correo = correo;
        this.fNacim = fNacim;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getFNacim() {
        return fNacim;
    }

    public void setFNacim(String fNacim) {
        this.fNacim = fNacim;
    }

    @Override
    public String toString() {
        return "Personas{" + "id=" + id + ", nombre=" + nombre + ", documento=" + documento + ", correo=" + correo + ", FNacim=" + fNacim + '}';
    }    
}
