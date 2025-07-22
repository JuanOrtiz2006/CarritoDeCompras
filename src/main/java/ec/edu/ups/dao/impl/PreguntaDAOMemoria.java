package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.PreguntaDAO;
import ec.edu.ups.modelo.Pregunta;
import ec.edu.ups.modelo.TipoPregunta;
import ec.edu.ups.util.Contexto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Implementación de PreguntaDAO que almacena las preguntas en memoria.
 * No persiste los datos, útil para pruebas y operaciones temporales.
 *
 * @author JuanOrtiz2006
 * @version 1.0
 */
public class PreguntaDAOMemoria implements PreguntaDAO {
    List<Pregunta> preguntas;
     public PreguntaDAOMemoria(){
         preguntas = new ArrayList<>();
         crear(new Pregunta(Contexto.getHandler().get("lbl.preguntas.nombremascota"), TipoPregunta.PERSONAL));
         crear(new Pregunta(Contexto.getHandler().get("lbl.pregunta.peliculafavorita"), TipoPregunta.PERSONAL));
         crear(new Pregunta(Contexto.getHandler().get("lbl.preguntas.nombremadre"), TipoPregunta.FAMILIAR));
         crear(new Pregunta(Contexto.getHandler().get("lbl.pregunta.mejoramigo"), TipoPregunta.FAMILIAR));
         crear(new Pregunta(Contexto.getHandler().get("lbl.pregunta.comida"), TipoPregunta.HISTORICA));
         crear(new Pregunta(Contexto.getHandler().get("lbl.pregunta.apodoinfancia"), TipoPregunta.HISTORICA));
         crear(new Pregunta(Contexto.getHandler().get("lbl.pregunta.calleinfancia"), TipoPregunta.LUGAR));
         crear(new Pregunta(Contexto.getHandler().get("lbl.pregunta.ciudad"), TipoPregunta.LUGAR));
         crear(new Pregunta(Contexto.getHandler().get("lbl.pregunta.primermaestro"), TipoPregunta.TRABAJO));
         crear(new Pregunta(Contexto.getHandler().get("lbl.pregunta.primertrabajo"), TipoPregunta.TRABAJO));

     }
    /**
     * Crea una nueva pregunta y la agrega a la lista en memoria.
     *
     * @param pregunta La pregunta a ser creada.
     */
    @Override
    public void crear(Pregunta pregunta) {
         preguntas.add(pregunta);

    }

    /**
     * Obtiene todas las preguntas en memoria como un arreglo de Strings.
     *
     * @return Un arreglo de Strings con el texto de las preguntas.
     */
    @Override
    public String[] obtenerPreguntas() {
        int size=preguntas.size();
        String[] preguntasString = new String[size];
        int cont=0;
        for(Pregunta pregunta: preguntas){
            preguntasString[cont]=pregunta.getTexto();
            cont++;
        }
        return preguntasString;
    }

    /**
     * Obtiene una pregunta en base a su texto.
     *
     * @param preguntaB El texto de la pregunta a buscar.
     * @return La pregunta correspondiente al texto, o null si no se encuentra.
     */
    @Override
    public Pregunta obtenerPregunta(String preguntaB) {
        for (Pregunta pregunta: preguntas){
            if(pregunta.getTexto().equals(preguntaB)){
                return pregunta;
            }
        }
        return null;
    }

    /**
     * Obtiene todos los tipos de preguntas únicos en memoria.
     *
     * @return Un arreglo de Strings con los tipos de preguntas.
     */
    @Override
    public String[] obtenerTipos() {
        Set<String> tiposUnicos = new HashSet<>();
        for (Pregunta pregunta : preguntas) {
            if (pregunta != null && pregunta.getTipo() != null) {
                tiposUnicos.add(pregunta.getTipo().toString());
            }
        }
        return tiposUnicos.toArray(new String[0]);
    }



}
