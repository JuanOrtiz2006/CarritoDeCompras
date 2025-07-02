package ec.edu.ups.modelo;

import java.util.GregorianCalendar;

public class Usuario {
    private String nombre;
    private GregorianCalendar fechanacimiento;
    private String correo;
    private String telefono;
    private String username;
    private String password;
    private Rol rol;


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
}
