package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.PreguntaDAO;
import ec.edu.ups.modelo.Pregunta;
import ec.edu.ups.modelo.TipoPregunta;
import ec.edu.ups.util.Contexto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @Override
    public void crear(Pregunta pregunta) {
         preguntas.add(pregunta);

    }

    @Override
    public List<Pregunta> listarTodos() {
        return preguntas;
    }

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

    @Override
    public Pregunta obtenerPregunta(String preguntaB) {
        for (Pregunta pregunta: preguntas){
            if(pregunta.getTexto().equals(preguntaB)){
                return pregunta;
            }
        }
        return null;
    }

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
