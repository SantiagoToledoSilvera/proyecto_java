package proyecto_prestamos.modelos;

public class Empleado extends Persona {
    
       private String rol;
       private int salario;
   
   public Empleado(int id, String nombre, String documento, String correo, String fNacim, String rol, int salario) {
       super(id, nombre, documento, correo, fNacim);
       this.rol = rol;
       this.salario = salario;
   }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public int getSalario() {
        return salario;
    }

    public void setSalario(int salario) {
        this.salario = salario;
    }

    @Override
    public String toString() {
        return super.toString() + ", rol=" + rol + ", salario=" + salario;
    }
   
}
