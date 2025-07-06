package ec.edu.ups.modelo;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

public class Carrito {
    private Usuario usuario;
    private int codigo;
    private GregorianCalendar fecha;
    private List<ItemCarrito> items;
    private final double IVA = 0.12;

    public Carrito(){
        this.items = new ArrayList<>();
    }


    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public GregorianCalendar getFecha() {
        return fecha;
    }

    public void setFecha(GregorianCalendar fecha) {
        this.fecha = fecha;
    }


    public List<ItemCarrito> getItems() {
        return items;
    }

    public void setItems(List<ItemCarrito> items) {
        this.items = items;
    }



    public boolean contieneProducto(int codigoProducto) {
        return items.stream().anyMatch(item -> item.getProducto().getCodigo() == codigoProducto);
    }

    public void agregarProducto(Producto producto, int cantidad) {
        // Verificar si el producto ya existe
        for (ItemCarrito item : items) {
            if (item.getProducto().getCodigo() == producto.getCodigo()) {
                // Si existe, actualizar la cantidad
                item.setCantidad(item.getCantidad() + cantidad);
                return;
            }
        }
        // Si no existe, agregar nuevo item
        items.add(new ItemCarrito(producto, cantidad));
    }

    public ItemCarrito obtenerItemPorCodigo(int codigoProducto) {
        return items.stream()
                .filter(item -> item.getProducto().getCodigo() == codigoProducto)
                .findFirst()
                .orElse(null);
    }

    public void eliminarProducto(int codigoProducto) {
        Iterator<ItemCarrito> it = items.iterator();
        while (it.hasNext()) {
            if (it.next().getProducto().getCodigo() == codigoProducto) {
                it.remove();
                break;
            }
        }
    }

    public void actualizarProducto(ItemCarrito item) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getProducto().getCodigo() == item.getProducto().getCodigo()) {
                // Actualiza la cantidad del item encontrado
                items.get(i).setCantidad(item.getCantidad());
                break;
            }
        }
    }

    public void vaciarCarrito() {
        items.clear();
    }

    public List<ItemCarrito> obtenerItems() {
        return items;
    }


    public boolean estaVacio() {
        return items.isEmpty();
    }

    public boolean esValido() {
        return !estaVacio() && usuario != null;
    }

    public boolean perteneceAUsuario(Usuario usuario) {
        return this.usuario != null && this.usuario.equals(usuario);
    }

    public double calcularSubtotal() {
        double subtotal = 0;
        for (ItemCarrito item : items) {
            subtotal += item.getProducto().getPrecio() * item.getCantidad();
        }
        return subtotal;
    }

    public double calcularIVA() {
        double subtotal = calcularSubtotal();
        return subtotal * IVA;
    }


    public double calcularTotal() {
        return calcularSubtotal() + calcularIVA();
    }


}

