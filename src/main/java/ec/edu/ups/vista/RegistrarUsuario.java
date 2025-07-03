package ec.edu.ups.vista;

import ec.edu.ups.util.Contexto;

import javax.swing.*;

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
    private JButton btnSiguiente;
    private JPanel panelBoton;
    private JLabel lblNombre;
    private JLabel lblFecha;
    private JLabel lblCorreo;
    private JLabel lblTelefono;
    private JLabel lblTitulo;
    private JLabel lblTitulo2;
    private JLabel lblUsuario;
    private JLabel lblPassword;

    public RegistrarUsuario(){
        setContentPane(panelGeneral);
        setTitle(Contexto.getHandler().get("login.titulo"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        lblTitulo.setText(Contexto.getHandler().get("lbl.titulo"));
        lblNombre.setText(Contexto.getHandler().get("lbl.nombrepersonal"));
        lblFecha.setText(Contexto.getHandler().get("lbl.fecha"));
        lblCorreo.setText(Contexto.getHandler().get("lbl.correo"));
        lblTelefono.setText(Contexto.getHandler().get("lbl.telefono"));
        lblTitulo2.setText(Contexto.getHandler().get("lbl.titulo2"));
        lblUsuario.setText(Contexto.getHandler().get("lbl.usuario"));
        lblPassword.setText(Contexto.getHandler().get("lbl.password"));
        btnSiguiente.setText(Contexto.getHandler().get("btn.siguiente"));
    }

    public JPanel getPanelGeneral() {
        return panelGeneral;
    }

    public JPanel getPanelCentro() {
        return panelCentro;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public JPanel getPanelInfoPersonal() {
        return panelInfoPersonal;
    }

    public JPanel getPanelNombre() {
        return panelNombre;
    }

    public JPanel getPanelFecha() {
        return panelFecha;
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

    public JPanel getPanelCorreo() {
        return panelCorreo;
    }

    public JPanel getPanelTelefono() {
        return panelTelefono;
    }

    public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    public JPanel getPanelUsuario() {
        return panelUsuario;
    }

    public JPanel getPanelUserName() {
        return panelUserName;
    }

    public JTextField getTxtPassword() {
        return txtPassword;
    }

    public JPanel getPanelPassword() {
        return panelPassword;
    }

    public JButton getBtnSiguiente() {
        return btnSiguiente;
    }

    public JPanel getPanelBoton() {
        return panelBoton;
    }

    public JLabel getLblNombre() {
        return lblNombre;
    }

    public JLabel getLblFecha() {
        return lblFecha;
    }

    public JLabel getLblCorreo() {
        return lblCorreo;
    }

    public JLabel getLblTelefono() {
        return lblTelefono;
    }

    public JLabel getLblTitulo() {
        return lblTitulo;
    }

    public JLabel getLblTitulo2() {
        return lblTitulo2;
    }

    public JLabel getLblUsuario() {
        return lblUsuario;
    }

    public JLabel getLblPassword() {
        return lblPassword;
    }

    public void mostrarMensaje(String mensaje){
        JOptionPane.showMessageDialog(null,mensaje);

    }

    public String[] mostrarRegistroMensaje() {
        JTextField nombreField = new JTextField();
        JTextField passwordField = new JTextField();
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel(Contexto.getHandler().get("login.registro.usuario")));
        panel.add(nombreField);
        panel.add(Box.createVerticalStrut(10));
        panel.add(new JLabel(Contexto.getHandler().get("login.registro.contrasena")));
        panel.add(passwordField);

        int result = JOptionPane.showConfirmDialog(null, panel,
                Contexto.getHandler().get("login.registro.titulo"),
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            return new String[]{nombreField.getText(), passwordField.getText()};
        }
        return null;
    }

}
