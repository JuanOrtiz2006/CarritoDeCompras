package ec.edu.ups.vista;

import ec.edu.ups.util.Contexto;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.net.URL;

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

        btnAceptar.setIcon(cargarIcono("product.png"));
        actualizarIdioma();
    }

    public void actualizarIdioma() {
        var handler = Contexto.getHandler();
        // Títulos de ventana y etiquetas
        setTitle(handler.get("crearproducto.titulo"));
        lblCodigo.setText(handler.get("crearproducto.codigo"));
        lblNombre.setText(handler.get("crearproducto.nombre"));
        lblPrecio.setText(handler.get("crearproducto.precio"));
        btnAceptar.setText(handler.get("crearproducto.boton"));

        // Actualizar título del borde si es un TitledBorder
        Border border = panelPrincipal.getBorder();
        if (border instanceof TitledBorder) {
            ((TitledBorder) border).setTitle(handler.get("crearproducto.borde"));
            panelPrincipal.repaint(); // Para que se refleje el nuevo texto
        }

        btnAceptar.setIcon(cargarIcono("product.png"));
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
