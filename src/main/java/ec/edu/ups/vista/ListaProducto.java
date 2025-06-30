package ec.edu.ups.vista;

import ec.edu.ups.modelo.Producto;
import ec.edu.ups.util.Contexto;

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
        setTitle(Contexto.getHandler().get("listaproducto.titulo"));
        setSize(500, 500);
        setClosable(true);
        setMaximizable(true);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);

        modelo = new DefaultTableModel();
        Object[] columnas = {
                Contexto.getHandler().get("listaproducto.columna.id"),
                Contexto.getHandler().get("listaproducto.columna.nombre"),
                Contexto.getHandler().get("listaproducto.columna.precio")
        };
        modelo.setColumnIdentifiers(columnas);
        tblProductos.setModel(modelo);

        // Cargar ítems del combo traducidos
        cmbTipo.addItem(""); // vacío
        cmbTipo.addItem(Contexto.getHandler().get("listaproducto.filtro.codigo"));
        cmbTipo.addItem(Contexto.getHandler().get("listaproducto.filtro.nombre"));

        // Botón y etiqueta traducidos
        btnListar.setText(Contexto.getHandler().get("listaproducto.boton"));
        lblTipo.setText(Contexto.getHandler().get("listaproducto.etiqueta"));
    }

    public void recargarTextos() {
        setTitle(Contexto.getHandler().get("listaproducto.titulo"));
        modelo.setColumnIdentifiers(new Object[]{
                Contexto.getHandler().get("listaproducto.columna.id"),
                Contexto.getHandler().get("listaproducto.columna.nombre"),
                Contexto.getHandler().get("listaproducto.columna.precio")
        });

        // Recargar combo y otros textos
        cmbTipo.removeAllItems();
        cmbTipo.addItem("");
        cmbTipo.addItem(Contexto.getHandler().get("listaproducto.filtro.codigo"));
        cmbTipo.addItem(Contexto.getHandler().get("listaproducto.filtro.nombre"));

        btnListar.setText(Contexto.getHandler().get("listaproducto.boton"));
        lblTipo.setText(Contexto.getHandler().get("listaproducto.etiqueta"));
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
