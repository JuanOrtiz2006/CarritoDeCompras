package ec.edu.ups.vista;

import javax.swing.*;

public class ProductoView extends JFrame{
    private JPanel panelGeneral;
    private JButton btnCrear;
    private JButton btnBuscar;
    private JPanel panelOpciones;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnListar;

    public ProductoView(){

    }

    public JPanel getPanelGeneral() {
        return panelGeneral;
    }

    public JButton getBtnCrear() {
        return btnCrear;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JPanel getPanelOpciones() {
        return panelOpciones;
    }

    public JButton getBtnActualizar() {
        return btnActualizar;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public JButton getBtnListar() {
        return btnListar;
    }

    public void setPanelGeneral(JPanel panelGeneral) {
        this.panelGeneral = panelGeneral;
    }

    public void setBtnCrear(JButton btnCrear) {
        this.btnCrear = btnCrear;
    }

    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

    public void setPanelOpciones(JPanel panelOpciones) {
        this.panelOpciones = panelOpciones;
    }

    public void setBtnActualizar(JButton btnActualizar) {
        this.btnActualizar = btnActualizar;
    }

    public void setBtnEliminar(JButton btnEliminar) {
        this.btnEliminar = btnEliminar;
    }

    public void setBtnListar(JButton btnListar) {
        this.btnListar = btnListar;
    }
}
