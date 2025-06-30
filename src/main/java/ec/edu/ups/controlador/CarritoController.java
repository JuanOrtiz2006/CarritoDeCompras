package ec.edu.ups.controlador;

import ec.edu.ups.dao.*;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.vista.*;

import ec.edu.ups.util.Contexto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class CarritoController {

    private CrearCarrito crearCarrito;
    private ListaCarrito listaCarrito;
    private final CarritoDAO carritoDAO;
    private final ProductoDAO productoDAO;
    private Carrito carrito;
    private Usuario usuario;
    private boolean modoEdicion = false;


    public CarritoController(CarritoDAO carritoDAO, ProductoDAO productoDAO) {
        this.carritoDAO = carritoDAO;
        this.productoDAO = productoDAO;
        this.carrito = new Carrito();
        this.usuario = new Usuario(); // Esto debería actualizarse con el usuario real en sesión
    }

    public void setCrearCarrito(CrearCarrito crearCarrito) {
        this.crearCarrito = crearCarrito;

        if (!modoEdicion) {
            // Nuevo carrito sin código aún
            this.carrito = new Carrito();
            GregorianCalendar fechaActual = obtenerFechaActual();
            carrito.setFecha(fechaActual);

            // Mostrar el código actual (sin incrementarlo todavía)
            crearCarrito.getTxtCodigoCarrito().setText(String.valueOf(Carrito.getContadorActual()));
            crearCarrito.getTxtFecha().setText(String.format("%1$td/%1$tm/%1$tY", fechaActual));

            crearCarrito.activarModoCreacion();
        }
    }

    public void setListaCarrito(ListaCarrito listaCarrito) {
        this.listaCarrito = listaCarrito;
    }

    public void eventosParaCrearCarrito() {
        crearCarrito.getBtnSeleccionar().addActionListener(e -> seleccionarItem());
        crearCarrito.getBtnAgregar().addActionListener(e -> anadirProductoACarrito());
        crearCarrito.getBtnGuardar().addActionListener(e -> guardarCarrito());
        crearCarrito.getBtnVaciar().addActionListener(e -> vaciarCarrito());

        crearCarrito.getTblProductos().getColumn(Contexto.getHandler().get("boton.editar")).setCellEditor(new BotonEditor(crearCarrito.getTblProductos(), this));
        crearCarrito.getTblProductos().getColumn(Contexto.getHandler().get("boton.editar")).setCellEditor(new BotonEditor(crearCarrito.getTblProductos(), this));
    }

    private void seleccionarItem() {
        int codigo = Integer.parseInt(crearCarrito.getTxtCodigo().getText());
        Producto producto = productoDAO.buscarPorCodigo(codigo);
        if (producto != null) {
            crearCarrito.productoEncontrado(producto.getNombre(), producto.getPrecio());
        } else {
            crearCarrito.mostrarMensaje("Producto no encontrado.");
        }
    }

    private void anadirProductoACarrito() {
        int codigo = Integer.parseInt(crearCarrito.getTxtCodigo().getText());
        int cantidad = Integer.parseInt(crearCarrito.getTxtCantidad().getText());
        Producto producto = productoDAO.buscarPorCodigo(codigo);
        if (producto != null) {
            carrito.agregarProducto(producto, cantidad);
            cargarProductosEnTabla();
            mostrarTotalesEnTabla();
        } else {
            crearCarrito.mostrarMensaje("Producto no encontrado.");
        }
    }

    private void cargarProductosEnTabla() {
        DefaultTableModel modelo = (DefaultTableModel) crearCarrito.getTblProductos().getModel();
        modelo.setRowCount(0);
        for (ItemCarrito item : carrito.getItems()) {
            modelo.addRow(new Object[]{
                    item.getProducto().getCodigo(),
                    item.getProducto().getNombre(),
                    item.getProducto().getPrecio(),
                    item.getCantidad(),
                    item.getProducto().getPrecio() * item.getCantidad(),
                    "EDITAR",
                    "ELIMINAR"
            });
        }
    }

    private void mostrarTotalesEnTabla() {
        DefaultTableModel modelo = (DefaultTableModel) crearCarrito.getTblTotal().getModel();
        modelo.setRowCount(0);
        modelo.addRow(new Object[]{
                String.format("%.2f", carrito.calcularSubtotal()),
                String.format("%.2f", carrito.calcularIVA()),
                String.format("%.2f", carrito.calcularTotal())
        });
    }

    private void guardarCarrito() {
        if (carrito.estaVacio()) {
            crearCarrito.mostrarMensaje("El carrito está vacío. Agregue al menos un producto antes de guardar.");
            return;
        }

        // Asignar el código solo aquí (cuando se guarda)
        int nuevoCodigo = Carrito.generarCodigo();
        carrito.setCodigo(nuevoCodigo);

        GregorianCalendar fechaActual = obtenerFechaActual();
        carrito.setFecha(fechaActual);
        carrito.setUsuario(usuario);

        carritoDAO.crear(carrito);
        crearCarrito.mostrarMensaje("Carrito creado correctamente por usuario: " + usuario.getUsername());

        crearCarrito.limpiarFormulario();
        modoEdicion = false;
    }

    private GregorianCalendar obtenerFechaActual() {
        return new GregorianCalendar();
    }

    private void vaciarCarrito() {
        if (JOptionPane.showConfirmDialog(crearCarrito, "¿Vaciar carrito?", "Confirmación", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            carrito.vaciarCarrito();
            cargarProductosEnTabla();
            mostrarTotalesEnTabla();
            crearCarrito.mostrarMensaje("Carrito vaciado.");
        }
    }

    public void eliminarItem(int fila) {
        if (fila >= 0 && fila < carrito.getItems().size()) {
            int codigoProducto = carrito.getItems().get(fila).getProducto().getCodigo();
            carrito.eliminarProducto(codigoProducto);
            cargarProductosEnTabla();
            mostrarTotalesEnTabla();
            crearCarrito.mostrarMensaje("Item eliminado.");
        }
    }

    public void editarItem(int fila) {
        if (fila >= 0 && fila < carrito.getItems().size()) {
            ItemCarrito item = carrito.getItems().get(fila);
            String nuevaCantidadStr = JOptionPane.showInputDialog("Nueva cantidad para " + item.getProducto().getNombre(), item.getCantidad());
            try {
                int nuevaCantidad = Integer.parseInt(nuevaCantidadStr);
                carrito.actualizarProducto(new ItemCarrito(item.getProducto(), nuevaCantidad));
                cargarProductosEnTabla();
                mostrarTotalesEnTabla();
                crearCarrito.mostrarMensaje("Cantidad actualizada.");
            } catch (Exception e) {
                crearCarrito.mostrarMensaje("Cantidad inválida.");
            }
        }
    }

    // === Lista de carritos ===

    public void eventosEnListarCarritos() {
        listaCarrito.getBtnListar().addActionListener(e -> cargarCarritosEnTabla());
        listaCarrito.getBtnBuscar().addActionListener(e -> buscarCarrito());

        listaCarrito.getTblCarritos().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int fila = listaCarrito.getTblCarritos().getSelectedRow();
                if (fila != -1) {
                    int codigo = (int) listaCarrito.getTblCarritos().getValueAt(fila, 1);
                    String[] opciones = {"Editar", "Eliminar", "Cancelar"};
                    int opcion = listaCarrito.mostrarConfirmDialog("¿Qué desea hacer con el carrito #" + codigo + "?", opciones);
                    if (opcion == 0) editarCarrito(codigo);
                    else if (opcion == 1) eliminarCarrito(codigo);
                }
            }
        });
    }

    private void cargarCarritosEnTabla() {
        DefaultTableModel modelo = (DefaultTableModel) listaCarrito.getTblCarritos().getModel();
        modelo.setRowCount(0);
        for (Carrito c : carritoDAO.listarCarritos()) {
            modelo.addRow(new Object[]{
                    c.getUsuario().getUsername(),
                    c.getCodigo(),
                    String.format("%1$td/%1$tm/%1$tY", c.getFecha()),
                    String.format("%.2f", c.calcularTotal())
            });
        }
    }

    private void buscarCarrito() {
        int codigo = Integer.parseInt(listaCarrito.getTxtCodigo().getText());
        Carrito c = carritoDAO.buscarPorCodigo(codigo);
        if (c != null) {
            cargarCarritoEnTabla(c);
        } else {
            listaCarrito.mostrarMensaje("No se encontró el carrito.");
        }
    }

    private void cargarCarritoEnTabla(Carrito c) {
        DefaultTableModel modelo = (DefaultTableModel) listaCarrito.getTblCarritos().getModel();
        modelo.setRowCount(0);
        modelo.addRow(new Object[]{
                c.getUsuario().getUsername(),
                c.getCodigo(),
                String.format("%1$td/%1$tm/%1$tY", c.getFecha()),
                String.format("%.2f", c.calcularTotal())
        });
    }

    private void eliminarCarrito(int codigo) {
        if (JOptionPane.showConfirmDialog(listaCarrito, "¿Eliminar carrito?", "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            carritoDAO.eliminar(codigo);
            cargarCarritosEnTabla();
            listaCarrito.mostrarMensaje("Carrito eliminado.");
        }
    }

    public void editarCarrito(int codigo) {
        Carrito carritoCargado = carritoDAO.buscarPorCodigo(codigo);
        if (carritoCargado == null) return;

        // Activamos modo edición
        this.modoEdicion = true;
        this.carrito = carritoCargado;

        // Crear nueva instancia de la vista (o reutilizar si deseas)
        crearCarrito = new CrearCarrito();
        setCrearCarrito(crearCarrito); // Cargar datos
        eventosParaCrearCarrito();     // Asignar listeners (¡IMPORTANTE!)

        // Cargar los datos del carrito en la vista
        cargarCarritoParaEdicion(carritoCargado);

        // Agregar al DesktopPane y mostrar
        listaCarrito.getParent().add(crearCarrito);
        crearCarrito.setVisible(true);
        crearCarrito.toFront(); // Asegura que se muestre encima
    }
    private void cargarCarritoParaEdicion(Carrito carritoCargado) {
        crearCarrito.getTxtCodigoCarrito().setText(String.valueOf(carritoCargado.getCodigo()));
        crearCarrito.getTxtFecha().setText(String.format("%1$td/%1$tm/%1$tY", carritoCargado.getFecha()));
        crearCarrito.activarModoEdicion(); // Cambia botones, campos, etc.

        cargarProductosEnTabla();      // Carga los ítems en tabla
        mostrarTotalesEnTabla();       // Carga totales en tabla

        crearCarrito.getBtnEditar().addActionListener(e -> actualizarCarrito());
    }

    private void actualizarCarrito() {
        if (carrito.estaVacio()) {
            crearCarrito.mostrarMensaje("El carrito está vacío.");
            return;
        }

        try {
            String fechaTexto = crearCarrito.getTxtFecha().getText();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date fecha = sdf.parse(fechaTexto);
            GregorianCalendar fechaGC = new GregorianCalendar();
            fechaGC.setTime(fecha);
            carrito.setFecha(fechaGC);
        } catch (Exception e) {
            crearCarrito.mostrarMensaje("Fecha inválida.");
            return;
        }

        carritoDAO.actualizar(carrito);
        crearCarrito.mostrarMensaje("Carrito actualizado correctamente.");
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
