package ec.edu.ups.vista;

import ec.edu.ups.controlador.CarritoController;
import ec.edu.ups.util.Contexto;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.TableCellEditor;

public class BotonEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {
    private JButton button;
    private String label;
    private int row;
    private JTable table;
    private CarritoController controlador;

    private final String editarLabel;
    private final String eliminarLabel;

    public BotonEditor(JTable table, CarritoController controlador) {
        this.table = table;
        this.controlador = controlador;
        button = new JButton();
        button.addActionListener(this);

        // Obtener las etiquetas internacionalizadas
        editarLabel = Contexto.getHandler().get("boton.editar");
        eliminarLabel = Contexto.getHandler().get("boton.eliminar");
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        this.row = row;
        this.label = (value == null) ? "" : value.toString();
        button.setText(label);
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        return label;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        fireEditingStopped();
        fireEditingStopped();
        if (editarLabel.equals(label)) {
            System.out.println("Editar fila: " + row);
            controlador.editarItem(row);
        } else if (eliminarLabel.equals(label)) {
            System.out.println("Eliminar fila: " + row);
            controlador.eliminarItem(row);
        }
    }
}
