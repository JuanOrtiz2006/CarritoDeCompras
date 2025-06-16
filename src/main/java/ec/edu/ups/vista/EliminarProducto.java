package ec.edu.ups.vista;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EliminarProducto extends JFrame {
    private JPanel panelGeneral;
    private JPanel panelCentral;
    private JTextField txtCodigo;
    private JLabel lblCodigo;
    private JButton btnEliminar;
    private JPanel panelDatos;


    public EliminarProducto(){
        setContentPane(panelGeneral);
        setTitle("Eliminar Producto");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        //setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });
    }

    public JPanel getPanelGeneral() {
        return panelGeneral;
    }

    public JPanel getPanelCentral() {
        return panelCentral;
    }

    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public JLabel getLblCodigo() {
        return lblCodigo;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public JPanel getPanelDatos() {
        return panelDatos;
    }

    public void setPanelGeneral(JPanel panelGeneral) {
        this.panelGeneral = panelGeneral;
    }

    public void setPanelCentral(JPanel panelCentral) {
        this.panelCentral = panelCentral;
    }

    public void setTxtCodigo(JTextField txtCodigo) {
        this.txtCodigo = txtCodigo;
    }

    public void setLblCodigo(JLabel lblCodigo) {
        this.lblCodigo = lblCodigo;
    }

    public void setBtnEliminar(JButton btnEliminar) {
        this.btnEliminar = btnEliminar;
    }

    public void setPanelDatos(JPanel panelDatos) {
        this.panelDatos = panelDatos;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void limpiarCampos() {
        txtCodigo.setText("");
    }
}
