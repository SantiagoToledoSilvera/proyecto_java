package proyecto_prestamos.servicio;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import proyecto_prestamos.ArchivosDB.*;
import proyecto_prestamos.dao.*;
import proyecto_prestamos.modelos.*;

public class RespaldoService {

    private final EmpleadoDAO empleadoDAO = new EmpleadoDAO();
    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final PrestamoDAO prestamoDAO = new PrestamoDAO();
    private final PagoDAO pagoDAO = new PagoDAO();
    
    private final ArchivoEmpleado archivoEmpleado = new ArchivoEmpleado();
    private final ArchivoCliente archivoCliente = new ArchivoCliente();
    private final ArchivoPrestamo archivoPrestamo = new ArchivoPrestamo();
    private final ArchivoPago archivoPago = new ArchivoPago();

    private void exigirAdmin() {
        if (!SesionActual.getInstancia().esAdmin()) {
            throw new NegocioException("Accion solo para administradores");
        }
    }
    
    private <T> int escribir(ArchivoCsv<T> archivo, List<T> lista) {
        try {
            archivo.exportar(lista);
        } catch (IOException e) {
            throw new NegocioException("No se pudo escribir el archivo: " + e.getMessage());
        }
        return lista.size();
    }

    private <T> List<T> leer(ArchivoCsv<T> archivo) {
        try {
            return archivo.cargar();
        } catch (IOException | RuntimeException e) {
            throw new NegocioException("No se pudo leer el archivo: " + e.getMessage());
        }
    }

    public int exportarClientes()  { exigirAdmin(); return escribir(archivoCliente, clienteDAO.listar()); }
    public int exportarEmpleados() { exigirAdmin(); return escribir(archivoEmpleado, empleadoDAO.listar()); }
    public int exportarPrestamos() { exigirAdmin(); return escribir(archivoPrestamo, prestamoDAO.listar()); }
    public int exportarPagos()     { exigirAdmin(); return escribir(archivoPago, pagoDAO.listar()); }

    public String exportarTodo() {
        exigirAdmin();
        return "Clientes: " + exportarClientes()
             + " | Empleados: " + exportarEmpleados()
             + " | Prestamos: " + exportarPrestamos()
             + " | Pagos: " + exportarPagos();
    }

    public int cargarClientes() {
        exigirAdmin();
        Set<Integer> ids = clienteDAO.listar().stream().map(c -> c.getId()).collect(Collectors.toSet());
        Set<String> docs = clienteDAO.listar().stream().map(c -> c.getDocumento()).collect(Collectors.toSet());
        int n = 0;
        for (Cliente c : leer(archivoCliente)) {
            if (ids.contains(c.getId()) || docs.contains(c.getDocumento())) continue;
            if (clienteDAO.insertarConId(c)) n++;
        }
        return n;
    }

    public int cargarEmpleados() {
        exigirAdmin();
        Set<Integer> ids = empleadoDAO.listar().stream().map(e -> e.getId()).collect(Collectors.toSet());
        Set<String> docs = empleadoDAO.listar().stream().map(e -> e.getDocumento()).collect(Collectors.toSet());
        int n = 0;
        for (Empleado e : leer(archivoEmpleado)) {
            if (ids.contains(e.getId()) || docs.contains(e.getDocumento())) continue;
            if (empleadoDAO.insertarConId(e)) n++;
        }
        return n;
    }

    public int cargarPrestamos() {
        exigirAdmin();
        Set<Integer> ids = prestamoDAO.listar().stream().map(p -> p.getId()).collect(Collectors.toSet());
        int n = 0;
        for (Prestamo p : leer(archivoPrestamo)) {
            if (ids.contains(p.getId())) continue;
            if (prestamoDAO.insertarConId(p)) n++;
        }
        return n;
    }

    public int cargarPagos() {
    exigirAdmin();
    Set<Integer> ids = pagoDAO.listar().stream().map(p -> p.getId()).collect(Collectors.toSet());
    int n = 0;
    for (Pago p : leer(archivoPago)) {
        if (ids.contains(p.getId())) continue;
        if (pagoDAO.insertarConId(p)) n++; 
    }
    return n;
}

    public String cargarTodo() {
        exigirAdmin();
        return "Clientes: " + cargarClientes()
             + " | Empleados: " + cargarEmpleados()
             + " | Prestamos: " + cargarPrestamos()
             + " | Pagos: " + cargarPagos();
    }
}