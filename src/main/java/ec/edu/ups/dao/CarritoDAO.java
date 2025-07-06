package ec.edu.ups.dao;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.Usuario;

import java.util.List;

public interface CarritoDAO {

    void crear(Carrito carrito);
    Carrito buscarPorCodigo(int codigo);
    void actualizar(Carrito carrito);
    void eliminar(int codigo);
    List<Carrito> listarCarritos();

    int obtenerSiguienteCodigoParaUsuario(Usuario usuario);
    List<Carrito> listarCarritosPorUsuario(Usuario usuario);
    Carrito buscarPorCodigoYUsuario(int codigo, Usuario usuario);
    int contarCarritosPorUsuario(Usuario usuario);
    boolean existeCarrito(int codigo, Usuario usuario);
    void eliminarPorCodigoYUsuario(int codigo, Usuario usuario);
}
