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

                MenuView menu = new MenuView();
                ProductoDAO productoDAO = new ProductoDAOMemoria();
                ProductoController productoController = new ProductoController(productoDAO);

                menu.setVisible(true);

                menu.getMenuCrearProducto().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        CrearProductoView crearProductoView = new CrearProductoView();
                        productoController.setCrearProductoView(crearProductoView);
                        productoController.eventoCrearProducto();
                        menu.getjDesktopPane().add(crearProductoView);
                    }
                });


                menu.getMenuBuscarProducto().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        BuscarProducto buscarProducto = new BuscarProducto();
                        productoController.setBuscarProducto(buscarProducto);
                        productoController.eventoBuscarProducto();
                        menu.getjDesktopPane().add(buscarProducto);
                        buscarProducto.setVisible(true);
                    }
                });

                menu.getMenuActualizarProducto().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ActualizarProducto actualizarProducto = new ActualizarProducto();
                        productoController.setActualizarProducto(actualizarProducto);
                        productoController.eventoActualizarProducto();
                        menu.getjDesktopPane().add(actualizarProducto);
                        actualizarProducto.setVisible(true);
                    }
                });

                menu.getMenuEliminarProducto().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        EliminarProducto eliminarProducto = new EliminarProducto();
                        productoController.setEliminarProducto(eliminarProducto);
                        productoController.eventoEliminarProducto();
                        menu.getjDesktopPane().add(eliminarProducto);
                        eliminarProducto.setVisible(true);
                    }
                });

                menu.getMenuListarProductos().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ListaProducto listaProducto = new ListaProducto();
                        productoController.setListaProducto(listaProducto);
                        productoController.eventoListarProductos();
                        menu.getjDesktopPane().add(listaProducto);
                        listaProducto.setVisible(true);
                    }
                });


            }
        });
    }
}
