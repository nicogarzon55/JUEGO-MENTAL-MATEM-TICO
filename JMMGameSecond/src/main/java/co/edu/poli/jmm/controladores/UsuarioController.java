package co.edu.poli.jmm.controladores;

import co.edu.poli.jmm.controladores.SelectorNivelController;
import co.edu.poli.jmm.modelo.Juego;
import co.edu.poli.jmm.servicios.DAOJugador;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controlador de InterfazUsuarioFinal.fxml
 * Permite al jugador ingresar su nombre y comenzar la partida.
 */
public class UsuarioController {

    @FXML private TextField txtNombre;
    @FXML private Label     nameLabel;

    /** Referencia compartida al modelo del juego. */
    private final Juego juego = new Juego();

    // ── Inicialización ────────────────────────────────────────────────────────

    @FXML
    public void initialize() {
        // Actualizar el label de vista previa mientras el usuario escribe
        txtNombre.textProperty().addListener((obs, viejo, nuevo) -> {
            nameLabel.setText(nuevo.isBlank() ? "..." : nuevo);
        });
    }

    // ── Eventos ───────────────────────────────────────────────────────────────

    @FXML
    private void onJugar() {
        String nombre = txtNombre.getText().trim();
        if (nombre.isBlank()) {
            nameLabel.setText("¡Ingresa tu nombre!");
            nameLabel.setStyle("-fx-text-fill: #ef4444; -fx-font-size: 14;");
            return;
        }

        // Configurar el juego con el nombre ingresado
        juego.setNombreJugador(nombre);
        juego.iniciarJuego();

        // Guardar jugador en BD
        DAOJugador daoJugador = new DAOJugador();
        daoJugador.create(juego.getJugador());

        // Navegar al selector de nivel
        navegarA("/co/edu/poli/jmm/vista/InterfazSelectorNivel.fxml",
                 controller -> ((SelectorNivelController) controller).setJuego(juego));
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
