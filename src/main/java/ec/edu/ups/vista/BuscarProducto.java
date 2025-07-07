package ec.edu.ups.vista;

import ec.edu.ups.modelo.Producto;
import ec.edu.ups.util.Contexto;
import ec.edu.ups.util.FormateadorUtils;


import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.URL;
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
        tblProducto.setModel(modelo);

        btnBuscar.setIcon(cargarIcono("search.png"));

        actualizarIdioma();
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

    public void actualizarIdioma() {
        var handler = Contexto.getHandler();
        setTitle(handler.get("buscarproducto.titulo"));
        labelBusqueda.setText(handler.get("buscarproducto.label"));
        btnBuscar.setText(handler.get("buscarproducto.boton"));

        // Actualizar columnas de la tabla
        String[] columnas = {
                handler.get("buscarproducto.columna.id"),
                handler.get("buscarproducto.columna.nombre"),
                handler.get("buscarproducto.columna.precio")
        };
        modelo.setColumnIdentifiers(columnas);

        // Actualizar opciones del combo
        cmbBusqueda.removeAllItems();
        cmbBusqueda.addItem("");
        cmbBusqueda.addItem(handler.get("buscarproducto.combo.codigo"));
        cmbBusqueda.addItem(handler.get("buscarproducto.combo.nombre"));

        // Actualizar título del panel general si tiene un borde
        Border border = panelGeneral.getBorder();
        if (border instanceof TitledBorder) {
            ((TitledBorder) border).setTitle(handler.get("buscarproducto.borde"));
            panelGeneral.repaint();
        }
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

    public void limpiarCampos(){
        txtBusqueda.setText("");
        modelo.setNumRows(0);
    }

    public void cargarProductoBuscado(Producto producto){
        String precioFormateado = FormateadorUtils.formatearMoneda(producto.getPrecio(), Contexto.getLocale());
        modelo.setNumRows(0);
        Object[] filaProducto={producto.getCodigo(),producto.getNombre(),precioFormateado};
        modelo.addRow(filaProducto);
    }

    public void cargarProductosListados(List<Producto> productos) {
        modelo.setNumRows(0); // Limpia la tabla
        for (Producto producto : productos) {
            Object[] filaProducto = {producto.getCodigo(), producto.getNombre(), FormateadorUtils.formatearMoneda(producto.getPrecio(), Contexto.getLocale())};
            modelo.addRow(filaProducto);
        }
    }

    public ImageIcon cargarIcono(String nombreArchivo) {
        URL url = getClass().getClassLoader().getResource("icons/" + nombreArchivo);
        if (url != null) {
            Image img = new ImageIcon(url).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } else {
            System.err.println("Icono no encontrado: iconos/" + nombreArchivo);
            return null;
        }
    }

}
