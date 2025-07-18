package ec.edu.ups.dao;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.Usuario;

import java.util.List;

/**
 * Interfaz para operaciones de acceso a datos de carritos de compras.
 * Define métodos CRUD y consultas específicas por usuario.
 *
 * @author JuanOrtiz2006
 * @version 1.0
 */
public interface CarritoDAO {

    /**
     * Crea un nuevo carrito.
     * @param carrito Carrito a crear.
     */
    void crear(Carrito carrito);

    /**
     * Busca un carrito por su código.
     * @param codigo Código del carrito.
     * @return Carrito encontrado o null.
     */
    Carrito buscarPorCodigo(int codigo);

    /**
     * Actualiza un carrito existente.
     * @param carrito Carrito con datos actualizados.
     */
    void actualizar(Carrito carrito);

    /**
     * Elimina un carrito por su código.
     * @param codigo Código del carrito a eliminar.
     */
    void eliminar(int codigo);

    /**
     * Lista todos los carritos.
     * @return Lista de carritos.
     */
    List<Carrito> listarCarritos();

    /**
     * Obtiene el siguiente código disponible para un usuario.
     * @param usuario Usuario asociado.
     * @return Siguiente código disponible.
     */
    int obtenerSiguienteCodigoParaUsuario(Usuario usuario);

    /**
     * Lista los carritos de un usuario.
     * @param usuario Usuario asociado.
     * @return Lista de carritos del usuario.
     */
    List<Carrito> listarCarritosPorUsuario(Usuario usuario);

    /**
     * Busca un carrito por código y usuario.
     * @param codigo Código del carrito.
     * @param usuario Usuario asociado.
     * @return Carrito encontrado o null.
     */
    Carrito buscarPorCodigoYUsuario(int codigo, Usuario usuario);

    /**
     * Cuenta los carritos de un usuario.
     * @param usuario Usuario asociado.
     * @return Número de carritos.
     */
    int contarCarritosPorUsuario(Usuario usuario);

    /**
     * Verifica si existe un carrito para un usuario.
     * @param codigo Código del carrito.
     * @param usuario Usuario asociado.
     * @return true si existe, false en caso contrario.
     */
    boolean existeCarrito(int codigo, Usuario usuario);

    /**
     * Elimina un carrito por código y usuario.
     * @param codigo Código del carrito.
     * @param usuario Usuario asociado.
     */
    void eliminarPorCodigoYUsuario(int codigo, Usuario usuario);
}
