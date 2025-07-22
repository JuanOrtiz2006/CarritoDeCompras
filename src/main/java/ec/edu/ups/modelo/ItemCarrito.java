package ec.edu.ups.modelo;

import java.io.Serializable;

/**
 * Clase que representa un ítem dentro del carrito de compras.
 * Contiene el producto y la cantidad seleccionada.
 *
 * @author JuanOrtiz2006
 * @version 1.0
 */
public class ItemCarrito implements Serializable {
    private static final long serialVersionUID = 1L;

    private int codigo;
    /**
     * Producto asociado al ítem.
     */
    private Producto producto;
    /**
     * Cantidad del producto en el ítem.
     */
    private int cantidad;

    /**
     * Constructor por defecto.
     */
    public ItemCarrito() {
    }

    /**
     * Constructor para crear un ítem con producto y cantidad.
     *
     * @param producto Producto asociado.
     * @param cantidad Cantidad del producto.
     */
    public ItemCarrito(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    /**
     * Establece el producto del ítem.
     *
     * @param producto Producto a establecer.
     */
    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    /**
     * Establece la cantidad del producto.
     *
     * @param cantidad Cantidad a establecer.
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Obtiene el producto del ítem.
     *
     * @return Producto asociado.
     */
    public Producto getProducto() {
        return producto;
    }

    /**
     * Obtiene la cantidad del producto.
     *
     * @return Cantidad del producto.
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Obtiene el código del ítem.
     *
     * @return Código del ítem.
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Calcula el subtotal del ítem (precio * cantidad).
     *
     * @return Subtotal del ítem.
     */
    public double getSubtotal() {
        return producto.getPrecio() * cantidad;
    }

    @Override
    public String toString() {
        return producto.toString() + " x " + cantidad + " = $" + getSubtotal();
    }

}
