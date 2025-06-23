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
    private JMenuItem menuCrearCarrito;
    private JMenuItem menuListaCarrito;

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
        menuCrearCarrito = new JMenuItem("Agregar Productos al Carrito");
        menuListaCarrito = new JMenuItem("Listar Carritos");

        menuBar.add(menuProducto);
        menuBar.add(menuCarrito);

        menuProducto.add(menuCrearProducto);
        menuProducto.add(menuBuscarProducto);
        menuProducto.add(menuActualizarProducto);
        menuProducto.add(menuEliminarProducto);
        menuProducto.add(menuListarProductos);

        menuCarrito.add(menuCrearCarrito);
        menuCarrito.add(menuListaCarrito);

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

    public JMenuItem getMenuCrearCarrito() {
        return menuCrearCarrito;
    }

    public JMenuItem getMenuListaCarrito() {
        return menuListaCarrito;
    }

    public JDesktopPane getjDesktopPane() {
        return jDesktopPane;
    }



}
