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
 * Vista (JInternalFrame) para la creación de productos.
 * <p>
 * Esta clase representa una ventana interna donde se pueden ingresar los datos
 * necesarios para crear un nuevo producto, incluyendo código, nombre y precio.
 * Permite validar la entrada de datos y actualizar la interfaz según el idioma seleccionado.
 * </p>
 *
 * @author JuanOrtiz2006
 * @version 1.0
 */
public class CrearProductoView extends JInternalFrame {
    /**
     * Componentes de la interfaz gráfica.
     */
    private JPanel panelPrincipal;
    private JTextField txtPrecio;
    private JTextField txtNombre;
    private JTextField txtCodigo;
    private JButton btnAceptar;
    private JPanel panelCentral;
    private JPanel panelDatos;
    private JLabel lblCodigo;
    private JLabel lblNombre;
    private JLabel lblPrecio;
    private JPanel panelBotones;

    /**
     * Constructor que inicializa la vista de creación de productos.
     * Configura el título, tamaño, iconos y validaciones de los campos de entrada.
     */
    public CrearProductoView() {
        setContentPane(panelPrincipal);
        setTitle(Contexto.getHandler().get("crearproducto.titulo"));
        setSize(500, 500);
        setClosable(true);
        setMaximizable(true);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);

        btnAceptar.setIcon(cargarIcono("product.png"));
        validaciones();
        actualizarIdioma();
    }

    /**
     * Actualiza los textos de la interfaz gráfica según el idioma configurado en el contexto.
     * Modifica títulos, etiquetas y bordes de los paneles.
     */
    public void actualizarIdioma() {
        var handler = Contexto.getHandler();
        // Títulos de ventana y etiquetas
        setTitle(handler.get("crearproducto.titulo"));
        lblCodigo.setText(handler.get("crearproducto.codigo"));
        lblNombre.setText(handler.get("crearproducto.nombre"));
        lblPrecio.setText(handler.get("crearproducto.precio"));
        btnAceptar.setText(handler.get("crearproducto.boton"));

        // Actualizar título del borde si es un TitledBorder
        Border border = panelPrincipal.getBorder();
        if (border instanceof TitledBorder) {
            ((TitledBorder) border).setTitle(handler.get("crearproducto.borde"));
            panelPrincipal.repaint(); // Para que se refleje el nuevo texto
        }

        btnAceptar.setIcon(cargarIcono("product.png"));
    }

    /**
     * Métodos de acceso para los componentes de la vista.
     * Permiten obtener los campos de texto y el botón de aceptar.
     */
    public JTextField getTxtPrecio() {
        return txtPrecio;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public JButton getBtnAceptar() {
        return btnAceptar;
    }

    /**
     * Muestra un mensaje en un cuadro de diálogo.
     *
     * @param mensaje El mensaje a mostrar al usuario.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    /**
     * Limpia los campos de entrada de datos.
     * Resetea los campos de código, nombre y precio a cadenas vacías.
     */
    public void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
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
     * Configura los filtros de validación para los campos de texto.
     * Establece límites de caracteres y formatos específicos para cada campo.
     * Utiliza LimiteCaracter para restringir la entrada del usuario.
     */
    public void validaciones(){
        ((AbstractDocument) txtCodigo.getDocument()).setDocumentFilter(new LimiteCaracter(3,true));
        ((AbstractDocument) txtNombre.getDocument()).setDocumentFilter(new LimiteCaracter(20,false));
        ((AbstractDocument) txtPrecio.getDocument()).setDocumentFilter(new LimiteCaracter(8,false));
    }

}
