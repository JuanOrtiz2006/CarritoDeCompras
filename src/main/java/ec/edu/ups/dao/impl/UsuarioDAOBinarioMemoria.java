package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;

import java.io.*;
import java.util.*;
import java.util.List;

/**
 * Implementación de UsuarioDAO que almacena los usuarios en un archivo binario.
 * Permite persistencia y recuperación de usuarios.
 *
 * @author JuanOrtiz2006
 * @version 1.0
 */
public class UsuarioDAOBinarioMemoria implements UsuarioDAO {

    private List<Usuario> usuarios;
    private final String rutaArchivo;

    public UsuarioDAOBinarioMemoria(String carpeta) {
        this.rutaArchivo = carpeta + File.separator + "usuarios.dat";
        this.usuarios = leerUsuariosDesdeArchivo();
        crear(new Usuario("0103527966","Jp1034506_Ot        ",Rol.ADMINISTRADOR));
    }

    /**
     * Autentica un usuario comparando el nombre de usuario y la contraseña.
     *
     * @param username El nombre de usuario.
     * @param password La contraseña.
     * @return El usuario autenticado o null si no se encuentra.
     */
    @Override
    public Usuario autenticar(String username, String password) {
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().equals(username) && usuario.getPassword().equals(password)) {
                return usuario;
            }
        }
        return null;
    }

    /**
     * Crea un nuevo usuario y lo almacena en el archivo.
     *
     * @param usuario El usuario a crear.
     */
    @Override
    public void crear(Usuario usuario) {
        usuario.setNombre(String.format("%-20s", usuario.getNombre()).substring(0, 20));
        usuario.setCorreo(String.format("%-15s", usuario.getCorreo()).substring(0, 15));

        // Validación mínima (solo si no está en controllers)
        if (usuario.getUsername().trim().isEmpty() || usuario.getCorreo().trim().isEmpty()) {
            throw new IllegalArgumentException("Campos obligatorios vacíos");
        }
        usuarios.add(usuario);
        escribirUsuariosEnArchivo();
    }

    /**
     * Busca un usuario por su nombre de usuario.
     *
     * @param username El nombre de usuario a buscar.
     * @return El usuario encontrado o null si no se encuentra.
     */
    @Override
    public Usuario buscarPorUsername(String username) {
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().equals(username)) {
                return usuario;
            }
        }
        return null;
    }

    /**
     * Elimina un usuario por su nombre de usuario.
     *
     * @param username El nombre de usuario a eliminar.
     */
    @Override
    public void eliminar(String username) {
        usuarios.removeIf(u -> u.getUsername().equals(username));
        escribirUsuariosEnArchivo();
    }

    /**
     * Actualiza la información de un usuario.
     *
     * @param usuario El usuario con la información actualizada.
     */
    @Override
    public void actualizar(Usuario usuario) {
        usuario.setNombre(String.format("%-20s", usuario.getNombre()).substring(0, 20));
        usuario.setCorreo(String.format("%-15s", usuario.getCorreo()).substring(0, 15));
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getUsername().equals(usuario.getUsername())) {
                usuarios.set(i, usuario);
                break;
            }
        }
        escribirUsuariosEnArchivo();
    }

    /**
     * Lista todos los usuarios.
     *
     * @return Una lista con todos los usuarios.
     */
    @Override
    public List<Usuario> listarTodos() {
        return usuarios;
    }

    /**
     * Lista los usuarios filtrando por rol.
     *
     * @param rol El rol por el cual filtrar.
     * @return Una lista de usuarios con el rol especificado.
     */
    @Override
    public List<Usuario> listarPorRol(String rol) {
        List<Usuario> filtrados = new ArrayList<>();
        for (Usuario u : usuarios) {
            if (u.getRol().toString().equalsIgnoreCase(rol)) {
                filtrados.add(u);
            }
        }
        return filtrados;
    }

    // Metodos Auxiliares
    private void escribirUsuariosEnArchivo() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
            out.writeObject(usuarios);
        } catch (IOException e) {
            System.out.println("Error escribiendo archivo binario: " + e.getMessage());
        }
    }

    private List<Usuario> leerUsuariosDesdeArchivo() {
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(archivo))) {
            return (List<Usuario>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error leyendo archivo binario: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
