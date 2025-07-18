package ec.edu.ups.util;

import java.util.Locale;

/**
 * Clase utilitaria para gestionar el contexto de internacionalización de la aplicación.
 * Permite inicializar y obtener el manejador de mensajes internacionalizados y el locale actual.
 *
 * @author JuanOrtiz2006
 * @version 1.0
 */
public class Contexto {
    /**
     * Manejador de mensajes internacionalizados.
     */
    private static MensajeInternacionalizacionHandler handler;

    /**
     * Inicializa el idioma y país para la internacionalización.
     *
     * @param lenguaje Código de lenguaje (ej: "es", "en").
     * @param pais     Código de país (ej: "EC", "US").
     */
    public static void iniciarIdioma(String lenguaje, String pais) {
        handler = new MensajeInternacionalizacionHandler(lenguaje, pais);
    }

    /**
     * Obtiene el manejador de mensajes internacionalizados.
     *
     * @return Instancia de MensajeInternacionalizacionHandler.
     */
    public static MensajeInternacionalizacionHandler getHandler() {
        return handler;
    }

    /**
     * Obtiene el locale actual configurado.
     *
     * @return Locale actual.
     */
    public static Locale getLocale() {
        return handler.getLocale();
    }
}
