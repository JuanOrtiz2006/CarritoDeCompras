package ec.edu.ups.vista;

import ec.edu.ups.util.Contexto;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

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
    private JButton btnRecuperar;

    public LoginView (){
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
        lblUsername.setText(handler.get("login.usuario"));
        lblPassword.setText(handler.get("login.contrasena"));
        btnLogin.setText(handler.get("login.entrar"));
        btnRegistrar.setText(handler.get("login.registrar"));
        btnRecuperar.setText(handler.get("login.recuperar"));
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

    public JButton getBtnRecuperar() {
        return btnRecuperar;
    }

    public void setPanelGeneral(JPanel panelGeneral) {
        this.panelGeneral = panelGeneral;
    }

    public void mostrarMensaje(String mensaje){
        JOptionPane.showMessageDialog(null,mensaje);
    }

}
