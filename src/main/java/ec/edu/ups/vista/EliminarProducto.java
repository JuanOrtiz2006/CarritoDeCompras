package ec.edu.ups.vista;

import ec.edu.ups.util.Contexto;
import ec.edu.ups.util.LimiteCaracter;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.net.URL;

/**
 * Vista (JInternalFrame) para eliminar un producto.
 * <p>
 * Esta clase representa una ventana interna donde se puede buscar y eliminar un producto
 * ingresando su código. Muestra el nombre y precio del producto encontrado, y permite
 * confirmar su eliminación.
 * </p>
 *
 * @author JuanOrtiz2006
 * @version 1.0
 */
public class EliminarProducto extends JInternalFrame {
    /**
     * Componentes de la interfaz gráfica.
     */
    private JPanel panelGeneral;
    private JPanel panelCentral;
    private JTextField txtCodigo;
    private JLabel lblCodigo;
    private JButton btnEliminar;
    private JPanel panelDatos;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JLabel lblNombre;
    private JButton btnSeleccionar;
    private JLabel lblPrecio;

    /**
     * Constructor que inicializa la vista de eliminación de productos.
     * Configura el título, tamaño, iconos y validaciones de los campos.
     */
    public EliminarProducto() {
        setContentPane(panelGeneral);
        setTitle(Contexto.getHandler().get("eliminarproducto.titulo"));
        setSize(500, 500);
        setClosable(true);
        setMaximizable(true);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);

        txtNombre.setEnabled(false);
        txtPrecio.setEnabled(false);

        btnSeleccionar.setIcon(cargarIcono("check.png"));
        btnEliminar.setIcon(cargarIcono("delete.png"));
        validaciones();
        actualizarIdioma();
    }

    /**
     * Actualiza los textos de la interfaz gráfica según el idioma configurado en el contexto.
     * Modifica títulos, etiquetas y botones.
     */
    public void actualizarIdioma() {
        var handler = Contexto.getHandler();

        setTitle(handler.get("eliminarproducto.titulo"));
        lblCodigo.setText(handler.get("eliminarproducto.etiqueta.codigo"));
        lblNombre.setText(handler.get("eliminarproducto.etiqueta.nombre"));
        lblPrecio.setText(handler.get("eliminarproducto.etiqueta.precio"));
        btnSeleccionar.setText(handler.get("eliminarproducto.boton.seleccionar"));
        btnEliminar.setText(handler.get("eliminarproducto.boton.eliminar"));

        Border border = panelGeneral.getBorder();
        if (border instanceof TitledBorder) {
            ((TitledBorder) border).setTitle(handler.get("eliminarproducto.borde"));
            panelGeneral.repaint();
        }
    }

    /**
     * Getters y setters para los componentes de la interfaz.
     */
    public JPanel getPanelGeneral() {
        return panelGeneral;
    }

    public JButton getBtnSeleccionar() {
        return btnSeleccionar;
    }

    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public void setPanelGeneral(JPanel panelGeneral) {
        this.panelGeneral = panelGeneral;
    }

    /**
     * Muestra un mensaje de confirmación al usuario.
     *
     * @param mensaje El mensaje a mostrar en el diálogo.
     * @return La opción seleccionada por el usuario (OK, CANCEL, etc.).
     */
    public int mostrarMensaje(String mensaje) {
        int opcion = JOptionPane.showConfirmDialog(this, mensaje);
        return opcion;
    }

    /**
     * Carga los datos del producto encontrado en los campos de texto.
     *
     * @param nombre El nombre del producto.
     * @param precio El precio del producto.
     */
    public void cargarProductoEncontrado(String nombre, double precio){
        txtNombre.setText(nombre);
        txtPrecio.setText(Double.toString(precio));
    }

    /**
     * Limpia los campos de texto de la vista.
     * Resetea los campos de código, nombre y precio.
     */
    public void limpiarCampos() {
        txtCodigo.setText("");
    }

    /**
     * Carga un icono desde el directorio de recursos.
     *
     * @param nombreArchivo El nombre del archivo del icono a cargar.
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

    /**
     * Configura los filtros de validación para los campos de texto.
     * Establece límites de caracteres y formatos específicos para cada campo.
     */
    public void validaciones(){
        ((AbstractDocument) txtCodigo.getDocument()).setDocumentFilter(new LimiteCaracter(10,true));
    }
}
