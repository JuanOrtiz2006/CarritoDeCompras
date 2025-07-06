package ec.edu.ups.controlador;

import ec.edu.ups.dao.*;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.FormateadorUtils;
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

    public void setListaCarrito(ListaCarrito listaCarrito) {
        this.listaCarrito = listaCarrito;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    //eventosPaginas
    public void eventosCrearCarrito() {
        var handler = Contexto.getHandler();

        crearCarrito.getBtnSeleccionar().addActionListener(e -> seleccionarItem());
        crearCarrito.getBtnAgregar().addActionListener(e -> anadirProductoACarrito());
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
    private void seleccionarItem() {
        var handler = Contexto.getHandler();
        String codigoStr = crearCarrito.getTxtCodigo().getText().trim();

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

    private void anadirProductoACarrito() {
        var handler = Contexto.getHandler();
        if (!validarUsuarioActual()) return;

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

    private void mostrarTotalesEnTabla() {
        DefaultTableModel modelo = (DefaultTableModel) crearCarrito.getTblTotal().getModel();
        modelo.setRowCount(0);
        modelo.addRow(new Object[]{
                FormateadorUtils.formatearMoneda(carrito.calcularSubtotal(), Contexto.getLocale()),
                FormateadorUtils.formatearMoneda(carrito.calcularIVA(), Contexto.getLocale()),
                FormateadorUtils.formatearMoneda(carrito.calcularTotal(), Contexto.getLocale())
        });
    }

    private void guardarCarrito() {
        var handler = Contexto.getHandler();

        if (!carrito.esValido()) {
            crearCarrito.mostrarMensaje(handler.get("mensaje.carrito.vacio"));
            return;
        }

        carrito.setCodigo(carritoDAO.obtenerSiguienteCodigoParaUsuario(usuario));
        carrito.setFecha(obtenerFechaActual());
        carrito.setUsuario(usuario);

        carritoDAO.crear(carrito);
        crearCarrito.mostrarMensaje(String.format("%s %s", handler.get("mensaje.carrito.creado"), usuario.getUsername()));
        crearCarrito.limpiarFormulario();
        crearCarrito.setVisible(false);
        modoEdicion = false;
    }

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
            } catch (Exception e) {
                crearCarrito.mostrarMensaje(handler.get("mensaje.item.errorcantidad"));
            }
        }
    }

    //metodosListarCarritos
    private void cargarCarritosEnTabla() {
        DefaultTableModel modelo = (DefaultTableModel) listaCarrito.getTblCarritos().getModel();
        modelo.setRowCount(0);

        // CAMBIO: Mostrar solo carritos del usuario actual
        for (Carrito c : carritoDAO.listarCarritosPorUsuario(this.usuario)) {
            modelo.addRow(new Object[]{
                    c.getUsuario().getUsername(),
                    c.getCodigo(),
                    FormateadorUtils.formatearFecha(c.getFecha().getTime(), Contexto.getLocale()),
                    FormateadorUtils.formatearMoneda(c.calcularTotal(), Contexto.getLocale())
            });
        }
    }

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

    //MetodosEditarCarrito
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

    private boolean validarUsuarioActual() {
        if (this.usuario == null) {
            crearCarrito.mostrarMensaje(Contexto.getHandler().get("mensaje.usuario.noautenticado"));
            return false;
        }
        return true;
    }

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
        crearCarrito.setVisible(false);
    }

    private GregorianCalendar obtenerFechaActual() {
        return new GregorianCalendar();
    }

}
