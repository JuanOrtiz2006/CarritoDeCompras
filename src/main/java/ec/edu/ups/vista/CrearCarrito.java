package ec.edu.ups.vista;

import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

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
    private DefaultTableModel modelo;
    private DefaultTableModel modelo2;
    private Carrito carritoActual;
    private List<ItemCarrito> items;



    public CrearCarrito(){
        setContentPane(panelGeneral);
        setTitle("Agregar Producto");
        setSize(800, 500);
        setClosable(true);
        setMaximizable(true);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        modelo = new DefaultTableModel();
        Object[] columnasLista={"ID","NOMBRE","PRECIO", "CANTIDAD"};
        modelo.setColumnIdentifiers(columnasLista);
        tblProductos.setModel(modelo);

        modelo2 = new DefaultTableModel();
        Object[] columnasTotales={"SubTotal","IVA","Total"};
        modelo2.setColumnIdentifiers(columnasTotales);
        tblTotal.setModel(modelo2);
        txtNombre.setEnabled(false);
        txtPrecio.setEnabled(false);

        carritoActual = new Carrito(0, new GregorianCalendar()); // el código real se asignará después

    }

    public JTextField getTxtCodigoCarrito() {
        return txtCodigoCarrito;
    }

    public JTextField getTxtFecha() {
        return txtFecha;
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

    public JTextField getTxtCantidad() {
        return txtCantidad;
    }

    public JButton getBtnAgregar() {
        return btnAgregar;
    }

    public JTable getTblProductos() {
        return tblProductos;
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public JTable getTblTotal() {
        return tblTotal;
    }

    public void setTxtCodigoCarrito(JTextField txtCodigoCarrito) {
        this.txtCodigoCarrito = txtCodigoCarrito;
    }

    public void setTxtFecha(JTextField txtFecha) {
        this.txtFecha = txtFecha;
    }

    public void setTxtCodigo(JTextField txtCodigo) {
        this.txtCodigo = txtCodigo;
    }

    public void setTxtNombre(JTextField txtNombre) {
        this.txtNombre = txtNombre;
    }

    public void setTxtPrecio(JTextField txtPrecio) {
        this.txtPrecio = txtPrecio;
    }

    public void setBtnSeleccionar(JButton btnSeleccionar) {
        this.btnSeleccionar = btnSeleccionar;
    }

    public void setTxtCantidad(JTextField txtCantidad) {
        this.txtCantidad = txtCantidad;
    }

    public void setBtnAgregar(JButton btnAgregar) {
        this.btnAgregar = btnAgregar;
    }

    public void setTblProductos(JTable tblProductos) {
        this.tblProductos = tblProductos;
    }

    public void setBtnGuardar(JButton btnGuardar) {
        this.btnGuardar = btnGuardar;
    }

    public void setTblTotal(JTable tblTotal) {
        this.tblTotal = tblTotal;
    }

    public void productoEncontrado(String nombre, double precio){
        txtNombre.setText(nombre);
        txtPrecio.setText(Double.toString(precio));
    }

    public void cargarItem(ItemCarrito itemCarrito) {
        int codigo = itemCarrito.getProducto().getCodigo();
        String nombre = itemCarrito.getProducto().getNombre();
        double precio = itemCarrito.getProducto().getPrecio();
        int cantidad = itemCarrito.getCantidad();
        Object[] filaProducto = {codigo,nombre, precio, cantidad};
        modelo.addRow(filaProducto);
        carritoActual.agregarProducto(itemCarrito.getProducto(), cantidad);
        calcularTotales();
    }

    private void calcularTotales(){
        double subtotal = 0.0;
        for (int i = 0; i < modelo.getRowCount(); i++) {
            double precio = (double) modelo.getValueAt(i, 2);
            int cantidad = (int) modelo.getValueAt(i, 3);
            subtotal += precio * cantidad;
        }
        double iva = subtotal * 0.15;
        double total = subtotal + iva;
        modelo2.setRowCount(0);
        Object[] filaTotal = {String.format("%.2f", subtotal),
                String.format("%.2f", iva),
                String.format("%.2f", total)};
        modelo2.addRow(filaTotal);
    }

    public Carrito obtenerCarrito() {
        int codigo = Integer.parseInt(txtCodigoCarrito.getText());
        carritoActual.setCodigo(codigo);
        carritoActual.setFecha(obtenerFechaDesdeTxt());
        return carritoActual;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void limpiarFormulario() {
        txtCodigoCarrito.setText("");
        txtFecha.setText("");
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
        txtCantidad.setText("");
        modelo.setRowCount(0);
        modelo2.setRowCount(0);
        carritoActual = new Carrito(0, new GregorianCalendar());
    }

    public GregorianCalendar obtenerFechaDesdeTxt() {
        String fechaTexto = txtFecha.getText().trim();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date fecha = formato.parse(fechaTexto);
            GregorianCalendar calendario = new GregorianCalendar();
            calendario.setTime(fecha);
            return calendario;
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Debe ser dd/MM/yyyy", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}
