package ec.edu.ups.vista;

import ec.edu.ups.modelo.Producto;
import ec.edu.ups.util.Contexto;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
        setTitle(Contexto.getHandler().get("buscarproducto.titulo"));
        setSize(500, 500);
        setClosable(true);
        setMaximizable(true);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);

        modelo = new DefaultTableModel();
        Object[] columnas = {
                Contexto.getHandler().get("buscarproducto.columna.id"),
                Contexto.getHandler().get("buscarproducto.columna.nombre"),
                Contexto.getHandler().get("buscarproducto.columna.precio")
        };
        modelo.setColumnIdentifiers(columnas);
        tblProducto.setModel(modelo);

        cmbBusqueda.addItem("");
        cmbBusqueda.addItem(Contexto.getHandler().get("buscarproducto.combo.codigo"));
        cmbBusqueda.addItem(Contexto.getHandler().get("buscarproducto.combo.nombre"));

        setVisible(true);

        cmbBusqueda.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String selectedItem = (String) e.getItem();
                    if (selectedItem.isEmpty()) {
                        txtBusqueda.setText("");
                    } else {
                        txtBusqueda.setText(Contexto.getHandler().get("buscarproducto.ingrese") + " " + selectedItem);
                    }
                }
            }
        });
    }
    
    public void recargarTextos(){
        setTitle(Contexto.getHandler().get("buscarproducto.titulo"));
        Object[] columnas = {
                Contexto.getHandler().get("buscarproducto.columna.id"),
                Contexto.getHandler().get("buscarproducto.columna.nombre"),
                Contexto.getHandler().get("buscarproducto.columna.precio")
        };
        modelo.setColumnIdentifiers(columnas);
        tblProducto.setModel(modelo);

        cmbBusqueda.addItem("");
        cmbBusqueda.addItem(Contexto.getHandler().get("buscarproducto.combo.codigo"));
        cmbBusqueda.addItem(Contexto.getHandler().get("buscarproducto.combo.nombre"));
        
    }

    public JPanel getPanelGeneral() {
        return panelGeneral;
    }

    public JTextField getTxtBusqueda() {
        return txtBusqueda;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JComboBox getCmbBusqueda() {
        return cmbBusqueda;
    }

    public void setPanelGeneral(JPanel panelGeneral) {
        this.panelGeneral = panelGeneral;
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
