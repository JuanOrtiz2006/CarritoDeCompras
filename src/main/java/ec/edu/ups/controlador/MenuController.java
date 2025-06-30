package ec.edu.ups.controlador;

import ec.edu.ups.modelo.Usuario;
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

    public MenuController(MenuView menuView, Usuario usuario) {
        this.menuView = menuView;
        this.usuario = usuario;

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



    public void construirMenus() {
        JMenuBar menuBar = menuView.getBarraDeMenus();

        // Menú Producto
        JMenu menuProducto = new JMenu(Contexto.getHandler().get("menu.producto"));

        if (usuario.getRol().toString().equalsIgnoreCase("ADMINISTRADOR")) {
            menuView.setMenuCrearProducto(new JMenuItem(Contexto.getHandler().get("menu.producto.crear")));
            menuView.setMenuBuscarProducto(new JMenuItem(Contexto.getHandler().get("menu.producto.buscar")));
            menuView.setMenuActualizarProducto(new JMenuItem(Contexto.getHandler().get("menu.producto.actualizar")));
            menuView.setMenuEliminarProducto(new JMenuItem(Contexto.getHandler().get("menu.producto.eliminar")));
            menuView.setMenuListarProductos(new JMenuItem(Contexto.getHandler().get("menu.producto.listar")));

            menuProducto.add(menuView.getMenuCrearProducto());
            menuProducto.add(menuView.getMenuBuscarProducto());
            menuProducto.add(menuView.getMenuActualizarProducto());
            menuProducto.add(menuView.getMenuEliminarProducto());
            menuProducto.add(menuView.getMenuListarProductos());
        } else {
            menuView.setMenuBuscarProducto(new JMenuItem(Contexto.getHandler().get("menu.producto.buscar")));
            menuView.setMenuListarProductos(new JMenuItem(Contexto.getHandler().get("menu.producto.listar")));

            menuProducto.add(menuView.getMenuBuscarProducto());
            menuProducto.add(menuView.getMenuListarProductos());
        }

        menuView.setMenuProducto(menuProducto);
        menuBar.add(menuProducto);

        // Menú Carrito
        JMenu menuCarrito = new JMenu(Contexto.getHandler().get("menu.carrito"));
        menuView.setMenuCrearCarrito(new JMenuItem(Contexto.getHandler().get("menu.carrito.crear")));
        menuView.setMenuListaCarrito(new JMenuItem(Contexto.getHandler().get("menu.carrito.listar")));

        menuCarrito.add(menuView.getMenuCrearCarrito());
        menuCarrito.add(menuView.getMenuListaCarrito());
        menuView.setMenuCarrito(menuCarrito);
        menuBar.add(menuCarrito);

        // Menú Idiomas
        JMenu menuIdiomas = new JMenu(Contexto.getHandler().get("menu.idiomas"));
        menuView.setMenuEspaniol(new JMenuItem(Contexto.getHandler().get("menu.idiomas.espaniol")));
        menuView.setMenuIngles(new JMenuItem(Contexto.getHandler().get("menu.idiomas.ingles")));
        menuView.setMenuFrances(new JMenuItem(Contexto.getHandler().get("menu.idiomas.frances")));
        menuIdiomas.add(menuView.getMenuEspaniol());
        menuIdiomas.add(menuView.getMenuIngles());
        menuIdiomas.add(menuView.getMenuFrances());
        menuView.setMenuIdiomas(menuIdiomas);
        menuBar.add(menuIdiomas);

        // Menú Usuario
        JMenu menuUsuario = new JMenu(Contexto.getHandler().get("menu.usuario"));
        menuView.setMenuEditarUsuario(new JMenuItem(Contexto.getHandler().get("menu.usuario.editar")));
        menuView.setMenuCerrarSesion(new JMenuItem(Contexto.getHandler().get("menu.usuario.cerrar")));
        menuUsuario.add(menuView.getMenuEditarUsuario());
        menuUsuario.add(menuView.getMenuCerrarSesion());
        menuView.setMenuUsuario(menuUsuario);
        menuBar.add(menuUsuario);
        if(usuario.getRol().toString().equalsIgnoreCase("ADMINISTRADOR")){
            menuView.setMenuGestiosUsuarios(new JMenuItem(Contexto.getHandler().get("menu.usuario.gestion")));
            menuUsuario.add(menuView.getMenuGestiosUsuarios());
        }

        menuView.setVisible(true);
        asignarEventos();
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
            carritoController.eventosParaCrearCarrito();
            menuView.getjDesktopPane().add(view);
            view.setVisible(true);
        });

        menuView.getMenuListaCarrito().addActionListener(e -> {
            ListaCarrito view = new ListaCarrito();
            carritoController.setListaCarrito(view);
            carritoController.eventosEnListarCarritos();
            menuView.getjDesktopPane().add(view);
            view.setVisible(true);
        });

        if(usuario.getRol().toString().equalsIgnoreCase("ADMINISTRADOR")){
            menuView.getMenuGestiosUsuarios().addActionListener(e -> {
                GestionUsuarios view = new GestionUsuarios();
                usuarioController.setGestionUsuarios(view);
                usuarioController.eventosGestionUsuario();
                menuView.getjDesktopPane().add(view);
                view.setVisible(true);
            });
        }

        menuView.getMenuEditarUsuario().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usuarioController.editarUsuario(usuario.getUsername(),usuario.getRol());
            }
        });

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
        // (Idiomas y usuario se pueden implementar luego aquí)
    }

    public JMenuItem getMenuCerrarSesion() {
        return menuView.getMenuCerrarSesion();
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void recargarIdioma() {
        // Actualizar textos del menú producto
        menuView.getMenuProducto().setText(Contexto.getHandler().get("menu.producto"));

        if (usuario.getRol().toString().equalsIgnoreCase("ADMINISTRADOR")) {
            if (menuView.getMenuCrearProducto() != null)
                menuView.getMenuCrearProducto().setText(Contexto.getHandler().get("menu.producto.crear"));
            if (menuView.getMenuBuscarProducto() != null)
                menuView.getMenuBuscarProducto().setText(Contexto.getHandler().get("menu.producto.buscar"));
            if (menuView.getMenuActualizarProducto() != null)
                menuView.getMenuActualizarProducto().setText(Contexto.getHandler().get("menu.producto.actualizar"));
            if (menuView.getMenuEliminarProducto() != null)
                menuView.getMenuEliminarProducto().setText(Contexto.getHandler().get("menu.producto.eliminar"));
            if (menuView.getMenuListarProductos() != null)
                menuView.getMenuListarProductos().setText(Contexto.getHandler().get("menu.producto.listar"));
        } else {
            if (menuView.getMenuBuscarProducto() != null)
                menuView.getMenuBuscarProducto().setText(Contexto.getHandler().get("menu.producto.buscar"));
            if (menuView.getMenuListarProductos() != null)
                menuView.getMenuListarProductos().setText(Contexto.getHandler().get("menu.producto.listar"));
        }

        // Actualizar textos del menú carrito
        menuView.getMenuCarrito().setText(Contexto.getHandler().get("menu.carrito"));
        menuView.getMenuCrearCarrito().setText(Contexto.getHandler().get("menu.carrito.crear"));
        menuView.getMenuListaCarrito().setText(Contexto.getHandler().get("menu.carrito.listar"));

        // Actualizar textos del menú idiomas
        menuView.getMenuIdiomas().setText(Contexto.getHandler().get("menu.idiomas"));
        menuView.getMenuEspaniol().setText(Contexto.getHandler().get("menu.idiomas.espaniol"));
        menuView.getMenuIngles().setText(Contexto.getHandler().get("menu.idiomas.ingles"));
        menuView.getMenuFrances().setText(Contexto.getHandler().get("menu.idiomas.frances"));

        // Actualizar textos del menú usuario
        menuView.getMenuUsuario().setText(Contexto.getHandler().get("menu.usuario"));
        menuView.getMenuEditarUsuario().setText(Contexto.getHandler().get("menu.usuario.editar"));
        menuView.getMenuCerrarSesion().setText(Contexto.getHandler().get("menu.usuario.cerrar"));

        if(usuario.getRol().toString().equalsIgnoreCase("ADMINISTRADOR") && menuView.getMenuGestiosUsuarios() != null) {
            menuView.getMenuGestiosUsuarios().setText(Contexto.getHandler().get("menu.usuario.gestion"));
        }

        // Refrescar la barra para que se muestren los cambios
        menuView.getBarraDeMenus().revalidate();
        menuView.getBarraDeMenus().repaint();
    }
}
