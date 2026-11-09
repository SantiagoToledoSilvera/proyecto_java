# CrediYa S.A.S. - Sistema de Gestión de Préstamos

Sistema de consola desarrollado en Java para digitalizar el control de créditos, clientes, empleados y pagos de la empresa CrediYa S.A.S., permitiendo persistencia mediante archivos planos (CSV) y base de datos MySQL (JDBC).

---

## 1. Descripción General
El proyecto implementa los principios de la Programación Orientada a Objetos (POO), manejo de excepciones personalizadas, colecciones, expresiones Lambda y Stream API para la generación de reportes y consultas financieras.

### Módulos principales:
- **Empleados:** Registro, consulta y gestión de personal (ID, nombre, documento, rol, correo, salario).
- **Clientes:** Administración de cartera de clientes (ID, nombre, documento, correo, teléfono) y asociación de créditos.
- **Préstamos:** Creación de créditos calculando automáticamente el interés y la cuota mensual, con control de estados.
- **Pagos:** Registro de abonos y actualización de saldos pendientes.
- **Reportes:** Filtrado y consulta de información avanzada utilizando Java Streams.

---

## 2. Ejecución

1. **Base de Datos:**
   Ejecuta el script SQL incluido (`crediya_db.sql.txt`) en tu gestor de MySQL para crear la estructura de tablas (`empleados`, `clientes`, `prestamos`, `pagos`).
2. **Configuración de Conexión:**
   Asegúrate de configurar tus credenciales de acceso a MySQL (usuario, contraseña y URL) en la clase de conexión (`Conexion.java`).
3. **Compilación y Ejecución:**
   Puedes compilar y ejecutar el proyecto mediante Maven utilizando la consola o ejecutando el archivo main:
   
   ```bash
   mvn clean compile
   mvn exec:java -Dexec.mainClass="proyecto_prestamos.main"
   ```

---

## 3. Ejemplos de Uso

Una vez ejecutado el programa, se mostrará un menú interactivo en la consola:

- **Registrar un Cliente:** 
  Selecciona la opción de gestión de clientes e ingresa los datos solicitados (Nombre, Documento, Correo, Teléfono) para guardarlo en la base de datos y archivos.
- **Crear un Préstamo:** 
  Asocia el ID de un cliente y un empleado para otorgar un monto determinado con su respectiva tasa de interés y número de cuotas.
- **Consultar Cartera Pendiente (Reportes):** 
  El sistema calculará y mostrará el saldo total adeudado por los clientes utilizando formatos monetarios claros y seguros.
