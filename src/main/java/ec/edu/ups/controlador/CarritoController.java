package ec.edu.ups.controlador;

import ec.edu.ups.dao.*;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.vista.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.GregorianCalendar;
import java.util.List;


public class CarritoController {

    private CrearCarrito crearCarrito;
    private ListaCarrito listaCarrito;
    private final CarritoDAO carritoDAO;
    private final ProductoDAO productoDAO;
    private Carrito carrito;
    private boolean modoEdicion = false; // Para saber si estamos editando

    public CarritoController(CarritoDAO carritoDAO, ProductoDAO productoDAO){
        this.carritoDAO = carritoDAO;
        this.productoDAO = productoDAO;
    }

    public void setCrearCarrito(CrearCarrito crearCarrito) {
        this.crearCarrito = crearCarrito;
        this.carrito = new Carrito();

        GregorianCalendar fechaActual = obtenerFechaActual();
        carrito.setFecha(fechaActual);
        crearCarrito.getTxtCodigoCarrito().setText(String.valueOf(carrito.getCodigo()));
        crearCarrito.getTxtFecha().setText(String.format("%1$td/%1$tm/%1$tY", fechaActual));


    }

    public void setListaCarrito(ListaCarrito listaCarrito) {
        this.listaCarrito = listaCarrito;
    }

