package ec.edu.ups.dao;

import ec.edu.ups.modelo.Pregunta;
import ec.edu.ups.modelo.TipoPregunta;
import java.util.List;


public interface PreguntaDAO {
    void crear(Pregunta pregunta);
    List<Pregunta> listarTodos();
    String[] obtenerPreguntas();
    Pregunta obtenerPregunta(String preguntaB);
    String[] obtenerTipos();
}
