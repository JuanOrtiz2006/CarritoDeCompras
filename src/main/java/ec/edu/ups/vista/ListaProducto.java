package ec.edu.ups.vista;

import ec.edu.ups.modelo.Producto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ListaProducto extends JInternalFrame{
    private JTable tblProductos;
    private JPanel panelLista;
    private JPanel panelGeneral;
    private JComboBox cmbTipo;
    private JButton btnListar;
    private JPanel panelOpcion;
    private JLabel lblTipo;
    private DefaultTableModel modelo;

    public ListaProducto(){
        setContentPane(panelGeneral);
        setTitle("Buscar Producto");
        setSize(500, 500);
        setClosable(true);
        setMaximizable(true);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        modelo = new DefaultTableModel();
        Object[] columnas={"ID","NOMBRE","PRECIO"};
        modelo.setColumnIdentifiers(columnas);
        tblProductos.setModel(modelo);

        cmbTipo.addItem("");
        cmbTipo.addItem("Codigo");
        cmbTipo.addItem("Nombre");
    }

    public JPanel getPanelGeneral() {
        return panelGeneral;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public JComboBox getCmbTipo() {
        return cmbTipo;
    }

    public JButton getBtnListar() {
        return btnListar;
    }

    public void setPanelGeneral(JPanel panelGeneral) {
        this.panelGeneral = panelGeneral;
    }

    public void setModelo(DefaultTableModel modelo) {
        this.modelo = modelo;
    }

    public void cargarProductos(List<Producto> productos) {
        modelo.setNumRows(0); // Limpia la tabla
        for (Producto producto : productos) {
            Object[] filaProducto = {producto.getCodigo(), producto.getNombre(), producto.getPrecio()};
            modelo.addRow(filaProducto);
        }
    }
}
