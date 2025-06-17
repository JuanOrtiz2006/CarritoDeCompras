package ec.edu.ups.vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuView extends JFrame {
    private JPanel panelGeneral;

    private JMenu menuProducto;
    private JMenuBar menuBar;
    private JMenuItem menuCrearProducto;
    private JMenuItem menuBuscarProducto;
    private JMenuItem menuActualizarProducto;
    private JMenuItem menuEliminarProducto;
    private JMenuItem menuListarProductos;
    private JDesktopPane jDesktopPane;
    private CrearProductoView crearProductoView;
    private BuscarProducto buscarProducto;

    public MenuView(){
        setTitle("Ejemplo de Menú");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jDesktopPane = new JDesktopPane();
        menuProducto = new JMenu("Producto");
        menuBar = new JMenuBar();
        menuCrearProducto = new JMenuItem("Crear Producto");
        menuBuscarProducto = new JMenuItem("BUscar Producto");
        menuActualizarProducto = new JMenuItem("Actualizar Producto");
        menuEliminarProducto = new JMenuItem("Eliminar Producto");
        menuListarProductos = new JMenuItem("Listar Producto");

        menuBar.add(menuProducto);
        menuProducto.add(menuCrearProducto);
        menuProducto.add(menuBuscarProducto);
        menuProducto.add(menuActualizarProducto);
        menuProducto.add(menuEliminarProducto);
        menuProducto.add(menuListarProductos);

        setJMenuBar(menuBar);
        setContentPane(jDesktopPane);
        setVisible(true);

        menuCrearProducto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearProductoView = new CrearProductoView();
                jDesktopPane.add(crearProductoView);
            }
        });

        menuBuscarProducto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProducto = new BuscarProducto();
                jDesktopPane.add(buscarProducto);
            }
        });
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
}
