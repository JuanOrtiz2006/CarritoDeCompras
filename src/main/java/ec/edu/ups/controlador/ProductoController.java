package ec.edu.ups.controlador;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.vista.CrearProductoView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProductoController {

    private final CrearProductoView crearProductoView;
    private final ProductoDAO productoDAO;

    public ProductoController(ProductoDAO productoDAO, CrearProductoView crearProductoView) {
        this.productoDAO = productoDAO;
        this.crearProductoView = crearProductoView;
        configurarEventos();
    }

    private void configurarEventos() {
        crearProductoView.getBtnAceptar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarProducto();
            }
        });
    }

    private void guardarProducto() {
        int codigo = Integer.parseInt(crearProductoView.getTxtCodigo().getText());
        String nombre = crearProductoView.getTxtNombre().getText();
        double precio = Double.parseDouble(crearProductoView.getTxtPrecio().getText());

        productoDAO.crear(new Producto(codigo, nombre, precio));
        crearProductoView.mostrarMensaje("Producto guardado correctamente");
        crearProductoView.limpiarCampos();
        crearProductoView.mostrarProductos(productoDAO.listarTodos());
    }

}
