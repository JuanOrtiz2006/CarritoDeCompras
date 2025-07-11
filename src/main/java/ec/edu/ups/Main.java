package ec.edu.ups;

import ec.edu.ups.controlador.*;
import ec.edu.ups.dao.*;
import ec.edu.ups.dao.impl.*;
import ec.edu.ups.modelo.*;
import ec.edu.ups.vista.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import ec.edu.ups.util.Contexto;

public class Main {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            //Inicializar idioma
            Contexto.iniciarIdioma("es", "EC");

            // Instanciar DAOs UNA VEZ para toda la aplicación
            ProductoDAO productoDAO = new ProductoDAOMemoria();
            CarritoDAO carritoDAO = new CarritoDAOMemoria();
            UsuarioDAO usuarioDAO = new UsuarioDAOMemoria();
            PreguntaDAO preguntaDAO = new PreguntaDAOMemoria();

            // Instanciar vistas comunes (login)
            LoginView loginView = new LoginView();
            RegistrarUsuario registrarUsuario = new RegistrarUsuario();
            PreguntasSeguridad preguntasSeguridad = new PreguntasSeguridad();
            RecuperarClave recuperarClave = new RecuperarClave();

            // Controlador usuario
            UsuarioController usuarioController = new UsuarioController(usuarioDAO, preguntaDAO);
            usuarioController.setLoginView(loginView);
            usuarioController.eventosLogin();
            usuarioController.setRegistrarUsuario(registrarUsuario);
            usuarioController.setPreguntasSeguridad(preguntasSeguridad);
            usuarioController.setRecuperarClave(recuperarClave);

            loginView.setVisible(true);

            loginView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loginView.getTxtUsername().setText("");
                    loginView.getTxtPassword().setText("");

                    Usuario usuarioAutenticado = usuarioController.getUsuarioAutenticado();
                    if (usuarioAutenticado != null) {
                        // Instanciar vistas para la sesión (solo una vez)
                        MenuView menuView = new MenuView();

                        CrearProductoView crearProducto = new CrearProductoView();
                        EliminarProducto eliminarProducto = new EliminarProducto();
                        BuscarProducto buscarProducto = new BuscarProducto();
                        ActualizarProducto actualizarProducto = new ActualizarProducto();
                        ListaProducto listaProductoView = new ListaProducto();

                        CrearCarrito crearCarritoView = new CrearCarrito();
                        ListaCarrito listaCarritoView = new ListaCarrito();


                        // Usar las mismas instancias de DAOs para TODOS los controladores
                        ProductoController productoController = new ProductoController(productoDAO);
                        CarritoController carritoController = new CarritoController(carritoDAO, productoDAO);

                        carritoController.setUsuario(usuarioAutenticado);
                        // Setear vistas en controladores
                        productoController.setCrearProductoView(crearProducto);
                        productoController.setEliminarProducto(eliminarProducto);
                        productoController.setBuscarProducto(buscarProducto);
                        productoController.setActualizarProducto(actualizarProducto);
                        productoController.setListaProducto(listaProductoView);
                        productoController.eventoCrearProducto();
                        productoController.eventoBuscarProducto();
                        productoController.eventoActualizarProducto();
                        productoController.eventoEliminarProducto();
                        productoController.eventoListarProductos();

                        // Similar para carritoController (así sucesivamente)
                        carritoController.setCrearCarrito(crearCarritoView);
                        carritoController.setListaCarrito(listaCarritoView);


                        MenuController menuController = new MenuController(menuView, usuarioAutenticado);
                        menuController.setUsuarioController(usuarioController);
                        menuController.setCarritoController(carritoController);
                        menuController.setProductoController(productoController);
                        menuController.setLoginView(loginView);
                        menuController.setRegistrarUsuario(registrarUsuario);
                        menuController.setPreguntasSeguridad(preguntasSeguridad);
                        menuController.construirMenus();

                        menuView.setVisible(true);

                        menuController.getMenuCerrarSesion().addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                var handler = Contexto.getHandler();
                                int opcion = JOptionPane.showConfirmDialog(
                                        menuView,
                                        handler.get("usuario.confirmar.cerrarsesion"),
                                        handler.get("confirmacion"),
                                        JOptionPane.YES_NO_OPTION
                                );


                                if (opcion == JOptionPane.YES_OPTION) {
                                    // Limpiar usuario en controladores
                                    usuarioController.setUsuario(null);
                                    carritoController.setUsuario(null);
                                    menuController.setUsuario(null);

                                    // Ocultar menú y mostrar login
                                    menuView.setVisible(false);
                                    loginView.setVisible(true);
                                }
                            }
                        });
                    }
                }
            });
        });
    }
}