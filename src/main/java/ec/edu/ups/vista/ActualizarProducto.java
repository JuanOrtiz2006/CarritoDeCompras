package ec.edu.ups.vista;

import javax.swing.*;

public class ActualizarProducto extends JInternalFrame{
    private JPanel panelGeneral;
    private JPanel panelCentral;
    private JPanel panelDatos;
    private JLabel lblCodigo;
    private JTextField txtCodigo;
    private JLabel lblNombre;
    private JTextField txtNombre;
    private JLabel lblPrecio;
    private JTextField txtPrecio;
    private JButton btnSeleccionar;
    private JButton btnActualizar;

    public ActualizarProducto(){
        setContentPane(panelGeneral);
        setTitle("Actualizar Producto");
        setSize(500, 500);
        setClosable(true);
        setMaximizable(true);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

    }

    public JPanel getPanelGeneral() {
        return panelGeneral;
    }

    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public JTextField getTxtPrecio() {
        return txtPrecio;
    }

    public JButton getBtnSeleccionar() {
        return btnSeleccionar;
    }

    public JButton getBtnActualizar() {
        return btnActualizar;
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
