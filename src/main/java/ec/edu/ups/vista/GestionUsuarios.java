package ec.edu.ups.vista;

import ec.edu.ups.util.Contexto;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class GestionUsuarios extends JInternalFrame{
    private JPanel panelGeneral;
    private JPanel panelBusqueda;
    private JTextField txtBusqueda;
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
    private JButton btnCrear;
    private JPanel panelSur;
    private DefaultTableModel modelo;

    public GestionUsuarios(){
        setContentPane(panelGeneral);
        setTitle(Contexto.getHandler().get("gestionusuarios.titulo"));
        setSize(500, 500);
        setClosable(true);
        setMaximizable(true);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);

        modelo = new DefaultTableModel();
        tblUsuarios.setModel(modelo);

        actualizarIdioma();
    }

    public void actualizarIdioma() {
        var handler = Contexto.getHandler();

        setTitle(handler.get("gestionusuarios.titulo"));
        lblBusqueda.setText(handler.get("gestionusuarios.etiqueta.busqueda"));
        lblLista.setText(handler.get("gestionusuarios.etiqueta.lista"));
        btnBuscar.setText(handler.get("gestionusuarios.boton.buscar"));
        btnListar.setText(handler.get("gestionusuarios.boton.listar"));
        btnCrear.setText(handler.get("gestionusuarios.boton.crear"));

        modelo.setColumnIdentifiers(new Object[]{
                handler.get("gestionusuarios.columna.rol"),
                handler.get("gestionusuarios.columna.usuario"),
                handler.get("gestionusuarios.columna.password")
        });

        cmbLista.removeAllItems();
        cmbLista.addItem("");
        cmbLista.addItem(handler.get("gestionusuarios.combo.usuarios"));
        cmbLista.addItem(handler.get("gestionusuarios.combo.admins"));
        cmbLista.addItem(handler.get("gestionusuarios.combo.todos"));

        Border border = panelGeneral.getBorder();
        if (border instanceof TitledBorder) {
            ((TitledBorder) border).setTitle(handler.get("gestionusuarios.borde"));
            panelGeneral.repaint();
        }
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

    public JButton getBtnCrear() {
        return btnCrear;
    }

    public void mostrarMensaje(String mensaje){
        JOptionPane.showMessageDialog(null,mensaje);

    }
}
