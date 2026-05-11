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
 * Controla la seleccion de nivel.
 * Recibe el juego ya creado y permite empezar desde cualquiera de los siete niveles.
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

    /**
     * Recibe el modelo que viene desde la pantalla de usuario.
     */
    public void setJuego(Juego juego) {
        this.juego = juego;
    }

    @FXML private void irNivel1() { iniciarDesdeNivel(0); }
    @FXML private void irNivel2() { iniciarDesdeNivel(1); }
    @FXML private void irNivel3() { iniciarDesdeNivel(2); }
    @FXML private void irNivel4() { iniciarDesdeNivel(3); }
    @FXML private void irNivel5() { iniciarDesdeNivel(4); }
    @FXML private void irNivel6() { iniciarDesdeNivel(5); }
    @FXML private void irNivel7() { iniciarDesdeNivel(6); }

    /**
     * Ajusta el nivel activo, lo reinicia y abre la primera fase.
     */
    private void iniciarDesdeNivel(int indice) {
        juego.setNivelActual(indice);
        juego.getNivelActivo().iniciar();

        navegarA("/co/edu/poli/jmm/vista/InterfazAdivinarReglaJuego.fxml",
            controller -> ((AdivinarReglaController) controller).setJuego(juego));
    }

    /**
     * Muestra la guia del juego en una ventana modal.
     */
    @FXML
    private void abrirReglas() {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(btn_level1.getScene().getWindow());
        dialog.setTitle("Guia del Juego");
        dialog.setResizable(false);

        GameRulesWindow rulesView = new GameRulesWindow(dialog::close);
        Scene scene = new Scene(rulesView, 800, 640);
        scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
        dialog.setScene(scene);
        dialog.showAndWait();
    }

    /**
     * Permite configurar el controlador de la vista destino antes de mostrarla.
     */
    @FunctionalInterface
    interface ControllerCallback {
        void configure(Object controller);
    }

    /**
     * Cambia a otra vista FXML y conserva el modelo de juego.
     */
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
