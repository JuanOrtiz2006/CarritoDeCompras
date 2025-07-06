package ec.edu.ups.vista;

import ec.edu.ups.util.Contexto;
import ec.edu.ups.util.FormateadorUtils;

import javax.swing.*;
import java.util.Date;

public class RegistrarUsuario extends JFrame {
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

    public RegistrarUsuario(){
        setContentPane(panelGeneral);
        setTitle(Contexto.getHandler().get("login.titulo"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        actualizarIdioma();
    }

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

    public void ejemplos(){
        txtNombre.setToolTipText("Ejemplo: Juan Pérez");
        txtCorreo.setToolTipText("Ejemplo: juan@example.com");
        txtTelefono.setToolTipText("Ejemplo: +593 99 123 4567");
        txtUsuario.setToolTipText("Ejemplo: jperez2025");
        txtPassword.setToolTipText("Ejemplo: ********");
        txtFecha.setToolTipText("Ejemplo: " + FormateadorUtils.formatearFecha(new Date(), Contexto.getLocale()));
    }
    public void limpiarCampos(){
        txtNombre.setText("");
        txtFecha.setText("");
        txtCorreo.setText("");
        txtTelefono.setText("");
        txtUsuario.setText("");
        txtPassword.setText("");
    }
    public void mostrarMensaje(String mensaje){
        JOptionPane.showMessageDialog(null,mensaje);

    }

}
