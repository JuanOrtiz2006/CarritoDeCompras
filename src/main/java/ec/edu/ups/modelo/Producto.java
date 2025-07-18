package ec.edu.ups.modelo;

/**
 * Clase que representa un producto en el sistema.
 * Contiene información básica como código, nombre y precio.
 *
 * @author JuanOrtiz2006
 * @version 1.0
 */
public class Producto {
    /**
     * Código único del producto.
     */
    private int codigo;
    /**
     * Nombre del producto.
     */
    private String nombre;
    /**
     * Precio del producto.
     */
    private double precio;

    /**
     * Constructor por defecto.
     * Inicializa un producto sin valores.
     */
    public Producto(){

    }
    /**
     * Constructor para crear un producto con código, nombre y precio.
     *
     * @param codigo Código único del producto.
     * @param nombre Nombre del producto.
     * @param precio Precio del producto.
     */
    public Producto(int codigo, String nombre, double precio) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
    }

    /**
     * Obtiene el código del producto.
     *
     * @return Código del producto.
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Establece el código del producto.
     *
     * @param codigo Código a establecer.
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtiene el nombre del producto.
     *
     * @return Nombre del producto.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del producto.
     *
     * @param nombre Nombre a establecer.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el precio del producto.
     *
     * @return Precio del producto.
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Establece el precio del producto.
     *
     * @param precio Precio a establecer.
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

}