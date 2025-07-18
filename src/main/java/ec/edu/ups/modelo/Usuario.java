package ec.edu.ups.modelo;

import java.io.Serializable;
import java.util.*;

/**
 * Clase que representa un usuario del sistema.
 * Contiene información básica como nombre de usuario y contraseña.
 *
 * @author JuanOrtiz2006
 * @version 1.0
 */
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Nombre de usuario.
     */
    private String nombre;
    /**
     * Contraseña del usuario.
     */
    private String password;
    private Rol rol;
    private List<Respuesta> respuestas;
    private String correo;
    private String telefono;
    private GregorianCalendar fechanacimiento;

    /**
     * Constructor por defecto.
     */
    public Usuario(){
    }

    /**
     * Constructor para crear un usuario con nombre y contraseña.
     *
     * @param username Nombre de usuario.
     * @param password Contraseña del usuario.
     * @param rol Rol del usuario.
     */
    public Usuario(String username, String password, Rol rol) {
        this.nombre = username;
        this.password = password;
        this.rol = rol;
        this.respuestas = new ArrayList<>();
    }

    /**
     * Obtiene el nombre de usuario.
     *
     * @return Nombre de usuario.
     */
    public String getUsername() { return nombre; }

    /**
     * Establece el nombre de usuario.
     *
     * @param username Nombre de usuario a establecer.
     */
    public void setUsername(String username) { this.nombre = username; }

    /**
     * Obtiene la contraseña del usuario.
     *
     * @return Contraseña.
     */
    public String getPassword() { return password; }

    /**
     * Establece la contraseña del usuario.
     *
     * @param password Contraseña a establecer.
     */
    public void setPassword(String password) { this.password = password; }

    /**
     * Obtiene el rol del usuario.
     *
     * @return Rol del usuario.
     */
    public Rol getRol() { return rol; }

    /**
     * Establece el rol del usuario.
     *
     * @param rol Rol a establecer.
     */
    public void setRol(Rol rol) { this.rol = rol; }

    /**
     * Obtiene el correo electrónico del usuario.
     *
     * @return Correo electrónico.
     */
    public String getCorreo() { return correo; }

    /**
     * Establece el correo electrónico del usuario.
     *
     * @param correo Correo electrónico a establecer.
     */
    public void setCorreo(String correo) { this.correo = correo; }

    /**
     * Obtiene el número de teléfono del usuario.
     *
     * @return Número de teléfono.
     */
    public String getTelefono() { return telefono; }

    /**
     * Obtiene el nombre del usuario.
     *
     * @return Nombre del usuario.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el número de teléfono del usuario.
     *
     * @param telefono Número de teléfono a establecer.
     */
    public void setTelefono(String telefono) { this.telefono = telefono; }

    /**
     * Obtiene la fecha de nacimiento del usuario.
     *
     * @return Fecha de nacimiento.
     */
    public GregorianCalendar getFechanacimiento() { return fechanacimiento; }

    /**
     * Establece la fecha de nacimiento del usuario.
     *
     * @param fechanacimiento Fecha de nacimiento a establecer.
     */
    public void setFechanacimiento(GregorianCalendar fechanacimiento) { this.fechanacimiento = fechanacimiento; }

    /**
     * Obtiene la lista de respuestas del usuario.
     *
     * @return Lista de respuestas.
     */
    public List<Respuesta> getRespuestas() {
        return respuestas;
    }

    /**
     * Establece la lista de respuestas del usuario.
     *
     * @param respuestas Lista de respuestas a establecer.
     */
    public void setRespuestas(List<Respuesta> respuestas) {
        this.respuestas = respuestas;
    }

    /**
     * Obtiene el nombre del usuario.
     *
     * @return Nombre del usuario.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Verifica si una respuesta es válida.
     *
     * @param respuestaV Respuesta a verificar.
     * @return true si la respuesta es válida, false en caso contrario.
     */
    public boolean verificarRespuesta(String respuestaV) {
        for(Respuesta respuesta: respuestas){
            if(respuesta.getRespuesta().equals(respuestaV)){
                return true;
            }
        }
        return false;
    }

    /**
     * Obtiene una pregunta aleatoria para la recuperación de contraseña.
     *
     * @return Pregunta para recuperación de contraseña o null si no hay respuestas registradas.
     */
    public Pregunta obtenerPreguntaParaRecuperacion() {
        if (respuestas != null && !respuestas.isEmpty()) {
            Random random = new Random();
            int indice = random.nextInt(respuestas.size());
            return respuestas.get(indice).getPregunta();
        }
        return null;
    }

    /**
     * Cambia la contraseña del usuario.
     *
     * @param nuevaPassword Nueva contraseña a establecer.
     */
    public void cambiarPassword(String nuevaPassword) {
        if (nuevaPassword != null && !nuevaPassword.trim().isEmpty()) {
            this.password = nuevaPassword.trim();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(nombre, usuario.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre);
    }

    /**
     * Valida una cédula ecuatoriana.
     *
     * @return true si la cédula es válida, false en caso contrario.
     */
    public boolean validarCedulaEcuatoriana() {
        if (nombre == null || !nombre.matches("\\d{10}")) return false;
        int suma = 0;
        for (int i = 0; i < 9; i++) {
            int num = Character.getNumericValue(nombre.charAt(i));
            suma += (i % 2 == 0) ? ((num * 2 > 9) ? num * 2 - 9 : num * 2) : num;
        }
        int digitoVerificador = (10 - (suma % 10)) % 10;
        return digitoVerificador == Character.getNumericValue(nombre.charAt(9));
    }

    /**
     * Valida si una contraseña es segura.
     *
     * @param clave Contraseña a validar.
     * @return true si la contraseña es segura, false en caso contrario.
     */
    public static boolean validarPasswordSegura(String clave) {
        if (clave == null) return false;
        return clave.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[@_\\-.]).{6,}$");
    }

    /**
     * Valida si la contraseña del usuario es segura.
     *
     * @return true si la contraseña es segura, false en caso contrario.
     */
    public boolean validarPasswordSegura() {
        return validarPasswordSegura(this.password);
    }

    /**
     * Valida un correo electrónico.
     *
     * @return true si el correo electrónico es válido, false en caso contrario.
     */
    public boolean validarCorreoElectronico() {
        return correo != null && correo.matches("^[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,}$");
    }

    /**
     * Valida un número de teléfono.
     *
     * @return true si el número de teléfono es válido, false en caso contrario.
     */
    public boolean validarTelefonoNumerico() {
        return telefono != null && telefono.matches("\\d+");
    }

    /**
     * Valida si un usuario es válido.
     *
     * @return true si el usuario es válido, false en caso contrario.
     */
    public boolean esUsuarioValido() {
        return validarCedulaEcuatoriana()
                && validarPasswordSegura()
                && validarCorreoElectronico()
                && validarTelefonoNumerico();
    }

}
