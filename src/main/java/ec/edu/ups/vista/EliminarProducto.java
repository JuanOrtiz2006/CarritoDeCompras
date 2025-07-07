package ec.edu.ups.vista;

import ec.edu.ups.util.Contexto;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.net.URL;

public class EliminarProducto extends JInternalFrame {
    private JPanel panelGeneral;
    private JPanel panelCentral;
    private JTextField txtCodigo;
    private JLabel lblCodigo;
    private JButton btnEliminar;
    private JPanel panelDatos;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JLabel lblNombre;
    private JButton btnSeleccionar;
    private JLabel lblPrecio;

    public EliminarProducto() {
        setContentPane(panelGeneral);
        setTitle(Contexto.getHandler().get("eliminarproducto.titulo"));
        setSize(500, 500);
        setClosable(true);
        setMaximizable(true);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);

        txtNombre.setEnabled(false);
        txtPrecio.setEnabled(false);

        btnSeleccionar.setIcon(cargarIcono("check.png"));
        btnEliminar.setIcon(cargarIcono("delete.png"));

        actualizarIdioma();
    }

    public void actualizarIdioma() {
        var handler = Contexto.getHandler();

        setTitle(handler.get("eliminarproducto.titulo"));
        lblCodigo.setText(handler.get("eliminarproducto.etiqueta.codigo"));
        lblNombre.setText(handler.get("eliminarproducto.etiqueta.nombre"));
        lblPrecio.setText(handler.get("eliminarproducto.etiqueta.precio"));
        btnSeleccionar.setText(handler.get("eliminarproducto.boton.seleccionar"));
        btnEliminar.setText(handler.get("eliminarproducto.boton.eliminar"));

        Border border = panelGeneral.getBorder();
        if (border instanceof TitledBorder) {
            ((TitledBorder) border).setTitle(handler.get("eliminarproducto.borde"));
            panelGeneral.repaint();
        }
    }

    public JPanel getPanelGeneral() {
        return panelGeneral;
    }

    public JButton getBtnSeleccionar() {
        return btnSeleccionar;
    }

    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public void setPanelGeneral(JPanel panelGeneral) {
        this.panelGeneral = panelGeneral;
    }

    public int mostrarMensaje(String mensaje) {
        int opcion = JOptionPane.showConfirmDialog(this, mensaje);
        return opcion;
    }

    public void cargarProductoEncontrado(String nombre, double precio){
        txtNombre.setText(nombre);
        txtPrecio.setText(Double.toString(precio));
    }

    public void limpiarCampos() {
        txtCodigo.setText("");
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
