package ec.edu.ups.modelo;

public class Pregunta {
    private String texto; // El texto real de la pregunta
    private TipoPregunta tipo;

    public Pregunta(String texto, TipoPregunta tipo) {
        this.texto = texto;
        this.tipo = tipo;
    }

    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }

    public TipoPregunta getTipo() { return tipo; }
    public void setTipo(TipoPregunta tipo) { this.tipo = tipo; }


}
