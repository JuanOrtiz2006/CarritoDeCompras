package ec.edu.ups.controlador;

import ec.edu.ups.dao.*;
import ec.edu.ups.modelo.*;
import ec.edu.ups.util.FormateadorUtils;
import ec.edu.ups.vista.*;

import ec.edu.ups.util.Contexto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Controlador para la gestión de carritos de compras.
 * Administra la creación, edición, eliminación, listado y manejo de productos en el carrito,
 * así como la interacción con las vistas y los DAOs correspondientes.
 *
 * @author JuanOrtiz2006
 * @version 1.0
 */
public class CarritoController {

    private CrearCarrito crearCarrito;
    private ListaCarrito listaCarrito;
    private final CarritoDAO carritoDAO;
    private final ProductoDAO productoDAO;
    private Carrito carrito;
    private Usuario usuario;
    private boolean modoEdicion = false;


    /**
     * Constructor que inicializa el controlador con los DAOs de carrito y producto.
     *
     * @param carritoDAO DAO para carritos.
     * @param productoDAO DAO para productos.
     */
    public CarritoController(CarritoDAO carritoDAO, ProductoDAO productoDAO) {
        this.carritoDAO = carritoDAO;
        this.productoDAO = productoDAO;
        this.carrito = new Carrito();
        this.usuario = new Usuario();
    }

    /**
     * Asigna la vista para crear carritos y prepara el formulario.
     * @param crearCarrito Vista de creación de carritos.
     */
    public void setCrearCarrito(CrearCarrito crearCarrito) {
        this.crearCarrito = crearCarrito;

        if (!modoEdicion) {
            this.carrito = new Carrito();
            GregorianCalendar fechaActual = obtenerFechaActual();
            carrito.setFecha(fechaActual);
            carrito.setUsuario(this.usuario);

            int siguienteCodigo = carritoDAO.obtenerSiguienteCodigoParaUsuario(this.usuario);
            crearCarrito.getTxtCodigoCarrito().setText(String.valueOf(siguienteCodigo));
            crearCarrito.getTxtFecha().setText(
                    FormateadorUtils.formatearFecha(fechaActual.getTime(), Contexto.getLocale())
            );            crearCarrito.activarModoCreacion();
        }
    }

    /**
     * Asigna la vista para listar carritos.
     * @param listaCarrito Vista de listado de carritos.
     */
    public void setListaCarrito(ListaCarrito listaCarrito) {
        this.listaCarrito = listaCarrito;
    }

    /**
     * Asigna el usuario autenticado para las operaciones de carrito.
     * @param usuario Usuario autenticado.
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Configura los eventos de la vista de creación de carritos.
     */
    //eventosPaginas
    public void eventosCrearCarrito() {
        var handler = Contexto.getHandler();

        for (ActionListener al : crearCarrito.getBtnSeleccionar().getActionListeners()) {
            crearCarrito.getBtnAgregar().removeActionListener(al);
        }
        crearCarrito.getBtnSeleccionar().addActionListener(e -> seleccionarItem());

        for (ActionListener al : crearCarrito.getBtnAgregar().getActionListeners()) {
            crearCarrito.getBtnAgregar().removeActionListener(al);
        }
        crearCarrito.getBtnAgregar().addActionListener(e -> anadirProductoACarrito());

        for (ActionListener al : crearCarrito.getBtnGuardar().getActionListeners()) {
            crearCarrito.getBtnGuardar().removeActionListener(al);
        }
        crearCarrito.getBtnGuardar().addActionListener(e -> guardarCarrito());

        crearCarrito.getBtnVaciar().addActionListener(e -> vaciarCarrito());
        crearCarrito.getTblProductos().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int fila = crearCarrito.getTblProductos().getSelectedRow();
                if (fila != -1) {
                    String[] opciones = {handler.get("opciones.editar"), handler.get("opciones.eliminar"), handler.get("opciones.cancelar")};
                    int opcion = JOptionPane.showOptionDialog(
                            crearCarrito,
                            handler.get("opciones.accionproducto"),
                            handler.get("opciones.titulo"),
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

    /**
     * Configura los eventos de la vista de listado de carritos.
     */
    public void eventosListarCarritos() {
        var handler = Contexto.getHandler();

        listaCarrito.getBtnListar().addActionListener(e -> cargarCarritosEnTabla());
        listaCarrito.getBtnBuscar().addActionListener(e -> buscarCarrito());

        listaCarrito.getTblCarritos().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int fila = listaCarrito.getTblCarritos().getSelectedRow();
                if (fila == -1) return;

                int codigo = (int) listaCarrito.getTblCarritos().getValueAt(fila, 1);
                String[] opciones = {
                        handler.get("opciones.editar"),
                        handler.get("opciones.eliminar"),
                        handler.get("opciones.cancelar")
                };

                int opcion = listaCarrito.mostrarConfirmDialog(handler.get("mensaje.carrito.accion"), opciones);
                if (opcion == 0) editarCarrito(codigo);
                else if (opcion == 1) eliminarCarrito(codigo);
            }
        });
    }


