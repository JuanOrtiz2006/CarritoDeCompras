package ec.edu.ups.vista;

import ec.edu.ups.modelo.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuView extends JFrame {
    private JPanel panelGeneral;

    private JMenu menuProducto;
    private JMenu menuCarrito;
    private JMenu menuIdiomas;
    private JMenu menuUsuario;
    private JMenuBar menuBar;

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

    private JDesktopPane jDesktopPane;

    private Usuario usuario;

    public MenuView(Usuario usuario){
        this.usuario = usuario;

        setTitle("Ejemplo de Menú");
        setSize(520, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jDesktopPane = new JDesktopPane();

        menuBar = new JMenuBar();

        if(this.usuario.getRol().toString().equals("ADMINISTRADOR")){
            menuAdministrador();
        } else {
            menuUsuario();
        }

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

    private void menuUsuario(){
        menuProducto = new JMenu("Producto");
        menuBuscarProducto = new JMenuItem("Buscar Producto");
        menuListarProductos = new JMenuItem("Listar Producto");
        menuProducto.add(menuBuscarProducto);
        menuProducto.add(menuListarProductos);

        menuCarrito = new JMenu("Carrito");
        menuCrearCarrito = new JMenuItem("Agregar Productos al Carrito");
        menuListaCarrito = new JMenuItem("Listar Carritos");
        menuCarrito.add(menuCrearCarrito);
        menuCarrito.add(menuListaCarrito);
        menuBar.add(menuProducto);
        menuBar.add(menuCarrito);

        menuIdiomas();
        menuEditarUsuario();
        setJMenuBar(menuBar);
    }

    private void menuAdministrador(){
        menuProducto = new JMenu("Producto");
        menuCrearProducto = new JMenuItem("Crear Producto");
        menuBuscarProducto = new JMenuItem("Buscar Producto");
        menuActualizarProducto = new JMenuItem("Actualizar Producto");
        menuEliminarProducto = new JMenuItem("Eliminar Producto");
        menuListarProductos = new JMenuItem("Listar Producto");

        menuProducto.add(menuCrearProducto);
        menuProducto.add(menuBuscarProducto);
        menuProducto.add(menuActualizarProducto);
        menuProducto.add(menuEliminarProducto);
        menuProducto.add(menuListarProductos);

        menuCarrito = new JMenu("Carrito");
        menuCrearCarrito = new JMenuItem("Agregar Productos al Carrito");
        menuListaCarrito = new JMenuItem("Listar Carritos");
        menuCarrito.add(menuCrearCarrito);
        menuCarrito.add(menuListaCarrito);

        menuBar.add(menuProducto);
        menuBar.add(menuCarrito);
        menuIdiomas();
        menuEditarUsuario();
        setJMenuBar(menuBar);
    }

    private void menuIdiomas(){
        menuIdiomas = new JMenu("Idiomas");
        menuEspaniol = new JMenuItem("Español");
        menuIngles = new JMenuItem("Ingles");
        menuFrances = new JMenuItem("Frances");

        menuIdiomas.add(menuEspaniol);
        menuIdiomas.add(menuIngles);
        menuIdiomas.add(menuFrances);

        menuBar.add(menuIdiomas);
    }

    private void menuEditarUsuario(){
        menuUsuario = new JMenu("Usuario");
        menuEditarUsuario = new JMenuItem("Editar");
        menuCerrarSesion = new JMenuItem("Cerrar Sesion");

        menuUsuario.add(menuEditarUsuario);
        menuUsuario.add(menuCerrarSesion);

        menuBar.add(menuUsuario);
    }

}
