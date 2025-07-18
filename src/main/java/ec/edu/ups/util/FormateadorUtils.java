package ec.edu.ups.util;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Clase utilitaria para formatear monedas y fechas según el locale.
 *
 * @author JuanOrtiz2006
 * @version 1.0
 */
public class FormateadorUtils {

    /**
     * Formatea una cantidad numérica como moneda según el locale.
     *
     * @param cantidad Cantidad a formatear.
     * @param locale   Locale para el formato.
     * @return Cadena con el formato de moneda.
     */
    public static String formatearMoneda(double cantidad, Locale locale) {
        NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(locale);
        return formatoMoneda.format(cantidad);
    }

    /**
     * Formatea una fecha según el locale.
     *
     * @param fecha  Fecha a formatear.
     * @param locale Locale para el formato.
     * @return Cadena con la fecha formateada.
     */
    public static String formatearFecha(Date fecha, Locale locale) {
        DateFormat formato = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
        return formato.format(fecha);
    }
}
