package ec.edu.ups.vista;

import ec.edu.ups.util.Contexto;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

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
    private JButton btnActualizar;
    private JComboBox cmbTipoPregunta;
    private boolean modoEdicion = false;

    public PreguntasSeguridad() {
        setContentPane(panelGeneral);
        setTitle(Contexto.getHandler().get("lbl.preguntas.titulo"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        btnRegistrarse.setText(Contexto.getHandler().get("login.registrar"));
        deshabilitarPreguntasTipo1();
        deshabilitarPreguntasTipo2();
        deshabilitarPreguntasTipo3();
        deshabilitarPreguntasTipo4();
        deshabilitarPreguntasTipo5();

        btnRegistrarse.setIcon(cargarIcono("addUser.png"));
        btnActualizar.setIcon(cargarIcono("upload.png"));

        actualizarIdioma();

    }

    public void actualizarIdioma() {
        var handler = Contexto.getHandler();

        setTitle(handler.get("lbl.preguntas.titulo"));
        lblTitulo.setText(handler.get("lbl.preguntas.titulo"));
        btnRegistrarse.setText(handler.get("login.registrar"));
        btnActualizar.setText(handler.get("usuario.btn.guardar"));

        cargarPreguntas(new String[]{
                handler.get("lbl.preguntas.nombremadre"),
                handler.get("lbl.preguntas.nombremascota"),
                handler.get("lbl.pregunta.ciudad"),
                handler.get("lbl.pregunta.comida"),
                handler.get("lbl.pregunta.primermaestro"),
                handler.get("lbl.pregunta.mejoramigo"),
                handler.get("lbl.pregunta.primertrabajo"),
                handler.get("lbl.pregunta.calleinfancia"),
                handler.get("lbl.pregunta.peliculafavorita"),
                handler.get("lbl.pregunta.apodoinfancia")
        });
        cargarCheckBox(new String[]{
                handler.get("tipo.pregunta.1"),
                handler.get("tipo.pregunta.2"),
                handler.get("tipo.pregunta.3"),
                handler.get("tipo.pregunta.4"),
                handler.get("tipo.pregunta.5")
        });
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


    public JLabel getLblPregunta() {
        return lblPregunta;
    }

    public JLabel getLblPregunta2() {
        return lblPregunta2;
    }

    public JPanel getPanelGeneral() {
        return panelGeneral;
    }

    public JButton getBtnRegistrarse() {
        return btnRegistrarse;
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

    public JButton getBtnActualizar() {
        return btnActualizar;
    }

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

    public void limpiarCampos(){
        ckbTipo1.setSelected(false);
        ckbTipo2.setSelected(false);
        ckbTipo3.setSelected(false);
        ckbTipo4.setSelected(false);
        ckbTipo5.setSelected(false);

        txtPregunta.setText("");
        txtPregunta2.setText("");
        txtPregunta3.setText("");
        txtPregunta4.setText("");
        txtPregunta5.setText("");
        txtPregunta6.setText("");
        txtPregunta7.setText("");
        txtPregunta8.setText("");
        txtPregunta9.setText("");
        txtPregunta10.setText("");

    }

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

}