package ec.edu.ups.modelo;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

public class Carrito {
    private int codigo;
    private GregorianCalendar fecha;
    private List<ItemCarrito> items;

    public Carrito(int codigo, GregorianCalendar fecha) {
        this.codigo = codigo;
        this.fecha=fecha;
        items = new ArrayList<>();
    }

    public int getCodigo() {
        return codigo;
    }

    public GregorianCalendar getFecha() {
        return fecha;
    }

    public List<ItemCarrito> getItems() {
        return items;
    }

    public void setFecha(GregorianCalendar fecha) {
        this.fecha = fecha;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setItems(List<ItemCarrito> items) {
        this.items = items;
    }

    public void agregarProducto(Producto producto, int cantidad) {
        items.add(new ItemCarrito(producto, cantidad));
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

    public void vaciarCarrito() {
        items.clear();
    }

    public double calcularTotal() {
        double total = 0;
        for (ItemCarrito item : items) {
            total += item.getProducto().getPrecio() * item.getCantidad();
        }
        return total;
    }

    public List<ItemCarrito> obtenerItems() {
        return items;
    }

    public boolean estaVacio() {
        return items.isEmpty();
    }
}

