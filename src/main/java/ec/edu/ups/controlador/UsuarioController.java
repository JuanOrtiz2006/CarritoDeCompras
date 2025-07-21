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

/**
 * Controlador para la gestión de usuarios.
 * Administra la autenticación, registro, edición, eliminación y recuperación de usuarios,
 * así como la interacción con las vistas y los DAOs correspondientes.
 *
 * @author JuanOrtiz2006
 * @version 1.0
 */
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
    /**
     * Constructor que inicializa el controlador con los DAOs de usuario y pregunta.
     *
     * @param usuarioDAO DAO para usuarios.
     * @param preguntaDAO DAO para preguntas de seguridad.
     */
    public UsuarioController(UsuarioDAO usuarioDAO, PreguntaDAO preguntaDAO) {
        this.usuarioDAO = usuarioDAO;
        this.preguntaDAO = preguntaDAO;
        this.usuario = null;
    }

    // === Setters de vistas ===
    /**
     * Asigna la vista de login.
     * @param loginView Vista de login.
     */
    public void setLoginView(LoginView loginView) {
        this.loginView = loginView;
    }

    /**
     * Asigna la vista de gestión de usuarios.
     * @param gestionUsuarios Vista de gestión de usuarios.
     */
    public void setGestionUsuarios(GestionUsuarios gestionUsuarios) {
        this.gestionUsuarios = gestionUsuarios;
    }

    /**
     * Asigna la vista de registro de usuario.
     * @param registrarUsuario Vista de registro.
     */
    public void setRegistrarUsuario(RegistrarUsuario registrarUsuario) {
        this.registrarUsuario = registrarUsuario;
    }

    /**
     * Asigna la vista de preguntas de seguridad.
     * @param preguntasSeguridad Vista de preguntas de seguridad.
     */
    public void setPreguntasSeguridad(PreguntasSeguridad preguntasSeguridad) {
        this.preguntasSeguridad = preguntasSeguridad;
    }

    /**
     * Asigna el usuario autenticado.
     * @param usuario Usuario autenticado.
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Asigna la vista de recuperación de clave.
     * @param recuperarClave Vista de recuperación de clave.
     */
    public void setRecuperarClave(RecuperarClave recuperarClave) {
        this.recuperarClave = recuperarClave;
    }

    /**
     * Obtiene el usuario autenticado actualmente.
     * @return Usuario autenticado.
     */
    public Usuario getUsuarioAutenticado() {
        return usuario;
    }

    //eventosVistas

    /**
     * Configura los eventos de la vista de login.
     */
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

    /**
     * Configura los eventos de la vista de gestión de usuarios.
     */
    public void eventosGestionUsuario() {
        gestionUsuarios.getBtnBuscar().addActionListener(e -> buscarUsuario());
        gestionUsuarios.getBtnListar().addActionListener(e -> listar());
        gestionUsuarios.getBtnCrear().addActionListener(e -> crearUsuarios());
        activarAccionesEnTablaUsuarios();
    }

    /**
     * Configura el evento para registrar o editar usuario.
     * @param modo true para edición, false para registro.
     */
    private void eventoRegistrarUsuario(boolean modo) {
        registrarUsuario.limpiarCampos();
        registrarUsuario.actualizarIdioma();

        if (modo) {
            // modo edición
            registrarUsuario.getTxtUsuario().setEnabled(false);
            registrarUsuario.getTxtPassword().setEnabled(false);
        } else {
            // modo registro
            registrarUsuario.getTxtUsuario().setEnabled(true);
            registrarUsuario.getTxtPassword().setEnabled(true);
            registrarUsuario.ejemplos();
        }

        for (ActionListener al : registrarUsuario.getBtnSiguiente().getActionListeners()) {
            registrarUsuario.getBtnSiguiente().removeActionListener(al);
        }

        registrarUsuario.getBtnSiguiente().addActionListener(e -> {
            var handler = Contexto.getHandler();

            String nombre = registrarUsuario.getTxtNombre().getText().trim();
            String fechaTexto = registrarUsuario.getTxtFecha().getText().trim();
            String correo = registrarUsuario.getTxtCorreo().getText().trim();
            String telefono = registrarUsuario.getTxtTelefono().getText().trim();
            String usuario = registrarUsuario.getTxtUsuario().getText().trim(); // cédula
            String contrasenia = registrarUsuario.getTxtPassword().getText().trim();

            //Validar campos vacíos
            if (nombre.isEmpty() || fechaTexto.isEmpty() || correo.isEmpty() || telefono.isEmpty()
                    || usuario.isEmpty() || contrasenia.isEmpty()) {
                registrarUsuario.mostrarMensaje(handler.get("usuario.campos.vacios")); // Mensaje: "Todos los campos son obligatorios."
                return;
            }

            //Validar fecha
            GregorianCalendar fechaNacimiento = new GregorianCalendar();
            try {
                DateFormat formato = DateFormat.getDateInstance(DateFormat.MEDIUM, Contexto.getLocale());
                Date fecha = formato.parse(fechaTexto);
                fechaNacimiento.setTime(fecha);
            } catch (ParseException i) {
                registrarUsuario.mostrarMensaje(handler.get("usuario.fecha.invalida")); // "Formato de fecha inválido"
                return;
            }

            //Crear usuario para aplicar validaciones del modelo
            Usuario usuariocreado = new Usuario(usuario, contrasenia, Rol.USUARIO);
            usuariocreado.setNombre(nombre);
            usuariocreado.setCorreo(correo);
            usuariocreado.setTelefono(telefono);
            usuariocreado.setFechanacimiento(fechaNacimiento);

            // 4. Validaciones de mínimo/máximo
            if (nombre.length() < 3) {
                registrarUsuario.mostrarMensaje(handler.get("usuario.nombre.minimo"));
                return;
            }

            if (telefono.length() != 10 || !telefono.matches("\\d+")) {
                registrarUsuario.mostrarMensaje(handler.get("usuario.telefono.invalido"));
                return;
            }

            if (usuario.length() != 10 || !usuario.matches("\\d+")) {
                registrarUsuario.mostrarMensaje(handler.get("usuario.cedula.invalida"));
                return;
            }

            if (!usuariocreado.validarCedulaEcuatoriana()) {
                registrarUsuario.mostrarMensaje(handler.get("usuario.cedula.invalida"));
                return;
            }

            if (!usuariocreado.validarCorreoElectronico()) {
                registrarUsuario.mostrarMensaje(handler.get("usuario.correo.invalido"));
                return;
            }

            if (!usuariocreado.validarPasswordSegura()) {
                registrarUsuario.mostrarMensaje(handler.get("usuario.contrasenia.invalida"));
                return;
            }

            if (!esMayorDeEdad(fechaNacimiento)) {
                registrarUsuario.mostrarMensaje(handler.get("usuario.menor.edad")); // "Debe ser mayor de 18 años."
                return;
            }
            //Rellenar los campos a longitud fija
            nombre = String.format("%-20s", nombre);
            correo = String.format("%-20s", correo);
            contrasenia = String.format("%-20s", contrasenia);

            usuariocreado.setNombre(nombre);
            usuariocreado.setCorreo(correo);
            usuariocreado.setPassword(contrasenia);


            //Sitodo es valido continuar al siguiente paso
            registrarUsuario.setVisible(false);
            eventoPreguntasSeguridad(usuariocreado, modo);
        });


    }

    /**
     * Configura el evento para preguntas de seguridad en registro o edición.
     * @param usuariocreado Usuario a registrar o editar.
     * @param modo true para edición, false para registro.
     */
    public void eventoPreguntasSeguridad(Usuario usuariocreado, boolean modo) {
        var handler = Contexto.getHandler();
        preguntasSeguridad.limpiarCampos();
        preguntasSeguridad.actualizarIdioma();
        preguntasSeguridad.cargarPreguntas(preguntaDAO.obtenerPreguntas());
        preguntasSeguridad.cargarCheckBox(preguntaDAO.obtenerTipos());
        preguntasSeguridad.setVisible(true);
        for (ActionListener al : preguntasSeguridad.getBtnRegistrarse().getActionListeners()) {
            preguntasSeguridad.getBtnRegistrarse().removeActionListener(al);
        }
        for (ActionListener al : preguntasSeguridad.getBtnActualizar().getActionListeners()) {
            preguntasSeguridad.getBtnActualizar().removeActionListener(al);
        }
        for (ActionListener al : preguntasSeguridad.getCkbTipo1().getActionListeners()) {
            preguntasSeguridad.getCkbTipo1().removeActionListener(al);
        }
        for (ActionListener al : preguntasSeguridad.getCkbTipo2().getActionListeners()) {
            preguntasSeguridad.getCkbTipo2().removeActionListener(al);
        }
        for (ActionListener al : preguntasSeguridad.getCkbTipo3().getActionListeners()) {
            preguntasSeguridad.getCkbTipo3().removeActionListener(al);
        }
        for (ActionListener al : preguntasSeguridad.getCkbTipo4().getActionListeners()) {
            preguntasSeguridad.getCkbTipo4().removeActionListener(al);
        }
        for (ActionListener al : preguntasSeguridad.getCkbTipo5().getActionListeners()) {
            preguntasSeguridad.getCkbTipo5().removeActionListener(al);
        }
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
                        if (usuarioDAO.buscarPorUsername(usuariocreado.getUsername()) != null) {
                            preguntasSeguridad.mostrarMensaje(handler.get("usuario.ya.existe"));
                            preguntasSeguridad.setVisible(false);
                            loginView.setVisible(true);
                            return;
                    }
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
                    preguntasSeguridad.mostrarMensaje(handler.get("usuario.actualizar.sinusuario"));
                    return;
                }
                List<Respuesta> respuestas = recolectarRespuestas();
                if (respuestas.size() < 3) {
                    preguntasSeguridad.mostrarMensaje(handler.get("usuario.preguntas.minimas"));
                    return;
                }
                usuarioAEditar.setRespuestas(respuestas);
                try {
                    String nombre = registrarUsuario.getTxtNombre().getText().trim();
                    String fechaTexto = registrarUsuario.getTxtFecha().getText().trim();
                    String correo = registrarUsuario.getTxtCorreo().getText().trim();
                    String telefono = registrarUsuario.getTxtTelefono().getText().trim();

                    // Validar y convertir la fecha
                    GregorianCalendar fechaNacimiento = new GregorianCalendar();
                    try {
                        DateFormat formato = DateFormat.getDateInstance(DateFormat.MEDIUM, Contexto.getLocale());
                        Date fecha = formato.parse(fechaTexto);
                        fechaNacimiento.setTime(fecha);
                        usuarioAEditar.setFechanacimiento(fechaNacimiento);
                    } catch (ParseException ex) {
                        preguntasSeguridad.mostrarMensaje(handler.get("usuario.fecha.invalida"));
                        return;
                    }

                    // Asignar los nuevos valores
                    usuarioAEditar.setNombre(String.format("%-20s", nombre));
                    usuarioAEditar.setCorreo(String.format("%-20s", correo));
                    usuarioAEditar.setTelefono(telefono);
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

    /**
     * Configura el evento para recuperación de clave.
     */
    private void eventoRecuperarClave(){
        recuperarClave.limpiarCampos();
        recuperarClave.actualizarIdioma();
        final Usuario[] usuarioEncontrado = new Usuario[1];
        final Pregunta[] preguntaActual = new Pregunta[1];
        final Set<Pregunta> preguntasIntentadas = new HashSet<>();

        recuperarClave.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = recuperarClave.getTxtUsuario().getText().trim();
                Usuario usuario = usuarioDAO.buscarPorUsername(username);


                if (usuario != null && !usuario.getRespuestas().isEmpty()) {
                    usuarioEncontrado[0] = usuario;

                    Pregunta aleatoria = usuario.obtenerPreguntaParaRecuperacion();
                    while (preguntasIntentadas.contains(aleatoria) && preguntasIntentadas.size() < usuario.getRespuestas().size()) {
                        aleatoria = usuario.obtenerPreguntaParaRecuperacion(); // asegura pregunta no repetida
                    }
                    preguntaActual[0] = aleatoria;
                    preguntasIntentadas.add(aleatoria);

                    recuperarClave.getLblPregunta().setText(aleatoria.getTexto());
                    recuperarClave.getTxtUsuario().setEnabled(false);
                    recuperarClave.getBtnBuscar().setEnabled(false);
                    recuperarClave.getPanelAutenticar().setVisible(true);
                } else {
                    recuperarClave.mostrarMensaje(Contexto.getHandler().get("mensaje.usuario.noencontrado"));
                }
            }
        });

        recuperarClave.getBtnRecuperar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var handler = Contexto.getHandler();
                if (usuarioEncontrado[0] == null || preguntaActual[0] == null) {
                    recuperarClave.mostrarMensaje(handler.get("usuario.buscar.primero"));
                    return;
                }
                String respuesta = recuperarClave.getTxtPregunta().getText().trim();
                boolean esCorrecta = usuarioEncontrado[0].verificarRespuesta(respuesta);

                if (esCorrecta) {
                    String nuevaClave = JOptionPane.showInputDialog(
                            recuperarClave,
                            handler.get("usuario.clave.correcta"),
                            handler.get("usuario.registro.titulo"),
                            JOptionPane.PLAIN_MESSAGE
                    );

                    if (nuevaClave != null && !nuevaClave.trim().isEmpty()) {
                        if (Usuario.validarPasswordSegura(nuevaClave)) {
                            try {
                                usuarioEncontrado[0].cambiarPassword(nuevaClave);
                                usuarioDAO.actualizar(usuarioEncontrado[0]);
                                recuperarClave.mostrarMensaje(handler.get("usuario.clave.actualizada"));

                                recuperarClave.setVisible(false);
                                recuperarClave.getTxtUsuario().setEnabled(true);
                                recuperarClave.getBtnBuscar().setEnabled(true);
                                recuperarClave.getPanelAutenticar().setVisible(false);
                                loginView.getTxtUsername().setText("");
                                loginView.getTxtPassword().setText("");
                                loginView.setVisible(true);
                            } catch (Exception ex) {
                                preguntasSeguridad.mostrarMensaje(handler.get("usuario.actualizar.error") + ex.getMessage());
                            }
                        } else {
                            recuperarClave.getTxtUsuario().setEnabled(true);
                            recuperarClave.getBtnBuscar().setEnabled(true);
                            recuperarClave.getPanelAutenticar().setVisible(false);
                            loginView.getTxtUsername().setText("");
                            loginView.getTxtPassword().setText("");
                            recuperarClave.mostrarMensaje(handler.get("usuario.contrasenia.invalida"));
                        }
                    }

                    recuperarClave.setVisible(false);
                    loginView.getTxtUsername().setText("");
                    loginView.getTxtPassword().setText("");
                    loginView.setVisible(true);

                } else {
                    if (preguntasIntentadas.size() < 2) {
                        Pregunta nueva = usuarioEncontrado[0].obtenerPreguntaParaRecuperacion();
                        while (preguntasIntentadas.contains(nueva) && preguntasIntentadas.size() < usuarioEncontrado[0].getRespuestas().size()) {
                            nueva = usuarioEncontrado[0].obtenerPreguntaParaRecuperacion();
                        }
                        preguntaActual[0] = nueva;
                        preguntasIntentadas.add(nueva);

                        recuperarClave.getLblPregunta().setText(nueva.getTexto());
                        recuperarClave.getTxtPregunta().setText("");
                        recuperarClave.mostrarMensaje(handler.get("usuario.respuestas.incorrectas"));
                    } else {
                        recuperarClave.mostrarMensaje(handler.get("usuario.registro.error"));
                        recuperarClave.getBtnBuscar().setEnabled(true);
                        recuperarClave.getPanelAutenticar().setVisible(false);
                        recuperarClave.setVisible(false);
                        loginView.setVisible(true);
                    }
                }
            }
        });

    }

    //metodosLogin
    /**
     * Autentica al usuario con los datos ingresados en la vista de login.
     */
    private void autenticar() {
        String username = loginView.getTxtUsername().getText();

        String contrasenia = loginView.getTxtPassword().getText();
        contrasenia = String.format("%-20s", contrasenia);
        if (username.isEmpty() || contrasenia.isEmpty()) {
            loginView.mostrarMensaje(Contexto.getHandler().get("usuario.campos.vacios"));
            return;
        }

        usuario = usuarioDAO.autenticar(username, contrasenia);

        if (usuario == null) {
            loginView.mostrarMensaje(Contexto.getHandler().get("usuario.contrasena.incorrectos"));
        } else {
            loginView.dispose();
        }
    }

    // meotodsGesionUsuarios

    /**
     * Busca un usuario por nombre en la vista de gestión de usuarios.
     */
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

    /**
     * Lista los usuarios según la selección en la vista de gestión de usuarios.
     */
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

    /**
     * Carga los datos del usuario encontrado en la tabla de la vista de gestión.
     * @param usuario Usuario encontrado.
     */
    private void cargarUsuarioEncontrado(Usuario usuario) {
        DefaultTableModel modelo = (DefaultTableModel) gestionUsuarios.getTblUsuarios().getModel();
        // Clear the table first
        modelo.setRowCount(0);
        // Add the found user
        modelo.addRow(new Object[]{usuario.getRol(), usuario.getUsername(), usuario.getNombre(), usuario.getPassword()});
        // Clear the search field
        gestionUsuarios.getTxtBusqueda().setText("");
    }

    /**
     * Carga los usuarios con rol USUARIO en la tabla.
     */
    private void cargarClientes() {
        DefaultTableModel modelo = (DefaultTableModel) gestionUsuarios.getTblUsuarios().getModel();
        // Clear the table first
        modelo.setRowCount(0);
        // Load users with USUARIO role
        for (Usuario u : usuarioDAO.listarPorRol("USUARIO")) {
            modelo.addRow(new Object[]{u.getRol(), u.getUsername(),u.getNombre(), u.getPassword()});
        }
    }

    /**
     * Carga los usuarios con rol ADMINISTRADOR en la tabla.
     */
    private void cargarAdministradores() {
        DefaultTableModel modelo = (DefaultTableModel) gestionUsuarios.getTblUsuarios().getModel();
        // Clear the table first
        modelo.setRowCount(0);
        // Load users with ADMINISTRADOR role
        for (Usuario u : usuarioDAO.listarPorRol("ADMINISTRADOR")) {
            modelo.addRow(new Object[]{u.getRol(), u.getUsername(),u.getNombre(), u.getPassword()});
        }
    }

    /**
     * Carga todos los usuarios en la tabla.
     */
    private void cargarUsuarios() {
        DefaultTableModel modelo = (DefaultTableModel) gestionUsuarios.getTblUsuarios().getModel();
        // Clear the table first
        modelo.setRowCount(0);
        // Load all users
        for (Usuario u : usuarioDAO.listarTodos()) {
            modelo.addRow(new Object[]{u.getRol(), u.getUsername(),u.getNombre(), u.getPassword()});
        }
    }

    /**
     * Activa las acciones de edición y eliminación en la tabla de usuarios.
     */
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

    /**
     * Muestra las opciones de edición o eliminación para un usuario seleccionado.
     * @param username Nombre de usuario.
     * @param rol Rol del usuario.
     */
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

    /**
     * Edita los datos de un usuario seleccionado.
     * @param usernameOriginal Nombre de usuario original.
     * @param rol Rol del usuario.
     */
    public void editarUsuario(String usernameOriginal, Rol rol) {
        usuarioAEditar = usuarioDAO.buscarPorUsername(usernameOriginal); // Evita usar strings sueltos

        if (usuarioAEditar == null) {
            JOptionPane.showMessageDialog(null, Contexto.getHandler().get("usuario.no.encontrado"));
            return;
        }

        registrarUsuario.limpiarCampos();
        registrarUsuario.actualizarIdioma();

        // Cargar datos en los campos
        registrarUsuario.getTxtNombre().setText(usuarioAEditar.getNombre().trim());
        registrarUsuario.getTxtCorreo().setText(usuarioAEditar.getCorreo());
        registrarUsuario.getTxtTelefono().setText(usuarioAEditar.getTelefono());
        registrarUsuario.getTxtUsuario().setText(usuarioAEditar.getUsername());
        registrarUsuario.getTxtUsuario().setEnabled(false); // ¡Muy importante! No permitir cambiar username
        registrarUsuario.getTxtPassword().setText(usuarioAEditar.getPassword());

        if (usuarioAEditar.getFechanacimiento() != null) {
            String fechaStr = FormateadorUtils.formatearFecha(
                    usuarioAEditar.getFechanacimiento().getTime(), Contexto.getLocale());
            registrarUsuario.getTxtFecha().setText(fechaStr);
        } else {
            registrarUsuario.getTxtFecha().setText("");
        }

        registrarUsuario.setVisible(true);

        eventoRegistrarUsuario(true); // modo edición
    }


    /**
     * Elimina un usuario por nombre.
     * @param username Nombre de usuario.
     */
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

    /**
     * Crea un nuevo usuario desde la vista de gestión.
     */
    private void crearUsuarios() {
        JTextField cedulaField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JComboBox<String> rolBox = new JComboBox<>(new String[]{
                Contexto.getHandler().get("usuario.normal"),
                Contexto.getHandler().get("usuario.administrador")
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel(Contexto.getHandler().get("usuario.cedula")));
        panel.add(cedulaField);
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
            String cedula = cedulaField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            String rolSeleccionado = rolBox.getSelectedItem().toString();

            // Validaciones
            if (cedula.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(null, Contexto.getHandler().get("usuario.campos.vacios"));
                return;
            }

            if (!cedula.matches("\\d{10}")) {
                JOptionPane.showMessageDialog(null, Contexto.getHandler().get("usuario.cedula.invalida"));
                return;
            }

            Usuario nuevoUsuario = new Usuario(cedula, password, rolSeleccionado.equals(Contexto.getHandler().get("usuario.administrador")) ? Rol.ADMINISTRADOR : Rol.USUARIO);

            if (!nuevoUsuario.validarCedulaEcuatoriana()) {
                JOptionPane.showMessageDialog(null, Contexto.getHandler().get("usuario.cedula.invalida"));
                return;
            }

            if (!Usuario.validarPasswordSegura(password)) {
                JOptionPane.showMessageDialog(null, Contexto.getHandler().get("usuario.contrasenia.invalida"));
                return;
            }

            // Rellenar campos a longitud fija
            password = String.format("%-20s", password);
            nuevoUsuario.setPassword(password);

            try {
                usuarioDAO.crear(nuevoUsuario);
                JOptionPane.showMessageDialog(null, Contexto.getHandler().get("usuario.registro.exito"));
                listar();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, Contexto.getHandler().get("usuario.registro.error") + e.getMessage());
            }
        }
    }

    /**
     * Recolecta las respuestas de seguridad ingresadas en la vista.
     * @return Lista de respuestas.
     */
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

    /**
     * Valida si una fecha de nacimiento corresponde a una persona mayor de edad.
     * @param fechaNacimiento Fecha de nacimiento del usuario.
     * @return true si es mayor de edad (18 años o más), false en caso contrario.
     */
    private boolean esMayorDeEdad(GregorianCalendar fechaNacimiento) {
        GregorianCalendar fechaActual = new GregorianCalendar();
        GregorianCalendar fechaLimite = new GregorianCalendar();

        // Calcular la fecha hace 18 años
        fechaLimite.setTime(fechaActual.getTime());
        fechaLimite.add(GregorianCalendar.YEAR, -18);

        // Verificar si la fecha de nacimiento es anterior o igual a la fecha límite
        return fechaNacimiento.compareTo(fechaLimite) <= 0;
    }
}
