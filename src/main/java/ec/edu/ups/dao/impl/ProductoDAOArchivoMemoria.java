package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Pregunta;
import ec.edu.ups.modelo.Producto;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Implementación de ProductoDAO que almacena los productos en un archivo de texto.
 * Permite persistencia y recuperación de productos.
 *
 * @author JuanOrtiz2006
 * @version 1.0
 */
public class ProductoDAOArchivoMemoria implements ProductoDAO {

    private List<Producto> productos;
    private String rutaArchivo;

    public ProductoDAOArchivoMemoria(String rutaCarpeta){
        this.rutaArchivo = rutaCarpeta + File.separator + "productos.txt";

        File archivo = new File(rutaArchivo);
        try {
            if (!archivo.exists()) {
                archivo.getParentFile().mkdirs(); // crea carpetas si no existen
                archivo.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("Error creando archivo de productos: " + e.getMessage());
        }

        this.productos = new ArrayList<>();

        List<Producto> leidas = lecturaProductos();
        if (!leidas.isEmpty()) {
            this.productos = leidas;
        } else {
            crearProductosPorDefecto();
        }

    }

    @Override
    public void crear(Producto producto) {
        productos.add(producto);
        escribirProductosEnArchivo(productos);
    }

    @Override
    public Producto buscarPorCodigo(int codigo) {
        for (Producto producto : productos) {
            if (producto.getCodigo() == codigo) {
                return producto;
            }
        }
        return null;
    }

    @Override
    public List<Producto> buscarPorNombre(String nombre) {
        List<Producto> productosEncontrados = new ArrayList<>();
        for (Producto producto : productos) {
            if (producto.getNombre().equalsIgnoreCase(nombre)) {
                productosEncontrados.add(producto);
            }
        }
        return productosEncontrados;
    }

    @Override
    public void actualizar(Producto producto) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getCodigo() == producto.getCodigo()) {
                productos.set(i, producto);
            }
        }
        escribirProductosEnArchivo(productos);

    }

    @Override
    public void eliminar(int codigo) {
        Iterator<Producto> iterator = productos.iterator();
        while (iterator.hasNext()) {
            Producto producto = iterator.next();
            if (producto.getCodigo() == codigo) {
                iterator.remove();
            }
        }

        escribirProductosEnArchivo(productos);
    }

    @Override
    public List<Producto> listarTodos() {
        return productos;
    }


    private List<Producto> lecturaProductos() {
        List<Producto> productosLeidos = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;

            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                int codigo = Integer.parseInt(partes[0]);
                String nombre = partes[1];
                double precio = Double.parseDouble(partes[2]);
                productosLeidos.add(new Producto(codigo,nombre,precio));
            }
        } catch (IOException e) {
            System.out.println("Error leyendo archivo: " + e.getMessage());
        }

        return productosLeidos;
    }

    private void escribirProductosEnArchivo(List<Producto> productos) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))) {
            for (Producto p : productos) {
                StringBuilder sb = new StringBuilder();
                sb.append(p.getCodigo()).append(",");
                sb.append(p.getNombre()).append(",");
                sb.append(p.getPrecio()).append(",");

                writer.write(sb.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error escribiendo productos: " + e.getMessage());
        }
    }

    private void crearProductosPorDefecto() {
        crear(new Producto(1,"Computadora",201));
        crear(new Producto(2,"Celular",101));
        crear(new Producto(3,"Banana",20.1));
    }
}
