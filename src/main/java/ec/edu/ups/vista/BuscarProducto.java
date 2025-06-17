package ec.edu.ups.vista;

import ec.edu.ups.modelo.Producto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

public class BuscarProducto extends JInternalFrame {
    private JPanel panelGeneral;
    private JPanel panelCentral;
    private JTextField txtBusqueda;
    private JButton btnBuscar;
    private JPanel panelInput;
    private JPanel panelSur;
    private JComboBox cmbBusqueda;
    private JLabel labelBusqueda;
    private JTable tblProducto;
    private DefaultTableModel modelo;

    public BuscarProducto() {
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
        tblProducto.setModel(modelo);
        cmbBusqueda.addItem("");
        cmbBusqueda.addItem("Codigo");
        cmbBusqueda.addItem("Nombre");
        setVisible(true);

        cmbBusqueda.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String selectedItem = (String) e.getItem();
                    txtBusqueda.setText("Ingrese aqui el " + selectedItem);
                }
            }
        });

    }

    public JPanel getPanelGeneral() {
        return panelGeneral;
    }

    public JPanel getPanelCentral() {
        return panelCentral;
    }

    public JTextField getTxtBusqueda() {
        return txtBusqueda;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JPanel getPanelInput() {
        return panelInput;
    }

    public JPanel getPanelSur() {
        return panelSur;
    }

    public JComboBox getCmbBusqueda() {
        return cmbBusqueda;
    }

    public JLabel getLabelBusqueda() {
        return labelBusqueda;
    }

    public JTable getTblProducto() {
        return tblProducto;
    }

    public void setPanelGeneral(JPanel panelGeneral) {
        this.panelGeneral = panelGeneral;
    }

    public void setPanelCentral(JPanel panelCentral) {
        this.panelCentral = panelCentral;
    }

    public void setTxtBusqueda(JTextField txtBusqueda) {
        this.txtBusqueda = txtBusqueda;
    }

    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

    public void setPanelInput(JPanel panelInput) {
        this.panelInput = panelInput;
    }

    public void setPanelSur(JPanel panelSur) {
        this.panelSur = panelSur;
    }

    public void setCmbBusqueda(JComboBox cmbBusqueda) {
        this.cmbBusqueda = cmbBusqueda;
    }

    public void setLabelBusqueda(JLabel labelBusqueda) {
        this.labelBusqueda = labelBusqueda;
    }

    public void setTblProducto(JTable tblProducto) {
        this.tblProducto = tblProducto;
    }

    public void mostrarMensaje(String mensaje){
        JOptionPane.showMessageDialog(null, mensaje);
    }

    public void eliminarCampos (){
        txtBusqueda.setText("");
        modelo.setNumRows(0);
    }
    public void cargarProducto(Producto producto){
        modelo.setNumRows(0);
        Object[] filaProducto={producto.getCodigo(),producto.getNombre(),producto.getPrecio()};
        modelo.addRow(filaProducto);
    }
    public void cargarProductos(List<Producto> productos) {
        modelo.setNumRows(0); // Limpia la tabla
        for (Producto producto : productos) {
            Object[] filaProducto = {producto.getCodigo(), producto.getNombre(), producto.getPrecio()};
            modelo.addRow(filaProducto);
        }
    }
}
