package ec.edu.ups.controlador;

import ec.edu.ups.dao.*;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.vista.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class CarritoController {

    // === Atributos ===
    private CrearCarrito crearCarrito;
    private ListaCarrito listaCarrito;
    private final CarritoDAO carritoDAO;
    private final ProductoDAO productoDAO;
    private Carrito carrito;
    private boolean modoEdicion = false; // Indica si el carrito está en modo edición

    // === Constructor ===
    public CarritoController(CarritoDAO carritoDAO, ProductoDAO productoDAO) {
        this.carritoDAO = carritoDAO;
        this.productoDAO = productoDAO;
    }

    // === Setters de vistas ===
    public void setCrearCarrito(CrearCarrito crearCarrito) {
        this.crearCarrito = crearCarrito;
        this.carrito = new Carrito();

        GregorianCalendar fechaActual = obtenerFechaActual();
        carrito.setFecha(fechaActual);
        crearCarrito.getTxtCodigoCarrito().setText(String.valueOf(carrito.getCodigo()));
        crearCarrito.getTxtFecha().setText(String.format("%1$td/%1$tm/%1$tY", fechaActual));
        crearCarrito.activarModoCreacion();

    }

    public void setListaCarrito(ListaCarrito listaCarrito) {
        this.listaCarrito = listaCarrito;
    }

    // === Inicializa eventos de CrearCarrito ===
    public void crearCarrito() {
        crearCarrito.getBtnSeleccionar().addActionListener(e -> itemSeleccionado());
        crearCarrito.getBtnAgregar().addActionListener(e -> anadirProducto());
        crearCarrito.getBtnGuardar().addActionListener(e -> guardarCarrito());
        crearCarrito.getBtnVaciar().addActionListener(e -> vaciarCarrito());

        crearCarrito.getTblProductos().getColumn("EDITAR").setCellEditor(new BotonEditor(crearCarrito.getTblProductos(), this));
        crearCarrito.getTblProductos().getColumn("ELIMINAR").setCellEditor(new BotonEditor(crearCarrito.getTblProductos(), this));
    }

    // === Acciones en CrearCarrito ===
    private void itemSeleccionado() {
        int codigo = Integer.parseInt(crearCarrito.getTxtCodigo().getText());
        Producto producto = productoDAO.buscarPorCodigo(codigo);
        crearCarrito.productoEncontrado(producto.getNombre(), producto.getPrecio());
    }

    private void anadirProducto() {
        int codigo = Integer.parseInt(crearCarrito.getTxtCodigo().getText());
        Producto producto = productoDAO.buscarPorCodigo(codigo);
        int cantidad = Integer.parseInt(crearCarrito.getTxtCantidad().getText());
        carrito.agregarProducto(producto, cantidad);

        cargarProductos();
        mostrarTotales();
    }

    private void cargarProductos() {
        List<ItemCarrito> items = carrito.obtenerItems();
        DefaultTableModel modelo = (DefaultTableModel) crearCarrito.getTblProductos().getModel();
        modelo.setRowCount(0);

        for (ItemCarrito item : items) {
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

    private void mostrarTotales() {
        double subtotal = carrito.calcularSubtotal();
        double iva = carrito.calcularIVA();
        double total = carrito.calcularTotal();

        DefaultTableModel modelo2 = (DefaultTableModel) crearCarrito.getTblTotal().getModel();
        modelo2.setRowCount(0);
        modelo2.addRow(new Object[]{
                String.format("%.2f", subtotal),
                String.format("%.2f", iva),
                String.format("%.2f", total)
        });
    }

    private void guardarCarrito() {
        if (carrito.estaVacio()) {
            crearCarrito.mostrarMensaje("El carrito está vacío. Agregue al menos un producto antes de guardar.");
            return;
        }

        GregorianCalendar fechaActual = obtenerFechaActual();
        carrito.setFecha(fechaActual);
        carritoDAO.crear(carrito);
        crearCarrito.mostrarMensaje("Carrito creado correctamente.");

        crearCarrito.limpiarFormulario();
        carrito = new Carrito();
        crearCarrito.getTxtCodigoCarrito().setText(String.valueOf(carrito.getCodigo()));
        modoEdicion = false;
    }

    private void vaciarCarrito() {
        int opcion = JOptionPane.showConfirmDialog(
                crearCarrito,
                "¿Está seguro que desea vaciar todo el carrito?",
                "Confirmar vaciado",
                JOptionPane.YES_NO_OPTION);

        if (opcion == JOptionPane.YES_OPTION) {
            carrito.vaciarCarrito();
            cargarProductos();
            mostrarTotales();
            crearCarrito.mostrarMensaje("Carrito vaciado correctamente.");
        }
    }

    public void eliminarItem(int fila) {
        if (fila >= 0 && fila < carrito.getItems().size()) {
            int opcion = JOptionPane.showConfirmDialog(
                    crearCarrito,
                    "¿Eliminar item seleccionado?",
                    "Confirmar",
                    JOptionPane.YES_NO_OPTION);
            if (opcion == JOptionPane.YES_OPTION) {
                int codigoProducto = carrito.getItems().get(fila).getProducto().getCodigo();
                carrito.eliminarProducto(codigoProducto);
                cargarProductos();
                mostrarTotales();
                crearCarrito.mostrarMensaje("Item eliminado correctamente.");
            }
        }
    }

    public void editarItem(int fila) {
        if (fila >= 0 && fila < carrito.getItems().size()) {
            ItemCarrito item = carrito.getItems().get(fila);
            String cantidadStr = JOptionPane.showInputDialog(
                    crearCarrito,
                    "Nueva cantidad para " + item.getProducto().getNombre(),
                    item.getCantidad());
            if (cantidadStr != null) {
                try {
                    int nuevaCantidad = Integer.parseInt(cantidadStr);
                    if (nuevaCantidad <= 0) {
                        crearCarrito.mostrarMensaje("Cantidad debe ser mayor a cero.");
                        return;
                    }
                    carrito.actualizarProducto(new ItemCarrito(item.getProducto(), nuevaCantidad));
                    cargarProductos();
                    mostrarTotales();
                    crearCarrito.mostrarMensaje("Producto actualizado correctamente.");
                } catch (NumberFormatException ex) {
                    crearCarrito.mostrarMensaje("Cantidad inválida.");
                }
            }
        }
    }

    // === Acciones en ListaCarrito ===
    public void listarCarritos() {
        listaCarrito.getBtnBuscar().addActionListener(e -> buscarCarrito());
        listaCarrito.getBtnListar().addActionListener(e -> cargarCarritos());
        listaCarrito.getTblCarritos().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                JTable tabla = listaCarrito.getTblCarritos();
                int fila = tabla.getSelectedRow();
                if (fila >= 0) {
                    int codigo = Integer.parseInt(tabla.getValueAt(fila, 1).toString());
                    String[] opciones = {"Editar", "Eliminar", "Cancelar"};
                    int respuesta = listaCarrito.mostrarConfirmDialog("¿Acción para carrito #" + codigo + "?", opciones);
                    if (respuesta == 0) editarCarrito(codigo);
                    else if (respuesta == 1) eliminarCarrito(codigo);
                }
            }
        });
    }

    private void buscarCarrito() {
        int codigo = Integer.parseInt(listaCarrito.getTxtCodigo().getText());
        Carrito carrito = carritoDAO.buscarPorCodigo(codigo);
        cargarCarritoEnTabla(carrito);
    }

    private void cargarCarritoEnTabla(Carrito carrito) {
        String fechaTexto = String.format("%1$td/%1$tm/%1$tY", carrito.getFecha());
        DefaultTableModel modelo = (DefaultTableModel) listaCarrito.getTblCarritos().getModel();
        modelo.setRowCount(0);
        modelo.addRow(new Object[]{
                carrito.getUsuario(),
                carrito.getCodigo(),
                fechaTexto,
                carrito.calcularTotal()
        });
    }

    private void cargarCarritos() {
        List<Carrito> carritos = carritoDAO.listarCarritos();
        DefaultTableModel modelo = (DefaultTableModel) listaCarrito.getTblCarritos().getModel();
        modelo.setRowCount(0);

        for (Carrito c : carritos) {
            String fechaTexto = String.format("%1$td/%1$tm/%1$tY", c.getFecha());
            modelo.addRow(new Object[]{
                    c.getUsuario(),
                    c.getCodigo(),
                    fechaTexto,
                    c.calcularTotal()
            });
        }
    }

    private void eliminarCarrito(int codigo) {
        String[] opciones = {"Si", "No"};
        int respuesta = listaCarrito.mostrarConfirmDialog("¿Desea eliminar el carrito?", opciones);
        if (respuesta == 0) {
            carritoDAO.eliminar(codigo);
            int filaSeleccionada = listaCarrito.getTblCarritos().getSelectedRow();
            ((DefaultTableModel) listaCarrito.getTblCarritos().getModel()).removeRow(filaSeleccionada);
            JOptionPane.showMessageDialog(listaCarrito, "Carrito eliminado correctamente.");
        }
    }

    // === Modo edición ===
    public void editarCarrito(int codigo) {
        Carrito carritoCargado = carritoDAO.buscarPorCodigo(codigo);
        if (carritoCargado == null) return;

        // Mostrar la vista CrearCarrito
        crearCarrito = new CrearCarrito();
        crearCarrito(); // Registra eventos
        setCrearCarrito(crearCarrito); // Establece la referencia

        // Carga los datos del carrito seleccionado
        cargarCarritoParaEdicion(carritoCargado);

        // Mostrar el formulario dentro del JDesktopPane
        listaCarrito.getParent().add(crearCarrito);
        crearCarrito.setVisible(true);
    }

    private GregorianCalendar obtenerFechaActual() {
        return new GregorianCalendar();
    }

    private void cargarCarritoParaEdicion(Carrito carritoCargado) {
        this.carrito = carritoCargado;
        this.modoEdicion = true;

        // Mostrar los datos en el formulario
        crearCarrito.getTxtCodigoCarrito().setText(String.valueOf(carrito.getCodigo()));
        crearCarrito.getTxtFecha().setText(String.format("%1$td/%1$tm/%1$tY", carrito.getFecha()));
        crearCarrito.activarModoEdicion();

        cargarProductos();
        mostrarTotales();

        crearCarrito.getBtnEditar().addActionListener(e -> guardarCarrito());
    }

    private void actualizarCarrito(){
        if (carrito.estaVacio()) {
            crearCarrito.mostrarMensaje("El carrito está vacío. Agregue al menos un producto antes de guardar.");
            return;
        }

        int codigoCarro = Integer.parseInt(crearCarrito.getTxtCodigoCarrito().getText());
        GregorianCalendar fechaActual = null; // Declarar fuera del try-catch

        try {
            String fechaTexto = crearCarrito.getTxtFecha().getText();
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            Date fecha = formato.parse(fechaTexto);

            fechaActual = new GregorianCalendar();
            fechaActual.setTime(fecha);

        } catch (ParseException e) {
            e.printStackTrace();
            crearCarrito.mostrarMensaje("Error: Formato de fecha incorrecto. Use dd/MM/yyyy");
            return; // Salir del método si hay error en la fecha
        }

        List<ItemCarrito> items = new ArrayList<ItemCarrito>();
        int rows = crearCarrito.getTblProductos().getRowCount();

        try {
            for(int i = 0; i < rows; i++){ // Corregido: i < rows en lugar de i <= rows
                Object codigo = crearCarrito.getTblProductos().getValueAt(i, 0);
                Object nombre = crearCarrito.getTblProductos().getValueAt(i, 1);
                Object precio = crearCarrito.getTblProductos().getValueAt(i, 2);
                Object cantidad = crearCarrito.getTblProductos().getValueAt(i, 3);

                Producto producto = new Producto(
                        Integer.parseInt(codigo.toString()),
                        nombre.toString(),
                        Double.parseDouble(precio.toString())
                );

                ItemCarrito item = new ItemCarrito(producto, Integer.parseInt(cantidad.toString()));
                items.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
            crearCarrito.mostrarMensaje("Error al procesar los productos de la tabla.");
            return;
        }

        // Usar fechaActual (GregorianCalendar) en lugar de Date
        Carrito carritoActualizado = new Carrito(codigoCarro, fechaActual, items);
        carritoDAO.actualizar(carritoActualizado);
        crearCarrito.mostrarMensaje("Carrito actualizado correctamente.");

        crearCarrito.limpiarFormulario();
        modoEdicion = false;
    }


}