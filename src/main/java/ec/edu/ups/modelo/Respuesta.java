package ec.edu.ups.modelo;

import java.io.Serializable;

/**
 * Clase que representa una respuesta a una pregunta de seguridad.
 * Relaciona una pregunta con la respuesta dada por el usuario.
 *
 * @author JuanOrtiz2006
 * @version 1.0
 */
public class Respuesta implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Pregunta asociada a la respuesta.
     */
    private Pregunta pregunta;
    /**
     * Respuesta dada por el usuario.
     */
    private String respuesta;

    /**
     * Constructor para crear una respuesta con pregunta y texto de respuesta.
     *
     * @param pregunta Pregunta asociada.
     * @param respuesta Respuesta dada.
     */
    public Respuesta(Pregunta pregunta, String respuesta) {
        this.pregunta = pregunta;
        this.respuesta = respuesta;
    }

    /**
     * Obtiene la pregunta asociada.
     *
     * @return Pregunta asociada.
     */
    public Pregunta getPregunta() {
        return pregunta;
    }

    /**
     * Establece la pregunta asociada.
     *
     * @param pregunta Pregunta a establecer.
     */
    public void setPregunta(Pregunta pregunta) {
        this.pregunta = pregunta;
    }

    /**
     * Obtiene el texto de la respuesta.
     *
     * @return Respuesta dada.
     */
    public String getRespuesta() { return respuesta; }

    /**
     * Establece el texto de la respuesta.
     *
     * @param respuesta Respuesta a establecer.
     */
    public void setRespuesta(String respuesta) { this.respuesta = respuesta; }

}
