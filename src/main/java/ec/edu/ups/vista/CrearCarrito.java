package ec.edu.ups.vista;

import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.util.Contexto;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
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
        setTitle(Contexto.getHandler().get("crearcarrito.titulo"));
        setSize(800, 500);
        setClosable(true);
        setMaximizable(true);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);

        modeloItems = new DefaultTableModel();
        tblProductos.setModel(modeloItems);
        modeloTotales = new DefaultTableModel();
        tblTotal.setModel(modeloTotales);

        actualizarIdioma();

        txtCodigoCarrito.setEnabled(false);
        txtNombre.setEnabled(false);
        txtPrecio.setEnabled(false);
        txtFecha.setEnabled(false);

    }

    public void actualizarIdioma() {
        var handler = Contexto.getHandler();
        setTitle(handler.get("crearcarrito.titulo"));

        // Etiquetas
        lblCodigoCarrto.setText(handler.get("crearcarrito.codigo"));
        lblFecha.setText(handler.get("crearcarrito.fecha"));
        lblProducto.setText(handler.get("crearcarrito.producto"));
        lblCantidad.setText(handler.get("crearcarrito.cantidad"));

        // Textos en campos
        txtCodigo.setText(handler.get("crearcarrito.columna.id"));
        txtNombre.setText(handler.get("crearcarrito.columna.nombre"));
        txtPrecio.setText(handler.get("crearcarrito.columna.precio"));
        txtCantidad.setText(handler.get("crearcarrito.columna.cantidad"));

        // Botones
        btnSeleccionar.setText(handler.get("crearcarrito.boton.seleccionar"));
        btnAgregar.setText(handler.get("crearcarrito.boton.agregar"));
        btnGuardar.setText(handler.get("crearcarrito.boton.guardar"));
        btnVaciar.setText(handler.get("crearcarrito.boton.vaciar"));
        btnEditar.setText(handler.get("crearcarrito.boton.editar"));

        // Tabla de productos
        String[] columnasLista = {
                handler.get("crearcarrito.columna.id"),
                handler.get("crearcarrito.columna.nombre"),
                handler.get("crearcarrito.columna.precio"),
                handler.get("crearcarrito.columna.cantidad"),
                handler.get("crearcarrito.columna.total")
        };
        modeloItems.setColumnIdentifiers(columnasLista);

        // Tabla de totales
        String[] columnasTotales = {
                handler.get("crearcarrito.columna.subtotal"),
                handler.get("crearcarrito.columna.iva"),
                handler.get("crearcarrito.columna.totalgeneral")
        };
        modeloTotales.setColumnIdentifiers(columnasTotales);

        // Borde del panel
        Border border = panelGeneral.getBorder();
        if (border instanceof TitledBorder) {
            ((TitledBorder) border).setTitle(handler.get("crearcarrito.borde"));
            panelGeneral.repaint();
        }
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

    public void cargarProductoEncontrado(String nombre, double precio) {
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

    public void limpiarCamposItem(){
        txtCodigo.setText("");
        txtCantidad.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}
