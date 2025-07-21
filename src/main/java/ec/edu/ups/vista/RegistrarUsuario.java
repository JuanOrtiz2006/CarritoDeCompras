package ec.edu.ups.vista;

import ec.edu.ups.util.Contexto;
import ec.edu.ups.util.FormateadorUtils;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Date;
import javax.swing.text.AbstractDocument;
import ec.edu.ups.util.LimiteCaracter;

/**
 * Vista (JFrame) para el registro y edición de usuarios.
 * <p>
 * Esta clase representa la ventana principal donde los usuarios pueden ingresar
 * sus datos personales y credenciales para crear una nueva cuenta o editar una existente.
 * Permite ingresar nombre, fecha de nacimiento, correo electrónico, teléfono, usuario y contraseña.
 * <p>
 * Incluye validaciones de formato y longitud en los campos, así como soporte para internacionalización
 * de los textos de la interfaz gráfica.
 *
 * @author JuanOrtiz2006
 * @version 1.0
 */

public class RegistrarUsuario extends JFrame {

    /**
     * Componentes de la interfaz gráfica.
     */
    private JPanel panelGeneral;
    private JPanel panelCentro;
    private JTextField txtNombre;
    private JPanel panelInfoPersonal;
    private JPanel panelNombre;
    private JPanel panelFecha;
    private JTextField txtFecha;
    private JTextField txtCorreo;
    private JTextField txtTelefono;
    private JPanel panelCorreo;
    private JPanel panelTelefono;
    private JTextField txtUsuario;
    private JPanel panelUsuario;
    private JPanel panelUserName;
    private JTextField txtPassword;
    private JPanel panelPassword;
    private JButton btnGuardar;
    private JPanel panelBoton;
    private JLabel lblNombre;
    private JLabel lblFecha;
    private JLabel lblCorreo;
    private JLabel lblTelefono;
    private JLabel lblTitulo;
    private JLabel lblTitulo2;
    private JLabel lblUsuario;
    private JLabel lblPassword;
    private boolean modoEdicion = false;

    /**
     * Constructor de la clase RegistrarUsuario.
     * Configura la ventana, los componentes y las validaciones iniciales.
     */
    public RegistrarUsuario(){
        setContentPane(panelGeneral);
        setTitle(Contexto.getHandler().get("login.titulo"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        btnGuardar.setIcon(cargarIcono("save.png"));

        validaciones();
        actualizarIdioma();
    }

    /**
     * Actualiza los textos de la interfaz gráfica según el idioma configurado en el contexto.
     * Utiliza el manejador de contexto para obtener las traducciones correspondientes.
     */
    public void actualizarIdioma() {
        var handler = Contexto.getHandler();

        setTitle(handler.get("login.titulo"));
        lblTitulo.setText(handler.get("lbl.titulo"));
        lblNombre.setText(handler.get("lbl.nombrepersonal"));
        lblFecha.setText(handler.get("lbl.fecha"));
        lblCorreo.setText(handler.get("lbl.correo"));
        lblTelefono.setText(handler.get("lbl.telefono"));
        lblTitulo2.setText(handler.get("lbl.titulo2"));
        lblUsuario.setText(handler.get("lbl.usuario"));
        lblPassword.setText(handler.get("lbl.password"));
        btnGuardar.setText(handler.get("btn.siguiente"));

        var border = panelGeneral.getBorder();
        if (border instanceof javax.swing.border.TitledBorder) {
            ((javax.swing.border.TitledBorder) border).setTitle(handler.get("registrarusuario.borde"));
            panelGeneral.repaint();
        }
    }

    /**
     * getters y setters para los componentes de la interfaz.
     */
    public JPanel getPanelGeneral() {
        return panelGeneral;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public JTextField getTxtFecha() {
        return txtFecha;
    }

    public JTextField getTxtCorreo() {
        return txtCorreo;
    }

    public JTextField getTxtTelefono() {
        return txtTelefono;
    }

    public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    public JTextField getTxtPassword() {
        return txtPassword;
    }

    public JButton getBtnSiguiente() {
        return btnGuardar;
    }

    public void setTxtNombre(JTextField txtNombre) {
        this.txtNombre = txtNombre;
    }

    public void setTxtFecha(JTextField txtFecha) {
        this.txtFecha = txtFecha;
    }

    public void setTxtCorreo(JTextField txtCorreo) {
        this.txtCorreo = txtCorreo;
    }

    public void setTxtTelefono(JTextField txtTelefono) {
        this.txtTelefono = txtTelefono;
    }

    public void setTxtUsuario(JTextField txtUsuario) {
        this.txtUsuario = txtUsuario;
    }

    public void setTxtPassword(JTextField txtPassword) {
        this.txtPassword = txtPassword;
    }

    public void activarModoEdicion() {
        this.modoEdicion = true;
    }

    public void activarModoRegistro() {
        this.modoEdicion = false;
        limpiarCampos();
    }
    public boolean isModoEdicion() {
        return modoEdicion;
    }

    /**
     * Muestra ejemplos de formato en los campos de texto.
     * Utiliza el formateador de fechas para mostrar un ejemplo de fecha.
     */
    public void ejemplos(){
        txtNombre.setToolTipText("Ejemplo: Juan Pérez");
        txtCorreo.setToolTipText("Ejemplo: juan@example.com");
        txtTelefono.setToolTipText("Ejemplo: +593 99 123 4567");
        txtUsuario.setToolTipText("Ejemplo: jperez2025");
        txtPassword.setToolTipText("Ejemplo: ********");
        txtFecha.setToolTipText("Ejemplo: " + FormateadorUtils.formatearFecha(new Date(), Contexto.getLocale()));
    }

    /**
     * Limpia los campos de texto de la interfaz.
     * Resetea todos los campos a su estado inicial vacío.
     */
    public void limpiarCampos(){
        txtNombre.setText("");
        txtFecha.setText("");
        txtCorreo.setText("");
        txtTelefono.setText("");
        txtUsuario.setText("");
        txtPassword.setText("");
    }

    /**
     * Muestra un mensaje en un cuadro de diálogo.
     * Utiliza JOptionPane para mostrar el mensaje proporcionado.
     *
     * @param mensaje El mensaje a mostrar en el cuadro de diálogo.
     */
    public void mostrarMensaje(String mensaje){
        JOptionPane.showMessageDialog(null,mensaje);

    }
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
     * Configura los filtros de validación para los campos de texto.
     * Establece límites de caracteres y formatos específicos para cada campo.
     * Utiliza LimiteCaracter para restringir la entrada del usuario.
     */
    public void validaciones(){
        ((AbstractDocument) txtNombre.getDocument()).setDocumentFilter(new LimiteCaracter(20,false));
        ((AbstractDocument) txtCorreo.getDocument()).setDocumentFilter(new LimiteCaracter(20,false));
        ((AbstractDocument) txtTelefono.getDocument()).setDocumentFilter(new LimiteCaracter(10,true));
        ((AbstractDocument) txtUsuario.getDocument()).setDocumentFilter(new LimiteCaracter(10,true));
        ((AbstractDocument) txtPassword.getDocument()).setDocumentFilter(new LimiteCaracter(20,false));
    }


}
