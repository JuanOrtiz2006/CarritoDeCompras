package ec.edu.ups.vista;

import ec.edu.ups.util.Contexto;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.net.URL;

/**
 * Vista (JFrame) para la selección y manejo de archivos.
 * Permite elegir el tipo de archivo, ingresar la ruta y seleccionar el archivo.
 * Incluye soporte para internacionalización de textos y carga de iconos.
 *
 * @author JuanOrtiz2006
 * @version 1.0
 */
public class ArchivoVista extends JFrame {
    /**
     * Componentes de la interfaz gráfica.
     */
    private JPanel panelGeneral;
    private JComboBox cmbArchivo;
    private JTextField txtRuta;
    private JButton btnEleccion;
    private JPanel panelCentral;
    private JPanel panelTipoArchivo;
    private JLabel lblArchivo;
    private JPanel panelRuta;
    private JLabel lblRuta;
    private JPanel panelEleccion;
    private JButton btnArchivo;

    /**
     * Constructor que inicializa la vista de selección de archivos.
     * Configura el contenido, tamaño, opciones del combo y actualiza el idioma.
     */
    public ArchivoVista(){
        setContentPane(panelGeneral);
        //setTitle(Contexto.getHandler().get("login.titulo"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 200);
        setLocationRelativeTo(null);

        cmbArchivo.addItem("");
        cmbArchivo.addItem("Archivo de Texto");
        cmbArchivo.addItem("Archivo Binario");
        cmbArchivo.addItem("Memoria");

        //btnLogin.setIcon(cargarIcono("login.png"));
        actualizarIdioma();
    }

    /**
     * Actualiza los textos de la interfaz gráfica según el idioma configurado en el contexto.
     * Modifica títulos, etiquetas y el borde del panel general.
     */
    public void actualizarIdioma() {
        var handler = Contexto.getHandler();

        //setTitle(handler.get("login.titulo"));
        //lblArchivo.setText(handler.get("login.usuario"));
        //lblRuta.setText(handler.get("login.contrasena"));
        //btnEleccion.setText(handler.get("login.entrar"));
        Border border = panelGeneral.getBorder();

        if (border instanceof TitledBorder) {
            ((TitledBorder) border).setTitle(handler.get("buscarproducto.borde"));
            panelGeneral.repaint();
        }
    }

    /**
     * Métodos de acceso para los componentes de la vista.
     * Permiten obtener los campos de texto, botones y paneles.
     */
    public JComboBox getCmbArchivo() {
        return cmbArchivo;
    }

    public JTextField getTxtRuta() {
        return txtRuta;
    }

    public JButton getBtnEleccion() {
        return btnEleccion;
    }

    public JPanel getPanelRuta() {
        return panelRuta;
    }

    public JButton getBtnArchivo() {
        return btnArchivo;
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
}
