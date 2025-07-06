package ec.edu.ups.vista;

import ec.edu.ups.modelo.Producto;
import ec.edu.ups.util.Contexto;
import ec.edu.ups.util.FormateadorUtils;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
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
        setTitle(Contexto.getHandler().get("listaproducto.titulo"));
        setSize(500, 500);
        setClosable(true);
        setMaximizable(true);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);

        modelo = new DefaultTableModel();
        tblProductos.setModel(modelo);

        actualizarIdioma();
    }

    public void actualizarIdioma() {
        var handler = Contexto.getHandler();

        setTitle(handler.get("listaproducto.titulo"));
        lblTipo.setText(handler.get("listaproducto.etiqueta"));
        btnListar.setText(handler.get("listaproducto.boton"));

        modelo.setColumnIdentifiers(new Object[]{
                handler.get("listaproducto.columna.id"),
                handler.get("listaproducto.columna.nombre"),
                handler.get("listaproducto.columna.precio")
        });

        cmbTipo.removeAllItems();
        cmbTipo.addItem(""); // vacío
        cmbTipo.addItem(handler.get("listaproducto.filtro.codigo"));
        cmbTipo.addItem(handler.get("listaproducto.filtro.nombre"));

        Border border = panelGeneral.getBorder();
        if (border instanceof TitledBorder) {
            ((TitledBorder) border).setTitle(handler.get("listaproducto.borde"));
            panelGeneral.repaint();
        }
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
            Object[] filaProducto = {producto.getCodigo(), producto.getNombre(), FormateadorUtils.formatearMoneda(producto.getPrecio(), Contexto.getLocale())};
            modelo.addRow(filaProducto);
        }
    }
}
