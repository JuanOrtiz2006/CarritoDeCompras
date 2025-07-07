package ec.edu.ups.vista;

import ec.edu.ups.util.Contexto;

import javax.swing.*;

public class MenuView extends JFrame {
    private JDesktopPane jDesktopPane;
    private JMenuBar menuBar;
    private JPanel panelGeneral;
    // Menús
    private JMenu menuProducto;
    private JMenu menuCarrito;
    private JMenu menuIdiomas;
    private JMenu menuUsuario;
    // Ítems
    private JMenuItem menuCrearProducto;
    private JMenuItem menuBuscarProducto;
    private JMenuItem menuActualizarProducto;
    private JMenuItem menuEliminarProducto;
    private JMenuItem menuListarProductos;
    private JMenuItem menuCrearCarrito;
    private JMenuItem menuListaCarrito;
    private JMenuItem menuEspaniol;
    private JMenuItem menuIngles;
    private JMenuItem menuFrances;
    private JMenuItem menuEditarUsuario;
    private JMenuItem menuCerrarSesion;
    private JMenuItem menuGestiosUsuarios;

    public MenuView() {
        setTitle("Menú");
        setSize(920, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jDesktopPane = new PanelGrafico();
        menuBar = new JMenuBar();
        setContentPane(jDesktopPane);
        setJMenuBar(menuBar);
    }
    // Getters y Setters para que el controlador configure la vista
    public JDesktopPane getjDesktopPane() {
        return jDesktopPane;
    }

    public JMenuItem getMenuCrearProducto() {
        return menuCrearProducto;
    }

    public JMenuItem getMenuBuscarProducto() {
        return menuBuscarProducto;
    }

    public JMenuItem getMenuActualizarProducto() {
        return menuActualizarProducto;
    }

    public JMenuItem getMenuEliminarProducto() {
        return menuEliminarProducto;
    }

    public JMenuItem getMenuListarProductos() {
        return menuListarProductos;
    }

    public JMenuItem getMenuCrearCarrito() {
        return menuCrearCarrito;
    }

    public JMenuItem getMenuListaCarrito() {
        return menuListaCarrito;
    }

    public JMenuItem getMenuEspaniol() {
        return menuEspaniol;
    }

    public JMenuItem getMenuIngles() {
        return menuIngles;
    }

    public JMenuItem getMenuFrances() {
        return menuFrances;
    }

    public JMenuItem getMenuEditarUsuario() {
        return menuEditarUsuario;
    }

    public JMenuItem getMenuCerrarSesion() {
        return menuCerrarSesion;
    }

    public JMenuItem getMenuGestiosUsuarios() {
        return menuGestiosUsuarios;
    }

    public void construirMenus(String rol) {
        var handler = Contexto.getHandler();
        menuBar.removeAll();

        // Menú Producto
        menuProducto = new JMenu(handler.get("menu.producto"));

        if (rol.equalsIgnoreCase("ADMINISTRADOR")) {
            menuCrearProducto = new JMenuItem(handler.get("menu.producto.crear"));
            menuActualizarProducto = new JMenuItem(handler.get("menu.producto.actualizar"));
            menuEliminarProducto = new JMenuItem(handler.get("menu.producto.eliminar"));
            menuProducto.add(menuCrearProducto);
            menuProducto.add(menuActualizarProducto);
            menuProducto.add(menuEliminarProducto);
        }

        menuBuscarProducto = new JMenuItem(handler.get("menu.producto.buscar"));
        menuListarProductos = new JMenuItem(handler.get("menu.producto.listar"));
        menuProducto.add(menuBuscarProducto);
        menuProducto.add(menuListarProductos);
        menuBar.add(menuProducto);

        // Menú Carrito
        menuCarrito = new JMenu(handler.get("menu.carrito"));
        menuCrearCarrito = new JMenuItem(handler.get("menu.carrito.crear"));
        menuListaCarrito = new JMenuItem(handler.get("menu.carrito.listar"));
        menuCarrito.add(menuCrearCarrito);
        menuCarrito.add(menuListaCarrito);
        menuBar.add(menuCarrito);

        // Menú Idiomas
        menuIdiomas = new JMenu(handler.get("menu.idiomas"));
        menuEspaniol = new JMenuItem(handler.get("menu.idiomas.espaniol"));
        menuIngles = new JMenuItem(handler.get("menu.idiomas.ingles"));
        menuFrances = new JMenuItem(handler.get("menu.idiomas.frances"));
        menuIdiomas.add(menuEspaniol);
        menuIdiomas.add(menuIngles);
        menuIdiomas.add(menuFrances);
        menuBar.add(menuIdiomas);

        // Menú Usuario
        menuUsuario = new JMenu(handler.get("menu.usuario"));
        menuEditarUsuario = new JMenuItem(handler.get("menu.usuario.editar"));
        menuUsuario.add(menuEditarUsuario);
        if (rol.equalsIgnoreCase("ADMINISTRADOR")) {
            menuGestiosUsuarios = new JMenuItem(handler.get("menu.usuario.gestion"));
            menuUsuario.add(menuGestiosUsuarios);
        }

        menuCerrarSesion = new JMenuItem(handler.get("menu.usuario.cerrar"));
        menuUsuario.add(menuCerrarSesion);

        menuBar.add(menuUsuario);
        revalidate();
        repaint();
    }

    public void actualizarIdioma(String rol) {
        construirMenus(rol);
    }


}
