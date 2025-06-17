package ec.edu.ups.controlador;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.vista.ProductoView;
import ec.edu.ups.vista.CrearProductoView;
import ec.edu.ups.vista.BuscarProducto;
import ec.edu.ups.vista.EliminarProducto;
import ec.edu.ups.vista.ListaProducto;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class ProductoController {

    private  CrearProductoView crearProductoView;
    private BuscarProducto buscarProducto;
    private EliminarProducto eliminarProducto;
    private ListaProducto listaProducto;
    private final ProductoDAO productoDAO;

    public ProductoController(ProductoDAO productoDAO) {
        this.productoDAO = productoDAO;
    }

    public CrearProductoView getCrearProductoView() {
        return crearProductoView;
    }

    public BuscarProducto getBuscarProducto() {
        return buscarProducto;
    }

    public EliminarProducto getEliminarProducto() {
        return eliminarProducto;
    }

    public ListaProducto getListaProducto() {
        return listaProducto;
    }

    public ProductoDAO getProductoDAO() {
        return productoDAO;
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


    private void crearProducto() {
        int codigo = Integer.parseInt(crearProductoView.getTxtCodigo().getText());
        String nombre = crearProductoView.getTxtNombre().getText();
        double precio = Double.parseDouble(crearProductoView.getTxtPrecio().getText());

        productoDAO.crear(new Producto(codigo, nombre, precio));
        crearProductoView.limpiarCampos();
        crearProductoView.mostrarMensaje("Producto " + nombre + " guardado correctamente");

    }

    public void buscarProducto(){
        String tipoBusqueda = (String) buscarProducto.getCmbBusqueda().getSelectedItem();
        String valorBuscado = buscarProducto.getTxtBusqueda().getText();

        if (tipoBusqueda == "Codigo"){
            try {
                int codigo = Integer.parseInt(valorBuscado);
                Producto producto = productoDAO.buscarPorCodigo(codigo);
                if (producto != null) {
                    buscarProducto.cargarProducto(producto);
                } else {
                    buscarProducto.eliminarCampos();
                    buscarProducto.mostrarMensaje("Producto no enctontrado");
                }
            } catch (NumberFormatException e){
                buscarProducto.mostrarMensaje("Codigo incorrecto");
            }
        } else if(tipoBusqueda == "Nombre"){
            List<Producto> productos = productoDAO.buscarPorNombre(valorBuscado);
            if (!productos.isEmpty()) {
                buscarProducto.cargarProductos(productos);
            } else {
                buscarProducto.eliminarCampos();
                buscarProducto.mostrarMensaje("No se encontraron productos con ese nombre.");
            }

        }
    }

    public void actualizarProducto(){
        int codigo = Integer.parseInt(crearProductoView.getTxtCodigo().getText());
        String nombre = crearProductoView.getTxtNombre().getText();
        double precio = Double.parseDouble(crearProductoView.getTxtPrecio().getText());

        productoDAO.actualizar(new Producto(codigo,nombre,precio));
        crearProductoView.limpiarCampos();
        crearProductoView.mostrarMensaje("Producto " + nombre + " actualizado correctamente");
    }

    public void eliminarProducto(){
        int codigo = Integer.parseInt(eliminarProducto.getTxtCodigo().getText());

        productoDAO.eliminar(codigo);
        eliminarProducto.limpiarCampos();
        JOptionPane.showMessageDialog(null, "Producto con codigo " + codigo + "eliminado");
    }

    public void listarProductos(){
        List<Producto> productos = productoDAO.listarTodos();
        listaProducto.cargarProductos(productos);
    }

}
