package ec.edu.ups.vista;

import ec.edu.ups.util.Contexto;

import javax.swing.*;

public class CrearProductoView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTextField txtPrecio;
    private JTextField txtNombre;
    private JTextField txtCodigo;
    private JButton btnAceptar;
    private JPanel panelCentral;
    private JPanel panelDatos;
    private JLabel lblCodigo;
    private JLabel lblNombre;
    private JLabel lblPrecio;
    private JPanel panelBotones;

    public CrearProductoView() {
        setContentPane(panelPrincipal);
        setTitle(Contexto.getHandler().get("crearproducto.titulo"));
        setSize(500, 500);
        setClosable(true);
        setMaximizable(true);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);

        // Cargar textos desde el archivo de idioma
        lblCodigo.setText(Contexto.getHandler().get("crearproducto.codigo"));
        lblNombre.setText(Contexto.getHandler().get("crearproducto.nombre"));
        lblPrecio.setText(Contexto.getHandler().get("crearproducto.precio"));
        btnAceptar.setText(Contexto.getHandler().get("crearproducto.boton"));
    }

    // Metodo para actualizar textos si se cambia el idioma
    public void recargarTextos() {
        setTitle(Contexto.getHandler().get("crearproducto.titulo"));
        lblCodigo.setText(Contexto.getHandler().get("crearproducto.codigo"));
        lblNombre.setText(Contexto.getHandler().get("crearproducto.nombre"));
        lblPrecio.setText(Contexto.getHandler().get("crearproducto.precio"));
        btnAceptar.setText(Contexto.getHandler().get("crearproducto.boton"));
    }

    public JTextField getTxtPrecio() {
        return txtPrecio;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public JButton getBtnAceptar() {
        return btnAceptar;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
    }

}
