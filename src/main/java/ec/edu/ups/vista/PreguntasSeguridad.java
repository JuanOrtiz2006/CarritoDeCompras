package ec.edu.ups.vista;

import ec.edu.ups.util.Contexto;

import javax.swing.*;

public class PreguntasSeguridad extends JFrame {
    private JPanel panelGeneral;
    private JLabel lblTitulo;
    private JPanel panelCentral;
    private JTextField txtPregunta;
    private JPanel panelP;
    private JTextField txtPregunta2;
    private JTextField txtPregunta3;
    private JPanel panelP2;
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
    private JCheckBox ckbTipo1;
    private JCheckBox ckbTipo2;
    private JCheckBox ckbTipo3;
    private JCheckBox ckbTipo4;
    private JCheckBox ckbTipo5;
    private JPanel panelTipos;
    private JPanel panelP3;
    private JPanel panelP4;
    private JLabel lblPregunta4;
    private JTextField txtPregunta4;
    private JPanel panelP5;
    private JLabel lblPregunta5;
    private JTextField txtPregunta5;
    private JPanel panelP6;
    private JLabel lblPregunta6;
    private JTextField txtPregunta6;
    private JPanel panelP7;
    private JLabel lblPregunta7;
    private JTextField txtPregunta7;
    private JPanel panelP8;
    private JLabel lblPregunta8;
    private JTextField txtPregunta8;
    private JLabel lblPregunta9;
    private JPanel panelP9;
    private JTextField txtPregunta9;
    private JLabel lblPregunta10;
    private JTextField txtPregunta10;
    private JPanel panelP10;
    private JButton btnBuscar;
    private JComboBox cmbTipoPregunta;
    private JButton btnGuardar;

    public PreguntasSeguridad() {
        setContentPane(panelGeneral);
        setTitle(Contexto.getHandler().get("lbl.preguntas.titulo"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        // Etiquetas traducidas (i18n)
        btnRegistrarse.setText(Contexto.getHandler().get("login.registrar"));

        deshabilitarPreguntasTipo1();
        deshabilitarPreguntasTipo2();
        deshabilitarPreguntasTipo3();
        deshabilitarPreguntasTipo4();
        deshabilitarPreguntasTipo5();

    }

    // ================== GETTERS ==================

    public JPanel getPanelCentral() {
        return panelCentral;
    }

    public JTextField getTxtPregunta() {
        return txtPregunta;
    }

    public JTextField getTxtPregunta2() {
        return txtPregunta2;
    }

    public JButton getRegistrarseButton() {
        return btnRegistrarse;
    }

    public JPanel getPanelBotonRegistrar() {
        return panelBotonRegistrar;
    }

    public JPanel getPanelBotonClave() {
        return panelBotonClave;
    }

    public JLabel getLblPregunta() {
        return lblPregunta;
    }

    public JLabel getLblPregunta2() {
        return lblPregunta2;
    }

    public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    public JPanel getPanelUsuario() {
        return panelUsuario;
    }

    public JPanel getPanelGeneral() {
        return panelGeneral;
    }

    public JButton getBtnRegistrarse() {
        return btnRegistrarse;
    }

    public JButton getBtnRecuperar() {
        return btnRecuperar;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JTextField getTxtPregunta3() {
        return txtPregunta3;
    }

    public JLabel getLblPregunta3() {
        return lblPregunta3;
    }

    public JCheckBox getCkbTipo1() {
        return ckbTipo1;
    }

    public JCheckBox getCkbTipo2() {
        return ckbTipo2;
    }

    public JCheckBox getCkbTipo3() {
        return ckbTipo3;
    }

    public JCheckBox getCkbTipo4() {
        return ckbTipo4;
    }

    public JCheckBox getCkbTipo5() {
        return ckbTipo5;
    }

    public JLabel getLblPregunta4() {
        return lblPregunta4;
    }

    public JTextField getTxtPregunta4() {
        return txtPregunta4;
    }

    public JLabel getLblPregunta5() {
        return lblPregunta5;
    }

    public JTextField getTxtPregunta5() {
        return txtPregunta5;
    }

    public JLabel getLblPregunta6() {
        return lblPregunta6;
    }

    public JTextField getTxtPregunta6() {
        return txtPregunta6;
    }

    public JLabel getLblPregunta7() {
        return lblPregunta7;
    }

    public JTextField getTxtPregunta7() {
        return txtPregunta7;
    }

    public JLabel getLblPregunta8() {
        return lblPregunta8;
    }

    public JTextField getTxtPregunta8() {
        return txtPregunta8;
    }

    public JLabel getLblPregunta9() {
        return lblPregunta9;
    }

    public JTextField getTxtPregunta9() {
        return txtPregunta9;
    }

    public JLabel getLblPregunta10() {
        return lblPregunta10;
    }

    public JTextField getTxtPregunta10() {
        return txtPregunta10;
    }

    public JComboBox getCmbTipoPregunta() {
        return cmbTipoPregunta;
    }

    // ================== FUNCIONES AUXILIARES ==================

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje);
    }

    public void habilitarPreguntasTipo1(){
        panelP.setVisible(true);
        panelP2.setVisible(true);
    }
    public void habilitarPreguntasTipo2(){
        panelP3.setVisible(true);
        panelP4.setVisible(true);
    }
    public void habilitarPreguntasTipo3(){
        panelP5.setVisible(true);
        panelP6.setVisible(true);
    }
    public void habilitarPreguntasTipo4(){
        panelP7.setVisible(true);
        panelP8.setVisible(true);
    }

    public void habilitarPreguntasTipo5(){
        panelP9.setVisible(true);
        panelP10.setVisible(true);
    }

    public void deshabilitarPreguntasTipo1(){
        panelP.setVisible(false);
        panelP2.setVisible(false);
    }

    public void deshabilitarPreguntasTipo2(){
        panelP3.setVisible(false);
        panelP4.setVisible(false);
    }
    public void deshabilitarPreguntasTipo3(){
        panelP5.setVisible(false);
        panelP6.setVisible(false);
    }
    public void deshabilitarPreguntasTipo4(){
        panelP7.setVisible(false);
        panelP8.setVisible(false);
    }

    public void deshabilitarPreguntasTipo5(){
        panelP9.setVisible(false);
        panelP10.setVisible(false);
    }

    public void cargarPreguntas(String[] preguntas){
        lblPregunta.setText(preguntas[0]);
        lblPregunta2.setText(preguntas[1]);
        lblPregunta3.setText(preguntas[2]);
        lblPregunta4.setText(preguntas[3]);
        lblPregunta5.setText(preguntas[4]);
        lblPregunta6.setText(preguntas[5]);
        lblPregunta7.setText(preguntas[6]);
        lblPregunta8.setText(preguntas[7]);
        lblPregunta9.setText(preguntas[8]);
        lblPregunta10.setText(preguntas[9]);
    }

    public void cargarCheckBox(String[] tipos){
        ckbTipo1.setText(tipos[0]);
        ckbTipo2.setText(tipos[1]);
        ckbTipo3.setText(tipos[2]);
        ckbTipo4.setText(tipos[3]);
        ckbTipo5.setText(tipos[4]);
    }
}