package ec.edu.ups.controlador;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.modelo.Carrito;

import ec.edu.ups.modelo.Producto;
import ec.edu.ups.vista.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class CarritoController {

    private CrearCarrito crearCarrito;
    private final CarritoDAO carritoDAO;

    public CarritoController(CarritoDAO carritoDAO){
        this.carritoDAO = carritoDAO;
    }

    public CrearCarrito getCrearCarrito() {
        return crearCarrito;
    }

    public CarritoDAO getCarritoDAO() {
        return carritoDAO;
    }

    public void setCrearCarrito(CrearCarrito crearCarrito) {
        this.crearCarrito = crearCarrito;
    }

    public void eventoCrearCarrito(){
        crearCarrito.getBtnGuardar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearCarrito();
            }
        });
    }

    private void crearCarrito(){
        Carrito carrito = crearCarrito.obtenerCarrito();

        if (carrito.estaVacio()) {
            crearCarrito.mostrarMensaje("El carrito está vacío. No se puede guardar.");
            return;
        }

        carritoDAO.crear(carrito);
        crearCarrito.mostrarMensaje("Carrito guardado correctamente con código " + carrito.getCodigo());
        crearCarrito.limpiarFormulario();
    }




}
