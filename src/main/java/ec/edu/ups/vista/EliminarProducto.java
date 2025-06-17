package ec.edu.ups.vista;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EliminarProducto extends JFrame {
    private JPanel panelGeneral;
    private JPanel panelCentral;
    private JTextField txtCodigo;
    private JLabel lblCodigo;
    private JButton btnEliminar;
    private JPanel panelDatos;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JLabel lblNombre;
    private JButton btnSeleccionar;
    private JLabel lblPrecio;


    public EliminarProducto(){
        setContentPane(panelGeneral);
        setTitle("Eliminar Producto");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        //setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        txtNombre.setEnabled(false);
        txtPrecio.setEnabled(false);
    }

    public JPanel getPanelGeneral() {
        return panelGeneral;
    }

    public JPanel getPanelCentral() {
        return panelCentral;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public JTextField getTxtPrecio() {
        return txtPrecio;
    }

    public JLabel getLblNombre() {
        return lblNombre;
    }

    public JButton getBtnSeleccionar() {
        return btnSeleccionar;
    }

    public JLabel getLblPrecio() {
        return lblPrecio;
    }

    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public JLabel getLblCodigo() {
        return lblCodigo;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public JPanel getPanelDatos() {
        return panelDatos;
    }

    public void setPanelGeneral(JPanel panelGeneral) {
        this.panelGeneral = panelGeneral;
    }

    public void setPanelCentral(JPanel panelCentral) {
        this.panelCentral = panelCentral;
    }

    public void setTxtCodigo(JTextField txtCodigo) {
        this.txtCodigo = txtCodigo;
    }

    public void setLblCodigo(JLabel lblCodigo) {
        this.lblCodigo = lblCodigo;
    }

    public void setBtnEliminar(JButton btnEliminar) {
        this.btnEliminar = btnEliminar;
    }

    public void setPanelDatos(JPanel panelDatos) {
        this.panelDatos = panelDatos;
    }

    public void setTxtNombre(JTextField txtNombre) {
        this.txtNombre = txtNombre;
    }

    public void setTxtPrecio(JTextField txtPrecio) {
        this.txtPrecio = txtPrecio;
    }

    public void setLblNombre(JLabel lblNombre) {
        this.lblNombre = lblNombre;
    }

    public void setBtnSeleccionar(JButton btnSeleccionar) {
        this.btnSeleccionar = btnSeleccionar;
    }

    public void setLblPrecio(JLabel lblPrecio) {
        this.lblPrecio = lblPrecio;
    }

    public int mostrarMensaje(String mensaje) {
        int opcion = JOptionPane.showConfirmDialog(this, mensaje);
        return opcion;
    }

    public void productoEncontrado(String nombre, double precio){
        txtNombre.setText(nombre);
        txtPrecio.setText(Double.toString(precio));
    }

    public void limpiarCampos() {
        txtCodigo.setText("");
    }
}
