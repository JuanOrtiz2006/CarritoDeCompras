package ec.edu.ups.vista;

import ec.edu.ups.controlador.CarritoController;
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
    private JButton btnVaciar;
    private DefaultTableModel modelo;
    private DefaultTableModel modelo2;



    public CrearCarrito(){
        setContentPane(panelGeneral);
        setTitle("Crear Carrito");
        setSize(800, 500);
        setClosable(true);
        setMaximizable(true);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);

        modelo = new DefaultTableModel();
        Object[] columnasLista = {"ID", "NOMBRE", "PRECIO", "CANTIDAD","TOTAL", "EDITAR", "ELIMINAR"};
        modelo.setColumnIdentifiers(columnasLista);
        tblProductos.setModel(modelo);


        modelo2 = new DefaultTableModel();
        Object[] columnasTotales={"SubTotal","IVA","Total"};
        modelo2.setColumnIdentifiers(columnasTotales);
        tblTotal.setModel(modelo2);
        txtCodigoCarrito.setEnabled(false);
        txtNombre.setEnabled(false);
        txtPrecio.setEnabled(false);

        setVisible(true);
    }

    public void setController(CarritoController controller) {
        tblProductos.getColumn("EDITAR").setCellRenderer(new Boton("EDITAR"));
        tblProductos.getColumn("ELIMINAR").setCellRenderer(new Boton("ELIMINAR"));

        tblProductos.getColumn("EDITAR").setCellEditor(new BotonEditor(tblProductos, controller));
        tblProductos.getColumn("ELIMINAR").setCellEditor(new BotonEditor(tblProductos, controller));
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

    public JTextField getTxtCodigoCarrito() {
        return txtCodigoCarrito;
    }

    public JButton getBtnVaciar() {
        return btnVaciar;
    }

    public JTable getTblProductos() {
        return tblProductos;
    }

    public void productoEncontrado(String nombre, double precio){
        txtNombre.setText(nombre);
        txtPrecio.setText(Double.toString(precio));
    }

    public void limpiarFormulario() {
        txtFecha.setText("");
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
        txtCantidad.setText("");
        modelo.setRowCount(0);
        modelo2.setRowCount(0);
    }

    public GregorianCalendar obtenerFecha() {
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

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }


}
