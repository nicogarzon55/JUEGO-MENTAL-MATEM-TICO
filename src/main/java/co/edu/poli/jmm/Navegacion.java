package co.edu.poli.jmm;

import co.edu.poli.jmm.vista.GameRulesWindow;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Centraliza cambios de escena y acciones comunes de navegacion.
 * Tambien conserva el estado del tema visual para aplicarlo al cambiar de vista.
 */
public class Navegacion {

    private static boolean darkMode = true;
    private static final String VISTA_BASE = "/co/edu/poli/jmm/vista/";

    /**
     * Carga un archivo FXML desde la carpeta de vistas y lo coloca en el Stage actual.
     */
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

    /**
     * Alterna entre tema claro y oscuro en la escena indicada.
     */
    public static void cambiarTema(Scene scene) {
        scene.getStylesheets().clear();
        darkMode = !darkMode;
        aplicarTema(scene);
    }

    /**
     * Agrega la hoja de estilos que corresponde al tema activo.
     */
    private static void aplicarTema(Scene scene) {
        String css = darkMode ? "/css/dark.css" : "/css/light.css";
        var recursoCss = Navegacion.class.getResource(css);

        if (recursoCss != null) {
            scene.getStylesheets().add(recursoCss.toExternalForm());
        }
    }

    /**
     * Abre la ventana modal con la guia y reglas del juego.
     */
    public static void abrirReglas() {
        Stage popup = new Stage();
        GameRulesWindow rules = new GameRulesWindow(popup::close);
        Scene scene = new Scene(rules);

        popup.setScene(scene);
        popup.setTitle("Reglas del Juego");
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setResizable(false);
        popup.showAndWait();
    }
}
