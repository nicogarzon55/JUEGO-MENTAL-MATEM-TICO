package co.edu.poli.jmm.controladores;

import co.edu.poli.jmm.modelo.Juego;
import co.edu.poli.jmm.modelo.Jugador;
import co.edu.poli.jmm.servicios.DAOJugador;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Controlador de InterfazUsuario.fxml
 * Permite al jugador iniciar una partida nueva o continuar una existente.
 */
public class UsuarioController {

    @FXML private TextField txtNombre;
    @FXML private Label     nameLabel;
    @FXML private Label     lblMensaje;
    @FXML private Label     lblPuntajeRecuperado;
    @FXML private VBox      panelPuntajeRecuperado;
    @FXML private Button    btnJugar;
    @FXML private Button    btnContinuar;

    private final Juego      juego      = new Juego();
    private final DAOJugador daoJugador = new DAOJugador();

    // ── Inicialización ────────────────────────────────────────────────────────

    @FXML
    public void initialize() {
        txtNombre.textProperty().addListener((obs, viejo, nuevo) -> {
            nameLabel.setText(nuevo.isBlank() ? "..." : nuevo);
            ocultarMensaje();
            panelPuntajeRecuperado.setVisible(false);
            panelPuntajeRecuperado.setManaged(false);
        });
    }

    // ── Eventos ───────────────────────────────────────────────────────────────

    /**
     * NUEVO JUEGO — el nick NO debe existir en BD.
     * Si ya existe, bloquea y sugiere usar "Continuar Juego".
     */
    @FXML
    private void onJugar() {
        String nombre = txtNombre.getText().trim();
        if (!validarNombre(nombre)) return;

        // Verificar ANTES de crear
        Jugador existente = daoJugador.buscarPorNombre(nombre);
        if (existente != null) {
            mostrarMensajeError("❌ El nick \"" + nombre + "\" ya existe. Usa \"Continuar Juego\".");
            return;
        }

        // Nick libre → crear jugador nuevo
        juego.setNombreJugador(nombre);
        juego.iniciarJuego();

        String resultado = daoJugador.create(juego.getJugador());
        if (resultado.startsWith("DUPLICADO")) {
            mostrarMensajeError("❌ El nick \"" + nombre + "\" ya existe. Usa \"Continuar Juego\".");
            return;
        }

        navegarA("/co/edu/poli/jmm/vista/InterfazSelectorNivel.fxml",
                controller -> ((SelectorNivelController) controller).setJuego(juego));
    }

    /**
     * CONTINUAR JUEGO — el nick DEBE existir en BD.
     * Recupera el puntaje y navega con el jugador existente.
     */
    @FXML
    private void onContinuar() {
        String nombre = txtNombre.getText().trim();
        if (!validarNombre(nombre)) return;

        Jugador existente = daoJugador.buscarPorNombre(nombre);
        if (existente == null) {
            mostrarMensajeError("❌ Nick no encontrado. Usa \"Nuevo Juego\" para registrarte.");
            return;
        }

        // Mostrar panel con puntaje recuperado de BD
        lblPuntajeRecuperado.setText(String.valueOf(existente.getPuntaje()));
        panelPuntajeRecuperado.setVisible(true);
        panelPuntajeRecuperado.setManaged(true);
        mostrarMensajeOk("✅ ¡Bienvenido de vuelta, " + existente.getNombre() + "!");

        // iniciarJuego() PRIMERO para configurar niveles internos
        juego.iniciarJuego();

        // setJugador() DESPUÉS para que el puntaje de BD no sea pisado
        juego.setJugador(existente);

        navegarA("/co/edu/poli/jmm/vista/InterfazSelectorNivel.fxml",
                controller -> ((SelectorNivelController) controller).setJuego(juego));
    }

    // ── Helpers UI ────────────────────────────────────────────────────────────

    private boolean validarNombre(String nombre) {
        if (nombre.isBlank()) {
            mostrarMensajeError("¡Ingresa tu nombre!");
            return false;
        }
        return true;
    }

    private void mostrarMensajeError(String msg) {
        lblMensaje.setText(msg);
        lblMensaje.setStyle("-fx-font-size: 13px; -fx-text-fill: #ef4444;");
    }

    private void mostrarMensajeOk(String msg) {
        lblMensaje.setText(msg);
        lblMensaje.setStyle("-fx-font-size: 13px; -fx-text-fill: #10b981;");
    }

    private void ocultarMensaje() {
        lblMensaje.setText(" ");
        lblMensaje.setStyle("-fx-font-size: 13px; -fx-text-fill: transparent;");
    }

    // ── Navegación ────────────────────────────────────────────────────────────

    @FunctionalInterface
    interface ControllerCallback {
        void configure(Object controller);
    }

    private void navegarA(String fxml, ControllerCallback callback) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();
            if (callback != null) callback.configure(loader.getController());
            Stage stage = (Stage) txtNombre.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            System.err.println("[UsuarioController] Error al navegar: " + e.getMessage());
            e.printStackTrace();
        }
    }
}