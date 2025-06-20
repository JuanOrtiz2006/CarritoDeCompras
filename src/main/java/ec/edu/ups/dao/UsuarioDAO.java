package ec.edu.ups.dao;

import ec.edu.ups.modelo.Usuario;

import java.util.List;

public interface UsuarioDAO {

    Usuario autenticar(String username, String password);

    void crear(Usuario usuario);

    Usuario buscarPorUsername(String username);


    void actualizar(Usuario usuario);

    void eliminar(String username);

    List<Usuario> listarTodos();

    List<Usuario> listarPorRol(String rol);

}
