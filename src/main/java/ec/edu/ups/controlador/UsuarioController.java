package ec.edu.ups.controlador;

import ec.edu.ups.dao.PreguntaDAO;
import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.*;
import ec.edu.ups.util.Contexto;
import ec.edu.ups.util.FormateadorUtils;
import ec.edu.ups.vista.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.text.DateFormat;
import java.text.ParseException;
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
    private Usuario usuarioAEditar; // usado solo en modo edición


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

    //eventosVistas

    public void eventosLogin() {
        loginView.getBtnLogin().addActionListener(e -> autenticar());
        loginView.getBtnRegistrar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginView.setVisible(false);
                registrarUsuario.setVisible(true);
                eventoRegistrarUsuario(false);
            }
        });
        loginView.getBtnRecuperar().addActionListener(e -> {
            loginView.setVisible(false);
            recuperarClave.setVisible(true);
            eventoRecuperarClave();
        });
    }

    public void eventosGestionUsuario() {
        gestionUsuarios.getBtnBuscar().addActionListener(e -> buscarUsuario());
        gestionUsuarios.getBtnListar().addActionListener(e -> listar());
        gestionUsuarios.getBtnCrear().addActionListener(e -> crearUsuarios());
        activarAccionesEnTablaUsuarios();
    }

    private void eventoRegistrarUsuario(boolean modo) {
        registrarUsuario.limpiarCampos();

        registrarUsuario.ejemplos();
        registrarUsuario.actualizarIdioma();
        // Ejemplo de formato de fecha para ayudar al usuario
        Date ejemploFecha = new Date();
        String formatoEjemplo = FormateadorUtils.formatearFecha(ejemploFecha, Contexto.getLocale());
        registrarUsuario.getTxtFecha().setText(formatoEjemplo);
        registrarUsuario.getTxtFecha().setToolTipText("Ejemplo: " + formatoEjemplo);

        registrarUsuario.getBtnSiguiente().addActionListener(e -> {
            var handler = Contexto.getHandler();

            String nombre = registrarUsuario.getTxtNombre().getText();
            String fechaTexto = registrarUsuario.getTxtFecha().getText();
            String correo = registrarUsuario.getTxtCorreo().getText();
            String telefono = registrarUsuario.getTxtTelefono().getText();
            String usuario = registrarUsuario.getTxtUsuario().getText();
            String contrasenia = registrarUsuario.getTxtPassword().getText();

            GregorianCalendar fechaNacimiento = new GregorianCalendar();
            try {
                DateFormat formato = DateFormat.getDateInstance(DateFormat.MEDIUM, Contexto.getLocale());
                Date fecha = formato.parse(fechaTexto);
                fechaNacimiento.setTime(fecha);
            } catch (ParseException i) {
                String mensaje = String.format(
                        handler.get("usuario.fecha.invalida"),
                        formatoEjemplo
                );
                registrarUsuario.mostrarMensaje(mensaje);
                return;
            }

            Usuario usuariocreado = new Usuario(usuario, contrasenia, Rol.USUARIO);
            if (modo) {
                usuariocreado = usuarioAEditar;
                registrarUsuario.getTxtUsuario().setEnabled(false);
                registrarUsuario.getTxtPassword().setEnabled(false);
            } else {
                usuariocreado = new Usuario(usuario, contrasenia, Rol.USUARIO);
            }
            usuariocreado.setNombre(nombre);
            usuariocreado.setCorreo(correo);
            usuariocreado.setTelefono(telefono);
            usuariocreado.setFechanacimiento(fechaNacimiento);

            registrarUsuario.setVisible(false);
            eventoPreguntasSeguridad(usuariocreado, modo);
        });
    }

    public void eventoPreguntasSeguridad(Usuario usuariocreado, boolean modo) {
        var handler = Contexto.getHandler();
        preguntasSeguridad.limpiarCampos();
        preguntasSeguridad.actualizarIdioma();

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

        preguntasSeguridad.getBtnRegistrarse().setVisible(!modo);
        preguntasSeguridad.getBtnActualizar().setVisible(modo);

        // Reemplaza el ActionListener del botón "Registrarse" (línea 276 aproximadamente)
        preguntasSeguridad.getBtnRegistrarse().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                List<Respuesta> respuestas = recolectarRespuestas();
                // Validar que se hayan respondido al menos 3 preguntas
                if(respuestas.size() >= 3){
                    usuariocreado.setRespuestas(respuestas);

                    // Guardar el usuario en la base de datos
                    try {
                        usuarioDAO.crear(usuariocreado);
                        preguntasSeguridad.mostrarMensaje(handler.get("usuario.actualizado.exito"));
                        preguntasSeguridad.setVisible(false);
                        loginView.setVisible(true);
                    } catch (Exception ex) {
                        preguntasSeguridad.mostrarMensaje(handler.get("usuario.registro.error") + ex.getMessage());
                    }
                } else {
                    preguntasSeguridad.mostrarMensaje(handler.get("usuario.preguntas.minimas"));
                }
            }
        });
        preguntasSeguridad.getBtnActualizar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (usuarioAEditar == null) {
                    preguntasSeguridad.mostrarMensaje("usuario.actualizar.sinusuario");
                    return;
                }

                List<Respuesta> respuestas = recolectarRespuestas();
                if (respuestas.size() < 3) {
                    preguntasSeguridad.mostrarMensaje("usuario.preguntas.minimas");
                    return;
                }

                usuarioAEditar.setRespuestas(respuestas);

                try {
                    usuarioDAO.actualizar(usuarioAEditar);
                    preguntasSeguridad.mostrarMensaje(handler.get("usuario.actualizado.exito"));
                    preguntasSeguridad.setVisible(false);
                    gestionUsuarios.setVisible(true); // puedes regresar al panel de gestión
                } catch (Exception ex) {
                    preguntasSeguridad.mostrarMensaje(handler.get("usuario.actualizar.error") + ex.getMessage());
                }
            }
        });
    }

    private void eventoRecuperarClave(){
        var handler = Contexto.getHandler();
        recuperarClave.limpiarCampos();
        recuperarClave.actualizarIdioma();
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
                    recuperarClave.mostrarMensaje(handler.get("mensaje.usuario.noencontrado"));
                }
            }
        });

        recuperarClave.getBtnRecuperar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(usuarioEncontrado[0] != null) {
                    String respuesta = recuperarClave.getTxtPregunta().getText();
                    if(usuarioEncontrado[0].verificarRespuesta(respuesta)){
                        recuperarClave.mostrarMensaje(handler.get("usuario.clave.correcta"));
                    } else {
                        recuperarClave.mostrarMensaje(handler.get("usuario.respuestas.incorrectas"));
                    }
                } else {
                    recuperarClave.mostrarMensaje("usuario.buscar.primero");
                }
            }
        });

    }

    //metodosLogin
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

    // meotodsGesionUsuarios

    private void buscarUsuario() {
        String username = gestionUsuarios.getTxtBusqueda().getText().trim();
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(null, Contexto.getHandler().get("usuario.buscar.vacio"));
            return;
        }

        Usuario encontrado = usuarioDAO.buscarPorUsername(username);
        if (encontrado != null) {
            cargarUsuarioEncontrado(encontrado);
        } else {
            JOptionPane.showMessageDialog(null, Contexto.getHandler().get("usuario.no.encontrado"));
        }
    }

    private void listar() {
        String seleccion = gestionUsuarios.getCmbLista().getSelectedItem().toString();

        if(seleccion.equals("")){
        } else if (seleccion.equals(Contexto.getHandler().get("gestionusuarios.combo.usuarios"))) {
            cargarClientes();
        } else if (seleccion.equals(Contexto.getHandler().get("gestionusuarios.combo.admins"))) {
            cargarAdministradores();
        } else {
            cargarUsuarios();
        }
    }

    private void cargarUsuarioEncontrado(Usuario usuario) {
        DefaultTableModel modelo = (DefaultTableModel) gestionUsuarios.getTblUsuarios().getModel();
        // Clear the table first
        modelo.setRowCount(0);
        // Add the found user
        modelo.addRow(new Object[]{usuario.getRol(), usuario.getUsername(), usuario.getPassword()});
        // Clear the search field
        gestionUsuarios.getTxtBusqueda().setText("");
    }

    private void cargarClientes() {
        DefaultTableModel modelo = (DefaultTableModel) gestionUsuarios.getTblUsuarios().getModel();
        // Clear the table first
        modelo.setRowCount(0);
        // Load users with USUARIO role
        for (Usuario u : usuarioDAO.listarPorRol("USUARIO")) {
            modelo.addRow(new Object[]{u.getRol(), u.getUsername(), u.getPassword()});
        }
    }

    private void cargarAdministradores() {
        DefaultTableModel modelo = (DefaultTableModel) gestionUsuarios.getTblUsuarios().getModel();
        // Clear the table first
        modelo.setRowCount(0);
        // Load users with ADMINISTRADOR role
        for (Usuario u : usuarioDAO.listarPorRol("ADMINISTRADOR")) {
            modelo.addRow(new Object[]{u.getRol(), u.getUsername(), u.getPassword()});
        }
    }

    private void cargarUsuarios() {
        DefaultTableModel modelo = (DefaultTableModel) gestionUsuarios.getTblUsuarios().getModel();
        // Clear the table first
        modelo.setRowCount(0);
        // Load all users
        for (Usuario u : usuarioDAO.listarTodos()) {
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
        usuarioAEditar = usuarioDAO.buscarPorUsername(usernameOriginal); // Evita usar strings sueltos

        if (usuarioAEditar == null) {
            JOptionPane.showMessageDialog(null, Contexto.getHandler().get("usuario.no.encontrado"));
            return;
        }

        registrarUsuario.limpiarCampos();
        registrarUsuario.actualizarIdioma();

        // Cargar datos en los campos
        registrarUsuario.getTxtNombre().setText(usuarioAEditar.getNombre());
        registrarUsuario.getTxtCorreo().setText(usuarioAEditar.getCorreo());
        registrarUsuario.getTxtTelefono().setText(usuarioAEditar.getTelefono());
        registrarUsuario.getTxtUsuario().setText(usuarioAEditar.getUsername());
        registrarUsuario.getTxtUsuario().setEnabled(false); // ¡Muy importante! No permitir cambiar username
        registrarUsuario.getTxtPassword().setText(usuarioAEditar.getPassword());

        String fechaStr = FormateadorUtils.formatearFecha(
                usuarioAEditar.getFechanacimiento().getTime(), Contexto.getLocale());
        registrarUsuario.getTxtFecha().setText(fechaStr);

        registrarUsuario.setVisible(true);

        eventoRegistrarUsuario(true); // modo edición
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

    private List<Respuesta> recolectarRespuestas() {
        List<Respuesta> respuestas = new ArrayList<>();
        JTextField[] campos = {
                preguntasSeguridad.getTxtPregunta(),
                preguntasSeguridad.getTxtPregunta2(),
                preguntasSeguridad.getTxtPregunta3(),
                preguntasSeguridad.getTxtPregunta4(),
                preguntasSeguridad.getTxtPregunta5(),
                preguntasSeguridad.getTxtPregunta6(),
                preguntasSeguridad.getTxtPregunta7(),
                preguntasSeguridad.getTxtPregunta8(),
                preguntasSeguridad.getTxtPregunta9(),
                preguntasSeguridad.getTxtPregunta10()
        };
        JLabel[] etiquetas = {
                preguntasSeguridad.getLblPregunta(),
                preguntasSeguridad.getLblPregunta2(),
                preguntasSeguridad.getLblPregunta3(),
                preguntasSeguridad.getLblPregunta4(),
                preguntasSeguridad.getLblPregunta5(),
                preguntasSeguridad.getLblPregunta6(),
                preguntasSeguridad.getLblPregunta7(),
                preguntasSeguridad.getLblPregunta8(),
                preguntasSeguridad.getLblPregunta9(),
                preguntasSeguridad.getLblPregunta10()
        };

        for (int i = 0; i < campos.length; i++) {
            if (campos[i].getText() != null && !campos[i].getText().trim().isEmpty()) {
                Pregunta p = preguntaDAO.obtenerPregunta(etiquetas[i].getText());
                respuestas.add(new Respuesta(p, campos[i].getText().trim()));
            }
        }

        return respuestas;
    }

}