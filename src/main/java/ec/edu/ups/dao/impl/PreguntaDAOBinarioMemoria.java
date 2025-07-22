package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.PreguntaDAO;
import ec.edu.ups.modelo.Pregunta;
import ec.edu.ups.modelo.TipoPregunta;
import ec.edu.ups.util.Contexto;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Implementación de PreguntaDAO que almacena las preguntas en un archivo binario.
 * Permite persistencia y recuperación de preguntas de seguridad.
 *
 * @author JuanOrtiz2006
 * @version 1.0
 */
public class PreguntaDAOBinarioMemoria implements PreguntaDAO {
    private List<Pregunta> preguntas;
    private String rutaArchivo;

    public PreguntaDAOBinarioMemoria(String rutaCarpeta) {
        this.rutaArchivo = rutaCarpeta + File.separator + "preguntas.dat";
        this.preguntas = leerPreguntasDesdeArchivo();
        if (this.preguntas == null || this.preguntas.isEmpty()) {
            this.preguntas = new ArrayList<>();
            agregarPreguntasPorDefecto();
            escribirPreguntasEnArchivo();
        }
    }

    private void agregarPreguntasPorDefecto() {
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
        escribirPreguntasEnArchivo();
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
        return preguntasString;    }

    @Override
    public Pregunta obtenerPregunta(String preguntaB) {
        for (Pregunta pregunta: preguntas){
            if(pregunta.getTexto().equals(preguntaB)){
                return pregunta;
            }
        }
        return null;    }

    @Override
    public String[] obtenerTipos() {
        Set<String> tiposUnicos = new HashSet<>();
        for (Pregunta pregunta : preguntas) {
            if (pregunta != null && pregunta.getTipo() != null) {
                tiposUnicos.add(pregunta.getTipo().toString());
            }
        }
        return tiposUnicos.toArray(new String[0]);    }

    private void escribirPreguntasEnArchivo() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
            out.writeObject(preguntas);
        } catch (IOException e) {
            System.out.println("Error escribiendo archivo binario: " + e.getMessage());
        }
    }

    private List<Pregunta> leerPreguntasDesdeArchivo() {
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(archivo))) {
            return (List<Pregunta>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error leyendo archivo binario: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
