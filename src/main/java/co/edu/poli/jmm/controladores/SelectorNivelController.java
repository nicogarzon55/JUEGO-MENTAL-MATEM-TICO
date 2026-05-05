package co.edu.poli.jmm.controladores;

import co.edu.poli.jmm.modelo.Juego;
import co.edu.poli.jmm.vista.GameRulesWindow;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Controlador de InterfazSelectorNivel.fxml
 *
 * Muestra los 7 botones de nivel y el botón de reglas del juego.
 * Recibe el objeto Juego ya configurado desde UsuarioController.
 */
public class SelectorNivelController {

    @FXML private Button btn_level1;
    @FXML private Button btn_level2;
    @FXML private Button btn_level3;
    @FXML private Button btn_level4;
    @FXML private Button btn_level5;
    @FXML private Button btn_level6;
    @FXML private Button btn_level7;

    private Juego juego;

    // ── Inyección del modelo ──────────────────────────────────────────────────

    public void setJuego(Juego juego) {
        this.juego = juego;
    }

    // ── Botones de nivel ──────────────────────────────────────────────────────

    @FXML private void irNivel1() { iniciarDesdeNivel(0); }
    @FXML private void irNivel2() { iniciarDesdeNivel(1); }
    @FXML private void irNivel3() { iniciarDesdeNivel(2); }
    @FXML private void irNivel4() { iniciarDesdeNivel(3); }
    @FXML private void irNivel5() { iniciarDesdeNivel(4); }
    @FXML private void irNivel6() { iniciarDesdeNivel(5); }
    @FXML private void irNivel7() { iniciarDesdeNivel(6); }

    private void iniciarDesdeNivel(int indice) {
        juego.setNivelActual(indice);
        juego.getNivelActivo().iniciar();

        navegarA(
            "/co/edu/poli/jmm/vista/InterfazAdivinarReglaJuego.fxml",
            controller -> ((AdivinarReglaController) controller).setJuego(juego)
        );
    }

    // ── Botón Reglas ──────────────────────────────────────────────────────────

    @FXML
    private void abrirReglas() {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(btn_level1.getScene().getWindow());
        dialog.setTitle("Guía del Juego");
        dialog.setResizable(false);

        GameRulesWindow rulesView = new GameRulesWindow(dialog::close);

        Scene scene = new Scene(rulesView, 800, 640);
        scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
        dialog.setScene(scene);
        dialog.showAndWait();
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

            Stage stage = (Stage) btn_level1.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            System.err.println("[SelectorNivelController] Error al navegar: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
