package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.modelo.*;

import java.io.*;
import java.util.*;

/**
 * Implementación de CarritoDAO que almacena los carritos en un archivo de texto.
 * Permite persistencia y recuperación de carritos por usuario.
 *
 * @author JuanOrtiz2006
 * @version 1.0
 */
public class CarritoDAOArchivoMemoria implements CarritoDAO {

    private List<Carrito> carritos;
    private String ruta;

    public CarritoDAOArchivoMemoria(String carpeta) {
        this.ruta = carpeta + File.separator + "carritos.txt";
        this.carritos =  new ArrayList<>();

        List<Carrito> leidas = leerDesdeArchivo();
        if (!leidas.isEmpty()) {
            this.carritos = leidas;
        }

        this.carritos = leerDesdeArchivo();

    }

    @Override
    public void crear(Carrito carrito) {
        carritos.add(carrito);
        guardarEnArchivo();
    }

    @Override
    public Carrito buscarPorCodigo(int codigo) {
        for (Carrito carrito : carritos) {
            if (carrito.getCodigo() == codigo) {
                return carrito;
            }
        }
        return null;
    }

    @Override
    public void actualizar(Carrito carrito) {
        for (int i = 0; i < carritos.size(); i++) {
            if (carritos.get(i).getCodigo() == carrito.getCodigo()) {
                carritos.set(i, carrito);
                break;
            }
        }
        guardarEnArchivo();
    }

    @Override
    public void eliminar(int codigo) {
        carritos.removeIf(c -> c.getCodigo() == codigo);
        guardarEnArchivo();
    }

    @Override
    public List<Carrito> listarCarritos() {
        return carritos;
    }

    @Override
    public int obtenerSiguienteCodigoParaUsuario(Usuario usuario) {
        int max = 0;
        for (Carrito c : listarCarritosPorUsuario(usuario)) {
            if (c.getCodigo() > max) {
                max = c.getCodigo();
            }
        }
        return max + 1;
    }

    @Override
    public List<Carrito> listarCarritosPorUsuario(Usuario usuario) {
        List<Carrito> lista = new ArrayList<>();
        for (Carrito c : carritos) {
            if (c.getUsuario().getUsername().equals(usuario.getUsername())) {
                lista.add(c);
            }
        }
        return lista;
    }

    @Override
    public Carrito buscarPorCodigoYUsuario(int codigo, Usuario usuario) {
        for (Carrito c : listarCarritosPorUsuario(usuario)) {
            if (c.getCodigo() == codigo) {
                return c;
            }
        }
        return null;
    }

    @Override
    public int contarCarritosPorUsuario(Usuario usuario) {
        return listarCarritosPorUsuario(usuario).size();
    }

    @Override
    public boolean existeCarrito(int codigo, Usuario usuario) {
        return buscarPorCodigoYUsuario(codigo, usuario) != null;
    }

    @Override
    public void eliminarPorCodigoYUsuario(int codigo, Usuario usuario) {
        carritos.removeIf(c -> c.getCodigo() == codigo && c.getUsuario().getUsername().equals(usuario.getUsername()));
        guardarEnArchivo();
    }

    // ------------------ Auxiliares ----------------------

    private void guardarEnArchivo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ruta))) {
            for (Carrito carrito : carritos) {
                StringBuilder sb = new StringBuilder();
                sb.append(carrito.getUsuario().getUsername()).append(",");
                sb.append(carrito.getCodigo()).append(",");
                sb.append(convertirCalendarAString(carrito.getFecha()));

                for (ItemCarrito item : carrito.getItems()) {
                    sb.append(",");
                    sb.append(item.getProducto().getCodigo()).append("-").append(item.getCantidad());
                }

                writer.write(sb.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error escribiendo carritos: " + e.getMessage());
        }
    }

    private List<Carrito> leerDesdeArchivo() {
        List<Carrito> lista = new ArrayList<>();
        File archivo = new File(ruta);
        if (!archivo.exists()) return lista;

        try (BufferedReader reader = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length >= 3) {
                    String username = partes[0];
                    int codigo = Integer.parseInt(partes[1]);
                    GregorianCalendar fecha = convertirStringACalendar(partes[2]);

                    Usuario usuario = new Usuario();
                    usuario.setUsername(username);

                    List<ItemCarrito> items = new ArrayList<>();
                    for (int i = 3; i < partes.length; i++) {
                        String[] productoYcantidad = partes[i].split("-");
                        if (productoYcantidad.length == 2) {
                            int productoId = Integer.parseInt(productoYcantidad[0]);
                            int cantidad = Integer.parseInt(productoYcantidad[1]);

                            Producto producto = new Producto();
                            producto.setCodigo(productoId); // Solo se setea el ID

                            items.add(new ItemCarrito(producto, cantidad));
                        }
                    }

                    Carrito carrito = new Carrito();
                    carrito.setUsuario(usuario);
                    carrito.setCodigo(codigo);
                    carrito.setFecha(fecha);
                    carrito.setItems(items);

                    lista.add(carrito);
                }
            }
        } catch (IOException e) {
            System.out.println("Error leyendo carritos: " + e.getMessage());
        }

        return lista;
    }

    private GregorianCalendar convertirStringACalendar(String fechaStr) {
        String[] partes = fechaStr.split("-");
        int anio = Integer.parseInt(partes[0]);
        int mes = Integer.parseInt(partes[1]) - 1;
        int dia = Integer.parseInt(partes[2]);
        return new GregorianCalendar(anio, mes, dia);
    }

    private String convertirCalendarAString(GregorianCalendar calendar) {
        int anio = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH) + 1;
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        return String.format("%04d-%02d-%02d", anio, mes, dia);
    }
}