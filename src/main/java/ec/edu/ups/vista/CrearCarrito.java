package ec.edu.ups.vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CrearCarrito extends JInternalFrame{
    private JPanel panelGeneral;
    private JTextField txtCodigoCarrito;
    private JTextField txtFecha;
    private JPanel panelDatosCarrito;
    private JPanel panelCodigoCarro;
    private JLabel lblCodigoCarrto;
    private JLabel lblFecha;
    private JPanel panelNorte;
    private JPanel panelDatos;
    private JPanel panelProducto;
    private JLabel lblProducto;
    private JPanel panelDatosProducto;
    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JButton btnSeleccionar;
    private JPanel panelCantidad;
    private JLabel lblCantidad;
    private JTextField txtCantidad;
    private JButton btnAgregar;
    private JPanel panelCentral;
    private JTable tblProductos;
    private JPanel panelSur;
    private JButton btnGuardar;
    private JTable tblTotal;
    private JButton btnVaciar;
    private JButton btnEditar;
    private DefaultTableModel modeloItems;
    private DefaultTableModel modeloTotales;

    public CrearCarrito(){
        setContentPane(panelGeneral);
        setTitle("Crear Carrito");
        setSize(800, 500);
        setClosable(true);
        setMaximizable(true);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);

        modeloItems = new DefaultTableModel();
        Object[] columnasLista = {"ID", "NOMBRE", "PRECIO", "CANTIDAD","TOTAL", "EDITAR", "ELIMINAR"};
        modeloItems.setColumnIdentifiers(columnasLista);
        tblProductos.setModel(modeloItems);

        modeloTotales = new DefaultTableModel();
        Object[] columnasTotales={"SubTotal","IVA","Total"};
        modeloTotales.setColumnIdentifiers(columnasTotales);
        tblTotal.setModel(modeloTotales);
        txtCodigoCarrito.setEnabled(false);
        txtNombre.setEnabled(false);
        txtPrecio.setEnabled(false);
        txtFecha.setEnabled(false);
        setVisible(true);
    }

    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public JButton getBtnSeleccionar() {
        return btnSeleccionar;
    }

    public JTextField getTxtCantidad() {
        return txtCantidad;
    }

    public JButton getBtnAgregar() {
        return btnAgregar;
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public JTable getTblTotal() {
        return tblTotal;
    }

    public JTextField getTxtFecha() {
        return txtFecha;
    }

    public JTextField getTxtCodigoCarrito() {
        return txtCodigoCarrito;
    }

    public JButton getBtnVaciar() {
        return btnVaciar;
    }

    public JTable getTblProductos() {
        return tblProductos;
    }

    public JButton getBtnEditar() {
        return btnEditar;
    }

    public void productoEncontrado(String nombre, double precio) {
        txtNombre.setText(nombre);
        txtPrecio.setText(String.valueOf(precio));
    }

    public void limpiarFormulario() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
        txtCantidad.setText("");
        modeloItems.setRowCount(0);
        modeloTotales.setRowCount(0);
    }

    public void activarModoEdicion() {
        btnGuardar.setVisible(false);
        btnEditar.setVisible(true);
        panelNorte.setVisible(false);
    }

    public void activarModoCreacion() {
        btnGuardar.setVisible(true);
        btnEditar.setVisible(false);
        panelNorte.setVisible(true);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}
