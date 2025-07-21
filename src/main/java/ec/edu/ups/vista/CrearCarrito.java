package ec.edu.ups.vista;

import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.util.Contexto;
import ec.edu.ups.util.LimiteCaracter;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.net.URL;

/**
 * Vista (JInternalFrame) para la creación de un carrito de compras.
 * Permite ingresar datos del carrito, seleccionar productos y agregar items al carrito.
 * Incluye soporte para internacionalización de textos y carga dinámica de productos.
 *
 * @author JuanOrtiz2006
 * @version 1.0
 */
public class CrearCarrito extends JInternalFrame{
    /**
     * Componentes de la interfaz gráfica.
     */
    private JPanel panelGeneral;
    private JTextField txtCodigoCarrito;
    private JTextField txtFecha;
    private JPanel panelDatosCarrito;
    private JPanel panelCodigoCarro;
    private JLabel lblCodigoCarrto;
    private JLabel lblFecha;
    private JPanel panelNorte;
    private JPanel panelDatos;
    private JPanel panelProducto;
    private JLabel lblProducto;
    private JPanel panelDatosProducto;
    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JButton btnSeleccionar;
    private JPanel panelCantidad;
    private JLabel lblCantidad;
    private JTextField txtCantidad;
    private JButton btnAgregar;
    private JPanel panelCentral;
    private JTable tblProductos;
    private JPanel panelSur;
    private JButton btnGuardar;
    private JTable tblTotal;
    private JButton btnVaciar;
    private JButton btnEditar;
    private DefaultTableModel modeloItems;
    private DefaultTableModel modeloTotales;

    /**
     * Constructor que inicializa la vista de creación de carrito.
     * Configura el título, tamaño, iconos y validaciones de los campos de entrada.
     */
    public CrearCarrito(){
        setContentPane(panelGeneral);
        setTitle(Contexto.getHandler().get("crearcarrito.titulo"));
        setSize(900, 500);
        setClosable(true);
        setMaximizable(true);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);

        modeloItems = new DefaultTableModel();
        tblProductos.setModel(modeloItems);
        modeloTotales = new DefaultTableModel();
        tblTotal.setModel(modeloTotales);

        btnSeleccionar.setIcon(cargarIcono("check.png"));
        btnAgregar.setIcon(cargarIcono("plus.png"));
        btnVaciar.setIcon(cargarIcono("clear.png"));
        btnGuardar.setIcon(cargarIcono("shoppingCart.png"));
        btnEditar.setIcon(cargarIcono("edit.png"));

        actualizarIdioma();
        validaciones();

