package ec.edu.ups.controlador;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.vista.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    public void eventoCrearProducto() {
        crearProductoView.getBtnAceptar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearProducto();
            }
        });
    }

    private void crearProducto() {
        int codigo = Integer.parseInt(crearProductoView.getTxtCodigo().getText());
        String nombre = crearProductoView.getTxtNombre().getText();
        double precio = Double.parseDouble(crearProductoView.getTxtPrecio().getText());

        productoDAO.crear(new Producto(codigo, nombre, precio));
        crearProductoView.limpiarCampos();
        crearProductoView.mostrarMensaje("Producto " + nombre + " guardado correctamente");

    }

    public void eventoBuscarProducto() {
        buscarProducto.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProducto();
            }
        });
    }

    private void buscarProducto() {
        String tipoBusqueda = (String) buscarProducto.getCmbBusqueda().getSelectedItem();
        String valorBuscado = buscarProducto.getTxtBusqueda().getText();

        if (tipoBusqueda == "Codigo") {
            try {
                int codigo = Integer.parseInt(valorBuscado);
                Producto producto = productoDAO.buscarPorCodigo(codigo);
                if (producto != null) {
                    buscarProducto.cargarProducto(producto);
                } else {
                    buscarProducto.eliminarCampos();
                    buscarProducto.mostrarMensaje("Producto no enctontrado");
                }
            } catch (NumberFormatException e) {
                buscarProducto.mostrarMensaje("Codigo incorrecto");
            }
        } else if (tipoBusqueda == "Nombre") {
            List<Producto> productos = productoDAO.buscarPorNombre(valorBuscado);
            if (!productos.isEmpty()) {
                buscarProducto.cargarProductos(productos);
            } else {
                buscarProducto.eliminarCampos();
                buscarProducto.mostrarMensaje("No se encontraron productos con ese nombre.");
            }

        }
    }

    public void eventoActualizarProducto() {
        actualizarProducto.getBtnSeleccionar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int codigo = Integer.parseInt(actualizarProducto.getTxtCodigo().getText());
                Producto producto = productoDAO.buscarPorCodigo(codigo);
                actualizarProducto.productoEncontrado(producto.getNombre(), producto.getPrecio());
            }
        });
        actualizarProducto.getBtnActualizar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarProducto();
            }
        });
    }

    public void actualizarProducto() {
        int opcion = actualizarProducto.mostrarMensaje("Desea actualizar el producto ?");
        if (opcion == JOptionPane.YES_OPTION) {
            int codigo = Integer.parseInt(actualizarProducto.getTxtCodigo().getText());
            String nombre = actualizarProducto.getTxtNombre().getText();
            double precio = Double.parseDouble(actualizarProducto.getTxtPrecio().getText());

            productoDAO.actualizar(new Producto(codigo, nombre, precio));
            actualizarProducto.limpiarCampos();
            JOptionPane.showMessageDialog(null, "Producto actualizado");
        }
    }

    public void eventoEliminarProducto() {
        eliminarProducto.getBtnSeleccionar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int codigo = Integer.parseInt(eliminarProducto.getTxtCodigo().getText());
                Producto producto = productoDAO.buscarPorCodigo(codigo);
                eliminarProducto.productoEncontrado(producto.getNombre(), producto.getPrecio());
            }
        });

        eliminarProducto.getBtnEliminar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarProducto();
            }
        });
    }

    private void eliminarProducto() {
        int opcion = eliminarProducto.mostrarMensaje("Desea eliminar este producto?");

        if (opcion == JOptionPane.YES_OPTION) {
            int codigo = Integer.parseInt(eliminarProducto.getTxtCodigo().getText());
            productoDAO.eliminar(codigo);
            eliminarProducto.limpiarCampos();
            JOptionPane.showMessageDialog(null, "Producto Eliminado");
        }
    }

    public void eventoListarProductos() {
        listaProducto.getBtnListar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarProductos();
            }
        });
    }

    private void listarProductos() {
        String tipoOrden = (String) listaProducto.getCmbTipo().getSelectedItem();
        List<Producto> productos = productoDAO.listarTodos();

        if (tipoOrden != null) {
            if (tipoOrden.equalsIgnoreCase("Código")) {
                productos.sort(Comparator.comparingInt(Producto::getCodigo));
            } else if (tipoOrden.equalsIgnoreCase("Nombre")) {
                productos.sort(Comparator.comparing(Producto::getNombre, String.CASE_INSENSITIVE_ORDER));
            }
        }

        listaProducto.cargarProductos(productos);
    }

}
