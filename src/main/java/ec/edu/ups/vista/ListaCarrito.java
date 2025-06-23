package ec.edu.ups.vista;

import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.Producto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ListaCarrito extends JInternalFrame{
    private JPanel panelGeneral;
    private JTextField txtCodigo;
    private JButton btnBuscar;
    private JButton btnListar;
    private JTable tblCarritos;
    private JPanel panelCentral;
    private JPanel panelBusqueda;
    private JPanel panelTabla;
    private JLabel lblCodigo;
    private JScrollPane scrTabla;
    private DefaultTableModel modelo;

    public ListaCarrito(){
        setContentPane(panelGeneral);
        setTitle("Buscar Producto");
        setSize(500, 500);
        setClosable(true);
        setMaximizable(true);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        modelo = new DefaultTableModel();
        Object[] columnas = {"ID", "FECHA", "DESCRIPCION", "EDITAR", "ELIMINAR"};
        modelo.setColumnIdentifiers(columnas);
        tblCarritos.setModel(modelo);



    }

    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JButton getBtnListar() {
        return btnListar;
    }

    public JTable getTblCarritos() {
        return tblCarritos;
    }

    public void setTxtCodigo(JTextField txtCodigo) {
        this.txtCodigo = txtCodigo;
    }

    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

    public void setBtnListar(JButton btnListar) {
        this.btnListar = btnListar;
    }

    public void setTblCarritos(JTable tblCarritos) {
        this.tblCarritos = tblCarritos;
    }


}
