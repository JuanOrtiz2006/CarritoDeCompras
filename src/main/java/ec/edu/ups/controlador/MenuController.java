package ec.edu.ups.controlador;

import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.vista.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MenuController {
    private MenuView menuView;
    private Usuario usuario;
    private ProductoController productoController;
    private CarritoController carritoController;
    private UsuarioController usuarioController;
    private List<Carrito> listaCarritos;

    public MenuController(MenuView menuView, Usuario usuario, ProductoController productoController,
                          CarritoController carritoController, UsuarioController usuarioController, List<Carrito> listaCarritos) {
        this.menuView = menuView;
        this.usuario = usuario;
        this.productoController = productoController;
        this.carritoController = carritoController;
        this.usuarioController = usuarioController;
        this.listaCarritos = listaCarritos;

        construirMenus();
        asignarEventos();
    }

    private void construirMenus() {
        JMenuBar menuBar = menuView.getBarraDeMenus();

        // Menú Producto
        JMenu menuProducto = new JMenu("Producto");

        if (usuario.getRol().toString().equalsIgnoreCase("ADMINISTRADOR")) {
            menuView.setMenuCrearProducto(new JMenuItem("Crear Producto"));
            menuView.setMenuBuscarProducto(new JMenuItem("Buscar Producto"));
            menuView.setMenuActualizarProducto(new JMenuItem("Actualizar Producto"));
            menuView.setMenuEliminarProducto(new JMenuItem("Eliminar Producto"));
            menuView.setMenuListarProductos(new JMenuItem("Listar Productos"));

            menuProducto.add(menuView.getMenuCrearProducto());
            menuProducto.add(menuView.getMenuBuscarProducto());
            menuProducto.add(menuView.getMenuActualizarProducto());
            menuProducto.add(menuView.getMenuEliminarProducto());
            menuProducto.add(menuView.getMenuListarProductos());
        } else {
            menuView.setMenuBuscarProducto(new JMenuItem("Buscar Producto"));
            menuView.setMenuListarProductos(new JMenuItem("Listar Productos"));

            menuProducto.add(menuView.getMenuBuscarProducto());
            menuProducto.add(menuView.getMenuListarProductos());
        }

        menuView.setMenuProducto(menuProducto);
        menuBar.add(menuProducto);

        // Menú Carrito
        JMenu menuCarrito = new JMenu("Carrito");
        menuView.setMenuCrearCarrito(new JMenuItem("Agregar Productos al Carrito"));
        menuView.setMenuListaCarrito(new JMenuItem("Listar Carritos"));

        menuCarrito.add(menuView.getMenuCrearCarrito());
        menuCarrito.add(menuView.getMenuListaCarrito());
        menuView.setMenuCarrito(menuCarrito);
        menuBar.add(menuCarrito);

        // Menú Idiomas
        JMenu menuIdiomas = new JMenu("Idiomas");
        menuView.setMenuEspaniol(new JMenuItem("Español"));
        menuView.setMenuIngles(new JMenuItem("Inglés"));
        menuView.setMenuFrances(new JMenuItem("Francés"));
        menuIdiomas.add(menuView.getMenuEspaniol());
        menuIdiomas.add(menuView.getMenuIngles());
        menuIdiomas.add(menuView.getMenuFrances());
        menuView.setMenuIdiomas(menuIdiomas);
        menuBar.add(menuIdiomas);

        // Menú Usuario
        JMenu menuUsuario = new JMenu("Usuario");
        menuView.setMenuEditarUsuario(new JMenuItem("Editar"));
        menuView.setMenuCerrarSesion(new JMenuItem("Cerrar Sesión"));
        menuUsuario.add(menuView.getMenuEditarUsuario());
        menuUsuario.add(menuView.getMenuCerrarSesion());
        menuView.setMenuUsuario(menuUsuario);
        menuBar.add(menuUsuario);
        if(usuario.getRol().toString().equalsIgnoreCase("ADMINISTRADOR")){
            menuView.setMenuGestiosUsuarios(new JMenuItem("Gestionar Usuarios"));
            menuUsuario.add(menuView.getMenuGestiosUsuarios());
        }
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
            carritoController.crearCarrito();
            menuView.getjDesktopPane().add(view);
            view.setVisible(true);
        });

        menuView.getMenuListaCarrito().addActionListener(e -> {
            ListaCarrito view = new ListaCarrito();
            carritoController.setListaCarrito(view);
            carritoController.listarCarritos();
            menuView.getjDesktopPane().add(view);
            view.setVisible(true);
        });

        menuView.getMenuGestiosUsuarios().addActionListener(e -> {
            GestionUsuarios view = new GestionUsuarios();
            usuarioController.setGestionUsuarios(view);
            usuarioController.eventosGestionUsuario();
            menuView.getjDesktopPane().add(view);
            view.setVisible(true);
        });

        // (Idiomas y usuario se pueden implementar luego aquí)
    }
}
