package ec.edu.ups.util;

import java.util.Locale;

public class Contexto {
    private static MensajeInternacionalizacionHandler handler;

    public static void iniciarIdioma(String lenguaje, String pais) {
        handler = new MensajeInternacionalizacionHandler(lenguaje, pais);
    }

    public static MensajeInternacionalizacionHandler getHandler() {
        return handler;
    }

    public static Locale getLocale() {
        return handler.getLocale();
    }

}
