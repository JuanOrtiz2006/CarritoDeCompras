package ec.edu.ups.controlador;

import ec.edu.ups.dao.PreguntaDAO;
import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.*;
import ec.edu.ups.util.Contexto;
import ec.edu.ups.vista.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.*;

public class UsuarioController {

    // === Atributos ===
    private Usuario usuario;
    private final UsuarioDAO usuarioDAO;
    private final PreguntaDAO preguntaDAO;
    private LoginView loginView;
    private GestionUsuarios gestionUsuarios;
    private RegistrarUsuario registrarUsuario;
    private PreguntasSeguridad preguntasSeguridad;
    private RecuperarClave recuperarClave;

    // === Constructor ===
    public UsuarioController(UsuarioDAO usuarioDAO, PreguntaDAO preguntaDAO) {
        this.usuarioDAO = usuarioDAO;
        this.preguntaDAO = preguntaDAO;
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

    public void setRecuperarClave(RecuperarClave recuperarClave) {
        this.recuperarClave = recuperarClave;
    }

    public Usuario getUsuarioAutenticado() {
        return usuario;
    }

    // ===============================================================
    // ===================== EVENTOS PRINCIPALES =====================
    // ===============================================================

    public void eventosLogin() {
        loginView.getBtnLogin().addActionListener(e -> autenticar());
        loginView.getBtnRegistrar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginView.setVisible(false);
                registrarUsuario.setVisible(true);
                eventoRegistrarUsuario();
            }
        });
        loginView.getBtnRecuperar().addActionListener(e -> {
            loginView.setVisible(false);
            preguntasSeguridad.setVisible(true);
            eventoRecuperarClave();
        });
    }

    public void eventosGestionUsuario() {
        gestionUsuarios.getBtnBuscar().addActionListener(e -> buscarUsuario());
        gestionUsuarios.getBtnListar().addActionListener(e -> listar());
        gestionUsuarios.getBtnCrear().addActionListener(e -> crearUsuarios());
        activarAccionesEnTablaUsuarios();
    }

    private void eventoRegistrarUsuario() {

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
            eventoPreguntasSeguridad(usuariocreado);
        });
    }

    private void eventoRecuperarClave(){
        final Usuario[] usuarioEncontrado = new Usuario[1];

        recuperarClave.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = recuperarClave.getTxtUsuario().getText();
                Usuario usuario = usuarioDAO.buscarPorUsername(username);
                if(usuario != null){
                    usuarioEncontrado[0] = usuario;
                    Pregunta pregunta = usuario.obtenerPreguntaParaRecuperacion();
                    recuperarClave.getLblPregunta().setText(pregunta.getTexto());
                    recuperarClave.getTxtUsuario().setEnabled(false);
                    recuperarClave.getBtnBuscar().setEnabled(false);
                } else {
                    recuperarClave.mostrarMensaje("Usuario no encontrado");
                }
            }
        });

        recuperarClave.getBtnRecuperar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(usuarioEncontrado[0] != null) {
                    String respuesta = recuperarClave.getTxtPregunta().getText();
                    if(usuarioEncontrado[0].verificarRespuesta(respuesta)){
                        recuperarClave.mostrarMensaje("Respuesta correcta. Aquí se podrá cambiar la clave");
                    } else {
                        recuperarClave.mostrarMensaje("Respuesta incorrecta");
                    }
                } else {
                    recuperarClave.mostrarMensaje("Primero busque un usuario");
                }
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
    // ================= REGISTRO DE USUARIO Y PREGUNTAS =============
    // ===============================================================

    public void eventoPreguntasSeguridad(Usuario usuariocreado) {
        preguntasSeguridad.cargarPreguntas(preguntaDAO.obtenerPreguntas());
        preguntasSeguridad.cargarCheckBox(preguntaDAO.obtenerTipos());
        preguntasSeguridad.setVisible(true);

        preguntasSeguridad.getCkbTipo1().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (preguntasSeguridad.getCkbTipo1().isSelected()) {
                    preguntasSeguridad.habilitarPreguntasTipo1();
                } else {
                    preguntasSeguridad.deshabilitarPreguntasTipo1();
                }
            }
        });
        preguntasSeguridad.getCkbTipo2().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (preguntasSeguridad.getCkbTipo2().isSelected()) {
                    preguntasSeguridad.habilitarPreguntasTipo2();
                } else {
                    preguntasSeguridad.deshabilitarPreguntasTipo2();
                }
            }
        });
        preguntasSeguridad.getCkbTipo3().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (preguntasSeguridad.getCkbTipo3().isSelected()) {
                    preguntasSeguridad.habilitarPreguntasTipo3();
                } else {
                    preguntasSeguridad.deshabilitarPreguntasTipo3();
                }
            }
        });
        preguntasSeguridad.getCkbTipo4().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (preguntasSeguridad.getCkbTipo4().isSelected()) {
                    preguntasSeguridad.habilitarPreguntasTipo4();
                } else {
                    preguntasSeguridad.deshabilitarPreguntasTipo4();
                }
            }
        });
        preguntasSeguridad.getCkbTipo5().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (preguntasSeguridad.getCkbTipo5().isSelected()) {
                    preguntasSeguridad.habilitarPreguntasTipo5();
                } else {
                    preguntasSeguridad.deshabilitarPreguntasTipo5();
                }
            }
        });

        // Reemplaza el ActionListener del botón "Registrarse" (línea 276 aproximadamente)
        preguntasSeguridad.getBtnRegistrarse().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Respuesta> respuestas = new ArrayList<>();

                // Recolectar todas las respuestas no vacías
                if(preguntasSeguridad.getTxtPregunta().getText() != null &&
                        !preguntasSeguridad.getTxtPregunta().getText().trim().isEmpty()){
                    Pregunta pregunta = preguntaDAO.obtenerPregunta(preguntasSeguridad.getLblPregunta().getText());
                    String respuesta = preguntasSeguridad.getTxtPregunta().getText().trim();
                    respuestas.add(new Respuesta(pregunta, respuesta));
                }
                if(preguntasSeguridad.getTxtPregunta2().getText() != null &&
                        !preguntasSeguridad.getTxtPregunta2().getText().trim().isEmpty()){
                    Pregunta pregunta = preguntaDAO.obtenerPregunta(preguntasSeguridad.getLblPregunta2().getText());
                    String respuesta = preguntasSeguridad.getTxtPregunta2().getText().trim();
                    respuestas.add(new Respuesta(pregunta, respuesta));
                }
                if(preguntasSeguridad.getTxtPregunta3().getText() != null &&
                        !preguntasSeguridad.getTxtPregunta3().getText().trim().isEmpty()){
                    Pregunta pregunta = preguntaDAO.obtenerPregunta(preguntasSeguridad.getLblPregunta3().getText());
                    String respuesta = preguntasSeguridad.getTxtPregunta3().getText().trim();
                    respuestas.add(new Respuesta(pregunta, respuesta));
                }
                if(preguntasSeguridad.getTxtPregunta4().getText() != null &&
                        !preguntasSeguridad.getTxtPregunta4().getText().trim().isEmpty()){
                    Pregunta pregunta = preguntaDAO.obtenerPregunta(preguntasSeguridad.getLblPregunta4().getText());
                    String respuesta = preguntasSeguridad.getTxtPregunta4().getText().trim();
                    respuestas.add(new Respuesta(pregunta, respuesta));
                }
                if(preguntasSeguridad.getTxtPregunta5().getText() != null &&
                        !preguntasSeguridad.getTxtPregunta5().getText().trim().isEmpty()){
                    Pregunta pregunta = preguntaDAO.obtenerPregunta(preguntasSeguridad.getLblPregunta5().getText());
                    String respuesta = preguntasSeguridad.getTxtPregunta5().getText().trim();
                    respuestas.add(new Respuesta(pregunta, respuesta));
                }
                if(preguntasSeguridad.getTxtPregunta6().getText() != null &&
                        !preguntasSeguridad.getTxtPregunta6().getText().trim().isEmpty()){
                    Pregunta pregunta = preguntaDAO.obtenerPregunta(preguntasSeguridad.getLblPregunta6().getText());
                    String respuesta = preguntasSeguridad.getTxtPregunta6().getText().trim();
                    respuestas.add(new Respuesta(pregunta, respuesta));
                }
                if(preguntasSeguridad.getTxtPregunta7().getText() != null &&
                        !preguntasSeguridad.getTxtPregunta7().getText().trim().isEmpty()){
                    Pregunta pregunta = preguntaDAO.obtenerPregunta(preguntasSeguridad.getLblPregunta7().getText());
                    String respuesta = preguntasSeguridad.getTxtPregunta7().getText().trim();
                    respuestas.add(new Respuesta(pregunta, respuesta));
                }
                if(preguntasSeguridad.getTxtPregunta8().getText() != null &&
                        !preguntasSeguridad.getTxtPregunta8().getText().trim().isEmpty()){
                    Pregunta pregunta = preguntaDAO.obtenerPregunta(preguntasSeguridad.getLblPregunta8().getText());
                    String respuesta = preguntasSeguridad.getTxtPregunta8().getText().trim();
                    respuestas.add(new Respuesta(pregunta, respuesta));
                }
                if(preguntasSeguridad.getTxtPregunta9().getText() != null &&
                        !preguntasSeguridad.getTxtPregunta9().getText().trim().isEmpty()){
                    Pregunta pregunta = preguntaDAO.obtenerPregunta(preguntasSeguridad.getLblPregunta9().getText());
                    String respuesta = preguntasSeguridad.getTxtPregunta9().getText().trim();
                    respuestas.add(new Respuesta(pregunta, respuesta));
                }
                if(preguntasSeguridad.getTxtPregunta10().getText() != null &&
                        !preguntasSeguridad.getTxtPregunta10().getText().trim().isEmpty()){
                    Pregunta pregunta = preguntaDAO.obtenerPregunta(preguntasSeguridad.getLblPregunta10().getText());
                    String respuesta = preguntasSeguridad.getTxtPregunta10().getText().trim();
                    respuestas.add(new Respuesta(pregunta, respuesta));
                }

                // Validar que se hayan respondido al menos 3 preguntas
                if(respuestas.size() >= 3){
                    usuariocreado.setRespuestas(respuestas);

                    // *** AQUÍ ESTÁ LA CORRECCIÓN PRINCIPAL ***
                    // Guardar el usuario en la base de datos
                    try {
                        usuarioDAO.crear(usuariocreado);
                        preguntasSeguridad.mostrarMensaje("Usuario registrado exitosamente");
                        preguntasSeguridad.setVisible(false);
                        loginView.setVisible(true);
                    } catch (Exception ex) {
                        preguntasSeguridad.mostrarMensaje("Error al registrar usuario: " + ex.getMessage());
                    }
                } else {
                    preguntasSeguridad.mostrarMensaje("Mínimo conteste 3 preguntas");
                }
            }
        });
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
        String[] opciones = {Contexto.getHandler().get("opciones.editar"), Contexto.getHandler().get("opciones.eliminar"), Contexto.getHandler().get("opciones.cancelar")};
        int opcion = JOptionPane.showOptionDialog(null,
                Contexto.getHandler().get("mensaje.usuario.accion"),
                Contexto.getHandler().get("gestionusuarios.titulo.dialogo"),
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