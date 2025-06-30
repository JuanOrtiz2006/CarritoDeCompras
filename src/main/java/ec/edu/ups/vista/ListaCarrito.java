package ec.edu.ups.vista;

import ec.edu.ups.util.Contexto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

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

    public ListaCarrito() {
        setContentPane(panelGeneral);
        setTitle(Contexto.getHandler().get("listacarrito.titulo"));
        setSize(500, 500);
        setClosable(true);
        setMaximizable(true);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);

        lblCodigo.setText(Contexto.getHandler().get("listacarrito.codigo"));
        btnBuscar.setText(Contexto.getHandler().get("listacarrito.boton.buscar"));
        btnListar.setText(Contexto.getHandler().get("listacarrito.boton.listar"));

        modelo = new DefaultTableModel();
        Object[] columnas = {
                Contexto.getHandler().get("listacarrito.columna.usuario"),
                Contexto.getHandler().get("listacarrito.columna.id"),
                Contexto.getHandler().get("listacarrito.columna.fecha"),
                Contexto.getHandler().get("listacarrito.columna.total")
        };
        modelo.setColumnIdentifiers(columnas);
        tblCarritos.setModel(modelo);
    }

    // Metodo para recargar textos si se cambia el idioma
    public void recargarTextos() {
        setTitle(Contexto.getHandler().get("listacarrito.titulo"));
        lblCodigo.setText(Contexto.getHandler().get("listacarrito.codigo"));
        btnBuscar.setText(Contexto.getHandler().get("listacarrito.boton.buscar"));
        btnListar.setText(Contexto.getHandler().get("listacarrito.boton.listar"));

        modelo.setColumnIdentifiers(new Object[]{
                Contexto.getHandler().get("listacarrito.columna.usuario"),
                Contexto.getHandler().get("listacarrito.columna.id"),
                Contexto.getHandler().get("listacarrito.columna.fecha"),
                Contexto.getHandler().get("listacarrito.columna.total")
        });
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

    public int mostrarConfirmDialog(String mensaje, String[] opciones) {
        int seleccion = JOptionPane.showOptionDialog(
                null,
                mensaje,
                Contexto.getHandler().get("listacarrito.opciones.titulo"),
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]
        );
        return seleccion;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje);
    }
}
