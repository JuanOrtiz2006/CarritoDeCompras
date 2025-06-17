package ec.edu.ups.vista;

import ec.edu.ups.controlador.ProductoController;
import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.dao.impl.ProductoDAOMemoria;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                //CrearProductoView crearProductoView = new CrearProductoView();
                //BuscarProducto buscarProducto = new BuscarProducto();
                //EliminarProducto eliminarProducto = new EliminarProducto();
                //ListaProducto listaProducto = new ListaProducto();
                //ProductoDAO productoDAO = new ProductoDAOMemoria();
                //new ProductoController(productoDAO, crearProductoView,buscarProducto,eliminarProducto,listaProducto);
                MenuView menu = new MenuView();
                ProductoDAO productoDAO = new ProductoDAOMemoria();
                ProductoController productoController = new ProductoController(productoDAO);
                CrearProductoView crearProductoView = new CrearProductoView();


                menu.getMenuCrearProducto().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        productoController.setCrearProductoView(crearProductoView);
                        
                        menu.getjDesktopPane().add(crearProductoView);
                    }
                });
            }
        });
    }
}
