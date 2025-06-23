package ec.edu.ups.vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuView extends JFrame {
    private JPanel panelGeneral;

    private JMenu menuProducto;
    private JMenu menuCarrito;
    private JMenuBar menuBar;

    private JMenuItem menuCrearProducto;
    private JMenuItem menuBuscarProducto;
    private JMenuItem menuActualizarProducto;
    private JMenuItem menuEliminarProducto;
    private JMenuItem menuListarProductos;
    private JMenuItem menuAgregarProducto;
    private JMenuItem menuEliminarItem;
    private JMenuItem menuListarItems;

    private JDesktopPane jDesktopPane;

    public MenuView(){
        setTitle("Ejemplo de Menú");
        setSize(520, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jDesktopPane = new JDesktopPane();
        menuProducto = new JMenu("Producto");
        menuBar = new JMenuBar();
        menuCrearProducto = new JMenuItem("Crear Producto");
        menuBuscarProducto = new JMenuItem("Buscar Producto");
        menuActualizarProducto = new JMenuItem("Actualizar Producto");
        menuEliminarProducto = new JMenuItem("Eliminar Producto");
        menuListarProductos = new JMenuItem("Listar Producto");

        menuCarrito = new JMenu("Carrito");
        menuAgregarProducto = new JMenuItem("Agregar Producto");
        menuEliminarItem = new JMenuItem("Listar Carritos");
        menuListarItems = new JMenuItem("Actualizar Carrito");

        menuBar.add(menuProducto);
        menuBar.add(menuCarrito);

        menuProducto.add(menuCrearProducto);
        menuProducto.add(menuBuscarProducto);
        menuProducto.add(menuActualizarProducto);
        menuProducto.add(menuEliminarProducto);
        menuProducto.add(menuListarProductos);

        menuCarrito.add(menuAgregarProducto);
        menuCarrito.add(menuEliminarItem);
        menuCarrito.add(menuListarItems);

        setJMenuBar(menuBar);
        setContentPane(jDesktopPane);
        setVisible(true);

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

    public JDesktopPane getjDesktopPane() {
        return jDesktopPane;
    }

    public JMenuItem getMenuAgregarProducto() {
        return menuAgregarProducto;
    }

    public JMenuItem getMenuEliminarItem() {
        return menuEliminarItem;
    }

    public JMenuItem getMenuListarItems() {
        return menuListarItems;
    }



    public void setMenuCrearProducto(JMenuItem menuCrearProducto) {
        this.menuCrearProducto = menuCrearProducto;
    }

    public void setMenuBuscarProducto(JMenuItem menuBuscarProducto) {
        this.menuBuscarProducto = menuBuscarProducto;
    }

    public void setMenuActualizarProducto(JMenuItem menuActualizarProducto) {
        this.menuActualizarProducto = menuActualizarProducto;
    }

    public void setMenuEliminarProducto(JMenuItem menuEliminarProducto) {
        this.menuEliminarProducto = menuEliminarProducto;
    }

    public void setMenuListarProductos(JMenuItem menuListarProductos) {
        this.menuListarProductos = menuListarProductos;
    }

    public void setjDesktopPane(JDesktopPane jDesktopPane) {
        this.jDesktopPane = jDesktopPane;
    }

    public void setMenuAgregarProducto(JMenuItem menuAgregarProducto) {
        this.menuAgregarProducto = menuAgregarProducto;
    }

    public void setMenuEliminarItem(JMenuItem menuEliminarItem) {
        this.menuEliminarItem = menuEliminarItem;
    }

    public void setMenuListarItems(JMenuItem menuListarItems) {
        this.menuListarItems = menuListarItems;
    }
}