    public void crearCarrito() {
        crearCarrito.getBtnSeleccionar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                itemSeleccionado();
            }
        });
        crearCarrito.getBtnAgregar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                anadirProducto();
            }
        });
        crearCarrito.getBtnGuardar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarCarrito();
            }
        });
        crearCarrito.getBtnVaciar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vaciarCarrito();
            }
        });

        crearCarrito.getTblProductos().getColumn("EDITAR").setCellEditor(new ec.edu.ups.vista.BotonEditor(crearCarrito.getTblProductos(), this));
        crearCarrito.getTblProductos().getColumn("ELIMINAR").setCellEditor(new ec.edu.ups.vista.BotonEditor(crearCarrito.getTblProductos(), this));
    }

    private void itemSeleccionado(){
        int codigo = Integer.parseInt(crearCarrito.getTxtCodigo().getText());
        Producto producto = productoDAO.buscarPorCodigo(codigo);
        crearCarrito.productoEncontrado(producto.getNombre(),producto.getPrecio());
    }

    private void anadirProducto() {
        int codigo = Integer.parseInt(crearCarrito.getTxtCodigo().getText());
        Producto producto = productoDAO.buscarPorCodigo(codigo);
        int cantidad =  Integer.parseInt(crearCarrito.getTxtCantidad().getText());
        carrito.agregarProducto(producto, cantidad);

        cargarProductos();
        mostrarTotales();
    }

    private void cargarProductos() {
        List<ItemCarrito> items = carrito.obtenerItems();
        DefaultTableModel modelo = (DefaultTableModel) crearCarrito.getTblProductos().getModel();
        modelo.setNumRows(0);
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

    public   void eliminarItem(int fila) {
        if (fila >= 0 && fila < carrito.getItems().size()) {
            int opcion = JOptionPane.showConfirmDialog(
                    crearCarrito,
                    "¿Está seguro que desea eliminar el item seleccionado?",
                    "Confirmar eliminación",
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
                    "Ingrese nueva cantidad para el producto " + item.getProducto().getNombre(),
                    item.getCantidad());
            if (cantidadStr != null) {
                try {
                    int nuevaCantidad = Integer.parseInt(cantidadStr);
                    if (nuevaCantidad <= 0) {
                        crearCarrito.mostrarMensaje("La cantidad debe ser mayor que cero.");
                        return;
                    }
                    ItemCarrito itemEditado = new ItemCarrito(item.getProducto(), nuevaCantidad);
                    carrito.actualizarProducto(itemEditado);
                    cargarProductos();
                    mostrarTotales();
                    crearCarrito.mostrarMensaje("Producto actualizado correctamente.");
                } catch (NumberFormatException ex) {
                    crearCarrito.mostrarMensaje("Cantidad inválida.");
                }
            }
        }
    }

    private void mostrarTotales(){
        double subtotal = carrito.calcularSubtotal();
        double iva = carrito.calcularIVA();
        double total = carrito.calcularTotal();
        DefaultTableModel modelo2 = (DefaultTableModel) crearCarrito.getTblTotal().getModel();
        modelo2.setRowCount(0);
        Object[] filaTotal = {String.format("%.2f", subtotal),
                String.format("%.2f", iva),
                String.format("%.2f", total)};
        modelo2.addRow(filaTotal);
    }

    private void guardarCarrito() {
        if (carrito.estaVacio()) {
            crearCarrito.mostrarMensaje("El carrito está vacío. Agregue al menos un producto antes de guardar.");
            return;
        }

        if (modoEdicion) {
            carritoDAO.actualizar(carrito); // Asegúrate de tener este método
            crearCarrito.mostrarMensaje("Carrito #" + carrito.getCodigo() + " actualizado correctamente.");
        } else {
            carritoDAO.crear(carrito);
            crearCarrito.mostrarMensaje("Carrito #" + carrito.getCodigo() + " creado correctamente.");
        }
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

    public void listarCarritos(){
        listaCarrito.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarCarrito();
            }
        });
        listaCarrito.getBtnListar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarCarritos();
            }
        });
        listaCarrito.getTblCarritos().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JTable tabla = listaCarrito.getTblCarritos();
                int fila = tabla.getSelectedRow();
                if (fila >= 0) {
                    int codigo = Integer.parseInt(tabla.getValueAt(fila, 1).toString());

                    String[] opciones = {"Editar", "Eliminar", "Cancelar"};
                    int respuesta = listaCarrito.mostrarConfirmDialog("¿Qué desea hacer con el carrito #" + codigo + "?", opciones);

                    if(respuesta==0){
                    }
                    if(respuesta==1){
                        eliminarCarrito(codigo);
                    }

                }
            }
            @Override
            public void mousePressed(MouseEvent e) {

            }
            @Override
            public void mouseReleased(MouseEvent e) {

            }
            @Override
            public void mouseEntered(MouseEvent e) {

            }
            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    private void buscarCarrito(){
        int codigo = Integer.parseInt(listaCarrito.getTxtCodigo().getText());
        Carrito carrito = carritoDAO.buscarPorCodigo(codigo);
        cargarCarrito(carrito);
    }

    private void cargarCarrito(Carrito carrito){
        DefaultTableModel modelo = (DefaultTableModel) listaCarrito.getTblCarritos().getModel();
        modelo.setNumRows(0);
        modelo.addRow(new Object[]{
                carrito.getUsuario(),carrito.getCodigo(), carrito.getFecha(),carrito.calcularTotal()
        });
    }

    private void cargarCarritos(){
        List<Carrito> carritos = carritoDAO.listarCarritos();
        DefaultTableModel modelo = (DefaultTableModel) listaCarrito.getTblCarritos().getModel();
        modelo.setNumRows(0);

        for(Carrito carrito: carritos){
            modelo.addRow(new Object[]{
                    carrito.getUsuario(),carrito.getCodigo(), carrito.getFecha(),carrito.calcularTotal()
            });
        }
    }

    private void eliminarCarrito(int codigo){
        String[] opciones = {"Si","No"};
        int respuesta = listaCarrito.mostrarConfirmDialog("Desea continuar con la elimnacion?", opciones);
        if(respuesta==0){
            carritoDAO.eliminar(codigo);
            DefaultTableModel modelo = (DefaultTableModel) listaCarrito.getTblCarritos().getModel();
            int filaSeleccionada = listaCarrito.getTblCarritos().getSelectedRow();
            modelo.removeRow(filaSeleccionada);
            JOptionPane.showMessageDialog(listaCarrito, "Carrito eliminado correctamente.");
        }
    }

    private GregorianCalendar obtenerFechaActual() {
        return new GregorianCalendar();
    }
}