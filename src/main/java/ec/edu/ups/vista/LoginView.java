package ec.edu.ups.vista;

import javax.swing.*;

public class LoginView extends JFrame{
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

    public LoginView (){
        setContentPane(panelGeneral);
        setTitle("Iniciar Sesión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
    }

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

    public JPanel getPanelCentro() {
        return panelCentro;
    }

    public JPanel getPanelDatos() {
        return panelDatos;
    }

    public JLabel getLblUsername() {
        return lblUsername;
    }

    public JLabel getLblPassword() {
        return lblPassword;
    }

    public JPanel getPanelBoton() {
        return panelBoton;
    }

    public JButton getBtnRegistrar() {
        return btnRegistrar;
    }

    public void setPanelGeneral(JPanel panelGeneral) {
        this.panelGeneral = panelGeneral;
    }

    public void setTxtUsername(JTextField txtUsername) {
        this.txtUsername = txtUsername;
    }

    public void setTxtPassword(JTextField txtPassword) {
        this.txtPassword = txtPassword;
    }

    public void setBtnLogin(JButton btnLogin) {
        this.btnLogin = btnLogin;
    }

    public void setPanelCentro(JPanel panelCentro) {
        this.panelCentro = panelCentro;
    }

    public void setPanelDatos(JPanel panelDatos) {
        this.panelDatos = panelDatos;
    }

    public void setLblUsername(JLabel lblUsername) {
        this.lblUsername = lblUsername;
    }

    public void setLblPassword(JLabel lblPassword) {
        this.lblPassword = lblPassword;
    }

    public void setPanelBoton(JPanel panelBoton) {
        this.panelBoton = panelBoton;
    }

    public void setBtnRegistrar(JButton btnRegistrar) {
        this.btnRegistrar = btnRegistrar;
    }

    public void mostrarMensaje(String mensaje){
        JOptionPane.showMessageDialog(null,mensaje);

    }
    public String[] mostrarRegistroMensaje() {
        JTextField nombreField = new JTextField();
        JTextField passwordField = new JTextField();
        String nombreIngresado;
        String passwordIngresado;
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Nombre de usuario:"));
        panel.add(nombreField);
        panel.add(Box.createVerticalStrut(10)); // espacio
        panel.add(new JLabel("Contrasenia:"));
        panel.add(passwordField);

        int result = JOptionPane.showConfirmDialog(null, panel,
                "Registro de Usuario", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            nombreIngresado = nombreField.getText();
            passwordIngresado = passwordField.getText();
            String [] respuestas = {nombreIngresado,passwordIngresado};
            return respuestas;
        }
        return null;
    }


}