    //metodosCrearCarrito
    /**
     * Selecciona un producto por código y lo muestra en la vista.
     */
    private void seleccionarItem() {
        var handler = Contexto.getHandler();
        String codigoStr = crearCarrito.getTxtCodigo().getText().trim();
        if (codigoStr.isEmpty()) {
            crearCarrito.mostrarMensaje(handler.get("mensaje.carrito.codigovacio"));
            return;
        }
        try {
            int codigo = Integer.parseInt(codigoStr);
            Producto producto = productoDAO.buscarPorCodigo(codigo);
            if (producto != null) {
                crearCarrito.cargarProductoEncontrado(producto.getNombre(), producto.getPrecio());
            } else {
                crearCarrito.mostrarMensaje(handler.get("mensaje.producto.noencontrado"));
            }
        } catch (NumberFormatException e) {
            crearCarrito.mostrarMensaje(handler.get("mensaje.carrito.codigoinvalido"));
        }
    }

    /**
     * Añade un producto al carrito con la cantidad indicada.
     */
    private void anadirProductoACarrito() {
        var handler = Contexto.getHandler();
        if (!validarUsuarioActual()) return;

        if (crearCarrito.getTxtCodigo().getText().trim().isEmpty() || crearCarrito.getTxtCantidad().getText().trim().isEmpty()) {
            crearCarrito.mostrarMensaje(handler.get("mensaje.carrito.camposvacios"));
            return;
        }

        try {
            int codigo = Integer.parseInt(crearCarrito.getTxtCodigo().getText());
            int cantidad = Integer.parseInt(crearCarrito.getTxtCantidad().getText());

            if (cantidad <= 0) {
                crearCarrito.mostrarMensaje(handler.get("mensaje.cantidad.invalida"));
                return;
            }

            Producto producto = productoDAO.buscarPorCodigo(codigo);
            if (producto != null) {
                carrito.agregarProducto(producto, cantidad);
                crearCarrito.limpiarCamposItem();
                cargarProductosEnTabla();
                mostrarTotalesEnTabla();
            } else {
                crearCarrito.mostrarMensaje(handler.get("mensaje.producto.noencontrado"));
            }
        } catch (NumberFormatException e) {
            crearCarrito.mostrarMensaje(handler.get("mensaje.valores.invalidos"));
        }
    }

    /**
     * Carga los productos del carrito en la tabla de la vista.
     */
    private void cargarProductosEnTabla() {
        DefaultTableModel modelo = (DefaultTableModel) crearCarrito.getTblProductos().getModel();
        modelo.setRowCount(0);
        for (ItemCarrito item : carrito.getItems()) {
            modelo.addRow(new Object[]{
                    item.getProducto().getCodigo(),
                    item.getProducto().getNombre(),
                    FormateadorUtils.formatearMoneda(item.getProducto().getPrecio(), Contexto.getLocale()),
                    item.getCantidad(),
                    FormateadorUtils.formatearMoneda(
                            item.getProducto().getPrecio() * item.getCantidad(),
                            Contexto.getLocale()
                    )
            });
        }
    }

