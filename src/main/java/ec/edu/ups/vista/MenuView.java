package ec.edu.ups.vista;

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
        setSize(520, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jDesktopPane = new JDesktopPane();
        menuBar = new JMenuBar();

        setContentPane(jDesktopPane);
        setJMenuBar(menuBar); // No cambiar esto, JFrame usa javax.swing.JMenuBar
        setVisible(true);
    }

    public JDesktopPane getjDesktopPane() {
        return jDesktopPane;
    }
    public JMenuBar getBarraDeMenus() {
        return menuBar;
    }
    // Getters y Setters para que el controlador configure la vista

    public JMenu getMenuProducto() {
        return menuProducto;
    }

    public void setMenuProducto(JMenu menuProducto) {
        this.menuProducto = menuProducto;
    }

    public JMenu getMenuCarrito() {
        return menuCarrito;
    }

    public void setMenuCarrito(JMenu menuCarrito) {
        this.menuCarrito = menuCarrito;
    }

    public JMenu getMenuIdiomas() {
        return menuIdiomas;
    }

    public void setMenuIdiomas(JMenu menuIdiomas) {
        this.menuIdiomas = menuIdiomas;
    }

    public JMenu getMenuUsuario() {
        return menuUsuario;
    }

    public void setMenuUsuario(JMenu menuUsuario) {
        this.menuUsuario = menuUsuario;
    }

    public JMenuItem getMenuCrearProducto() {
        return menuCrearProducto;
    }

    public void setMenuCrearProducto(JMenuItem menuCrearProducto) {
        this.menuCrearProducto = menuCrearProducto;
    }

    public JMenuItem getMenuBuscarProducto() {
        return menuBuscarProducto;
    }

    public void setMenuBuscarProducto(JMenuItem menuBuscarProducto) {
        this.menuBuscarProducto = menuBuscarProducto;
    }

    public JMenuItem getMenuActualizarProducto() {
        return menuActualizarProducto;
    }

    public void setMenuActualizarProducto(JMenuItem menuActualizarProducto) {
        this.menuActualizarProducto = menuActualizarProducto;
    }

    public JMenuItem getMenuEliminarProducto() {
        return menuEliminarProducto;
    }

    public void setMenuEliminarProducto(JMenuItem menuEliminarProducto) {
        this.menuEliminarProducto = menuEliminarProducto;
    }

    public JMenuItem getMenuListarProductos() {
        return menuListarProductos;
    }

    public void setMenuListarProductos(JMenuItem menuListarProductos) {
        this.menuListarProductos = menuListarProductos;
    }

    public JMenuItem getMenuCrearCarrito() {
        return menuCrearCarrito;
    }

    public void setMenuCrearCarrito(JMenuItem menuCrearCarrito) {
        this.menuCrearCarrito = menuCrearCarrito;
    }

    public JMenuItem getMenuListaCarrito() {
        return menuListaCarrito;
    }

    public void setMenuListaCarrito(JMenuItem menuListaCarrito) {
        this.menuListaCarrito = menuListaCarrito;
    }

    public JMenuItem getMenuEspaniol() {
        return menuEspaniol;
    }

    public void setMenuEspaniol(JMenuItem menuEspaniol) {
        this.menuEspaniol = menuEspaniol;
    }

    public JMenuItem getMenuIngles() {
        return menuIngles;
    }

    public void setMenuIngles(JMenuItem menuIngles) {
        this.menuIngles = menuIngles;
    }

    public JMenuItem getMenuFrances() {
        return menuFrances;
    }

    public void setMenuFrances(JMenuItem menuFrances) {
        this.menuFrances = menuFrances;
    }

    public JMenuItem getMenuEditarUsuario() {
        return menuEditarUsuario;
    }

    public void setMenuEditarUsuario(JMenuItem menuEditarUsuario) {
        this.menuEditarUsuario = menuEditarUsuario;
    }

    public JMenuItem getMenuCerrarSesion() {
        return menuCerrarSesion;
    }

    public void setMenuCerrarSesion(JMenuItem menuCerrarSesion) {
        this.menuCerrarSesion = menuCerrarSesion;
    }

    public JMenuItem getMenuGestiosUsuarios() {
        return menuGestiosUsuarios;
    }

    public void setMenuGestiosUsuarios(JMenuItem menuGestiosUsuarios) {
        this.menuGestiosUsuarios = menuGestiosUsuarios;
    }

    public void setBarraDeMenus(JMenuBar menuBar) {
        this.menuBar = menuBar;
        setJMenuBar(menuBar); // actualizar barra visible
    }
}
