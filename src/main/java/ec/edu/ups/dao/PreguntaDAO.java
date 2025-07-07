package ec.edu.ups.dao;

import ec.edu.ups.modelo.Pregunta;

public interface PreguntaDAO {
    void crear(Pregunta pregunta);

    String[] obtenerPreguntas();
    Pregunta obtenerPregunta(String preguntaB);
    String[] obtenerTipos();
}
