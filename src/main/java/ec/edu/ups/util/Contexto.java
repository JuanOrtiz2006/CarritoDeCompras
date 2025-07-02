package ec.edu.ups.util;

public class Contexto {
    private static MensajeInternacionalizacionHandler handler;

    public static void iniciarIdioma(String lenguaje, String pais) {
        handler = new MensajeInternacionalizacionHandler(lenguaje, pais);
    }

    public static MensajeInternacionalizacionHandler getHandler() {
        return handler;
    }
}
