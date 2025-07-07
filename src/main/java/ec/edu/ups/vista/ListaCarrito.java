package ec.edu.ups.vista;

import ec.edu.ups.util.Contexto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.net.URL;

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
        setSize(800, 500);
        setClosable(true);
        setMaximizable(true);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);

        modelo = new DefaultTableModel();
        tblCarritos.setModel(modelo);


        btnBuscar.setIcon(cargarIcono("search.png"));
        btnListar.setIcon(cargarIcono("list.png"));

        actualizarIdioma();
    }

    public void actualizarIdioma() {
        var handler = Contexto.getHandler();

        setTitle(handler.get("listacarrito.titulo"));
        lblCodigo.setText(handler.get("listacarrito.codigo"));
        btnBuscar.setText(handler.get("listacarrito.boton.buscar"));
        btnListar.setText(handler.get("listacarrito.boton.listar"));

        modelo.setColumnIdentifiers(new Object[]{
                handler.get("listacarrito.columna.usuario"),
                handler.get("listacarrito.columna.id"),
                handler.get("listacarrito.columna.fecha"),
                handler.get("listacarrito.columna.total")
        });

        Border border = panelGeneral.getBorder();
        if (border instanceof TitledBorder) {
            ((TitledBorder) border).setTitle(handler.get("listacarrito.borde"));
            panelGeneral.repaint();
        }
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