    /**
     * Muestra los totales (subtotal, IVA, total) en la tabla de la vista.
     */
    private void mostrarTotalesEnTabla() {
        DefaultTableModel modelo = (DefaultTableModel) crearCarrito.getTblTotal().getModel();
        modelo.setRowCount(0);
        modelo.addRow(new Object[]{
                FormateadorUtils.formatearMoneda(carrito.calcularSubtotal(), Contexto.getLocale()),
                FormateadorUtils.formatearMoneda(carrito.calcularIVA(), Contexto.getLocale()),
                FormateadorUtils.formatearMoneda(carrito.calcularTotal(), Contexto.getLocale())
        });
    }

    /**
     * Guarda el carrito actual en el DAO.
     */
    private void guardarCarrito() {
        var handler = Contexto.getHandler();

        if (!carrito.esValido()) {
            crearCarrito.mostrarMensaje(handler.get("mensaje.carrito.vacio"));
            return;
        }

        try {
            carrito.setCodigo(carritoDAO.obtenerSiguienteCodigoParaUsuario(usuario));
            carrito.setFecha(obtenerFechaActual());
            carrito.setUsuario(usuario);
            carritoDAO.crear(carrito);
            crearCarrito.mostrarMensaje(String.format(handler.get("mensaje.carrito.creado"), usuario.getUsername()));
            crearCarrito.limpiarFormulario();
            crearCarrito.setVisible(false);
            modoEdicion = false;
        } catch (Exception ex) {
            crearCarrito.mostrarMensaje(handler.get("mensaje.error.guardarcarrito"));
        }

    }

    /**
     * Vacía el carrito actual y actualiza la vista.
     */
    private void vaciarCarrito() {
        var handler = Contexto.getHandler();

        int confirmacion = JOptionPane.showConfirmDialog(
                crearCarrito,
                handler.get("mensaje.carrito.confirmarvaciar"),
                handler.get("opciones.titulo"),
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            carrito.vaciarCarrito();
            cargarProductosEnTabla();
            mostrarTotalesEnTabla();
            crearCarrito.mostrarMensaje(handler.get("mensaje.carrito.vaciar"));
        }
    }

    /**
     * Elimina un ítem del carrito por su posición en la tabla.
     * @param fila Índice del ítem a eliminar.
     */
    public void eliminarItem(int fila) {
        if (fila >= 0 && fila < carrito.getItems().size()) {
            int codigoProducto = carrito.getItems().get(fila).getProducto().getCodigo();
            carrito.eliminarProducto(codigoProducto);
            cargarProductosEnTabla();
            mostrarTotalesEnTabla();
            crearCarrito.mostrarMensaje(Contexto.getHandler().get("mensaje.item.eliminar"));
        }
    }

    /**
     * Edita la cantidad de un ítem del carrito por su posición en la tabla.
     * @param fila Índice del ítem a editar.
     */
    public void editarItem(int fila) {
        var handler = Contexto.getHandler();
        if (fila >= 0 && fila < carrito.getItems().size()) {
            ItemCarrito item = carrito.getItems().get(fila);
            String nuevaCantidadStr = JOptionPane.showInputDialog(handler.get("mensaje.item.cantidad") + item.getProducto().getNombre(), item.getCantidad());
            try {
                int nuevaCantidad = Integer.parseInt(nuevaCantidadStr);
                carrito.actualizarProducto(new ItemCarrito(item.getProducto(), nuevaCantidad));
                cargarProductosEnTabla();
                mostrarTotalesEnTabla();
                crearCarrito.mostrarMensaje(handler.get("mensaje.item.actualizarcantidad"));
            } catch (NumberFormatException e) {
                crearCarrito.mostrarMensaje(handler.get("mensaje.item.errorcantidad"));
            }
        }
    }

