package ec.edu.ups.vista;

import ec.edu.ups.util.Contexto;
import ec.edu.ups.util.LimiteCaracter;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.net.URL;

/**
 * Vista (JFrame) para la recuperación de contraseña de usuario.
 * <p>
 * Esta clase representa la ventana donde el usuario puede ingresar su nombre de usuario
 * para buscar la pregunta de seguridad y recuperar su clave. Incluye campos para el usuario,
 * la pregunta de seguridad y botones para buscar y recuperar la contraseña.
 * <p>
 * Soporta internacionalización de textos y validaciones de formato en los campos.
 *
 * @author JuanOrtiz2006
 * @version 1.0
 */

public class RecuperarClave extends JFrame {
    /**
     * Componentes de la interfaz gráfica.
     */
    private JPanel panelGeneral;
    private JPanel panelCentral;
    private JTextField txtUsuario;
    private JButton btnBuscar;
    private JTextField txtPregunta;
    private JButton btnRecuperar;
    private JPanel panelUsuario;
    private JLabel lblUsuario;
    private JPanel panelAutenticar;
    private JPanel panelPregunta;
    private JLabel lblPregunta;

    /**
     * Constructor de la clase RecuperarClave.
     * Configura la ventana, los botones y los campos de texto.
     */
    public RecuperarClave(){
        setContentPane(panelGeneral);
        setTitle(Contexto.getHandler().get("lbl.preguntas.titulo"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        btnBuscar.setIcon(cargarIcono("search.png"));
        btnRecuperar.setIcon(cargarIcono("restore.png"));

        validaciones();
        actualizarIdioma();
        panelAutenticar.setVisible(false);
    }

    /**
     * Actualiza los textos de la interfaz gráfica según el idioma configurado.
     * Utiliza el manejador de contexto para obtener los textos localizados.
     */
    public void actualizarIdioma() {
        var handler = Contexto.getHandler();

        setTitle(handler.get("lbl.preguntas.titulo"));
        lblUsuario.setText(handler.get("login.usuario"));
        btnBuscar.setText(handler.get("login.boton.buscar"));
        btnRecuperar.setText(handler.get("usuario.btn.guardar"));
    }

    /**
     * Limpia los campos de texto de usuario y pregunta.
     * Este metodo se utiliza para reiniciar el formulario después de una operación.
     */
    public void limpiarCampos(){
        txtUsuario.setText("");
        txtPregunta.setText("");

    }

    /**
     * getters para acceder a los componentes de la interfaz gráfica.
     */
    public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JTextField getTxtPregunta() {
        return txtPregunta;
    }

    public JButton getBtnRecuperar() {
        return btnRecuperar;
    }

    public JLabel getLblPregunta() {
        return lblPregunta;
    }

    public JPanel getPanelAutenticar() {
        return panelAutenticar;
    }


    /**
     * Muestra un mensaje en un cuadro de diálogo.
     * Utiliza JOptionPane para mostrar mensajes al usuario.
     *
     * @param mensaje El mensaje a mostrar.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje);
    }

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
        ((AbstractDocument) txtUsuario.getDocument()).setDocumentFilter(new LimiteCaracter(10,true));
        ((AbstractDocument) txtPregunta.getDocument()).setDocumentFilter(new LimiteCaracter(20,false));

    }
}
