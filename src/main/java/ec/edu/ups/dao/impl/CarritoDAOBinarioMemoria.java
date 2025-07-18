package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.Usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de CarritoDAO que almacena los carritos en un archivo binario.
 * Permite persistencia y recuperación de carritos por usuario.
 *
 * @author JuanOrtiz2006
 * @version 1.0
 */
public class CarritoDAOBinarioMemoria implements CarritoDAO {

    private final String rutaArchivo;
    private List<Carrito> carritos;

    public CarritoDAOBinarioMemoria(String carpeta) {
        this.rutaArchivo = carpeta + File.separator + "carritos.dat";
        this.carritos = cargarDesdeArchivo();
    }

    /**
     * Crea un nuevo carrito y lo guarda en el archivo.
     *
     * @param carrito El carrito a crear.
     */
    @Override
    public void crear(Carrito carrito) {
        carritos.add(carrito);
        guardarEnArchivo();
    }

    /**
     * Busca un carrito por su código.
     *
     * @param codigo El código del carrito a buscar.
     * @return El carrito encontrado, o null si no existe.
     */
    @Override
    public Carrito buscarPorCodigo(int codigo) {
        for (Carrito c : carritos) {
            if (c.getCodigo() == codigo) return c;
        }
        return null;
    }

    /**
     * Actualiza un carrito existente.
     *
     * @param carrito El carrito con los nuevos datos.
     */
    @Override
    public void actualizar(Carrito carrito) {
        for (int i = 0; i < carritos.size(); i++) {
            if (carritos.get(i).getCodigo() == carrito.getCodigo()) {
                carritos.set(i, carrito);
                break;
            }
        }
        guardarEnArchivo();
    }

    /**
     * Elimina un carrito por su código.
     *
     * @param codigo El código del carrito a eliminar.
     */
    @Override
    public void eliminar(int codigo) {
        carritos.removeIf(c -> c.getCodigo() == codigo);
        guardarEnArchivo();
    }

    /**
     * Lista todos los carritos.
     *
     * @return Una lista con todos los carritos.
     */
    @Override
    public List<Carrito> listarCarritos() {
        return carritos;
    }

    /**
     * Obtiene el siguiente código disponible para un carrito de un usuario.
     *
     * @param usuario El usuario para el cual se desea el siguiente código de carrito.
     * @return El siguiente código disponible.
     */
    @Override
    public int obtenerSiguienteCodigoParaUsuario(Usuario usuario) {
        int max = 0;
        for (Carrito c : listarCarritosPorUsuario(usuario)) {
            if (c.getCodigo() > max) {
                max = c.getCodigo();
            }
        }
        return max + 1;
    }

    /**
     * Lista los carritos de un usuario específico.
     *
     * @param usuario El usuario del cual se desean listar los carritos.
     * @return Una lista con los carritos del usuario.
     */
    @Override
    public List<Carrito> listarCarritosPorUsuario(Usuario usuario) {
        List<Carrito> resultado = new ArrayList<>();
        for (Carrito c : carritos) {
            if (c.getUsuario().getUsername().equals(usuario.getUsername())) {
                resultado.add(c);
            }
        }
        return resultado;
    }

    /**
     * Busca un carrito por su código y el usuario al que pertenece.
     *
     * @param codigo  El código del carrito a buscar.
     * @param usuario El usuario al que debe pertenecer el carrito.
     * @return El carrito encontrado, o null si no existe.
     */
    @Override
    public Carrito buscarPorCodigoYUsuario(int codigo, Usuario usuario) {
        for (Carrito c : listarCarritosPorUsuario(usuario)) {
            if (c.getCodigo() == codigo) return c;
        }
        return null;
    }

    /**
     * Cuenta la cantidad de carritos que tiene un usuario.
     *
     * @param usuario El usuario del cual se desea conocer la cantidad de carritos.
     * @return La cantidad de carritos del usuario.
     */
    @Override
    public int contarCarritosPorUsuario(Usuario usuario) {
        return listarCarritosPorUsuario(usuario).size();
    }

    /**
     * Verifica si un carrito existe para un usuario dado.
     *
     * @param codigo  El código del carrito a verificar.
     * @param usuario El usuario al que debe pertenecer el carrito.
     * @return True si el carrito existe, false en caso contrario.
     */
    @Override
    public boolean existeCarrito(int codigo, Usuario usuario) {
        return buscarPorCodigoYUsuario(codigo, usuario) != null;
    }

    /**
     * Elimina un carrito por su código y el usuario al que pertenece.
     *
     * @param codigo  El código del carrito a eliminar.
     * @param usuario El usuario del cual se desea eliminar el carrito.
     */
    @Override
    public void eliminarPorCodigoYUsuario(int codigo, Usuario usuario) {
        carritos.removeIf(c -> c.getCodigo() == codigo &&
                c.getUsuario().getUsername().equals(usuario.getUsername()));
        guardarEnArchivo();
    }

    // ------------------ Auxiliares ----------------------

    private void guardarEnArchivo() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
            oos.writeObject(carritos);
        } catch (IOException e) {
            System.out.println("Error guardando carritos binarios: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private List<Carrito> cargarDesdeArchivo() {
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) return new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(rutaArchivo))) {
            return (List<Carrito>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error leyendo carritos binarios: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
