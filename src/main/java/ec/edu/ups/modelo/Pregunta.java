package ec.edu.ups.modelo;

import java.io.Serializable;

/**
 * Clase que representa una pregunta de seguridad para el usuario.
 * Se utiliza para recuperación de contraseña y validaciones.
 *
 * @author JuanOrtiz2006
 * @version 1.0
 */
public class Pregunta implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Texto de la pregunta.
     */
    private String texto;
    /**
     * Tipo de la pregunta.
     */
    private TipoPregunta tipo;

    /**
     * Constructor para crear una pregunta con texto y tipo.
     *
     * @param texto Texto de la pregunta.
     * @param tipo Tipo de la pregunta.
     */
    public Pregunta(String texto, TipoPregunta tipo) {
        this.texto = texto;
        this.tipo = tipo;
    }

    /**
     * Obtiene el texto de la pregunta.
     *
     * @return Texto de la pregunta.
     */
    public String getTexto() { return texto; }

    /**
     * Establece el texto de la pregunta.
     *
     * @param texto Texto a establecer.
     */
    public void setTexto(String texto) { this.texto = texto; }

    /**
     * Obtiene el tipo de la pregunta.
     *
     * @return Tipo de la pregunta.
     */
    public TipoPregunta getTipo() { return tipo; }

    /**
     * Establece el tipo de la pregunta.
     *
     * @param tipo Tipo a establecer.
     */
    public void setTipo(TipoPregunta tipo) { this.tipo = tipo; }

}
