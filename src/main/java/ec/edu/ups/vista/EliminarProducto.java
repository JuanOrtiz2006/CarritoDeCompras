package ec.edu.ups.vista;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EliminarProducto extends JInternalFrame {
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
        setSize(500, 500);
        setClosable(true);
        setMaximizable(true);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        txtNombre.setEnabled(false);
        txtPrecio.setEnabled(false);
    }

    public JPanel getPanelGeneral() {
        return panelGeneral;
    }

    public JButton getBtnSeleccionar() {
        return btnSeleccionar;
    }

    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public void setPanelGeneral(JPanel panelGeneral) {
        this.panelGeneral = panelGeneral;
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
