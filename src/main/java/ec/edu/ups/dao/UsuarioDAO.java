package ec.edu.ups.dao;

import ec.edu.ups.modelo.Usuario;

import java.util.List;

/**
 * Interfaz para operaciones de acceso a datos de usuarios.
 * Define métodos para autenticación, CRUD y consultas por rol.
 *
 * @author JuanOrtiz2006
 * @version 1.0
 */
public interface UsuarioDAO {

    /**
     * Autentica un usuario por nombre y contraseña.
     * @param username Nombre de usuario.
     * @param password Contraseña.
     * @return Usuario autenticado o null si no existe.
     */
    Usuario autenticar(String username, String password);

    /**
     * Crea un nuevo usuario.
     * @param usuario Usuario a crear.
     */
    void crear(Usuario usuario);

    /**
     * Busca un usuario por su nombre de usuario.
     * @param username Nombre de usuario.
     * @return Usuario encontrado o null.
     */
    Usuario buscarPorUsername(String username);

    /**
     * Elimina un usuario por su nombre de usuario.
     * @param username Nombre de usuario.
     */
    void eliminar(String username);

    /**
     * Actualiza los datos de un usuario.
     * @param usuario Usuario con datos actualizados.
     */
    void actualizar(Usuario usuario);

    /**
     * Lista todos los usuarios registrados.
     * @return Lista de usuarios.
     */
    List<Usuario> listarTodos();

    /**
     * Lista los usuarios por rol.
     * @param rol Rol de usuario.
     * @return Lista de usuarios con el rol especificado.
     */
    List<Usuario> listarPorRol(String rol);

}
