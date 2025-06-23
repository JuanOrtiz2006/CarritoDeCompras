package ec.edu.ups.modelo;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

public class Carrito {
    private int codigo;
    private GregorianCalendar fecha;
    private List<ItemCarrito> items;
    private final double IVA = 0.12;
    private static int contador = 1;

    public Carrito(){
        this.codigo = contador++;

        this.items = new ArrayList<>();
    }
    public Carrito(int codigo, GregorianCalendar fecha) {
        this.codigo = contador++;
        this.fecha=fecha;
        this.items = new ArrayList<>();
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

    public List<ItemCarrito> obtenerItems() {
        return items;
    }

    public boolean estaVacio() {
        return items.isEmpty();
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

