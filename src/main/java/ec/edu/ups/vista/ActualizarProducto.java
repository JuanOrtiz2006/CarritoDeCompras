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
 * Vista (JInternalFrame) para actualizar productos.
 * Permite buscar un producto por código, mostrar sus datos y actualizarlos.
 * Incluye soporte para internacionalización de textos y validaciones de entrada.
 *
 * @author JuanOrtiz2006
 * @version 1.0
 */
public class ActualizarProducto extends JInternalFrame{
    /**
     * Componentes de la interfaz gráfica.
     */
    private JPanel panelGeneral;
    private JPanel panelCentral;
    private JPanel panelDatos;
    private JLabel lblCodigo;
    private JTextField txtCodigo;
    private JLabel lblNombre;
    private JTextField txtNombre;
    private JLabel lblPrecio;
    private JTextField txtPrecio;
    private JButton btnSeleccionar;
    private JButton btnActualizar;

    /**
     * Constructor que inicializa la vista de actualización de productos.
     * Configura el contenido, título, iconos, validaciones y actualiza el idioma.
     */
    public ActualizarProducto(){
        setContentPane(panelGeneral);
        setTitle(Contexto.getHandler().get("actualizarproducto.titulo"));
        setSize(500, 500);
        setClosable(true);
        setMaximizable(true);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);

        btnSeleccionar.setIcon(cargarIcono("check.png"));
        btnActualizar.setIcon(cargarIcono("upload.png"));

        validaciones();
        actualizarIdioma();
    }

    /**
     * Actualiza los textos de la interfaz gráfica según el idioma configurado en el contexto.
     * Modifica títulos, etiquetas y el borde del panel general.
     */
    public void actualizarIdioma() {
        var handler = Contexto.getHandler();
        setTitle(handler.get("actualizarproducto.titulo"));
        lblCodigo.setText(handler.get("actualizarproducto.lbl.codigo"));
        lblNombre.setText(handler.get("actualizarproducto.lbl.nombre"));
        lblPrecio.setText(handler.get("actualizarproducto.lbl.precio"));
        btnSeleccionar.setText(handler.get("actualizarproducto.btn.seleccionar"));
        btnActualizar.setText(handler.get("actualizarproducto.btn.actualizar"));

        // Cambiar título del borde, si lo hay
        Border border = panelGeneral.getBorder();
        if (border instanceof TitledBorder) {
            ((TitledBorder) border).setTitle(handler.get("actualizarproducto.borde"));
            panelGeneral.repaint();
        }
    }

    /**
     * Métodos de acceso para los componentes de la vista.
     * Permiten obtener los campos de texto, botones y el panel general.
     */
    public JPanel getPanelGeneral() {
        return panelGeneral;
    }

    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public JTextField getTxtPrecio() {
        return txtPrecio;
    }

    public JButton getBtnSeleccionar() {
        return btnSeleccionar;
    }

    public JButton getBtnActualizar() {
        return btnActualizar;
    }

    public void setPanelGeneral(JPanel panelGeneral) {
        this.panelGeneral = panelGeneral;
    }

    /**
     * Muestra un mensaje de confirmación en un cuadro de diálogo.
     * Utiliza JOptionPane para mostrar el mensaje y retorna la opción seleccionada.
     *
     * @param mensaje El mensaje a mostrar.
     * @return La opción seleccionada por el usuario.
     */
    public int mostrarMensaje(String mensaje) {
        int opcion = JOptionPane.showConfirmDialog(this, mensaje);
        return opcion;
    }

    /**
     * Muestra los datos del producto encontrado en los campos de texto.
     *
     * @param nombre Nombre del producto.
     * @param precio Precio del producto.
     */
    public void productoEncontrado(String nombre, double precio){
        txtNombre.setText(nombre);
        txtPrecio.setText(Double.toString(precio));
    }

    /**
     * Limpia los campos de entrada del formulario.
     * Resetea el campo de código.
     */
    public void limpiarCampos() {
        txtCodigo.setText("");
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

    /**
     * Aplica validaciones de longitud y tipo de entrada a los campos de texto.
     * Limita la cantidad de caracteres permitidos en cada campo.
     */
    public void validaciones(){
        ((AbstractDocument) txtCodigo.getDocument()).setDocumentFilter(new LimiteCaracter(3,true));
        ((AbstractDocument) txtNombre.getDocument()).setDocumentFilter(new LimiteCaracter(20,false));
        ((AbstractDocument) txtPrecio.getDocument()).setDocumentFilter(new LimiteCaracter(10,false));
    }

}
