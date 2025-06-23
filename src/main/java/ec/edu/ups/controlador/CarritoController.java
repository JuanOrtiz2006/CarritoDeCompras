package ec.edu.ups.controlador;

import ec.edu.ups.dao.*;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.vista.*;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.GregorianCalendar;
import java.util.List;


public class CarritoController {

    private CrearCarrito crearCarrito;
    private final CarritoDAO carritoDAO;
    private final ProductoDAO productoDAO;
    private Carrito carrito;

    public CarritoController(CarritoDAO carritoDAO, ProductoDAO productoDAO){

        this.carritoDAO = carritoDAO;
        this.productoDAO = productoDAO;
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
    }

    public CrearCarrito getCrearCarrito() {
        return crearCarrito;
    }

    public CarritoDAO getCarritoDAO() {
        return carritoDAO;
    }

    public void setCrearCarrito(CrearCarrito crearCarrito) {
        this.crearCarrito = crearCarrito;
        this.carrito = new Carrito(); // Aquí recién se crea el primer carrito
        crearCarrito.getTxtCodigoCarrito().setText(String.valueOf(carrito.getCodigo()));
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

    private void cargarProductos(){

        List<ItemCarrito> items = carrito.obtenerItems();
        DefaultTableModel modelo = (DefaultTableModel) crearCarrito.getTblProductos().getModel();
        modelo.setNumRows(0);
        for (ItemCarrito item : items) {
            modelo.addRow(new Object[]{ item.getProducto().getCodigo(),
                    item.getProducto().getNombre(),
                    item.getProducto().getPrecio(),
                    item.getCantidad(),
                    item.getProducto().getPrecio() * item.getCantidad() });
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
}