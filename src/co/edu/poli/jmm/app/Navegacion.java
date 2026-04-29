package co.edu.poli.jmm.app;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import view.GameRulesWindow;

public class Navegacion {

    private static boolean darkMode = true;

    public static void cambiarEscena(String fxml, Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(
                Navegacion.class.getResource("/view/" + fxml)
            );

            Parent root = loader.load();

            Scene scene = new Scene(root);

            if (darkMode) {
                scene.getStylesheets().add(
                    Navegacion.class.getResource("/css/dark.css").toExternalForm()
                );
            } else {
                scene.getStylesheets().add(
                    Navegacion.class.getResource("/css/light.css").toExternalForm()
                );
            }

            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔥 CAMBIAR TEMA
    public static void cambiarTema(Scene scene) {

        scene.getStylesheets().clear();

        if (darkMode) {
            scene.getStylesheets().add(
                Navegacion.class.getResource("/css/light.css").toExternalForm()
            );
        } else {
            scene.getStylesheets().add(
                Navegacion.class.getResource("/css/dark.css").toExternalForm()
            );
        }

        darkMode = !darkMode;
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