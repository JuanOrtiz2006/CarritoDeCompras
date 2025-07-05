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
        btnGuardar.setText(Contexto.getHandler().get("btn.siguiente"));
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

    public void mostrarMensaje(String mensaje){
        JOptionPane.showMessageDialog(null,mensaje);

    }

}
