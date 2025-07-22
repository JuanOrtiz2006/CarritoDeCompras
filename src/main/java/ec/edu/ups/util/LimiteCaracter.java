package ec.edu.ups.util;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * Filtro de documento para limitar la cantidad de caracteres y permitir solo números si se requiere.
 * Útil para campos de texto en formularios.
 *
 * @author JuanOrtiz2006
 * @version 1.0
 */
public class LimiteCaracter extends DocumentFilter {
    /**
     * Máximo de caracteres permitidos.
     */
    private int maxCaracteres;
    /**
     * Indica si solo se permiten números.
     */
    private boolean soloNumeros;

    /**
     * Constructor que inicializa el filtro con el máximo de caracteres y si solo permite números.
     *
     * @param maxCaracteres Máximo de caracteres permitidos.
     * @param soloNumeros   true para permitir solo números, false para cualquier carácter.
     */
    public LimiteCaracter(int maxCaracteres, boolean soloNumeros) {
        this.maxCaracteres = maxCaracteres;
        this.soloNumeros = soloNumeros;
    }

    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
        if (string == null) return;

        if (permitido(fb, string)) {
            super.insertString(fb, offset, string, attr);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        if (text == null) return;

        if (permitido(fb, text)) {
            super.replace(fb, offset, length, text, attrs);
        }
    }

    /**
     * Verifica si el texto nuevo cumple con las restricciones de longitud y tipo de carácter.
     *
     * @param fb         Filtro de bypass.
     * @param textoNuevo Texto a verificar.
     * @return true si está permitido, false en caso contrario.
     * @throws BadLocationException Si ocurre un error en el documento.
     */
    private boolean permitido(FilterBypass fb, String textoNuevo) throws BadLocationException {
        int total = fb.getDocument().getLength() + textoNuevo.length();

        if (total > maxCaracteres) return false;
        if (soloNumeros && !textoNuevo.matches("\\d*")) return false;

        return true;
    }
}
