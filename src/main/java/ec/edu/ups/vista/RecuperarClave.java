package ec.edu.ups.vista;

import ec.edu.ups.util.Contexto;

import javax.swing.*;

public class RecuperarClave extends JFrame {
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

    public RecuperarClave(){
        setContentPane(panelGeneral);
        setTitle(Contexto.getHandler().get("lbl.preguntas.titulo"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        actualizarIdioma();
        panelPregunta.setVisible(false);
    }

    public void actualizarIdioma() {
        var handler = Contexto.getHandler();

        setTitle(handler.get("lbl.preguntas.titulo"));
        lblUsuario.setText(handler.get("login.usuario"));
        btnBuscar.setText(handler.get("login.boton.buscar"));
        btnRecuperar.setText(handler.get("usuario.btn.guardar"));
    }

    public void limpiarCampos(){
        txtUsuario.setText("");
        txtPregunta.setText("");

    }

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

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje);
    }

}
