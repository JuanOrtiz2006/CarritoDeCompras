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
 * Vista (JFrame) para el inicio de sesión del usuario.
 * <p>
 * Esta clase representa la ventana principal donde los usuarios pueden ingresar sus credenciales
 * para acceder al sistema. Permite ingresar nombre de usuario y contraseña, y proporciona botones
 * para iniciar sesión, registrarse o recuperar la contraseña.
 * <p>
 * Incluye validaciones de formato y longitud en los campos, así como soporte para internacionalización
 * de los textos de la interfaz gráfica.
 *
 * @author JuanOrtiz2006
 * @version 1.0
 */
public class LoginView extends JFrame{
    /**
     * Componentes de la interfaz gráfica.
     */
    private JPanel panelGeneral;
    private JTextField txtUsername;
    private JTextField txtPassword;
    private JButton btnLogin;
    private JPanel panelCentro;
    private JPanel panelDatos;
    private JLabel lblUsername;
    private JLabel lblPassword;
    private JPanel panelBoton;
    private JButton btnRegistrar;
    private JButton btnRecuperar;

    /**
     * Constructor de la clase LoginView.
     * Configura la ventana principal, establece el título, tamaño y ubicación,
     * y carga los iconos para los botones.
     */
    public LoginView (){
        setContentPane(panelGeneral);
        setTitle(Contexto.getHandler().get("login.titulo"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 200);
        setLocationRelativeTo(null);

        btnLogin.setIcon(cargarIcono("login.png"));
        btnRecuperar.setIcon(cargarIcono("restore.png"));
        btnRegistrar.setIcon(cargarIcono("registration.png"));
        validaciones();
        actualizarIdioma();
    }

    /**
     * Actualiza los textos de la interfaz gráfica según el idioma seleccionado.
     * Utiliza el manejador de contexto para obtener los textos traducidos.
     */
    public void actualizarIdioma() {
        var handler = Contexto.getHandler();

        setTitle(handler.get("login.titulo"));
        lblUsername.setText(handler.get("login.usuario"));
        lblPassword.setText(handler.get("login.contrasena"));
        btnLogin.setText(handler.get("login.entrar"));
        btnRegistrar.setText(handler.get("login.registrar"));
        btnRecuperar.setText(handler.get("login.recuperar"));
    }

    /**
     * Getters y Setters para los componentes de la interfaz gráfica.
     */
    public JPanel getPanelGeneral() {
        return panelGeneral;
    }

    public JTextField getTxtUsername() {
        return txtUsername;
    }

    public JTextField getTxtPassword() {
        return txtPassword;
    }

    public JButton getBtnLogin() {
        return btnLogin;
    }

    public JButton getBtnRegistrar() {
        return btnRegistrar;
    }

    public JButton getBtnRecuperar() {
        return btnRecuperar;
    }

    public void setPanelGeneral(JPanel panelGeneral) {
        this.panelGeneral = panelGeneral;
    }

    /**
     * Muestra un mensaje en un cuadro de diálogo.
     *
     * @param mensaje El mensaje a mostrar al usuario.
     */
    public void mostrarMensaje(String mensaje){
        JOptionPane.showMessageDialog(null,mensaje);
    }

    /**
     * Carga un icono desde el directorio de recursos.
     * Utiliza el ClassLoader para buscar el archivo en la carpeta "icons".
     *
     * @param nombreArchivo Nombre del archivo del icono a cargar.
     * @return Un objeto ImageIcon con el icono cargado, o null si no se encuentra.
     */

    /**
     * Carga un icono desde el directorio de recursos.
     * Utiliza el ClassLoader para buscar el icono en la carpeta "icons".
     *
     * @param nombreArchivo Nombre del archivo del icono a cargar.
     * @return Un objeto ImageIcon con el icono cargado, o null si no se encuentra.
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
     * Configura validaciones de longitud en los campos de texto.
     * Utiliza un filtro de documento para limitar la cantidad de caracteres.
     */
    public void validaciones(){
        ((AbstractDocument) txtUsername.getDocument()).setDocumentFilter(new LimiteCaracter(10,true));
        ((AbstractDocument) txtPassword.getDocument()).setDocumentFilter(new LimiteCaracter(20,false));
    }



}
