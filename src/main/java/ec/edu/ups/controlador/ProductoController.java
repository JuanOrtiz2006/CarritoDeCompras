package ec.edu.ups.controlador;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.util.Contexto;
import ec.edu.ups.vista.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ProductoController {
    private CrearProductoView crearProductoView;
    private BuscarProducto buscarProducto;
    private ActualizarProducto actualizarProducto;
    private EliminarProducto eliminarProducto;
    private ListaProducto listaProducto;
    private final ProductoDAO productoDAO;

    public ProductoController(ProductoDAO productoDAO) {
        this.productoDAO = productoDAO;
    }

    public void setCrearProductoView(CrearProductoView crearProductoView) {
        this.crearProductoView = crearProductoView;
    }
    public void setBuscarProducto(BuscarProducto buscarProducto) {
        this.buscarProducto = buscarProducto;
    }
    public void setEliminarProducto(EliminarProducto eliminarProducto) {
        this.eliminarProducto = eliminarProducto;
    }
    public void setListaProducto(ListaProducto listaProducto) {
        this.listaProducto = listaProducto;
    }
    public void setActualizarProducto(ActualizarProducto actualizarProducto) {
        this.actualizarProducto = actualizarProducto;
    }

    //Eventos
    public void eventoCrearProducto() {
        crearProductoView.getBtnAceptar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearProducto();
            }
        });
    }

    public void eventoBuscarProducto() {
        buscarProducto.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProducto();
            }
        });
    }

    public void eventoEliminarProducto() {
        eliminarProducto.getBtnSeleccionar().addActionListener(e -> {
            var handler = Contexto.getHandler();
            try {
                int codigo = Integer.parseInt(eliminarProducto.getTxtCodigo().getText());
                Producto producto = productoDAO.buscarPorCodigo(codigo);
                if (producto != null) {
                    eliminarProducto.cargarProductoEncontrado(producto.getNombre(), producto.getPrecio());
                } else {
                    eliminarProducto.mostrarMensaje(handler.get("mensaje.producto.noencontrado"));
                }
            } catch (NumberFormatException ex) {
                eliminarProducto.mostrarMensaje(handler.get("mensaje.producto.errorcodigo"));
            }
        });

        eliminarProducto.getBtnEliminar().addActionListener(e -> eliminarProducto());
    }

    public void eventoListarProductos() {
        listaProducto.getBtnListar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarProductos();
            }
        });
    }

    public void eventoActualizarProducto() {
        actualizarProducto.getBtnSeleccionar().addActionListener(e -> {
            var handler = Contexto.getHandler();
            try {
                int codigo = Integer.parseInt(actualizarProducto.getTxtCodigo().getText());
                Producto producto = productoDAO.buscarPorCodigo(codigo);
                if (producto != null) {
                    actualizarProducto.productoEncontrado(producto.getNombre(), producto.getPrecio());
                } else {
                    actualizarProducto.mostrarMensaje(handler.get("mensaje.producto.noencontrado"));
                }
            } catch (NumberFormatException ex) {
                actualizarProducto.mostrarMensaje(handler.get("mensaje.producto.errorcodigo"));
            }
        });

        actualizarProducto.getBtnActualizar().addActionListener(e -> actualizarProducto());
    }

    //Metodos
    private void crearProducto() {
        var handler = Contexto.getHandler();

        try {
            String codigoStr = crearProductoView.getTxtCodigo().getText();
            String nombre = crearProductoView.getTxtNombre().getText();
            String precioStr = crearProductoView.getTxtPrecio().getText();

            if (codigoStr.isEmpty() || nombre.isEmpty() || precioStr.isEmpty()) {
                crearProductoView.mostrarMensaje(handler.get("mensaje.producto.camposvacios"));
                return;
            }

            int codigo = Integer.parseInt(codigoStr);
            double precio = Double.parseDouble(precioStr);

            productoDAO.crear(new Producto(codigo, nombre, precio));
            crearProductoView.limpiarCampos();

            String mensaje = String.format(handler.get("mensaje.producto.creado"), nombre);
            crearProductoView.mostrarMensaje(mensaje);
        } catch (NumberFormatException e) {
            crearProductoView.mostrarMensaje(handler.get("mensaje.producto.errorcodigo"));
        }
    }

    private void buscarProducto() {
        var handler = Contexto.getHandler();

        String tipoBusqueda = (String) buscarProducto.getCmbBusqueda().getSelectedItem();
        String valorBuscado = buscarProducto.getTxtBusqueda().getText();

        if (tipoBusqueda == null || valorBuscado.isEmpty()) {
            buscarProducto.mostrarMensaje(handler.get("mensaje.producto.camposvacios"));
            return;
        }

        if (tipoBusqueda.equals(handler.get("buscarproducto.combo.codigo"))) {
            try {
                int codigo = Integer.parseInt(valorBuscado);
                Producto producto = productoDAO.buscarPorCodigo(codigo);
                if (producto != null) {
                    buscarProducto.cargarProductoBuscado(producto);
                } else {
                    buscarProducto.limpiarCampos();
                    buscarProducto.mostrarMensaje(handler.get("mensaje.producto.noencontrado"));
                }
            } catch (NumberFormatException e) {
                buscarProducto.mostrarMensaje(handler.get("mensaje.producto.errorcodigo"));
            }
        } else if (tipoBusqueda.equals(handler.get("buscarproducto.combo.nombre"))) {
            List<Producto> productos = productoDAO.buscarPorNombre(valorBuscado);
            if (!productos.isEmpty()) {
                buscarProducto.cargarProductosListados(productos);
            } else {
                buscarProducto.limpiarCampos();
                buscarProducto.mostrarMensaje(handler.get("mensaje.producto.noencontrado"));
            }
        }
    }

    public void actualizarProducto() {
        var handler = Contexto.getHandler();

        int opcion = actualizarProducto.mostrarMensaje(handler.get("mensaje.producto.confirmaractualizacion"));

        if (opcion == JOptionPane.YES_OPTION) {
            try {
                int codigo = Integer.parseInt(actualizarProducto.getTxtCodigo().getText());
                String nombre = actualizarProducto.getTxtNombre().getText();
                double precio = Double.parseDouble(actualizarProducto.getTxtPrecio().getText());

                productoDAO.actualizar(new Producto(codigo, nombre, precio));
                actualizarProducto.limpiarCampos();
                JOptionPane.showMessageDialog(null, handler.get("mensaje.producto.actualizado"));
                actualizarProducto.limpiarCampos();
            } catch (NumberFormatException e) {
                actualizarProducto.mostrarMensaje(handler.get("mensaje.producto.errorcodigo"));
            }
        }
    }

    private void eliminarProducto() {
        var handler = Contexto.getHandler();

        int opcion = eliminarProducto.mostrarMensaje(handler.get("mensaje.producto.confirmareliminar"));
        if (opcion == JOptionPane.YES_OPTION) {
            try {
                int codigo = Integer.parseInt(eliminarProducto.getTxtCodigo().getText());
                productoDAO.eliminar(codigo);
                eliminarProducto.limpiarCampos();
                JOptionPane.showMessageDialog(null, handler.get("mensaje.producto.eliminado"));
            } catch (NumberFormatException e) {
                eliminarProducto.mostrarMensaje(handler.get("mensaje.producto.errorcodigo"));
            }
        }
    }

    private void listarProductos() {
        var handler = Contexto.getHandler();
        String tipoOrden = (String) listaProducto.getCmbTipo().getSelectedItem();

        List<Producto> productos = new ArrayList<>(productoDAO.listarTodos());

        if (tipoOrden != null && !tipoOrden.trim().isEmpty()) {
            String codigoOpcion = handler.get("listaproducto.filtro.codigo");
            String nombreOpcion = handler.get("listaproducto.filtro.nombre");

            if (tipoOrden.equalsIgnoreCase(codigoOpcion)) {
                productos.sort(Comparator.comparingInt(Producto::getCodigo));
            } else if (tipoOrden.equalsIgnoreCase(nombreOpcion)) {
                productos.sort(Comparator.comparing(Producto::getNombre, String.CASE_INSENSITIVE_ORDER));
            }
        }

        listaProducto.cargarProductos(productos);
    }

}
