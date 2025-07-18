package ec.edu.ups.util;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Manejador para la internacionalización de mensajes.
 * Permite obtener textos traducidos según el locale configurado.
 *
 * @author JuanOrtiz2006
 * @version 1.0
 */
public class MensajeInternacionalizacionHandler {
    /**
     * Bundle de recursos con los mensajes traducidos.
     */
    private ResourceBundle bundle;
    /**
     * Locale actual configurado.
     */
    private Locale locale;

    /**
     * Constructor que inicializa el manejador con el idioma y país especificados.
     *
     * @param lenguaje Código de lenguaje (ej: "es", "en").
     * @param pais     Código de país (ej: "EC", "US").
     */
    public MensajeInternacionalizacionHandler(String lenguaje, String pais) {
        this.locale = new Locale(lenguaje, pais);
        this.bundle = ResourceBundle.getBundle("mensajes", locale);
    }

    /**
     * Obtiene el mensaje traducido para la clave dada.
     *
     * @param key Clave del mensaje.
     * @return Mensaje traducido.
     */
    public String get(String key) {
        return bundle.getString(key);
    }

    /**
     * Obtiene el locale actual.
     *
     * @return Locale configurado.
     */
    public Locale getLocale() {
        return locale;
    }
}