    /**
     * Carga los carritos en la tabla de la vista de listado.
     */
    private void cargarCarritosEnTabla() {
        DefaultTableModel modelo = (DefaultTableModel) listaCarrito.getTblCarritos().getModel();
        modelo.setRowCount(0);

        List<Carrito> listaCarritos;

        if (usuario != null && usuario.getRol() == Rol.ADMINISTRADOR) {
            listaCarritos = carritoDAO.listarCarritos(); // todos
        } else {
            listaCarritos = carritoDAO.listarCarritosPorUsuario(this.usuario); // solo suyos
        }

        for (Carrito c : listaCarritos) {
            modelo.addRow(new Object[]{
                    c.getUsuario().getUsername(),
                    c.getCodigo(),
                    FormateadorUtils.formatearFecha(c.getFecha().getTime(), Contexto.getLocale()),
                    FormateadorUtils.formatearMoneda(c.calcularTotal(), Contexto.getLocale())
            });
        }

    }

    /**
     * Carga un carrito específico en la tabla de la vista de listado.
     * @param c Carrito a mostrar.
     */
    private void cargarCarritoEnTabla(Carrito c) {
        DefaultTableModel modelo = (DefaultTableModel) listaCarrito.getTblCarritos().getModel();
        modelo.setRowCount(0);
        modelo.addRow(new Object[]{
                c.getUsuario().getUsername(),
                c.getCodigo(),
                FormateadorUtils.formatearFecha(c.getFecha().getTime(), Contexto.getLocale()),
                FormateadorUtils.formatearMoneda(c.calcularTotal(), Contexto.getLocale())
        });
    }

    /**
     * Busca un carrito por código y usuario y lo muestra en la tabla.
     */
    private void buscarCarrito() {
        try {
            int codigo = Integer.parseInt(listaCarrito.getTxtCodigo().getText());
            //Buscar por código y usuario
            Carrito c = carritoDAO.buscarPorCodigoYUsuario(codigo, this.usuario);
            if (c != null) {
                cargarCarritoEnTabla(c);
            } else {
                listaCarrito.mostrarMensaje(Contexto.getHandler().get("mensaje.carrito.noencontrado"));
            }
        } catch (NumberFormatException e) {
            listaCarrito.mostrarMensaje(Contexto.getHandler().get("mensaje.carrito.codigoinvalido"));
        }
    }

