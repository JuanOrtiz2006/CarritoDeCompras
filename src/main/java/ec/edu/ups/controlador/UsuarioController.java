package ec.edu.ups.controlador;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.Contexto;
import ec.edu.ups.vista.GestionUsuarios;
import ec.edu.ups.vista.LoginView;
import ec.edu.ups.vista.PreguntasSeguridad;
import ec.edu.ups.vista.RegistrarUsuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.*;

public class UsuarioController {

    // === Atributos ===
    private Usuario usuario;
    private final UsuarioDAO usuarioDAO;
    private LoginView loginView;
    private GestionUsuarios gestionUsuarios;
    private RegistrarUsuario registrarUsuario;
    private PreguntasSeguridad preguntasSeguridad;

    // === Constructor ===
    public UsuarioController(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
        this.usuario = null;
    }

    // === Setters de vistas ===
    public void setLoginView(LoginView loginView) {
        this.loginView = loginView;
    }

    public void setGestionUsuarios(GestionUsuarios gestionUsuarios) {
        this.gestionUsuarios = gestionUsuarios;
    }

    public void setRegistrarUsuario(RegistrarUsuario registrarUsuario) {
        this.registrarUsuario = registrarUsuario;
    }

    public void setPreguntasSeguridad(PreguntasSeguridad preguntasSeguridad) {
        this.preguntasSeguridad = preguntasSeguridad;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuarioAutenticado() {
        return usuario;
    }

    // ===============================================================
    // ===================== EVENTOS PRINCIPALES =====================
    // ===============================================================

    public void configurarEventosEnVistas() {
        loginView.getBtnLogin().addActionListener(e -> autenticar());
        loginView.getBtnRegistrar().addActionListener(e -> eventoregistrarUsuario());
        loginView.getBtnRecuperar().addActionListener(e -> {
            loginView.setVisible(false);
            cargarPreguntasParaRecuperar();
            preguntasSeguridad.getPanelBotonRegistrar().setVisible(false);
        });
    }

    public void eventosGestionUsuario() {
        gestionUsuarios.getBtnBuscar().addActionListener(e -> buscarUsuario());
        gestionUsuarios.getBtnListar().addActionListener(e -> listar());
        gestionUsuarios.getBtnCrear().addActionListener(e -> crearUsuarios());
        activarAccionesEnTablaUsuarios();
    }

    // ===============================================================
    // ================= REGISTRO DE USUARIO Y PREGUNTAS =============
    // ===============================================================

    private void eventoregistrarUsuario() {
        loginView.setVisible(false);
        registrarUsuario.setVisible(true);

        registrarUsuario.getBtnSiguiente().addActionListener(e -> {
            String nombre = registrarUsuario.getTxtNombre().getText();
            String fechaNacimiento = registrarUsuario.getTxtFecha().getText();
            String correo = registrarUsuario.getTxtCorreo().getText();
            String telefono = registrarUsuario.getTxtTelefono().getText();
            String usuario = registrarUsuario.getTxtUsuario().getText();
            String contrasenia = registrarUsuario.getTxtPassword().getText();

            Usuario usuariocreado = new Usuario(usuario, contrasenia, Rol.USUARIO);
            usuariocreado.setNombre(nombre);
            usuariocreado.setCorreo(correo);
            usuariocreado.setTelefono(telefono);

            registrarUsuario.setVisible(false);
            cargarInformacionAdicionalRegistrarUsuario(usuariocreado);
        });
    }

    public void cargarInformacionAdicionalRegistrarUsuario(Usuario usuariocreado) {
        preguntasSeguridad.mostrarPanelPreguntas();
        preguntasSeguridad.getPanelUsuario().setVisible(false);
        preguntasSeguridad.getPanelBotonClave().setVisible(false);
        preguntasSeguridad.setVisible(true);
        preguntasSeguridad.getPanelBotonRegistrar().setVisible(true);
        String[] preguntas = {
                "lbl.preguntas.nombremadre", "lbl.preguntas.nombremascota", "lbl.pregunta.ciudad",
                "lbl.pregunta.comida", "lbl.pregunta.primermaestro", "lbl.pregunta.mejoramigo",
                "lbl.pregunta.primertrabajo", "lbl.pregunta.calleinfancia", "lbl.pregunta.peliculafavorita",
                "lbl.pregunta.apodoinfancia"
        };

        String[] seleccionadas = new String[3];
        Random random = new Random();
        Set<Integer> usados = new HashSet<>();
        int contador = 0;
        while (contador < 3) {
            int indice = random.nextInt(preguntas.length);
            if (usados.add(indice)) {
                seleccionadas[contador++] = preguntas[indice];
            }
        }

        preguntasSeguridad.getLblPregunta().setText(Contexto.getHandler().get(seleccionadas[0]));
        preguntasSeguridad.getLblPregunta2().setText(Contexto.getHandler().get(seleccionadas[1]));
        preguntasSeguridad.getLblPregunta3().setText(Contexto.getHandler().get(seleccionadas[2]));

        Map<String, String> info = new LinkedHashMap<>();
        info.put(seleccionadas[0], null);
        info.put(seleccionadas[1], null);
        info.put(seleccionadas[2], null);
        usuariocreado.setInformacion(info);

        preguntasSeguridad.getBtnRegistrarse().addActionListener(e -> {
            String r1 = preguntasSeguridad.getTxtPregunta().getText();
            String r2 = preguntasSeguridad.getTxtPregunta2().getText();
            String r3 = preguntasSeguridad.getTxtPregunta3().getText();

            if (r1.isEmpty() || r2.isEmpty() || r3.isEmpty()) {
                preguntasSeguridad.mostrarMensaje(Contexto.getHandler().get("usuario.campos.obligatorios"));
                return;
            }

            int i = 0;
            for (String key : usuariocreado.getInformacion().keySet()) {
                if (i == 0) usuariocreado.setRespuestaInformacion(key, r1);
                if (i == 1) usuariocreado.setRespuestaInformacion(key, r2);
                if (i == 2) usuariocreado.setRespuestaInformacion(key, r3);
                i++;
            }

            usuarioDAO.crear(usuariocreado);
            preguntasSeguridad.mostrarMensaje(Contexto.getHandler().get("usuario.registro.exito"));
            preguntasSeguridad.setVisible(false);
            loginView.setVisible(true);
        });
    }

    private void cargarPreguntasParaRecuperar() {
        preguntasSeguridad.setVisible(true);
        preguntasSeguridad.limpiarCampos();
        preguntasSeguridad.ocultarPanelPreguntas();
        preguntasSeguridad.getPanelUsuario().setVisible(true);
        preguntasSeguridad.getPanelBotonClave().setVisible(true);
        preguntasSeguridad.getPanelBotonRegistrar().setVisible(false);
        eventosPreguntasSeguridad();
    }

    private void eventosPreguntasSeguridad() {
        preguntasSeguridad.getBtnBuscar().addActionListener(e -> {
            String username = preguntasSeguridad.getTxtUsuario().getText().trim();
            Usuario usuarioEncontrado = usuarioDAO.buscarPorUsername(username);

            if (usuarioEncontrado != null && usuarioEncontrado.getInformacion().size() >= 3) {
                String[] claves = usuarioEncontrado.getInformacion().keySet().toArray(new String[0]);
                preguntasSeguridad.getLblPregunta().setText(Contexto.getHandler().get(claves[0]));
                preguntasSeguridad.getLblPregunta2().setText(Contexto.getHandler().get(claves[1]));
                preguntasSeguridad.getLblPregunta3().setText(Contexto.getHandler().get(claves[2]));
                preguntasSeguridad.mostrarPanelPreguntas();
            } else {
                preguntasSeguridad.mostrarMensaje(Contexto.getHandler().get("usuario.no.encontrado"));
            }
        });

        preguntasSeguridad.getBtnRecuperar().addActionListener(e -> {
            String username = preguntasSeguridad.getTxtUsuario().getText().trim();
            Usuario usuarioRecuperado = usuarioDAO.buscarPorUsername(username);

            if (usuarioRecuperado == null) {
                preguntasSeguridad.mostrarMensaje(Contexto.getHandler().get("usuario.no.encontrado"));
                return;
            }

            Map<String, String> info = usuarioRecuperado.getInformacion();
            String[] claves = info.keySet().toArray(new String[0]);

            String r1 = preguntasSeguridad.getTxtPregunta().getText().trim();
            String r2 = preguntasSeguridad.getTxtPregunta2().getText().trim();
            String r3 = preguntasSeguridad.getTxtPregunta3().getText().trim();

            boolean validas =
                    info.get(claves[0]).equalsIgnoreCase(r1) &&
                            info.get(claves[1]).equalsIgnoreCase(r2) &&
                            info.get(claves[2]).equalsIgnoreCase(r3);

            if (validas) {
                String nueva = JOptionPane.showInputDialog(null, Contexto.getHandler().get("usuario.ingrese.nueva.contrasena"));
                if (nueva != null && !nueva.trim().isEmpty()) {
                    usuarioRecuperado.setPassword(nueva.trim());
                    usuarioDAO.actualizar(usuarioRecuperado);
                    preguntasSeguridad.mostrarMensaje(Contexto.getHandler().get("usuario.clave.actualizada"));
                    preguntasSeguridad.setVisible(false);
                    loginView.setVisible(true);
                } else {
                    preguntasSeguridad.mostrarMensaje(Contexto.getHandler().get("usuario.clave.vacia"));
                }
            } else {
                preguntasSeguridad.mostrarMensaje(Contexto.getHandler().get("usuario.respuestas.incorrectas"));
            }
        });
    }

    // ===============================================================
    // ========================== LOGIN ==============================
    // ===============================================================

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

    // ===============================================================
    // ================ GESTIÓN DE USUARIOS (ADMIN) ==================
    // ===============================================================

    private void buscarUsuario() {
        String username = gestionUsuarios.getTxtBusqueda().getText();
        Usuario encontrado = usuarioDAO.buscarPorUsername(username);
        if (encontrado != null) {
            cargarUsuarioEncontrado(encontrado);
        } else {
            JOptionPane.showMessageDialog(null, Contexto.getHandler().get("usuario.no.encontrado"));
        }
    }

    private void listar() {
        String seleccion = gestionUsuarios.getCmbLista().getSelectedItem().toString();
        if (seleccion.equals(Contexto.getHandler().get("usuario.normal"))) {
            cargarClientes();
        } else if (seleccion.equals(Contexto.getHandler().get("usuario.administrador"))) {
            cargarAdministradores();
        } else {
            cargarUsuarios();
        }
    }

    private void cargarUsuarioEncontrado(Usuario usuario) {
        DefaultTableModel modelo = (DefaultTableModel) gestionUsuarios.getTblUsuarios().getModel();
        modelo.setRowCount(0);
        modelo.addRow(new Object[]{usuario.getRol(), usuario.getUsername(), usuario.getPassword()});
        gestionUsuarios.getTxtBusqueda().setText("");
    }

    private void cargarAdministradores() {
        cargarUsuariosPorRol(Contexto.getHandler().get("usuario.adminstrador"));
    }

    private void cargarClientes() {
        cargarUsuariosPorRol("USUARIO");
    }

    private void cargarUsuarios() {
        DefaultTableModel modelo = (DefaultTableModel) gestionUsuarios.getTblUsuarios().getModel();
        modelo.setRowCount(0);
        for (Usuario u : usuarioDAO.listarTodos()) {
            modelo.addRow(new Object[]{u.getRol(), u.getUsername(), u.getPassword()});
        }
    }

    private void cargarUsuariosPorRol(String rol) {
        DefaultTableModel modelo = (DefaultTableModel) gestionUsuarios.getTblUsuarios().getModel();
        modelo.setRowCount(0);
        for (Usuario u : usuarioDAO.listarPorRol(rol)) {
            modelo.addRow(new Object[]{u.getRol(), u.getUsername(), u.getPassword()});
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
                    Rol rol = Rol.valueOf(rolStr);
                    mostrarOpcionesUsuario(username, rol);
                }
            }
        });
    }

    private void mostrarOpcionesUsuario(String username, Rol rol) {
        String[] opciones = {"Editar", "Eliminar", "Cancelar"};
        int opcion = JOptionPane.showOptionDialog(null,
                "¿Qué desea hacer con el usuario '" + username + "'?",
                "Acción sobre usuario",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, opciones, opciones[0]);

        if (opcion == 0) editarUsuario(username, rol);
        else if (opcion == 1) eliminarUsuario(username);
    }

    public void editarUsuario(String usernameOriginal, Rol rol) {
        String nuevoUsername = JOptionPane.showInputDialog(null,
                Contexto.getHandler().get("usuario.ingrese.nuevo.username"), usernameOriginal);
        if (nuevoUsername == null || nuevoUsername.trim().isEmpty()) return;

        String nuevaPassword = JOptionPane.showInputDialog(null,
                Contexto.getHandler().get("usuario.ingrese.nueva.contrasena"));
        if (nuevaPassword == null || nuevaPassword.trim().isEmpty()) return;

        JOptionPane.showMessageDialog(null, Contexto.getHandler().get("usuario.actualizado.exito"));
        listar();
    }

    private void eliminarUsuario(String username) {
        int confirm = JOptionPane.showConfirmDialog(null,
                Contexto.getHandler().get("usuario.confirmar.eliminar") + " '" + username + "'?",
                Contexto.getHandler().get("confirmacion"),
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            usuarioDAO.eliminar(username);
            JOptionPane.showMessageDialog(null, Contexto.getHandler().get("usuario.eliminado.exito"));
            listar();
        }
    }

    private void crearUsuarios() {
        JTextField nombreField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JComboBox<String> rolBox = new JComboBox<>(new String[]{
                Contexto.getHandler().get("usuario.normal"),
                Contexto.getHandler().get("usuario.administrador")
        });

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
                Rol rol = rolSeleccionado.equals(Contexto.getHandler().get("usuario.administrador"))
                        ? Rol.ADMINISTRADOR : Rol.USUARIO;
                Usuario nuevoUsuario = new Usuario(nombre, password, rol);
                usuarioDAO.crear(nuevoUsuario);
                JOptionPane.showMessageDialog(null, Contexto.getHandler().get("usuario.registro.exito"));
                listar();
            } else {
                JOptionPane.showMessageDialog(null, Contexto.getHandler().get("usuario.campos.obligatorios"));
            }
        }
    }
}