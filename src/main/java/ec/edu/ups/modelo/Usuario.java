package ec.edu.ups.modelo;

import java.util.*;

public class Usuario {
    private String nombre;
    private GregorianCalendar fechanacimiento;
    private String correo;
    private String telefono;
    private String username;
    private String password;
    private Rol rol;
    private List<Respuesta> respuestas;


    public Usuario(){
    }

    public Usuario(String username, String password, Rol rol) {
        this.username = username;
        this.password = password;
        this.rol = rol;
        this.respuestas = new ArrayList<>();
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public GregorianCalendar getFechanacimiento() { return fechanacimiento; }
    public void setFechanacimiento(GregorianCalendar fechanacimiento) { this.fechanacimiento = fechanacimiento; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public List<Respuesta> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(List<Respuesta> respuestas) {
        this.respuestas = respuestas;
    }


    public boolean verificarRespuesta(String respuestaV) {
        for(Respuesta respuesta: respuestas){
            if(respuesta.getRespuesta().equals(respuestaV)){
                return true;
            }
        }
        return false;
    }

    public Pregunta obtenerPreguntaParaRecuperacion() {
        if (respuestas != null && !respuestas.isEmpty()) {
            Random random = new Random();
            int indice = random.nextInt(respuestas.size());
            return respuestas.get(indice).getPregunta();
        }
        return null;
    }

    public void cambiarPassword(String nuevaPassword) {
        if (nuevaPassword != null && !nuevaPassword.trim().isEmpty()) {
            this.password = nuevaPassword.trim();
        }
    }
}

