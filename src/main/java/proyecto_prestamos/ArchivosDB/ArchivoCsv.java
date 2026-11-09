package proyecto_prestamos.ArchivosDB;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public abstract class ArchivoCsv<T> {

    private final Path ruta;

    protected ArchivoCsv(String nombreArchivo) {
        this.ruta = Paths.get("datos", nombreArchivo);
    }

    protected abstract String encabezado();
    protected abstract String aLinea(T t);
    protected abstract T deLinea(String[] c);

    public void exportar(List<T> lista) throws IOException {
        Files.createDirectories(ruta.getParent());
        List<String> lineas = new ArrayList<>();
        lineas.add(encabezado());
        lista.forEach(t -> lineas.add(aLinea(t)));
        Files.write(ruta, lineas, StandardCharsets.UTF_8);
    }

    public List<T> cargar() throws IOException {
        List<T> lista = new ArrayList<>();
        if (!Files.exists(ruta)) {
            return lista;
        }
        try (BufferedReader br = Files.newBufferedReader(ruta, StandardCharsets.UTF_8)) {
            br.readLine(); // encabezado
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.isBlank()) continue;
                lista.add(deLinea(linea.split(";", -1)));
            }
        }
        return lista;
    }
}