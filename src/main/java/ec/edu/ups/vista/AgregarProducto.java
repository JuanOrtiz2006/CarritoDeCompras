package ec.edu.ups.vista;

import ec.edu.ups.modelo.Producto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AgregarProducto extends JInternalFrame {
    private JPanel panelGeneral;
    private JTextField txtCantidad;
    private JPanel panelDatos;
    private JPanel panelProducto;
    private JLabel lblProducto;
    private JPanel panelCantidad;
    private JLabel lblCantidad;
    private JButton btnAgregar;
    private JTable tblProductos;
    private JButton btnGuardar;
    private JPanel panelSur;
    private JPanel panelNorte;
    private JPanel panelCentral;
    private JTable tblTotal;
    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JButton btnSeleccionar;
    private JPanel panelDatosProducto;
    private DefaultTableModel modelo;
    private DefaultTableModel modelo2;


    public AgregarProducto(){
        setContentPane(panelGeneral);
        setTitle("Agregar Producto");
        setSize(500, 500);
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
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int canntidad = Integer.parseInt(txtCantidad.getText());


            }
        });

    }



    public JTextField getTextCantidad() {
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



    public void productoEncontrado(String nombre, double precio){
        txtNombre.setText(nombre);
        txtPrecio.setText(Double.toString(precio));
    }

    public void cargarProducto(Producto producto) {
        Object[] filaProducto = {producto.getCodigo(), producto.getNombre(), producto.getPrecio(), txtCantidad.getText()};
        modelo.addRow(filaProducto);
    }
}

