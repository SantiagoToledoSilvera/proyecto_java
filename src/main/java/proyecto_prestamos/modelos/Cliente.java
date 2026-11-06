package proyecto_prestamos.modelos;

public class Cliente extends Persona {
    
   private String telefono;
   
   public Cliente(int id, String nombre, String documento, String correo, String fNacim, String telefono) {
       super(id, nombre, documento, correo, fNacim);
       this.telefono = telefono;
   }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return super.toString() + ", telefono=" + telefono; 
    }
}
