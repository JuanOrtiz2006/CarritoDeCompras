package ec.edu.ups.dao;

import ec.edu.ups.modelo.Pregunta;

/**
 * Interfaz para operaciones de acceso a datos de preguntas de seguridad.
 * Define métodos para crear y consultar preguntas y tipos.
 *
 * @author JuanOrtiz2006
 * @version 1.0
 */
public interface PreguntaDAO {
    /**
     * Crea una nueva pregunta.
     * @param pregunta Pregunta a crear.
     */
    void crear(Pregunta pregunta);

    /**
     * Obtiene todas las preguntas registradas.
     * @return Arreglo de textos de preguntas.
     */
    String[] obtenerPreguntas();

    /**
     * Obtiene una pregunta por su texto.
     * @param preguntaB Texto de la pregunta.
     * @return Pregunta encontrada o null.
     */
    Pregunta obtenerPregunta(String preguntaB);

    /**
     * Obtiene los tipos de preguntas disponibles.
     * @return Arreglo de tipos de preguntas.
     */
    String[] obtenerTipos();
}
