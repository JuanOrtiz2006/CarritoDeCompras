package ec.edu.ups.vista;

import ec.edu.ups.util.Contexto;

import javax.swing.*;

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

        // Traducción inicial
        lblCodigo.setText(Contexto.getHandler().get("eliminarproducto.etiqueta.codigo"));
        lblNombre.setText(Contexto.getHandler().get("eliminarproducto.etiqueta.nombre"));
        lblPrecio.setText(Contexto.getHandler().get("eliminarproducto.etiqueta.precio"));
        btnSeleccionar.setText(Contexto.getHandler().get("eliminarproducto.boton.seleccionar"));
        btnEliminar.setText(Contexto.getHandler().get("eliminarproducto.boton.eliminar"));
    }

    public void recargarTextos() {
        setTitle(Contexto.getHandler().get("eliminarproducto.titulo"));
        lblCodigo.setText(Contexto.getHandler().get("eliminarproducto.etiqueta.codigo"));
        lblNombre.setText(Contexto.getHandler().get("eliminarproducto.etiqueta.nombre"));
        lblPrecio.setText(Contexto.getHandler().get("eliminarproducto.etiqueta.precio"));
        btnSeleccionar.setText(Contexto.getHandler().get("eliminarproducto.boton.seleccionar"));
        btnEliminar.setText(Contexto.getHandler().get("eliminarproducto.boton.eliminar"));
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

    public void productoEncontrado(String nombre, double precio){
        txtNombre.setText(nombre);
        txtPrecio.setText(Double.toString(precio));
    }

    public void limpiarCampos() {
        txtCodigo.setText("");
    }
}