    /**
     * Elimina un carrito por código si pertenece al usuario autenticado.
     * @param codigo Código del carrito a eliminar.
     */
    private void eliminarCarrito(int codigo) {
        //Verificar que el carrito pertenece al usuario
        if (!carritoDAO.existeCarrito(codigo, this.usuario)) {
            listaCarrito.mostrarMensaje(Contexto.getHandler().get("mensaje.carrito.noautorizado"));
            return;
        }

        if (JOptionPane.showConfirmDialog(listaCarrito,
                Contexto.getHandler().get("mensaje.carrito.eliminar"),
                Contexto.getHandler().get("opciones.titulo"),
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

            //Usar metodo específico para eliminar por usuario
            carritoDAO.eliminarPorCodigoYUsuario(codigo, this.usuario);
            cargarCarritosEnTabla();
            listaCarrito.mostrarMensaje(Contexto.getHandler().get("mensaje.carrito.eliminado"));
        }
    }

    /**
     * Edita un carrito por código si pertenece al usuario autenticado.
     * @param codigo Código del carrito a editar.
     */
    public void editarCarrito(int codigo) {
        Carrito carritoCargado = carritoDAO.buscarPorCodigoYUsuario(codigo, this.usuario);
        if (carritoCargado == null) {
            listaCarrito.mostrarMensaje(Contexto.getHandler().get("mensaje.carrito.noautorizado"));
            return;
        }

        this.modoEdicion = true;
        this.carrito = carritoCargado;

        setCrearCarrito(crearCarrito);
        eventosCrearCarrito();
        cargarCarritoParaEdicion(carritoCargado);

        if (crearCarrito.getParent() == null) {
            listaCarrito.getParent().add(crearCarrito);
        }
        crearCarrito.setVisible(true);
        crearCarrito.toFront();
    }

    /**
     * Valida que el usuario actual esté autenticado.
     * @return true si el usuario está autenticado, false en caso contrario.
     */
    private boolean validarUsuarioActual() {
        if (this.usuario == null) {
            crearCarrito.mostrarMensaje(Contexto.getHandler().get("mensaje.usuario.noautenticado"));
            return false;
        }
        return true;
    }

    /**
     * Carga los datos de un carrito para edición en la vista.
     * @param carritoCargado Carrito a editar.
     */
    private void cargarCarritoParaEdicion(Carrito carritoCargado) {
        crearCarrito.getTxtCodigoCarrito().setText(String.valueOf(carritoCargado.getCodigo()));
        crearCarrito.getTxtFecha().setText(
                FormateadorUtils.formatearFecha(carritoCargado.getFecha().getTime(), Contexto.getLocale())
        );
        crearCarrito.activarModoEdicion();

        cargarProductosEnTabla();    // Carga productos actuales
        mostrarTotalesEnTabla();

        for (ActionListener al : crearCarrito.getBtnEditar().getActionListeners()) {
            crearCarrito.getBtnEditar().removeActionListener(al);
        }
        crearCarrito.getBtnEditar().addActionListener(e -> actualizarCarrito());
    }

    /**
     * Actualiza los datos del carrito editado y los guarda en el DAO.
     */
    private void actualizarCarrito() {
        if (carrito.estaVacio()) {
            crearCarrito.mostrarMensaje(Contexto.getHandler().get("mensaje.carrito.vacio"));
            return;
        }
        if (crearCarrito.getTxtFecha().getText().trim().isEmpty()) {
            crearCarrito.mostrarMensaje(Contexto.getHandler().get("mensaje.carrito.fechavacia"));
            return;
        }
        try {
            DefaultTableModel modelo = (DefaultTableModel) crearCarrito.getTblProductos().getModel();
            int rows = modelo.getRowCount();
            for(int i = 0; i < rows; i++){
                int codigoProducto = (int) modelo.getValueAt(i, 0);
                int cantidad = (int) modelo.getValueAt(i, 3);
                Producto producto = productoDAO.buscarPorCodigo(codigoProducto);
                if (producto == null) {
                    crearCarrito.mostrarMensaje(Contexto.getHandler().get("mensaje.producto.noencontrado"));
                    return;
                }
                carrito.actualizarProducto(new ItemCarrito(producto, cantidad));
            }
            String fechaTexto = crearCarrito.getTxtFecha().getText();
            DateFormat formato = DateFormat.getDateInstance(DateFormat.MEDIUM, Contexto.getLocale());
            Date fecha = formato.parse(fechaTexto);
            GregorianCalendar fechaGC = new GregorianCalendar();
            fechaGC.setTime(fecha);
            carrito.setFecha(fechaGC);
        } catch (ParseException e) {
            crearCarrito.mostrarMensaje(Contexto.getHandler().get("error.de.formato.de.fecha"));
            return;
        }


        carritoDAO.actualizar(carrito);
        crearCarrito.mostrarMensaje(Contexto.getHandler().get("mensaje.carrito.actualizado"));
        crearCarrito.setVisible(false);
    }

    /**
     * Obtiene la fecha actual como GregorianCalendar.
     * @return Fecha actual.
     */
    private GregorianCalendar obtenerFechaActual() {
        return new GregorianCalendar();
    }

}
