package ec.edu.ups.vista;

import ec.edu.ups.util.Contexto;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.URL;

/**
 * Vista (JInternalFrame) para la gestión de usuarios.
 * <p>
 * Esta clase representa una ventana interna donde se pueden buscar, listar y crear usuarios.
 * Incluye soporte para internacionalización de textos y carga dinámica de iconos.
 *
 * @author JuanOrtiz2006
 * @version 1.0
 */
public class GestionUsuarios extends JInternalFrame{
    /**
     * Componentes de la interfaz gráfica.
     */
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

    /**
     * Constructor que inicializa la vista de gestión de usuarios.
     * Configura el título, tamaño, iconos y modelo de la tabla.
     */
    public GestionUsuarios(){
        setContentPane(panelGeneral);
        setTitle(Contexto.getHandler().get("gestionusuarios.titulo"));
        setSize(500, 500);
        setClosable(true);
        setMaximizable(true);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);

        modelo = new DefaultTableModel();
        tblUsuarios.setModel(modelo);


        btnCrear.setIcon(cargarIcono("registration.png"));
        btnBuscar.setIcon(cargarIcono("search.png"));
        btnListar.setIcon(cargarIcono("list.png"));

        actualizarIdioma();
    }

    /**
     * Actualiza los textos de la interfaz gráfica según el idioma configurado en el contexto.
     * Modifica títulos, etiquetas y columnas de la tabla.
     */
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
                handler.get("gestionusuarios.columna.cedula"),
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

    /**
     * Métodos getter para acceder a los componentes de la interfaz.
     */
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

    /**
     * Muestra un mensaje en un cuadro de diálogo.
     *
     * @param mensaje El mensaje a mostrar.
     */
    public void mostrarMensaje(String mensaje){
        JOptionPane.showMessageDialog(null,mensaje);

    }

    /**
     * Carga un icono desde el directorio de recursos.
     *
     * @param nombreArchivo Nombre del archivo del icono a cargar.
     * @return Un objeto ImageIcon con el icono cargado, o null si no se encuentra.
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
