package ec.edu.ups.dao.impl;

import java.io.*;
import java.util.*;
import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.*;

/**
 * Implementación de UsuarioDAO que almacena los usuarios en un archivo de texto.
 * Permite persistencia y recuperación de usuarios.
 *
 * @author JuanOrtiz2006
 * @version 1.0
 */
public class UsuarioDAOArchivoMemoria implements UsuarioDAO {

    private List<Usuario> usuarios;
    private String rutaArchivo;

    public UsuarioDAOArchivoMemoria(String rutaCarpeta) {
        this.rutaArchivo = rutaCarpeta + File.separator + "usuarios.txt";

        File archivo = new File(rutaArchivo);
        try {
            if (!archivo.exists()) {
                archivo.getParentFile().mkdirs(); // crea carpetas si no existen
                archivo.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("Error creando archivo de usuarios: " + e.getMessage());
        }

        this.usuarios = new ArrayList<>();
        List<Usuario> leidas = lecturaUsuarios();
        if (!leidas.isEmpty()) {
            this.usuarios = leidas;
        } else {
            crear(new Usuario("0103527966","Jp1034506_Ot        ",Rol.ADMINISTRADOR));
        }
    }

    @Override
    public Usuario autenticar(String username, String password) {
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().equals(username) && usuario.getPassword().equals(password)) {
                return usuario;
            }
        }
        return null;
    }

    @Override
    public void crear(Usuario usuario) {
        usuarios.add(usuario);
        escribirUsuariosEnArchivo(usuarios);
    }

    @Override
    public Usuario buscarPorUsername(String username) {
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().equals(username)) {
                return usuario;
            }
        }
        return null;
    }

    @Override
    public void eliminar(String username) {
        Iterator<Usuario> iterator = usuarios.iterator();
        while (iterator.hasNext()) {
            Usuario usuario = iterator.next();
            if (usuario.getUsername().equals(username)) {
                iterator.remove();
                break;
            }
        }
        escribirUsuariosEnArchivo(usuarios);
    }

    @Override
    public void actualizar(Usuario usuario) {
        for (int i = 0; i < usuarios.size(); i++) {
            Usuario usuarioAux = usuarios.get(i);
            if (usuarioAux.getUsername().equals(usuario.getUsername())) {
                usuarios.set(i, usuario);
                break;
            }
        }
        escribirUsuariosEnArchivo(usuarios);
    }

    @Override
    public List<Usuario> listarTodos() {
        return usuarios;
    }

    @Override
    public List<Usuario> listarPorRol(String rol) {
        List<Usuario> usuariosRol = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            if (usuario.getRol().toString().equalsIgnoreCase(rol)) {
                usuariosRol.add(usuario);
            }
        }
        return usuariosRol;
    }

    // Metodos Auxiliares

    private List<Usuario> lecturaUsuarios() {
        List<Usuario> usuariosLeidos = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;

            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");

                if (partes.length >= 7) {
                    String cedula = partes[0];
                    String nombre = partes[1];
                    GregorianCalendar fechaNacimiento = convertirStringACalendar(partes[2]);
                    String correo = partes[3];
                    String telefono = partes[4];
                    String password = partes[5];
                    Rol rol = Rol.valueOf(partes[6]);

                    List<Respuesta> respuestas = new ArrayList<>();
                    for (int i = 7; i + 2 < partes.length; i += 3) {
                        try {
                            TipoPregunta tipo = TipoPregunta.valueOf(partes[i]);
                            String preguntaTexto = partes[i + 1];
                            String respuestaTexto = partes[i + 2];

                            Pregunta pregunta = new Pregunta(preguntaTexto, tipo);
                            Respuesta respuesta = new Respuesta(pregunta, respuestaTexto);
                            respuestas.add(respuesta);
                        } catch (Exception e) {
                            System.out.println("Error procesando respuesta: " + e.getMessage());
                        }
                    }

                    Usuario usuario = new Usuario();
                    usuario.setUsername(cedula);
                    usuario.setNombre(nombre);
                    usuario.setFechanacimiento(fechaNacimiento);
                    usuario.setCorreo(correo);
                    usuario.setTelefono(telefono);
                    usuario.setPassword(password);
                    usuario.setRol(rol);
                    usuario.setRespuestas(respuestas);

                    usuariosLeidos.add(usuario);
                }
            }
        } catch (IOException e) {
            System.out.println("Error leyendo archivo: " + e.getMessage());
        }

        return usuariosLeidos;
    }

    private void escribirUsuariosEnArchivo(List<Usuario> usuarios) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))) {
            for (Usuario u : usuarios) {
                StringBuilder sb = new StringBuilder();
                sb.append(u.getUsername()).append(",");
                sb.append(u.getNombre()).append(",");
                sb.append(convertirCalendarAString(u.getFechanacimiento())).append(",");
                sb.append(u.getCorreo()).append(",");
                sb.append(u.getTelefono()).append(",");
                sb.append(u.getPassword()).append(",");
                sb.append(u.getRol().toString());

                for (Respuesta r : u.getRespuestas()) {
                    sb.append(",");
                    sb.append(r.getPregunta().getTipo().toString()).append(",");
                    sb.append(r.getPregunta().getTexto()).append(",");
                    sb.append(r.getRespuesta());
                }

                writer.write(sb.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error escribiendo usuarios: " + e.getMessage());
        }
    }

    private GregorianCalendar convertirStringACalendar(String fechaStr) {
        if (fechaStr == null || fechaStr.trim().isEmpty() || fechaStr.equals("0000-00-00")) {
            return null;  // ← Previene error al leer
        }
        try {
            String[] partes = fechaStr.split("-");
            int anio = Integer.parseInt(partes[0]);
            int mes = Integer.parseInt(partes[1]) - 1;
            int dia = Integer.parseInt(partes[2]);
            return new GregorianCalendar(anio, mes, dia);
        } catch (Exception e) {
            System.out.println("Error al convertir fecha: " + fechaStr + " → " + e.getMessage());
            return null;
        }
    }

    private String convertirCalendarAString(GregorianCalendar calendar) {
        if (calendar == null) return "0000-00-00";  // ← Previene error al guardar
        int anio = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH) + 1;
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        return String.format("%04d-%02d-%02d", anio, mes, dia);
    }}
