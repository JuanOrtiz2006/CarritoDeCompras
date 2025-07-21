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
import java.io.File;

import ec.edu.ups.util.Contexto;

public class Main {

    // Instanciar DAOs una vez para toda la aplicación
    private static ProductoDAO productoDAO;
    private static CarritoDAO carritoDAO;
    private static UsuarioDAO usuarioDAO;
    private static PreguntaDAO preguntaDAO;

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            //Inicializar idioma
            Contexto.iniciarIdioma("es", "EC");


            //Instancia de vista para archivo
            ArchivoVista archivoVista = new ArchivoVista();

            // Instanciar vistas comunes (login)
            LoginView loginView = new LoginView();
            RegistrarUsuario registrarUsuario = new RegistrarUsuario();
            PreguntasSeguridad preguntasSeguridad = new PreguntasSeguridad();
            RecuperarClave recuperarClave = new RecuperarClave();
            GestionUsuarios gestionUsuarios = new GestionUsuarios();

            archivoVista.setVisible(true);

            archivoVista.getBtnArchivo().addActionListener(e -> {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // Solo carpetas
                chooser.setAcceptAllFileFilterUsed(false); // No permitir "Todos los archivos"

                int resultado = chooser.showOpenDialog(archivoVista);

                if (resultado == JFileChooser.APPROVE_OPTION) {
                    File carpetaSeleccionada = chooser.getSelectedFile();
                    archivoVista.getTxtRuta().setText(carpetaSeleccionada.getAbsolutePath());
                    System.out.println("Carpeta seleccionada: " + carpetaSeleccionada.getAbsolutePath());
                } else {
                    System.out.println("Selección cancelada.");
                }
            });

            archivoVista.getBtnEleccion().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int opcion = archivoVista.getCmbArchivo().getSelectedIndex();
                    String ruta = archivoVista.getTxtRuta().getText();

                    try {
                        switch (opcion){
                            case 1:
                                carritoDAO = new CarritoDAOArchivoMemoria(ruta);
                                preguntaDAO = new PreguntaDAOArchivoMemoria(ruta);
                                productoDAO = new ProductoDAOArchivoMemoria(ruta);
                                usuarioDAO = new UsuarioDAOArchivoMemoria(ruta);
                                break;

                            case 2:
                                carritoDAO = new CarritoDAOBinarioMemoria(ruta);
                                preguntaDAO = new PreguntaDAOBinarioMemoria(ruta);
                                productoDAO = new ProductoDAOBinarioMemoria(ruta);
                                usuarioDAO = new UsuarioDAOBinarioMemoria(ruta);
                                break;

                            case 3:
                                carritoDAO = new CarritoDAOMemoria();
                                preguntaDAO = new PreguntaDAOMemoria();
                                productoDAO = new ProductoDAOMemoria();
                                usuarioDAO = new UsuarioDAOMemoria();
                                break;

                            default:
                                JOptionPane.showMessageDialog(archivoVista, "Debe seleccionar un tipo de almacenamiento válido.");
                                return;
                        }

                        // Crear controlador de usuario con DAOs
                        UsuarioController usuarioController = new UsuarioController(usuarioDAO, preguntaDAO);
                        usuarioController.setLoginView(loginView);
                        usuarioController.setRegistrarUsuario(registrarUsuario);
                        usuarioController.setPreguntasSeguridad(preguntasSeguridad);
                        usuarioController.setRecuperarClave(recuperarClave);
                        usuarioController.setGestionUsuarios(gestionUsuarios);

                        usuarioController.eventosLogin();

                        archivoVista.setVisible(false);
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

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(archivoVista, "Error al inicializar DAOs: " + ex.getMessage());
                    }


                }
            });
            // Mostrar selector de tipo de almacenamiento al inicio
            archivoVista.setVisible(true);

        });
    }
}