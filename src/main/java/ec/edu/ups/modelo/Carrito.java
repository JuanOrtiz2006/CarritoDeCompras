package ec.edu.ups.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

/**
 * Clase que representa un carrito de compras.
 * Permite agregar, eliminar productos y calcular el total.
 *
 * @author JuanOrtiz2006
 * @version 1.0
 */
public class Carrito implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Atributo que representa al usuario propietario del carrito.
     */
    private Usuario usuario;
    /**
     * Atributo que representa el código del carrito.
     */
    private int codigo;
    /**
     * Atributo que representa la fecha de creación del carrito.
     */
    private GregorianCalendar fecha;
    /**
     * Lista de productos en el carrito.
     */
    private List<ItemCarrito> items;
    /**
     * Tasa de IVA aplicable a los productos del carrito.
     */
    private final double IVA = 0.12;

    /**
     * Constructor que inicializa el carrito vacío.
     */
    public Carrito(){
        this.items = new ArrayList<>();
    }

    /**
     * Obtiene el usuario propietario del carrito.
     *
     * @return Usuario propietario del carrito.
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Establece el usuario propietario del carrito.
     *
     * @param usuario Usuario a establecer como propietario del carrito.
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Obtiene el código del carrito.
     *
     * @return Código del carrito.
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Establece el código del carrito.
     *
     * @param codigo Código a establecer al carrito.
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtiene la fecha de creación del carrito.
     *
     * @return Fecha de creación del carrito.
     */
    public GregorianCalendar getFecha() {
        return fecha;
    }

    /**
     * Establece la fecha de creación del carrito.
     *
     * @param fecha Fecha a establecer como fecha de creación del carrito.
     */
    public void setFecha(GregorianCalendar fecha) {
        this.fecha = fecha;
    }

    /**
     * Obtiene la lista de items (productos) en el carrito.
     *
     * @return Lista de items en el carrito.
     */
    public List<ItemCarrito> getItems() {
        return items;
    }

    /**
     * Establece la lista de items (productos) en el carrito.
     *
     * @param items Lista de items a establecer en el carrito.
     */
    public void setItems(List<ItemCarrito> items) {
        this.items = items;
    }

    /**
     * Verifica si el carrito contiene un producto específico.
     *
     * @param codigoProducto Código del producto a buscar en el carrito.
     * @return Verdadero si el producto está en el carrito, falso en caso contrario.
     */
    public boolean contieneProducto(int codigoProducto) {
        return items.stream().anyMatch(item -> item.getProducto().getCodigo()==codigoProducto);
    }

    /**
     * Agrega un producto al carrito o actualiza su cantidad si ya existe.
     *
     * @param producto Producto a agregar al carrito.
     * @param cantidad Cantidad del producto a agregar.
     */
    public void agregarProducto(Producto producto, int cantidad) {
        // Verificar si el producto ya existe
        for (ItemCarrito item : items) {
            if (item.getProducto().getCodigo()==producto.getCodigo()) {
                // Si existe, actualizar la cantidad
                item.setCantidad(item.getCantidad() + cantidad);
                return;
            }
        }
        // Si no existe, agregar nuevo item
        items.add(new ItemCarrito(producto, cantidad));
    }

    /**
     * Elimina un producto del carrito.
     *
     * @param codigoProducto Código del producto a eliminar del carrito.
     */
    public void eliminarProducto(int codigoProducto) {
        Iterator<ItemCarrito> it = items.iterator();
        while (it.hasNext()) {
            if (it.next().getProducto().getCodigo()==codigoProducto) {
                it.remove();
                break;
            }
        }
    }

    /**
     * Actualiza la cantidad de un producto en el carrito.
     *
     * @param item ItemCarrito con el producto y la nueva cantidad a actualizar.
     */
    public void actualizarProducto(ItemCarrito item) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getProducto().getCodigo()==item.getProducto().getCodigo()) {
                // Actualiza la cantidad del item encontrado
                items.get(i).setCantidad(item.getCantidad());
                break;
            }
        }
    }

    /**
     * Vacía el carrito, eliminando todos los productos.
     */
    public void vaciarCarrito() {
        items.clear();
    }

    /**
     * Obtiene la lista de items en el carrito.
     *
     * @return Lista de items en el carrito.
     */
    public List<ItemCarrito> obtenerItems() {
        return items;
    }

    /**
     * Verifica si el carrito está vacío.
     *
     * @return Verdadero si el carrito está vacío, falso en caso contrario.
     */
    public boolean estaVacio() {
        return items.isEmpty();
    }

    /**
     * Verifica si el carrito es válido (no está vacío y tiene un usuario asociado).
     *
     * @return Verdadero si el carrito es válido, falso en caso contrario.
     */
    public boolean esValido() {
        return !estaVacio() && usuario != null;
    }

    /**
     * Verifica si el carrito pertenece a un usuario específico.
     *
     * @param usuario Usuario a verificar.
     * @return Verdadero si el carrito pertenece al usuario, falso en caso contrario.
     */
    public boolean perteneceAUsuario(Usuario usuario) {
        return this.usuario != null && this.usuario.equals(usuario);
    }

    /**
     * Calcula el subtotal de los productos en el carrito (sin IVA).
     *
     * @return Subtotal de los productos en el carrito.
     */
    public double calcularSubtotal() {
        double subtotal = 0;
        for (ItemCarrito item : items) {
            subtotal += item.getProducto().getPrecio() * item.getCantidad();
        }
        return subtotal;
    }

    /**
     * Calcula el IVA a aplicar sobre el subtotal de los productos en el carrito.
     *
     * @return IVA a aplicar.
     */
    public double calcularIVA() {
        double subtotal = calcularSubtotal();
        return subtotal * IVA;
    }

    /**
     * Calcula el total a pagar por los productos en el carrito (subtotal más IVA).
     *
     * @return Total a pagar.
     */
    public double calcularTotal() {
        return calcularSubtotal() + calcularIVA();
    }
}
