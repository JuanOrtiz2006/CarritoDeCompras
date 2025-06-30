package ec.edu.ups.vista;

import ec.edu.ups.util.Contexto;

import javax.swing.*;
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

        lblBusqueda.setText(Contexto.getHandler().get("gestionusuarios.etiqueta.busqueda"));
        lblLista.setText(Contexto.getHandler().get("gestionusuarios.etiqueta.lista"));
        btnBuscar.setText(Contexto.getHandler().get("gestionusuarios.boton.buscar"));
        btnListar.setText(Contexto.getHandler().get("gestionusuarios.boton.listar"));
        btnListar.setText(Contexto.getHandler().get("gestionusuarios.boton.listarTodos"));
        btnCrear.setText(Contexto.getHandler().get("gestionusuarios.boton.crear"));

        modelo = new DefaultTableModel();
        Object[] columnas = {
                Contexto.getHandler().get("gestionusuarios.columna.rol"),
                Contexto.getHandler().get("gestionusuarios.columna.usuario"),
                Contexto.getHandler().get("gestionusuarios.columna.password")
        };
        modelo.setColumnIdentifiers(columnas);
        tblUsuarios.setModel(modelo);

        cmbLista.removeAllItems();
        cmbLista.addItem("");
        cmbLista.addItem(Contexto.getHandler().get("gestionusuarios.combo.usuarios"));
        cmbLista.addItem(Contexto.getHandler().get("gestionusuarios.combo.admins"));
        cmbLista.addItem(Contexto.getHandler().get("gestionusuarios.combo.todos"));

    }

    public void recargarTextos() {
        setTitle(Contexto.getHandler().get("gestionusuarios.titulo"));
        lblBusqueda.setText(Contexto.getHandler().get("gestionusuarios.etiqueta.busqueda"));
        lblLista.setText(Contexto.getHandler().get("gestionusuarios.etiqueta.lista"));
        btnBuscar.setText(Contexto.getHandler().get("gestionusuarios.boton.buscar"));
        btnListar.setText(Contexto.getHandler().get("gestionusuarios.boton.listar"));
        btnCrear.setText(Contexto.getHandler().get("gestionusuarios.boton.crear"));

        modelo.setColumnIdentifiers(new Object[]{
                Contexto.getHandler().get("gestionusuarios.columna.rol"),
                Contexto.getHandler().get("gestionusuarios.columna.usuario"),
                Contexto.getHandler().get("gestionusuarios.columna.password")
        });

        cmbLista.removeAllItems();
        cmbLista.addItem("");
        cmbLista.addItem(Contexto.getHandler().get("gestionusuarios.combo.usuarios"));
        cmbLista.addItem(Contexto.getHandler().get("gestionusuarios.combo.admins"));
        cmbLista.addItem(Contexto.getHandler().get("gestionusuarios.combo.todos"));
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

    public int mostrarConfirmDialog(String mensaje, String[] opciones) {
        return JOptionPane.showOptionDialog(
                null,
                mensaje,
                Contexto.getHandler().get("gestionusuarios.titulo.dialogo"),
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]
        );
    }
}
