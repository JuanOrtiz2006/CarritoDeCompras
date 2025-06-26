package ec.edu.ups.controlador;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.vista.GestionUsuarios;
import ec.edu.ups.vista.LoginView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class UsuarioController {
    private Usuario usuario;
    private final UsuarioDAO usuarioDAO;
    private final LoginView loginView;
    private GestionUsuarios gestionUsuarios;

    public UsuarioController(UsuarioDAO usuarioDAO, LoginView loginView) {
        this.usuarioDAO = usuarioDAO;
        this.loginView = loginView;
        this.usuario = null;
        configurarEventosEnVistas();

    }

    public GestionUsuarios getGestionUsuarios() {
        return gestionUsuarios;
    }

    public void setGestionUsuarios(GestionUsuarios gestionUsuarios) {
        this.gestionUsuarios = gestionUsuarios;
    }

    private void configurarEventosEnVistas(){
        loginView.getBtnLogin().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                autenticar();
            }
        });
        loginView.getBtnRegistrar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarUsuario();
            }
        });
    }

    public void eventosGestionUsuario(){
        gestionUsuarios.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarUsuario();
            }
        });

        gestionUsuarios.getBtnListar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listar();
            }
        });
    }

    private void autenticar(){
        String username = loginView.getTxtUsername().getText();
        String contrasenia = loginView.getTxtPassword().getText();

        usuario = usuarioDAO.autenticar(username, contrasenia);
        if(usuario == null){
            loginView.mostrarMensaje("Usuario o contraseña incorrectos.");
        }else{
            loginView.dispose();
        }
    }

    private void registrarUsuario(){
        String [] respuestas = loginView.mostrarRegistroMensaje();
        if(respuestas!=null){
            Usuario usuarioRegistrado = new Usuario(respuestas[0],respuestas[1], Rol.USUARIO);
            usuarioDAO.crear(usuarioRegistrado);
        }
    }

    public Usuario getUsuarioAutenticado(){
        return usuario;
    }

    private void buscarUsuario(){
        String username = gestionUsuarios.getTxtBusqueda().getText();
        Usuario usuarioEncontrado = usuarioDAO.buscarPorUsername(username);
        if(usuarioEncontrado!=null){
            cargarUsuarioEncontrado(usuarioEncontrado);
        } else{
            JOptionPane.showMessageDialog(null,"Usuario no encontrado o nombre mal ingresado, ingrese de nuevo");
        }
    }

    private void cargarUsuarioEncontrado(Usuario usuario){
        DefaultTableModel modelo = (DefaultTableModel) gestionUsuarios.getTblUsuarios().getModel();
        modelo.setRowCount(0);
        modelo.addRow(new Object[]{
                usuario.getRol(),
                usuario.getUsername(),
                usuario.getPassword()
        });

        gestionUsuarios.getTxtBusqueda().setText("");
    }

    private void listar(){
        String seleccion = gestionUsuarios.getCmbLista().getSelectedItem().toString();
        if(seleccion.equals("USUARIOS")){
            cargarClientes();
        } else if (seleccion.equals("ADMINISTRADORES")) {
            cargarAdministradores();
        } else if (seleccion.equals("TODOS")) {
            cargarUsuarios();
        } else {
            JOptionPane.showMessageDialog(null, "Seleccion no valida, intente de nuevo");
        }
    }

    private void cargarAdministradores(){
        DefaultTableModel modelo = (DefaultTableModel) gestionUsuarios.getTblUsuarios().getModel();
        modelo.setRowCount(0);
        List<Usuario> usuariosAdministrador = usuarioDAO.listarPorRol("ADMINISTRADOR");
        for(Usuario usuarioLista : usuariosAdministrador){
            modelo.addRow(new Object[]{
                    usuarioLista.getRol(),
                    usuarioLista.getUsername(),
                    usuarioLista.getPassword()
            });
        }
    }

    private void cargarClientes(){
        DefaultTableModel modelo = (DefaultTableModel) gestionUsuarios.getTblUsuarios().getModel();
        modelo.setRowCount(0);
        List<Usuario> usuariosClientes = usuarioDAO.listarPorRol("USUARIO");

        for(Usuario usuarioLista : usuariosClientes){
            modelo.addRow(new Object[]{
                    usuarioLista.getRol(),
                    usuarioLista.getUsername(),
                    usuarioLista.getPassword()
            });
        }
    }

    private void cargarUsuarios(){
        DefaultTableModel modelo = (DefaultTableModel) gestionUsuarios.getTblUsuarios().getModel();
        modelo.setRowCount(0);
        List<Usuario> usuarios = usuarioDAO.listarTodos();

        for(Usuario usuarioLista : usuarios){
            modelo.addRow(new Object[]{
                    usuarioLista.getRol(),
                    usuarioLista.getUsername(),
                    usuarioLista.getPassword()
            });
        }
    }
}
