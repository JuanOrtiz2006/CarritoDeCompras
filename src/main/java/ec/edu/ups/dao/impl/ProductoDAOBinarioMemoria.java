package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Implementación de ProductoDAO que almacena los productos en un archivo binario.
 * Permite persistencia y recuperación de productos.
 *
 * @author JuanOrtiz2006
 * @version 1.0
 */
public class ProductoDAOBinarioMemoria implements ProductoDAO {

    private List<Producto> productos;
    private String rutaArchivo;

    public ProductoDAOBinarioMemoria(String rutaCarpeta){
        this.rutaArchivo = rutaCarpeta + File.separator + "productos.dat";
        this.productos = leerProductosDesdeArchivo();
        crear(new Producto(1,"Computadora         ",201));
        crear(new Producto(2,"Celular             ",101));
        crear(new Producto(3,"Banana              ",20.1));
    }

    /**
     * Crea un nuevo producto y lo almacena en el archivo binario.
     * @param producto El producto a crear.
     */
    @Override
    public void crear(Producto producto) {
        // Rellenar nombre a 20 caracteres exactos
        String nombreRellenado = String.format("%-20s", producto.getNombre()).substring(0, 20);
        producto.setNombre(nombreRellenado);
        productos.add(producto);
        escribirProductosEnArchivo();
    }

    /**
     * Busca un producto por su código.
     * @param codigo El código del producto a buscar.
     * @return El producto encontrado, o null si no se encuentra.
     */
    @Override
    public Producto buscarPorCodigo(int codigo) {
        for (Producto producto : productos) {
            if (producto.getCodigo() == codigo) {
                return producto;
            }
        }
        return null;
    }

    /**
     * Busca productos por su nombre.
     * @param nombre El nombre del producto a buscar.
     * @return Una lista de productos que coinciden con el nombre.
     */
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

    /**
     * Actualiza la información de un producto existente.
     * @param producto El producto con la información actualizada.
     */
    @Override
    public void actualizar(Producto producto) {
        producto.setNombre(String.format("%-20s", producto.getNombre()).substring(0, 20));
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getCodigo() == producto.getCodigo()) {
                productos.set(i, producto);
            }
        }
        escribirProductosEnArchivo();

    }

    /**
     * Elimina un producto del archivo binario.
     * @param codigo El código del producto a eliminar.
     */
    @Override
    public void eliminar(int codigo) {
        Iterator<Producto> iterator = productos.iterator();
        while (iterator.hasNext()) {
            Producto producto = iterator.next();
            if (producto.getCodigo() == codigo) {
                iterator.remove();
            }
        }

        escribirProductosEnArchivo();
    }

    /**
     * Lista todos los productos almacenados.
     * @return Una lista con todos los productos.
     */
    @Override
    public List<Producto> listarTodos() {
        return productos;
    }

    // Metodos Auxiliares
    private void escribirProductosEnArchivo() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
            out.writeObject(productos);
        } catch (IOException e) {
            System.out.println("Error escribiendo archivo binario: " + e.getMessage());
        }
    }

    private List<Producto> leerProductosDesdeArchivo() {
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(archivo))) {
            return (List<Producto>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error leyendo archivo binario: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
