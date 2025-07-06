package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.Usuario;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CarritoDAOMemoria implements CarritoDAO {

    private List<Carrito> carritos;

    public CarritoDAOMemoria(){
        carritos = new ArrayList<Carrito>();
    }
    @Override
    public void crear(Carrito carrito) {
        carritos.add(carrito);
    }

    @Override
    public Carrito buscarPorCodigo(int codigo) {
        for(Carrito carrito: carritos){
            if (carrito.getCodigo() == codigo){
                return carrito;
            }
        }
        return null;
    }

    @Override
    public void actualizar(Carrito carrito) {
        for(int i=0; i<carritos.size();i++){
            if(carritos.get(i).getCodigo() == carrito.getCodigo()){
                carritos.set(i,carrito);
            }
        }
    }

    @Override
    public void eliminar(int codigo) {
        Iterator<Carrito> iterator = carritos.iterator();
        while (iterator.hasNext()){
            Carrito carrito = iterator.next();
            if(carrito.getCodigo() == codigo){
                iterator.remove();
            }
        }

    }

    @Override
    public List<Carrito> listarCarritos() {
        return carritos;
    }



    @Override
    public int obtenerSiguienteCodigoParaUsuario(Usuario usuario) {
        // Buscar el código más alto para este usuario
        int maxCodigo = 0;
        for (Carrito carrito : listarCarritosPorUsuario(usuario)) {
            if (carrito.getCodigo() > maxCodigo) {
                maxCodigo = carrito.getCodigo();
            }
        }
        return maxCodigo + 1;
    }

    @Override
    public List<Carrito> listarCarritosPorUsuario(Usuario usuario) {
        List<Carrito> carritosPorUsuario = new ArrayList<>();
        for (Carrito carrito : listarCarritos()) {
            if (carrito.getUsuario() != null &&
                    carrito.getUsuario().getUsername().equals(usuario.getUsername())) {
                carritosPorUsuario.add(carrito);
            }
        }
        return carritosPorUsuario;
    }

    @Override
    public Carrito buscarPorCodigoYUsuario(int codigo, Usuario usuario) {
        for (Carrito carrito : listarCarritos()) {
            if (carrito.getCodigo() == codigo &&
                    carrito.getUsuario() != null &&
                    carrito.getUsuario().getUsername().equals(usuario.getUsername())) {
                return carrito;
            }
        }
        return null;
    }

    @Override
    public int contarCarritosPorUsuario(Usuario usuario) {
        return listarCarritosPorUsuario(usuario).size();
    }

    @Override
    public boolean existeCarrito(int codigo, Usuario usuario) {
        return buscarPorCodigoYUsuario(codigo, usuario) != null;
    }

    @Override
    public void eliminarPorCodigoYUsuario(int codigo, Usuario usuario) {
        Carrito carrito = buscarPorCodigoYUsuario(codigo, usuario);
        if (carrito != null) {
            eliminar(codigo);
        }
    }
}
