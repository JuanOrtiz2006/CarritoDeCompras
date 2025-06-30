package ec.edu.ups.vista;

import ec.edu.ups.util.Contexto;

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
        setTitle(Contexto.getHandler().get("login.titulo"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);

        // Asignar textos internacionalizados
        lblUsername.setText(Contexto.getHandler().get("login.usuario"));
        lblPassword.setText(Contexto.getHandler().get("login.contrasena"));
        btnLogin.setText(Contexto.getHandler().get("login.entrar"));
        btnRegistrar.setText(Contexto.getHandler().get("login.registrar"));
    }

    public void recargarTextos() {
        setTitle(Contexto.getHandler().get("login.titulo"));
        lblUsername.setText(Contexto.getHandler().get("login.usuario"));
        lblPassword.setText(Contexto.getHandler().get("login.contrasena"));
        btnLogin.setText(Contexto.getHandler().get("login.entrar"));
        btnRegistrar.setText(Contexto.getHandler().get("login.registrar"));
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

    public JButton getBtnRegistrar() {
        return btnRegistrar;
    }

    public void setPanelGeneral(JPanel panelGeneral) {
        this.panelGeneral = panelGeneral;
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
