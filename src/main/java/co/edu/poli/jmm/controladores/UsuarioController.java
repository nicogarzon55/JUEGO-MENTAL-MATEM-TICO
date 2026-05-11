package co.edu.poli.jmm.controladores;

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
 * Controla la pantalla inicial.
 * Valida el nombre del jugador, crea la partida y envia el modelo a la pantalla de niveles.
 */
public class UsuarioController {

    @FXML private TextField txtNombre;
    @FXML private Label nameLabel;

    /** Modelo compartido durante toda la partida. */
    private final Juego juego = new Juego();

    /**
     * Actualiza la vista previa del saludo mientras el usuario escribe su nombre.
     */
    @FXML
    public void initialize() {
        txtNombre.textProperty().addListener((obs, viejo, nuevo) ->
            nameLabel.setText(nuevo.isBlank() ? "..." : nuevo)
        );
    }

    /**
     * Inicia el juego si el nombre es valido y guarda el jugador en la base de datos.
     */
    @FXML
    private void onJugar() {
        String nombre = txtNombre.getText().trim();
        if (nombre.isBlank()) {
            nameLabel.setText("Ingresa tu nombre");
            nameLabel.setStyle("-fx-text-fill: #ef4444; -fx-font-size: 14;");
            return;
        }

        juego.setNombreJugador(nombre);
        juego.iniciarJuego();

        DAOJugador daoJugador = new DAOJugador();
        daoJugador.create(juego.getJugador());

        navegarA("/co/edu/poli/jmm/vista/InterfazSelectorNivel.fxml",
            controller -> ((SelectorNivelController) controller).setJuego(juego));
    }

    /**
     * Permite configurar el controlador de la vista destino antes de mostrarla.
     */
    @FunctionalInterface
    interface ControllerCallback {
        void configure(Object controller);
    }

    /**
     * Cambia a otra vista FXML conservando el modelo de juego cuando se requiere.
     */
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
