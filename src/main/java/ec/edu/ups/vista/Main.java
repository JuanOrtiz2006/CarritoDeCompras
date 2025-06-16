package ec.edu.ups.vista;

import ec.edu.ups.controlador.ProductoController;
import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.dao.impl.ProductoDAOMemoria;

public class Main {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                CrearProductoView crearProductoView = new CrearProductoView();
                BuscarProducto buscarProducto = new BuscarProducto();
                EliminarProducto eliminarProducto = new EliminarProducto();
                ListaProducto listaProducto = new ListaProducto();
                ProductoDAO productoDAO = new ProductoDAOMemoria();
                new ProductoController(productoDAO, crearProductoView,buscarProducto,eliminarProducto,listaProducto);
            }
        });
    }
}
