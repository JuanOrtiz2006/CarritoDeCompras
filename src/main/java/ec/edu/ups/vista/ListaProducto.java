package ec.edu.ups.vista;

import ec.edu.ups.modelo.Producto;
import ec.edu.ups.util.Contexto;
import ec.edu.ups.util.FormateadorUtils;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.URL;
import java.util.List;

/**
 * Vista (JInternalFrame) para la lista y filtrado de productos.
 * <p>
 * Esta clase representa una ventana interna donde se muestran los productos en una tabla,
 * permitiendo filtrar por tipo (código o nombre) y listar los resultados. Incluye soporte
 * para internacionalización de textos y carga dinámica de productos.
 *
 * @author JuanOrtiz2006
 * @version 1.0
 */
public class ListaProducto extends JInternalFrame{

    /**
     * Componentes de la interfaz gráfica.
     */
    private JTable tblProductos;
    private JPanel panelLista;
    private JPanel panelGeneral;
    private JComboBox cmbTipo;
    private JButton btnListar;
    private JPanel panelOpcion;
    private JLabel lblTipo;
    private DefaultTableModel modelo;

    /**
     * Constructor que inicializa la vista de lista de productos.
     * Configura el título, tamaño, iconos y modelo de la tabla.
     */
    public ListaProducto(){
        setContentPane(panelGeneral);
        setTitle(Contexto.getHandler().get("listaproducto.titulo"));
        setSize(500, 500);
        setClosable(true);
        setMaximizable(true);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);

        btnListar.setIcon(cargarIcono("list.png"));
        modelo = new DefaultTableModel();
        tblProductos.setModel(modelo);

        actualizarIdioma();
    }

    /**
     * Actualiza los textos de la interfaz gráfica según el idioma configurado en el contexto.
     * Modifica títulos, etiquetas y columnas de la tabla.
     */
    public void actualizarIdioma() {
        var handler = Contexto.getHandler();

        setTitle(handler.get("listaproducto.titulo"));
        lblTipo.setText(handler.get("listaproducto.etiqueta"));
        btnListar.setText(handler.get("listaproducto.boton"));

        modelo.setColumnIdentifiers(new Object[]{
                handler.get("listaproducto.columna.id"),
                handler.get("listaproducto.columna.nombre"),
                handler.get("listaproducto.columna.precio")
        });

        cmbTipo.removeAllItems();
        cmbTipo.addItem(""); // vacío
        cmbTipo.addItem(handler.get("listaproducto.filtro.codigo"));
        cmbTipo.addItem(handler.get("listaproducto.filtro.nombre"));

        Border border = panelGeneral.getBorder();
        if (border instanceof TitledBorder) {
            ((TitledBorder) border).setTitle(handler.get("listaproducto.borde"));
            panelGeneral.repaint();
        }
    }

    /**
     * Métodos getter y setter para los componentes de la interfaz.
     * Permiten acceder y modificar los componentes desde otras clases.
     */
    public JPanel getPanelGeneral() {
        return panelGeneral;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public JComboBox getCmbTipo() {
        return cmbTipo;
    }

    public JButton getBtnListar() {
        return btnListar;
    }

    public void setPanelGeneral(JPanel panelGeneral) {
        this.panelGeneral = panelGeneral;
    }

    public void setModelo(DefaultTableModel modelo) {
        this.modelo = modelo;
    }

    /**
     * Carga una lista de productos en la tabla.
     * Limpia la tabla antes de agregar los nuevos productos.
     *
     * @param productos Lista de productos a mostrar en la tabla.
     */
    public void cargarProductos(List<Producto> productos) {
        modelo.setNumRows(0); // Limpia la tabla
        for (Producto producto : productos) {
            Object[] filaProducto = {producto.getCodigo(), producto.getNombre(), FormateadorUtils.formatearMoneda(producto.getPrecio(), Contexto.getLocale())};
            modelo.addRow(filaProducto);
        }
    }

    /**
     * Muestra un mensaje en un cuadro de diálogo.
     * Utiliza JOptionPane para mostrar mensajes al usuario.
     *
     * @param mensaje El mensaje a mostrar.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje);
    }

    /**
     * Carga un icono desde el directorio de recursos.
     * Utiliza el ClassLoader para buscar el icono en la carpeta "icons".
     *
     * @param nombreArchivo Nombre del archivo del icono a cargar.
     * @return Un ImageIcon con el icono cargado, o null si no se encuentra.
     */
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
