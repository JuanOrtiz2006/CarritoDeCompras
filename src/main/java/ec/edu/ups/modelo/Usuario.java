package ec.edu.ups.modelo;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class Usuario {
    private String nombre;
    private GregorianCalendar fechanacimiento;
    private String correo;
    private String telefono;
    private String username;
    private String password;
    private Rol rol;
    private Map<String,String> informacion = new HashMap<String,String>();


    public Usuario(){
    }

    public Usuario(String username, String password, Rol rol) {
        this.username = username;
        this.password = password;
        this.rol = rol;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Rol getRol() {
        return rol;
    }

    public String getNombre() {
        return nombre;
    }

    public GregorianCalendar getFechanacimiento() {
        return fechanacimiento;
    }

    public String getCorreo() {
        return correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setFechanacimiento(GregorianCalendar fechanacimiento) {
        this.fechanacimiento = fechanacimiento;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Map<String, String> getInformacion() {
        return informacion;
    }

    public void setInformacion(Map<String, String> informacion) {
        this.informacion = informacion;
    }

    public void setRespuestaInformacion(String key, String value){
        informacion.put(key,value);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}
