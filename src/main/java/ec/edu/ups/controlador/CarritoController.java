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
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;


public class CarritoController {

    private CrearCarrito crearCarrito;
    private ListaCarrito listaCarrito;
    private final CarritoDAO carritoDAO;
    private final ProductoDAO productoDAO;
    private Carrito carrito;

    public CarritoController(CarritoDAO carritoDAO, ProductoDAO productoDAO){

        this.carritoDAO = carritoDAO;
        this.productoDAO = productoDAO;
    }

    public void setCrearCarrito(CrearCarrito crearCarrito) {
        this.crearCarrito = crearCarrito;
        this.carrito = new Carrito(); // Aquí recién se crea el primer carrito
        crearCarrito.getTxtCodigoCarrito().setText(String.valueOf(carrito.getCodigo()));
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
                // Eliminar producto por código
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

        GregorianCalendar fecha = crearCarrito.obtenerFecha();

        if (fecha == null) {
            crearCarrito.mostrarMensaje("Formato de fecha inválido. Use dd/MM/yyyy.");
            return;
        }

        carrito.setFecha(fecha);

        carritoDAO.crear(carrito);
        crearCarrito.mostrarMensaje("Carrito #" + carrito.getCodigo() + " creado correctamente");

        crearCarrito.limpiarFormulario();
        carrito = new Carrito();
        crearCarrito.getTxtCodigoCarrito().setText(String.valueOf(carrito.getCodigo()));
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

    public void listarProducto(){
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
                carrito.getCodigo(), carrito.getFecha(),"Descripcion"
        });
    }

    private void cargarCarritos(){
        List<Carrito> carritos = carritoDAO.listarCarritos();
        DefaultTableModel modelo = (DefaultTableModel) listaCarrito.getTblCarritos().getModel();
        modelo.setNumRows(0);

        for(Carrito carrito: carritos){
            modelo.addRow(new Object[]{
                    carrito.getCodigo(), carrito.getFecha(),"Descripcion"
            });
        }
    }

}