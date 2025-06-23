package ec.edu.ups;

import ec.edu.ups.controlador.CarritoController;
import ec.edu.ups.controlador.ProductoController;
import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.dao.impl.CarritoDAOMemoria;
import ec.edu.ups.dao.impl.ProductoDAOMemoria;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.modelo.*  ;
import ec.edu.ups.vista.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                MenuView menu = new MenuView();
                //instanciamos (Singleton)
                ProductoDAO productoDAO = new ProductoDAOMemoria();
                CarritoDAO carritoDAO = new CarritoDAOMemoria();

                ProductoController productoController = new ProductoController(productoDAO);
                CarritoController carritoController = new CarritoController(carritoDAO,productoDAO);
                List<Carrito> listaCarritos = new ArrayList<>();

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

                menu.getMenuCrearCarrito().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        CrearCarrito crearCarrito = new CrearCarrito();
                        carritoController.setCrearCarrito(crearCarrito);
                        carritoController.crearCarrito();
                        menu.getjDesktopPane().add(crearCarrito);
                    }

                });

                menu.getMenuListaCarrito().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ListaCarrito listaCarrito = new ListaCarrito();
                        carritoController.setListaCarrito(listaCarrito);
                        carritoController.listarCarritos();
                        menu.getjDesktopPane().add(listaCarrito);
                    }
                });


            }
        });
    }
}
