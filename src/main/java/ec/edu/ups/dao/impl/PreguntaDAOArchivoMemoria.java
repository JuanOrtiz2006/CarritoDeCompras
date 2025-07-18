package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.PreguntaDAO;
import ec.edu.ups.modelo.*;
import ec.edu.ups.util.Contexto;

import java.io.*;
import java.util.*;

/**
 * Implementación de PreguntaDAO que almacena las preguntas en un archivo de texto.
 * Permite persistencia y recuperación de preguntas de seguridad.
 *
 * @author JuanOrtiz2006
 * @version 1.0
 */
public class PreguntaDAOArchivoMemoria implements PreguntaDAO {
    List<Pregunta> preguntas;
    private String rutaArchivo;

    public PreguntaDAOArchivoMemoria(String rutaCarpeta){
        this.rutaArchivo = rutaCarpeta + File.separator + "preguntas.txt";

        File archivo = new File(rutaArchivo);
        try {
            if (!archivo.exists()) {
                archivo.getParentFile().mkdirs(); // crea carpetas si no existen
                archivo.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("Error creando archivo de preguntas: " + e.getMessage());
        }

        this.preguntas = new ArrayList<>();
        agregarPreguntasPorDefecto();

        List<Pregunta> leidas = lecturaPreguntas();
        if (!leidas.isEmpty()) {
            this.preguntas = leidas;
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
        escribirPreguntasEnArchivo(preguntas);
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

    private List<Pregunta> lecturaPreguntas() {
        List<Pregunta> preguntasLeidas = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;

            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                String pregunta = partes[0];
                TipoPregunta tipo = TipoPregunta.valueOf(partes[1]);
                preguntasLeidas.add(new Pregunta(pregunta,tipo));

            }
        } catch (IOException e) {
            System.out.println("Error leyendo archivo: " + e.getMessage());
        }

        return preguntasLeidas;
    }

    private void escribirPreguntasEnArchivo(List<Pregunta> preguntas) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))) {
            for (Pregunta p : preguntas) {
                StringBuilder sb = new StringBuilder();
                sb.append(p.getTexto()).append(",");
                sb.append(p.getTipo()).append(",");

                writer.write(sb.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error escribiendo preguntas: " + e.getMessage());
        }
    }
}
