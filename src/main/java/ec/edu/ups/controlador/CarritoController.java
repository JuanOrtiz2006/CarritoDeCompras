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
        this.usuario = new Usuario();
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
        crearCarrito.getTblProductos().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int fila = crearCarrito.getTblProductos().getSelectedRow();
                if (fila != -1) {
                    int codigoProducto = (int) crearCarrito.getTblProductos().getValueAt(fila, 0);
                    String nombre = (String) crearCarrito.getTblProductos().getValueAt(fila, 1);

                    String[] opciones = {Contexto.getHandler().get("opciones.editar"), Contexto.getHandler().get("opciones.eliminar"), Contexto.getHandler().get("opciones.cancelar")};
                    int opcion = JOptionPane.showOptionDialog(
                            crearCarrito,
                            Contexto.getHandler().get("opciones.accionproducto"),
                            Contexto.getHandler().get("opciones.titulo"),
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            opciones,
                            opciones[2]
                    );

                    if (opcion == 0) {
                        editarItem(fila);
                    } else if (opcion == 1) {
                        eliminarItem(fila);
                    }
                }
            }
        });
    }

    private void seleccionarItem() {
        int codigo = Integer.parseInt(crearCarrito.getTxtCodigo().getText());
        Producto producto = productoDAO.buscarPorCodigo(codigo);
        if (producto != null) {
            crearCarrito.productoEncontrado(producto.getNombre(), producto.getPrecio());
        } else {
            crearCarrito.mostrarMensaje(Contexto.getHandler().get("mensaje.producto.noencontrado"));
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
            crearCarrito.mostrarMensaje(Contexto.getHandler().get("mensaje.producto.noencontrado"));
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
            crearCarrito.mostrarMensaje(Contexto.getHandler().get("mensaje.carrito.vacio"));
            return;
        }

        // Asignar el código solo aquí (cuando se guarda)
        int nuevoCodigo = Carrito.generarCodigo();
        carrito.setCodigo(nuevoCodigo);

        GregorianCalendar fechaActual = obtenerFechaActual();
        carrito.setFecha(fechaActual);
        carrito.setUsuario(usuario);

        carritoDAO.crear(carrito);
        crearCarrito.mostrarMensaje(Contexto.getHandler().get("mensaje.carrito.creado") + usuario.getUsername());

        crearCarrito.limpiarFormulario();
        modoEdicion = false;
    }

    private GregorianCalendar obtenerFechaActual() {
        return new GregorianCalendar();
    }

    private void vaciarCarrito() {
        if (JOptionPane.showConfirmDialog(crearCarrito, Contexto.getHandler().get("mensaje.carrito.confirmarvaciar"), Contexto.getHandler().get("opciones.titulo"), JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            carrito.vaciarCarrito();
            cargarProductosEnTabla();
            mostrarTotalesEnTabla();
            crearCarrito.mostrarMensaje(Contexto.getHandler().get("mensaje.carrito.vaciar"));
        }
    }

    public void eliminarItem(int fila) {
        if (fila >= 0 && fila < carrito.getItems().size()) {
            int codigoProducto = carrito.getItems().get(fila).getProducto().getCodigo();
            carrito.eliminarProducto(codigoProducto);
            cargarProductosEnTabla();
            mostrarTotalesEnTabla();
            crearCarrito.mostrarMensaje(Contexto.getHandler().get("mensaje.item.eliminar"));
        }
    }

    public void editarItem(int fila) {
        if (fila >= 0 && fila < carrito.getItems().size()) {
            ItemCarrito item = carrito.getItems().get(fila);
            String nuevaCantidadStr = JOptionPane.showInputDialog(Contexto.getHandler().get("mensaje.item.cantidad") + item.getProducto().getNombre(), item.getCantidad());
            try {
                int nuevaCantidad = Integer.parseInt(nuevaCantidadStr);
                carrito.actualizarProducto(new ItemCarrito(item.getProducto(), nuevaCantidad));
                cargarProductosEnTabla();
                mostrarTotalesEnTabla();
                crearCarrito.mostrarMensaje(Contexto.getHandler().get("mensaje.item.actualizarcantidad"));
            } catch (Exception e) {
                crearCarrito.mostrarMensaje(Contexto.getHandler().get("mensaje.item.errorcantidad"));
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
                    String[] opciones = {Contexto.getHandler().get("opciones.editar"), Contexto.getHandler().get("opciones.eliminar"), Contexto.getHandler().get("opciones.cancelar")};
                    int opcion = listaCarrito.mostrarConfirmDialog(Contexto.getHandler().get("mensaje.carrito.accion"), opciones);
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
            listaCarrito.mostrarMensaje(Contexto.getHandler().get("mensaje.carrito.noencontrado"));
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
        if (JOptionPane.showConfirmDialog(listaCarrito, Contexto.getHandler().get("mensaje.carrito.eliminar"), Contexto.getHandler().get("opciones.titulo"), JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            carritoDAO.eliminar(codigo);
            cargarCarritosEnTabla();
            listaCarrito.mostrarMensaje(Contexto.getHandler().get("mensaje.carrito.eliminado"));
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
            crearCarrito.mostrarMensaje(Contexto.getHandler().get("mensaje.carrito.vacio"));
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
            crearCarrito.mostrarMensaje(Contexto.getHandler().get("mensaje.carrito.errorfecha"));
            return;
        }

        carritoDAO.actualizar(carrito);
        crearCarrito.mostrarMensaje(Contexto.getHandler().get("mensaje.carrito.actualizado"));
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
