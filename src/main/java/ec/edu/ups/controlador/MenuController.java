package ec.edu.ups.controlador;

import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.FormateadorUtils;
import ec.edu.ups.vista.*;

import ec.edu.ups.util.Contexto;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuController {
    private MenuView menuView;
    private Usuario usuario;
    private ProductoController productoController;
    private CarritoController carritoController;
    private UsuarioController usuarioController;
    private LoginView loginView;
    private RegistrarUsuario registrarUsuario;
    private PreguntasSeguridad preguntasSeguridad;
    private RecuperarClave recuperarClave;

    public MenuController(MenuView menuView, Usuario usuario) {
        this.menuView = menuView;
        this.usuario = usuario;
    }

    public void construirMenus() {
        menuView.construirMenus(usuario.getRol().toString());
        asignarEventos();
        menuView.setVisible(true);
    }

    private void asignarEventos() {
        // Producto
        if (menuView.getMenuCrearProducto() != null) {
            menuView.getMenuCrearProducto().addActionListener(e -> {
                CrearProductoView view = new CrearProductoView();
                productoController.setCrearProductoView(view);
                productoController.eventoCrearProducto();
                menuView.getjDesktopPane().add(view);
                view.setVisible(true);
            });
        }

        if (menuView.getMenuBuscarProducto() != null) {
            menuView.getMenuBuscarProducto().addActionListener(e -> {
                BuscarProducto view = new BuscarProducto();
                productoController.setBuscarProducto(view);
                productoController.eventoBuscarProducto();
                menuView.getjDesktopPane().add(view);
                view.setVisible(true);
            });
        }

        if (menuView.getMenuActualizarProducto() != null) {
            menuView.getMenuActualizarProducto().addActionListener(e -> {
                ActualizarProducto view = new ActualizarProducto();
                productoController.setActualizarProducto(view);
                productoController.eventoActualizarProducto();
                menuView.getjDesktopPane().add(view);
                view.setVisible(true);
            });
        }

        if (menuView.getMenuEliminarProducto() != null) {
            menuView.getMenuEliminarProducto().addActionListener(e -> {
                EliminarProducto view = new EliminarProducto();
                productoController.setEliminarProducto(view);
                productoController.eventoEliminarProducto();
                menuView.getjDesktopPane().add(view);
                view.setVisible(true);
            });
        }

        if (menuView.getMenuListarProductos() != null) {
            menuView.getMenuListarProductos().addActionListener(e -> {
                ListaProducto view = new ListaProducto();
                productoController.setListaProducto(view);
                productoController.eventoListarProductos();
                menuView.getjDesktopPane().add(view);
                view.setVisible(true);
            });
        }

        // Carrito
        menuView.getMenuCrearCarrito().addActionListener(e -> {
            CrearCarrito view = new CrearCarrito();
            carritoController.setCrearCarrito(view);
            carritoController.eventosCrearCarrito();
            menuView.getjDesktopPane().add(view);
            view.setVisible(true);
        });

        menuView.getMenuListaCarrito().addActionListener(e -> {
            ListaCarrito view = new ListaCarrito();
            carritoController.setListaCarrito(view);
            carritoController.eventosListarCarritos();
            menuView.getjDesktopPane().add(view);
            view.setVisible(true);
        });

        if(usuario.getRol().toString().equalsIgnoreCase(Contexto.getHandler().get("usuario.administrador"))){
            menuView.getMenuGestiosUsuarios().addActionListener(e -> {
                GestionUsuarios view = new GestionUsuarios();
                usuarioController.setGestionUsuarios(view);
                usuarioController.eventosGestionUsuario();
                menuView.getjDesktopPane().add(view);
                view.setVisible(true);
            });
        }

        menuView.getMenuEspaniol().addActionListener(e -> {
            Contexto.iniciarIdioma("es","EC");
            recargarIdioma();
        });

        menuView.getMenuIngles().addActionListener(e -> {
            Contexto.iniciarIdioma("en","US");
            recargarIdioma();
        });

        menuView.getMenuFrances().addActionListener(e -> {
            Contexto.iniciarIdioma("fr","FR");
            recargarIdioma();
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

            if (usuario != null) {
                usuarioController.editarUsuario(usuario.getUsername(),usuario.getRol());
                registrarUsuario.getTxtUsuario().setText(usuario.getNombre());
                registrarUsuario.getTxtCorreo().setText(usuario.getCorreo());
                registrarUsuario.getTxtTelefono().setText(usuario.getTelefono());
                registrarUsuario.getTxtUsuario().setText(usuario.getUsername());
                registrarUsuario.getTxtPassword().setText(usuario.getPassword());

                if (usuario.getFechanacimiento() != null) {
                    String fechaFormateada = FormateadorUtils.formatearFecha(
                            usuario.getFechanacimiento().getTime(), Contexto.getLocale()
                    );
                    registrarUsuario.getTxtFecha().setText(fechaFormateada);
                } else {
                    registrarUsuario.getTxtFecha().setText("");
                }

                registrarUsuario.setVisible(true);
            }
        });

    }

    public void recargarIdioma() {
        menuView.actualizarIdioma(usuario.getRol().toString());
        asignarEventos();
    }


    public void setProductoController(ProductoController productoController) {
        this.productoController = productoController;
    }

    public void setCarritoController(CarritoController carritoController) {
        this.carritoController = carritoController;
    }

    public void setUsuarioController(UsuarioController usuarioController) {
        this.usuarioController = usuarioController;
    }

    public JMenuItem getMenuCerrarSesion() {
        return menuView.getMenuCerrarSesion();
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setLoginView(LoginView loginView) {
        this.loginView = loginView;
    }

    public void setRegistrarUsuario(RegistrarUsuario registrarUsuario) {
        this.registrarUsuario = registrarUsuario;
    }

    public void setPreguntasSeguridad(PreguntasSeguridad preguntasSeguridad) {
        this.preguntasSeguridad = preguntasSeguridad;
    }

    public void setRecuperarClave(RecuperarClave recuperarClave) {
        this.recuperarClave = recuperarClave;
    }
}
