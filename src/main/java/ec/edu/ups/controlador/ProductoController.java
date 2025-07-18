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

/**
 * Controlador para la gestión de productos.
 * Administra la creación, búsqueda, actualización, eliminación y listado de productos,
 * así como la interacción con las vistas y el DAO correspondiente.
 *
 * @author JuanOrtiz2006
 * @version 1.0
 */
public class ProductoController {
    private CrearProductoView crearProductoView;
    private BuscarProducto buscarProducto;
    private ActualizarProducto actualizarProducto;
    private EliminarProducto eliminarProducto;
    private ListaProducto listaProducto;
    private final ProductoDAO productoDAO;

    /**
     * Constructor que inicializa el controlador con el DAO de producto.
     *
     * @param productoDAO DAO para productos.
     */
    public ProductoController(ProductoDAO productoDAO) {
        this.productoDAO = productoDAO;
    }

    /**
     * Asigna la vista para crear productos.
     * @param crearProductoView Vista de creación de productos.
     */
    public void setCrearProductoView(CrearProductoView crearProductoView) {
        this.crearProductoView = crearProductoView;
    }
    /**
     * Asigna la vista para buscar productos.
     * @param buscarProducto Vista de búsqueda de productos.
     */
    public void setBuscarProducto(BuscarProducto buscarProducto) {
        this.buscarProducto = buscarProducto;
    }
    /**
     * Asigna la vista para eliminar productos.
     * @param eliminarProducto Vista de eliminación de productos.
     */
    public void setEliminarProducto(EliminarProducto eliminarProducto) {
        this.eliminarProducto = eliminarProducto;
    }
    /**
     * Asigna la vista para listar productos.
     * @param listaProducto Vista de listado de productos.
     */
    public void setListaProducto(ListaProducto listaProducto) {
        this.listaProducto = listaProducto;
    }
    /**
     * Asigna la vista para actualizar productos.
     * @param actualizarProducto Vista de actualización de productos.
     */
    public void setActualizarProducto(ActualizarProducto actualizarProducto) {
        this.actualizarProducto = actualizarProducto;
    }

    // === Eventos ===

