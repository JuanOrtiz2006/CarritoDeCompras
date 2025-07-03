package ec.edu.ups.vista;

import ec.edu.ups.util.Contexto;

import javax.swing.*;

public class PreguntasSeguridad extends JFrame {
    private JPanel panelGeneral;
    private JLabel lblTitulo;
    private JPanel panelCentral;
    private JTextField txtPregunta;
    private JPanel panelPregunta1;
    private JTextField txtPregunta2;
    private JTextField txtPregunta3;
    private JPanel panelPregunta2;
    private JPanel panelPregunta3;
    private JButton btnRegistrarse;
    private JButton btnRecuperar;
    private JPanel panelBotonRegistrar;
    private JPanel panelBotonClave;
    private JLabel lblPregunta;
    private JLabel lblPregunta2;
    private JLabel lblPregunta3;
    private JTextField txtUsuario;
    private JPanel panelUsuario;
    private JLabel lblUsuario;
    private JPanel panelInformacion;
    private JButton btnBuscar;

    public PreguntasSeguridad() {
        setContentPane(panelGeneral);
        setTitle(Contexto.getHandler().get("lbl.preguntas.titulo"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        // Etiquetas traducidas (i18n)
        btnRegistrarse.setText(Contexto.getHandler().get("login.registrar"));
        btnRecuperar.setText(Contexto.getHandler().get("login.recuperar"));
        btnBuscar.setText(Contexto.getHandler().get("buscarproducto.boton")); // Puedes personalizar
        lblUsuario.setText(Contexto.getHandler().get("lbl.usuario"));     // Nuevo: nombre del campo

        // Inicialmente ocultar panel de preguntas y botón registrar
        ocultarPanelPreguntas();
        btnRegistrarse.setVisible(false);
    }

    // ================== GETTERS ==================

    public JPanel getPanelCentral() { return panelCentral; }
    public JTextField getTxtPregunta() { return txtPregunta; }
    public JTextField getTxtPregunta2() { return txtPregunta2; }
    public JTextField getTxtPregunta3() { return txtPregunta3; }
    public JButton getRegistrarseButton() { return btnRegistrarse; }
    public JPanel getPanelBotonRegistrar() { return panelBotonRegistrar; }
    public JPanel getPanelBotonClave() { return panelBotonClave; }
    public JLabel getLblPregunta() { return lblPregunta; }
    public JLabel getLblPregunta2() { return lblPregunta2; }
    public JLabel getLblPregunta3() { return lblPregunta3; }
    public JTextField getTxtUsuario() { return txtUsuario; }
    public JPanel getPanelUsuario() { return panelUsuario; }
    public JPanel getPanelInformacion() { return panelInformacion; }
    public JPanel getPanelGeneral() { return panelGeneral; }
    public JButton getBtnRegistrarse() { return btnRegistrarse; }
    public JButton getBtnRecuperar() { return btnRecuperar; }
    public JButton getBtnBuscar() { return btnBuscar; }

    // ================== FUNCIONES AUXILIARES ==================

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje);
    }

    public void limpiarCampos() {
        txtUsuario.setText("");
        txtPregunta.setText("");
        txtPregunta2.setText("");
        txtPregunta3.setText("");

        lblPregunta.setText("Pregunta 1:");
        lblPregunta2.setText("Pregunta 2:");
        lblPregunta3.setText("Pregunta 3:");
    }

    public void mostrarPanelPreguntas() {
        panelInformacion.setVisible(true);
        panelPregunta1.setVisible(true);
        panelPregunta2.setVisible(true);
        panelPregunta3.setVisible(true);
    }

    public void ocultarPanelPreguntas() {
        panelInformacion.setVisible(false);
        panelPregunta1.setVisible(false);
        panelPregunta2.setVisible(false);
        panelPregunta3.setVisible(false);
    }
}