        txtCodigoCarrito.setEnabled(false);
        txtNombre.setEnabled(false);
        txtPrecio.setEnabled(false);
        txtFecha.setEnabled(false);

    }

    /**
     * Configura las validaciones de los campos de texto.
     * Limita la cantidad de caracteres y establece un filtro para el campo de cantidad.
     */
    public void actualizarIdioma() {
        var handler = Contexto.getHandler();
        setTitle(handler.get("crearcarrito.titulo"));

        // Etiquetas
        lblCodigoCarrto.setText(handler.get("crearcarrito.codigo"));
        lblFecha.setText(handler.get("crearcarrito.fecha"));
        lblProducto.setText(handler.get("crearcarrito.producto"));
        lblCantidad.setText(handler.get("crearcarrito.cantidad"));

        // Textos en campos
        txtCodigo.setText(handler.get("crearcarrito.columna.id"));
        txtNombre.setText(handler.get("crearcarrito.columna.nombre"));
        txtPrecio.setText(handler.get("crearcarrito.columna.precio"));
        txtCantidad.setText(handler.get("crearcarrito.columna.cantidad"));

        // Botones
        btnSeleccionar.setText(handler.get("crearcarrito.boton.seleccionar"));
        btnAgregar.setText(handler.get("crearcarrito.boton.agregar"));
        btnGuardar.setText(handler.get("crearcarrito.boton.guardar"));
        btnVaciar.setText(handler.get("crearcarrito.boton.vaciar"));
        btnEditar.setText(handler.get("crearcarrito.boton.editar"));

        // Tabla de productos
        String[] columnasLista = {
                handler.get("crearcarrito.columna.id"),
                handler.get("crearcarrito.columna.nombre"),
                handler.get("crearcarrito.columna.precio"),
                handler.get("crearcarrito.columna.cantidad"),
                handler.get("crearcarrito.columna.total")
        };
        modeloItems.setColumnIdentifiers(columnasLista);

        // Tabla de totales
        String[] columnasTotales = {
                handler.get("crearcarrito.columna.subtotal"),
                handler.get("crearcarrito.columna.iva"),
                handler.get("crearcarrito.columna.totalgeneral")
        };
        modeloTotales.setColumnIdentifiers(columnasTotales);

        // Borde del panel
        Border border = panelGeneral.getBorder();
        if (border instanceof TitledBorder) {
            ((TitledBorder) border).setTitle(handler.get("crearcarrito.borde"));
            panelGeneral.repaint();
        }
    }

    /**
     * Configura las validaciones de los campos de texto.
     * Limita la cantidad de caracteres y establece un filtro para el campo de cantidad.
     */
    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public JButton getBtnSeleccionar() {
        return btnSeleccionar;
    }

    public JTextField getTxtCantidad() {
        return txtCantidad;
    }

    public JButton getBtnAgregar() {
        return btnAgregar;
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public JTable getTblTotal() {
        return tblTotal;
    }

    public JTextField getTxtFecha() {
        return txtFecha;
    }

    public JTextField getTxtCodigoCarrito() {
        return txtCodigoCarrito;
    }

    public JButton getBtnVaciar() {
        return btnVaciar;
    }

    public JTable getTblProductos() {
        return tblProductos;
    }

    public JButton getBtnEditar() {
        return btnEditar;
    }

    /**
     * Configura las validaciones de los campos de texto.
     * Limita la cantidad de caracteres y establece un filtro para el campo de cantidad.
     */
    public void cargarProductoEncontrado(String nombre, double precio) {
        txtNombre.setText(nombre);
        txtPrecio.setText(String.valueOf(precio));
    }


    /**
     * Limpia los campos de entrada de datos del formulario.
     * Resetea los campos de código, nombre, precio y cantidad a cadenas vacías.
     * También limpia las tablas de productos y totales.
     */
    public void limpiarFormulario() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
        txtCantidad.setText("");
        modeloItems.setRowCount(0);
        modeloTotales.setRowCount(0);
    }

    /**
     * Activa el modo de edición de la vista.
     * Oculta el botón de guardar y muestra el botón de editar.
     * También oculta el panel norte que contiene los datos del carrito.
     */
    public void activarModoEdicion() {
        btnGuardar.setVisible(false);
        btnEditar.setVisible(true);
        panelNorte.setVisible(false);
    }

    /**
     * Activa el modo de creación de la vista.
     * Muestra el botón de guardar y oculta el botón de editar.
     * También muestra el panel norte que contiene los datos del carrito.
     */
    public void activarModoCreacion() {
        btnGuardar.setVisible(true);
        btnEditar.setVisible(false);
        panelNorte.setVisible(true);
    }

    /**
     * Limpia los campos de entrada de datos del item del carrito.
     * Resetea los campos de código, cantidad, nombre y precio a cadenas vacías.
     */
    public void limpiarCamposItem(){
        txtCodigo.setText("");
        txtCantidad.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
    }

    /**
     * Muestra un mensaje en un cuadro de diálogo.
     * Utiliza JOptionPane para mostrar el mensaje proporcionado.
     *
     * @param mensaje El mensaje a mostrar en el cuadro de diálogo.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    /**
     * Carga un icono desde el directorio de recursos.
     * Utiliza el ClassLoader para buscar el icono en la carpeta "icons".
     *
     * @param nombreArchivo Nombre del archivo del icono a cargar.
     * @return Un ImageIcon con el icono cargado, o null si no se encuentra.
     */
    public ImageIcon cargarIcono(String nombreArchivo) {
        URL url = getClass().getClassLoader().getResource("icons/" + nombreArchivo);
        if (url != null) {
            Image img = new ImageIcon(url).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } else {
            System.err.println("Icono no encontrado: iconos/" + nombreArchivo);
            return null;
        }
    }

    public void validaciones(){
        ((AbstractDocument) txtCodigo.getDocument()).setDocumentFilter(new LimiteCaracter(4,true));
        ((AbstractDocument) txtCantidad.getDocument()).setDocumentFilter(new LimiteCaracter(4,true));

    }
}
