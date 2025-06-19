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
                CarritoController carritoController = new CarritoController(carritoDAO);
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

                menu.getMenuAgregarProducto().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        CrearCarrito crearCarrito = new CrearCarrito();
                        carritoController.setCrearCarrito(crearCarrito);
                        carritoController.eventoCrearCarrito();

                        crearCarrito.getBtnSeleccionar().addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                int codigo = Integer.parseInt(crearCarrito.getTxtCodigo().getText());
                                Producto producto = productoDAO.buscarPorCodigo(codigo);
                                crearCarrito.productoEncontrado(producto.getNombre(),producto.getPrecio());
                            }
                        });

                        crearCarrito.getBtnAgregar().addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                int codigo = Integer.parseInt(crearCarrito.getTxtCodigo().getText());
                                String nombre = crearCarrito.getTxtNombre().getText();
                                double precio = Double.parseDouble(crearCarrito.getTxtPrecio().getText());
                                int cantidad = Integer.parseInt(crearCarrito.getTxtCantidad().getText());

                                Producto producto = new Producto(codigo,nombre,precio);
                                ItemCarrito itemCarrito = new ItemCarrito(producto, cantidad);
                                crearCarrito.cargarItem(itemCarrito);
                            }
                        });

                        menu.getjDesktopPane().add(crearCarrito);
                    }

                });
            }
        });
    }
}
