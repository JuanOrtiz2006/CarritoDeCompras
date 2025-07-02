package ec.edu.ups.controlador;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.Contexto;
import ec.edu.ups.vista.GestionUsuarios;
import ec.edu.ups.vista.LoginView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.List;

public class UsuarioController {
    private Usuario usuario;
    private final UsuarioDAO usuarioDAO;
    private LoginView loginView;
    private GestionUsuarios gestionUsuarios;

    public UsuarioController(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
        this.usuario = null;
    }

    public void setLoginView(LoginView loginView) {
        this.loginView = loginView;
    }

    public void setGestionUsuarios(GestionUsuarios gestionUsuarios) {
        this.gestionUsuarios = gestionUsuarios;
    }

    public void configurarEventosEnVistas() {
        loginView.getBtnLogin().addActionListener(e -> autenticar());
        loginView.getBtnRegistrar().addActionListener(e -> registrarUsuario());
    }

    public void eventosGestionUsuario() {
        gestionUsuarios.getBtnBuscar().addActionListener(e -> buscarUsuario());
        gestionUsuarios.getBtnListar().addActionListener(e -> listar());
        activarAccionesEnTablaUsuarios(); // ← activar clic en filas
        gestionUsuarios.getBtnCrear().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearUsuarios();
            }
        });
    }

    private void autenticar() {
        String username = loginView.getTxtUsername().getText();
        String contrasenia = loginView.getTxtPassword().getText();

        usuario = usuarioDAO.autenticar(username, contrasenia);
        if (usuario == null) {
            loginView.mostrarMensaje(Contexto.getHandler().get("usuario.contrasena.incorrectos"));
        } else {
            loginView.dispose();
        }
    }

    private void registrarUsuario() {
        String[] respuestas = loginView.mostrarRegistroMensaje();
        if (respuestas != null) {
            Usuario usuarioRegistrado = new Usuario(respuestas[0], respuestas[1], Rol.USUARIO);
            usuarioDAO.crear(usuarioRegistrado);
        }
    }

    public Usuario getUsuarioAutenticado() {
        return usuario;
    }

    private void buscarUsuario() {
        String username = gestionUsuarios.getTxtBusqueda().getText();
        Usuario usuarioEncontrado = usuarioDAO.buscarPorUsername(username);
        if (usuarioEncontrado != null) {
            cargarUsuarioEncontrado(usuarioEncontrado);
        } else {
            JOptionPane.showMessageDialog(null,
                    Contexto.getHandler().get("usuario.no.encontrado"));
        }
    }

    private void cargarUsuarioEncontrado(Usuario usuario) {
        DefaultTableModel modelo = (DefaultTableModel) gestionUsuarios.getTblUsuarios().getModel();
        modelo.setRowCount(0);
        modelo.addRow(new Object[]{
                usuario.getRol(),
                usuario.getUsername(),
                usuario.getPassword()
        });

        gestionUsuarios.getTxtBusqueda().setText("");
    }

    private void listar() {
        String seleccion = gestionUsuarios.getCmbLista().getSelectedItem().toString();
        if (seleccion.equals(Contexto.getHandler().get("usuario.normal"))) {
            cargarClientes();
        } else if (seleccion.equals(Contexto.getHandler().get("usuario.administrador"))) {
            cargarAdministradores();
        } else if (seleccion.equals(Contexto.getHandler().get("gestionusuarios.combo.todos"))) {
            cargarUsuarios();
        } else {
            JOptionPane.showMessageDialog(null, Contexto.getHandler().get("mensaje.usuario.error"));
        }
    }

    private void cargarAdministradores() {
        DefaultTableModel modelo = (DefaultTableModel) gestionUsuarios.getTblUsuarios().getModel();
        modelo.setRowCount(0);
        List<Usuario> usuariosAdministrador = usuarioDAO.listarPorRol(Contexto.getHandler().get("usuario.adminstrador"));
        for (Usuario usuarioLista : usuariosAdministrador) {
            modelo.addRow(new Object[]{
                    usuarioLista.getRol(),
                    usuarioLista.getUsername(),
                    usuarioLista.getPassword()
            });
        }
    }

    private void cargarClientes() {
        DefaultTableModel modelo = (DefaultTableModel) gestionUsuarios.getTblUsuarios().getModel();
        modelo.setRowCount(0);
        List<Usuario> usuariosClientes = usuarioDAO.listarPorRol("USUARIO");

        for (Usuario usuarioLista : usuariosClientes) {
            modelo.addRow(new Object[]{
                    usuarioLista.getRol(),
                    usuarioLista.getUsername(),
                    usuarioLista.getPassword()
            });
        }
    }

    private void cargarUsuarios() {
        DefaultTableModel modelo = (DefaultTableModel) gestionUsuarios.getTblUsuarios().getModel();
        modelo.setRowCount(0);
        List<Usuario> usuarios = usuarioDAO.listarTodos();

        for (Usuario usuarioLista : usuarios) {
            modelo.addRow(new Object[]{
                    usuarioLista.getRol(),
                    usuarioLista.getUsername(),
                    usuarioLista.getPassword()
            });
        }
    }

    private void activarAccionesEnTablaUsuarios() {
        gestionUsuarios.getTblUsuarios().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                JTable tabla = gestionUsuarios.getTblUsuarios();
                int fila = tabla.getSelectedRow();
                if (fila >= 0) {
                    String rolStr = tabla.getValueAt(fila, 0).toString();
                    String username = tabla.getValueAt(fila, 1).toString();
                    String password = tabla.getValueAt(fila, 2).toString();
                    Rol rol = Rol.valueOf(rolStr);

                    String[] opciones = {"Editar", "Eliminar", "Cancelar"};
                    int opcion = JOptionPane.showOptionDialog(null,
                            "¿Qué desea hacer con el usuario '" + username + "'?",
                            "Acción sobre usuario",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null, opciones, opciones[0]);

                    if (opcion == 0) { // Editar
                        editarUsuario(username, rol);
                    } else if (opcion == 1) { // Eliminar
                        eliminarUsuario(username);
                    }
                }
            }
        });
    }

    public void editarUsuario(String usernameOriginal, Rol rol) {
        String nuevoUsername = JOptionPane.showInputDialog(null,
                Contexto.getHandler().get("usuario.ingrese.nuevo.username"), usernameOriginal);

        if (nuevoUsername == null || nuevoUsername.trim().isEmpty()) return;

        String nuevaPassword = JOptionPane.showInputDialog(null,
                Contexto.getHandler().get("usuario.ingrese.nueva.contrasena"));

        if (nuevaPassword == null || nuevaPassword.trim().isEmpty()) return;

        JOptionPane.showMessageDialog(null,
                Contexto.getHandler().get("usuario.actualizado.exito"));
        listar(); // refresca la tabla
    }

    private void eliminarUsuario(String username) {
        int confirmacion = JOptionPane.showConfirmDialog(null,
                Contexto.getHandler().get("usuario.confirmar.eliminar") + " '" + username + "'?",
                Contexto.getHandler().get("confirmacion"),
                JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            usuarioDAO.eliminar(username);
            JOptionPane.showMessageDialog(null,
                    Contexto.getHandler().get("usuario.eliminado.exito"));
            listar();
        }
    }

    private void crearUsuarios() {
        JTextField nombreField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JComboBox<String> rolBox = new JComboBox<>(new String[]{Contexto.getHandler().get("usuario.normal"), Contexto.getHandler().get("usuario.administrador")});

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel(Contexto.getHandler().get("usuario.nombre")));
        panel.add(nombreField);
        panel.add(Box.createVerticalStrut(10));
        panel.add(new JLabel(Contexto.getHandler().get("usuario.contrasena")));
        panel.add(passwordField);
        panel.add(Box.createVerticalStrut(10));
        panel.add(new JLabel(Contexto.getHandler().get("usuario.rol")));
        panel.add(rolBox);

        int result = JOptionPane.showConfirmDialog(null, panel,
                Contexto.getHandler().get("usuario.registro.titulo"),
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String nombre = nombreField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            String rolSeleccionado = rolBox.getSelectedItem().toString();

            if (!nombre.isEmpty() && !password.isEmpty()) {
                Rol rol = rolSeleccionado.equals(Contexto.getHandler().get("usuario.administrador")) ? Rol.ADMINISTRADOR : Rol.USUARIO;
                Usuario nuevoUsuario = new Usuario(nombre, password, rol);
                usuarioDAO.crear(nuevoUsuario);
                JOptionPane.showMessageDialog(null, "Usuario creado exitosamente.");
                listar(); // Actualiza la tabla
            } else {
                JOptionPane.showMessageDialog(null,     Contexto.getHandler().get("usuario.campos.obligatorios"));

            }
        }
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
