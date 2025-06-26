package ec.edu.ups.vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class GestionUsuarios extends JInternalFrame{
    private JPanel panelGeneral;
    private JPanel panelBusqueda;
    private JTextField txtBusqueda;
    private JComboBox cmbBusqueda;
    private JButton btnBuscar;
    private JPanel panelNorte;
    private JLabel lblBusqueda;
    private JTable tblUsuarios;
    private JPanel panelCentro;
    private JScrollPane scrTabla;
    private JButton btnListar;
    private JPanel panelLista;
    private JLabel lblLista;
    private JComboBox cmbLista;
    private JButton btnListarTodos;
    private DefaultTableModel modelo;

    public GestionUsuarios(){
        setContentPane(panelGeneral);
        setTitle("Buscar Producto");
        setSize(500, 500);
        setClosable(true);
        setMaximizable(true);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        modelo = new DefaultTableModel();
        Object[] columnas = {"ROL","USERNAME", "PASSWORD"};
        modelo.setColumnIdentifiers(columnas);
        tblUsuarios.setModel(modelo);

        cmbLista.addItem("");
        cmbLista.addItem("USUARIOS");
        cmbLista.addItem("ADMINISTRADORES");
        cmbLista.addItem("TODOS");

    }

    public JTextField getTxtBusqueda() {
        return txtBusqueda;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JTable getTblUsuarios() {
        return tblUsuarios;
    }

    public JButton getBtnListar() {
        return btnListar;
    }

    public JComboBox getCmbLista() {
        return cmbLista;
    }

    public int mostrarConfirmDialog(String mensaje, String[] opciones){

        int seleccion = JOptionPane.showOptionDialog(
                null,
                mensaje,
                "Opciones del carrito",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]
        );

        return seleccion;
    }
}