    /**
     * Configura el evento para crear productos desde la vista.
     */
    public void eventoCrearProducto() {
        crearProductoView.getBtnAceptar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearProducto();
            }
        });
    }

    /**
     * Configura el evento para buscar productos desde la vista.
     */
    public void eventoBuscarProducto() {
        buscarProducto.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProducto();
            }
        });
    }

    /**
     * Configura el evento para eliminar productos desde la vista.
     */
    public void eventoEliminarProducto() {
        eliminarProducto.getBtnSeleccionar().addActionListener(e -> {
            var handler = Contexto.getHandler();
            try {
                int codigo = Integer.parseInt(eliminarProducto.getTxtCodigo().getText());
                if (codigo < 0) {
                    eliminarProducto.mostrarMensaje("El código debe ser positivo.");
                    return;
                }
                Producto producto = productoDAO.buscarPorCodigo(codigo);
                if (producto != null) {
                    eliminarProducto.cargarProductoEncontrado(producto.getNombre(), producto.getPrecio());
                } else {
                    eliminarProducto.mostrarMensaje(handler.get("mensaje.producto.noencontrado"));
                }
            } catch (NumberFormatException ex) {
                eliminarProducto.mostrarMensaje(handler.get("mensaje.producto.errorcodigo"));
            } catch (Exception ex) {
                eliminarProducto.mostrarMensaje("Error al buscar el producto: " + ex.getMessage());
            }
        });

        eliminarProducto.getBtnEliminar().addActionListener(e -> eliminarProducto());
    }

    /**
     * Configura el evento para listar productos desde la vista.
     */
    public void eventoListarProductos() {
        listaProducto.getBtnListar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarProductos();
            }
        });
    }

    /**
     * Configura el evento para actualizar productos desde la vista.
     */
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

    // === Métodos de gestión ===

    /**
     * Crea un nuevo producto con los datos ingresados en la vista.
     */
    private void crearProducto() {
        var handler = Contexto.getHandler();

        try {
            String codigoStr = crearProductoView.getTxtCodigo().getText().trim();
            String nombre = crearProductoView.getTxtNombre().getText().trim();
            String precioStr = crearProductoView.getTxtPrecio().getText().trim();

            //Validación de campos vacíos
            if (codigoStr.isEmpty() || nombre.isEmpty() || precioStr.isEmpty()) {
                crearProductoView.mostrarMensaje(handler.get("mensaje.producto.camposvacios"));
                return;
            }

            //Validación de codigo numerico

            int codigo;
            try{
                codigo = Integer.parseInt(codigoStr);
                if(codigo<0){
                    crearProductoView.mostrarMensaje("Codigo invalido.");
                    return;
                }
            } catch (NumberFormatException ex) {
                crearProductoView.mostrarMensaje("El codigo debe ser un número válido.");
                return;
            }

            //Validar duplicados
            Producto productoExistente = productoDAO.buscarPorCodigo(codigo);
            if (productoExistente != null) {
                crearProductoView.mostrarMensaje("Ya existe un producto con este código.");
                return;
            }

            //Validar nombre mínimo
            if (nombre.length() < 3) {
                crearProductoView.mostrarMensaje("El nombre debe tener al menos 3 caracteres.");
                return;
            }

            // Validar precio numérico
            double precio;
            try {
                precio = Double.parseDouble(precioStr);
                if(precio<0){
                    crearProductoView.mostrarMensaje("Precio invalido.");
                    return;
                }
            } catch (NumberFormatException ex) {
                crearProductoView.mostrarMensaje("El precio debe ser un número válido.");
                return;
            }

            // 5. Rellenar campos con espacios si no cumplen el largo máximo
            String nombreRellenado = String.format("%-20s", nombre); // Rellenar a 20 caracteres

            Producto producto = new Producto(codigo, nombreRellenado, precio);
            try {
                productoDAO.crear(producto);
                crearProductoView.limpiarCampos();
                String mensaje = String.format(handler.get("mensaje.producto.creado"), nombre);
                crearProductoView.mostrarMensaje(mensaje);
            } catch (Exception e) {
                crearProductoView.mostrarMensaje("Error al crear el producto: " + e.getMessage());
            }

        } catch (Exception e) {
            crearProductoView.mostrarMensaje(handler.get("mensaje.producto.errorcodigo"));
        }
    }

    /**
     * Busca productos por código o nombre según la selección en la vista.
     */
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
                if (codigo < 0) {
                    buscarProducto.mostrarMensaje("El código debe ser un número positivo.");
                    return;
                }
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
            try {
                List<Producto> productos = productoDAO.buscarPorNombre(valorBuscado);
                if (!productos.isEmpty()) {
                    buscarProducto.cargarProductosListados(productos);
                } else {
                    buscarProducto.limpiarCampos();
                    buscarProducto.mostrarMensaje(handler.get("mensaje.producto.noencontrado"));
                }
            } catch (Exception e) {
                buscarProducto.mostrarMensaje("Error al buscar productos: " + e.getMessage());
            }
        }
    }

    /**
     * Actualiza los datos de un producto existente.
     */
    public void actualizarProducto() {
        var handler = Contexto.getHandler();

        // Validar campos vacíos
        String codigoStr = actualizarProducto.getTxtCodigo().getText().trim();
        String nombre = actualizarProducto.getTxtNombre().getText().trim();
        String precioStr = actualizarProducto.getTxtPrecio().getText().trim();

        if (codigoStr.isEmpty() || nombre.isEmpty() || precioStr.isEmpty()) {
            actualizarProducto.mostrarMensaje(handler.get("mensaje.producto.camposvacios"));
            return;
        }

        // Validar nombre mínimo
        if (nombre.length() < 3) {
            actualizarProducto.mostrarMensaje("El nombre debe tener al menos 3 caracteres.");
            return;
        }

        int opcion = actualizarProducto.mostrarMensaje(handler.get("mensaje.producto.confirmaractualizacion"));

        if (opcion == JOptionPane.YES_OPTION) {
            try {
                int codigo = Integer.parseInt(codigoStr);
                double precio = Double.parseDouble(precioStr);

                // Validar precio positivo
                if (precio < 0) {
                    actualizarProducto.mostrarMensaje("El precio debe ser positivo.");
                    return;
                }

                // Verificar que el producto existe
                Producto productoExistente = productoDAO.buscarPorCodigo(codigo);
                if (productoExistente == null) {
                    actualizarProducto.mostrarMensaje(handler.get("mensaje.producto.noencontrado"));
                    return;
                }
                String nombreRellenado = String.format("%-20s", nombre);
                productoDAO.actualizar(new Producto(codigo, nombreRellenado, precio));
                actualizarProducto.limpiarCampos();
                JOptionPane.showMessageDialog(null, handler.get("mensaje.producto.actualizado"));

            } catch (NumberFormatException e) {
                actualizarProducto.mostrarMensaje(handler.get("mensaje.producto.errorcodigo"));
            } catch (Exception e) {
                actualizarProducto.mostrarMensaje("Error al actualizar el producto: " + e.getMessage());
            }
        }
    }

    /**
     * Elimina un producto por código.
     */
    private void eliminarProducto() {
        var handler = Contexto.getHandler();

        try {
            int codigo = Integer.parseInt(eliminarProducto.getTxtCodigo().getText());

            // Verificar que el producto existe
            Producto producto = productoDAO.buscarPorCodigo(codigo);
            if (producto == null) {
                eliminarProducto.mostrarMensaje(handler.get("mensaje.producto.noencontrado"));
                return;
            }

            int opcion = eliminarProducto.mostrarMensaje(handler.get("mensaje.producto.confirmareliminar"));
            if (opcion == JOptionPane.YES_OPTION) {
                productoDAO.eliminar(codigo);
                eliminarProducto.limpiarCampos();
                JOptionPane.showMessageDialog(null, handler.get("mensaje.producto.eliminado"));
            }
        } catch (NumberFormatException e) {
            eliminarProducto.mostrarMensaje(handler.get("mensaje.producto.errorcodigo"));
        } catch (Exception e) {
            eliminarProducto.mostrarMensaje("Error al eliminar el producto: " + e.getMessage());
        }
    }

    /**
     * Lista los productos y los muestra en la vista.
     */
    private void listarProductos() {
        var handler = Contexto.getHandler();

        try {
            List<Producto> productos = new ArrayList<>(productoDAO.listarTodos());

            if (productos.isEmpty()) {
                listaProducto.mostrarMensaje("No hay productos registrados.");
                return;
            }

            String tipoOrden = (String) listaProducto.getCmbTipo().getSelectedItem();

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

        } catch (Exception e) {
            listaProducto.mostrarMensaje("Error al cargar la lista de productos: " + e.getMessage());
        }
    }

}
