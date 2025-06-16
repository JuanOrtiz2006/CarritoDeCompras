package ec.edu.ups.vista;

import ec.edu.ups.modelo.Producto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ListaProducto extends JFrame{
    private JTable tblProductos;
    private JPanel panelLista;
    private JPanel panelGeneral;
    private DefaultTableModel modelo;


    public ListaProducto(){
        setContentPane(panelGeneral);
        setTitle("Buscar Producto");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setVisible(true);
        modelo = new DefaultTableModel();
        Object[] columnas={"ID","NOMBRE","PRECIO"};
        modelo.setColumnIdentifiers(columnas);
        tblProductos.setModel(modelo);
    }

    public JTable getTblProductos() {
        return tblProductos;
    }

    public JPanel getPanelLista() {
        return panelLista;
    }

    public JPanel getPanelGeneral() {
        return panelGeneral;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public void setTblProductos(JTable tblProductos) {
        this.tblProductos = tblProductos;
    }

    public void setPanelLista(JPanel panelLista) {
        this.panelLista = panelLista;
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
