package ec.edu.ups.dao;

import ec.edu.ups.modelo.Producto;

import java.util.List;

/**
 * Interfaz para operaciones de acceso a datos de productos.
 * Define métodos CRUD y consultas por código y nombre.
 *
 * @author JuanOrtiz2006
 * @version 1.0
 */
public interface ProductoDAO {

    /**
     * Crea un nuevo producto.
     * @param producto Producto a crear.
     */
    void crear(Producto producto);

    /**
     * Busca un producto por su código.
     * @param codigo Código del producto.
     * @return Producto encontrado o null.
     */
    Producto buscarPorCodigo(int codigo);

    /**
     * Busca productos por nombre.
     * @param nombre Nombre del producto.
     * @return Lista de productos que coinciden con el nombre.
     */
    List<Producto> buscarPorNombre(String nombre);

    /**
     * Actualiza los datos de un producto.
     * @param producto Producto con datos actualizados.
     */
    void actualizar(Producto producto);

    /**
     * Elimina un producto por su código.
     * @param codigo Código del producto.
     */
    void eliminar(int codigo);

    /**
     * Lista todos los productos registrados.
     * @return Lista de productos.
     */
    List<Producto> listarTodos();

}
