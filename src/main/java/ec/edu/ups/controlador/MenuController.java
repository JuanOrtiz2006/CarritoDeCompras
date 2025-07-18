package ec.edu.ups.controlador;

import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.FormateadorUtils;
import ec.edu.ups.vista.*;

import ec.edu.ups.util.Contexto;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controlador para la gestión del menú principal de la aplicación.
 * Administra la construcción de menús, asignación de eventos y navegación entre vistas
 * según el rol del usuario autenticado.
 *
 * @author JuanOrtiz2006
 * @version 1.0
 */
public class MenuController {
    private MenuView menuView;
    private Usuario usuario;
    private ProductoController productoController;
    private CarritoController carritoController;
    private UsuarioController usuarioController;
    private LoginView loginView;
    private RegistrarUsuario registrarUsuario;
    private PreguntasSeguridad preguntasSeguridad;

    /**
     * Constructor que inicializa el controlador con la vista de menú y el usuario autenticado.
     *
     * @param menuView Vista del menú principal.
     * @param usuario Usuario autenticado.
     */
    public MenuController(MenuView menuView, Usuario usuario) {
        this.menuView = menuView;
        this.usuario = usuario;
    }

    /**
     * Construye los menús según el rol del usuario y asigna los eventos correspondientes.
     */
    public void construirMenus() {
        try {
            if (usuario == null || usuario.getRol() == null) {
                throw new IllegalStateException("Usuario o rol no válido");
            }

            menuView.construirMenus(usuario.getRol().toString());
            asignarEventos();
            menuView.setVisible(true);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Error al construir menús: " + e.getMessage(),
                    "Error crítico",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Asigna los eventos de acción a los elementos del menú.
     * Incluye la navegación entre vistas de productos, carritos y usuarios,
     * así como el cambio de idioma y cierre de sesión.
     */
    private void asignarEventos() {
        // Producto
        if (menuView.getMenuCrearProducto() != null) {
            menuView.getMenuCrearProducto().addActionListener(e -> {
                if (productoController == null) {
                    JOptionPane.showMessageDialog(menuView,
                            "Error: Controlador de producto no inicializado",
                            "Error del sistema",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    CrearProductoView view = new CrearProductoView();
                    productoController.setCrearProductoView(view);
                    productoController.eventoCrearProducto();
                    menuView.getjDesktopPane().add(view);
                    view.setVisible(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(menuView,
                            "Error al abrir la vista: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            });
        }

        if (menuView.getMenuBuscarProducto() != null) {
            menuView.getMenuBuscarProducto().addActionListener(e -> {
                if (productoController == null) {
                    JOptionPane.showMessageDialog(menuView,
                            "Error: Controlador de producto no inicializado",
                            "Error del sistema",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    BuscarProducto view = new BuscarProducto();
                    productoController.setBuscarProducto(view);
                    productoController.eventoBuscarProducto();
                    menuView.getjDesktopPane().add(view);
                    view.setVisible(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(menuView,
                            "Error al abrir la vista: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            });
        }

        if (menuView.getMenuActualizarProducto() != null) {
            menuView.getMenuActualizarProducto().addActionListener(e -> {
                if (productoController == null) {
                    JOptionPane.showMessageDialog(menuView,
                            "Error: Controlador de producto no inicializado",
                            "Error del sistema",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    ActualizarProducto view = new ActualizarProducto();
                    productoController.setActualizarProducto(view);
                    productoController.eventoActualizarProducto();
                    menuView.getjDesktopPane().add(view);
                    view.setVisible(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(menuView,
                            "Error al abrir la vista: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            });
        }

        if (menuView.getMenuEliminarProducto() != null) {
            menuView.getMenuEliminarProducto().addActionListener(e -> {
                if (productoController == null) {
                    JOptionPane.showMessageDialog(menuView,
                            "Error: Controlador de producto no inicializado",
                            "Error del sistema",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    EliminarProducto view = new EliminarProducto();
                    productoController.setEliminarProducto(view);
                    productoController.eventoEliminarProducto();
                    menuView.getjDesktopPane().add(view);
                    view.setVisible(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(menuView,
                            "Error al abrir la vista: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            });
        }

        if (menuView.getMenuListarProductos() != null) {
            menuView.getMenuListarProductos().addActionListener(e -> {
                if (productoController == null) {
                    JOptionPane.showMessageDialog(menuView,
                            "Error: Controlador de producto no inicializado",
                            "Error del sistema",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    ListaProducto view = new ListaProducto();
                    productoController.setListaProducto(view);
                    productoController.eventoListarProductos();
                    menuView.getjDesktopPane().add(view);
                    view.setVisible(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(menuView,
                            "Error al abrir la vista: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            });
        }

        // Carrito
        menuView.getMenuCrearCarrito().addActionListener(e -> {
            if (carritoController == null) {
                JOptionPane.showMessageDialog(menuView,
                        "Error: Controlador de carrito no inicializado",
                        "Error del sistema",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                CrearCarrito view = new CrearCarrito();
                carritoController.setCrearCarrito(view);
                carritoController.eventosCrearCarrito();
                menuView.getjDesktopPane().add(view);
                view.setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(menuView,
                        "Error al abrir la vista: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        menuView.getMenuListaCarrito().addActionListener(e -> {
            if (carritoController == null) {
                JOptionPane.showMessageDialog(menuView,
                        "Error: Controlador de carrito no inicializado",
                        "Error del sistema",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                ListaCarrito view = new ListaCarrito();
                carritoController.setListaCarrito(view);
                carritoController.eventosListarCarritos();
                menuView.getjDesktopPane().add(view);
                view.setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(menuView,
                        "Error al abrir la vista: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        //Usuario
        if(usuario.getRol().toString().equalsIgnoreCase(Contexto.getHandler().get("usuario.administrador"))){
            menuView.getMenuGestiosUsuarios().addActionListener(e -> {
                if (usuarioController == null) {
                    JOptionPane.showMessageDialog(menuView,
                            "Error: Controlador de usuario no inicializado",
                            "Error del sistema",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    GestionUsuarios view = new GestionUsuarios();
                    usuarioController.setGestionUsuarios(view);
                    usuarioController.eventosGestionUsuario();
                    menuView.getjDesktopPane().add(view);
                    view.setVisible(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(menuView,
                            "Error al abrir la vista: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            });
        }

        menuView.getMenuEspaniol().addActionListener(e -> {
            try {
                Contexto.iniciarIdioma("es","EC");
                recargarIdioma();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(menuView,
                        "Error al cambiar idioma: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        menuView.getMenuIngles().addActionListener(e -> {
            try {
                Contexto.iniciarIdioma("en","US");
                recargarIdioma();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(menuView,
                        "Error al cambiar idioma: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        menuView.getMenuFrances().addActionListener(e -> {
            try {
                Contexto.iniciarIdioma("fr","FR");
                recargarIdioma();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(menuView,
                        "Error al cambiar idioma: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        menuView.getMenuCerrarSesion().addActionListener(e -> {
            int opcion = JOptionPane.showConfirmDialog(menuView,
                    Contexto.getHandler().get("usuario.confirmar.cerrarsesion"),
                    Contexto.getHandler().get("confirmacion"),
                    JOptionPane.YES_NO_OPTION
            );
            if (opcion == JOptionPane.YES_OPTION) {
                menuView.dispose();
                loginView.actualizarIdioma();
                loginView.setVisible(true);
            }
        });

        menuView.getMenuEditarUsuario().addActionListener(e -> {
            try {
                if (usuario == null) {
                    JOptionPane.showMessageDialog(menuView,
                            "Error: Usuario no válido",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (usuarioController == null) {
                    JOptionPane.showMessageDialog(menuView,
                            "Error: Controlador de usuario no inicializado",
                            "Error del sistema",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (registrarUsuario == null) {
                    JOptionPane.showMessageDialog(menuView,
                            "Error: Vista de registro no inicializada",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validar campos del usuario antes de cargar
                usuarioController.editarUsuario(usuario.getUsername(), usuario.getRol());

                // Cargar datos de manera segura
                cargarDatosUsuario();

                registrarUsuario.setVisible(true);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(menuView,
                        "Error al editar usuario: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    /**
     * Carga los datos del usuario en la vista de registro de usuario.
     * Utiliza los datos actuales del usuario autenticado.
     */
    private void cargarDatosUsuario() {
        if (usuario.getNombre() != null) {
            registrarUsuario.getTxtUsuario().setText(usuario.getNombre());
        }
        if (usuario.getCorreo() != null) {
            registrarUsuario.getTxtCorreo().setText(usuario.getCorreo());
        }
        if (usuario.getTelefono() != null) {
            registrarUsuario.getTxtTelefono().setText(usuario.getTelefono());
        }
        if (usuario.getUsername() != null) {
            registrarUsuario.getTxtUsuario().setText(usuario.getUsername());
        }
        if (usuario.getPassword() != null) {
            registrarUsuario.getTxtPassword().setText(usuario.getPassword());
        }

        if (usuario.getFechanacimiento() != null) {
            try {
                String fechaFormateada = FormateadorUtils.formatearFecha(
                        usuario.getFechanacimiento().getTime(), Contexto.getLocale()
                );
                registrarUsuario.getTxtFecha().setText(fechaFormateada);
            } catch (Exception e) {
                registrarUsuario.getTxtFecha().setText("");
            }
        } else {
            registrarUsuario.getTxtFecha().setText("");
        }
    }

    /**
     * Recarga los textos de la interfaz según el idioma seleccionado.
     * Actualiza los menús y reasigna los eventos.
     */
    public void recargarIdioma() {
        try {
            if (usuario == null || usuario.getRol() == null) {
                throw new IllegalStateException("Usuario no válido para recargar idioma");
            }

            menuView.actualizarIdioma(usuario.getRol().toString());
            asignarEventos();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(menuView,
                    "Error al recargar idioma: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }


    /**
     * Asigna el controlador de productos.
     *
     * @param productoController Controlador de productos.
     */
    public void setProductoController(ProductoController productoController) {
        this.productoController = productoController;
    }

    /**
     * Asigna el controlador de carritos.
     *
     * @param carritoController Controlador de carritos.
     */
    public void setCarritoController(CarritoController carritoController) {
        this.carritoController = carritoController;
    }

    /**
     * Asigna el controlador de usuarios.
     *
     * @param usuarioController Controlador de usuarios.
     */
    public void setUsuarioController(UsuarioController usuarioController) {
        this.usuarioController = usuarioController;
    }

    /**
     * Obtiene el menú para cerrar sesión.
     *
     * @return JMenuItem para cerrar sesión.
     */
    public JMenuItem getMenuCerrarSesion() {
        return menuView.getMenuCerrarSesion();
    }

    /**
     * Asigna el usuario autenticado.
     *
     * @param usuario Usuario autenticado.
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Asigna la vista de login.
     *
     * @param loginView Vista de login.
     */
    public void setLoginView(LoginView loginView) {
        this.loginView = loginView;
    }

    /**
     * Asigna la vista de registro de usuario.
     *
     * @param registrarUsuario Vista de registro de usuario.
     */
    public void setRegistrarUsuario(RegistrarUsuario registrarUsuario) {
        this.registrarUsuario = registrarUsuario;
    }

    /**
     * Asigna la vista de preguntas de seguridad.
     *
     * @param preguntasSeguridad Vista de preguntas de seguridad.
     */
    public void setPreguntasSeguridad(PreguntasSeguridad preguntasSeguridad) {
        this.preguntasSeguridad = preguntasSeguridad;
    }
}

