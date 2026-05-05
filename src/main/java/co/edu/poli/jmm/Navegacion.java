package co.edu.poli.jmm;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import co.edu.poli.jmm.vista.GameRulesWindow;

public class Navegacion {

    private static boolean darkMode = true;
    private static final String VISTA_BASE = "/co/edu/poli/jmm/vista/";

    public static void cambiarEscena(String fxml, Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(
                Navegacion.class.getResource(VISTA_BASE + fxml)
            );

            Parent root = loader.load();

            Scene scene = new Scene(root);
            aplicarTema(scene);

            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔥 CAMBIAR TEMA
    public static void cambiarTema(Scene scene) {

        scene.getStylesheets().clear();
        darkMode = !darkMode;
        aplicarTema(scene);
    }

    private static void aplicarTema(Scene scene) {
        String css = darkMode ? "/css/dark.css" : "/css/light.css";
        var recursoCss = Navegacion.class.getResource(css);

        if (recursoCss != null) {
            scene.getStylesheets().add(recursoCss.toExternalForm());
        }
    }
    
    public static void abrirReglas() {

        Stage popup = new Stage();

        GameRulesWindow rules = new GameRulesWindow(() -> popup.close());

        Scene scene = new Scene(rules);

        popup.setScene(scene);
        popup.setTitle("Reglas del Juego");
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setResizable(false);

        popup.showAndWait();
    }
}
