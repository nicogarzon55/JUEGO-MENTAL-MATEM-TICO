package co.edu.poli.jmm;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Punto de entrada de la aplicacion.
 * Carga la primera vista del juego y prepara la ventana principal de JavaFX.
 */
public class Main extends Application {

    /**
     * Construye la escena inicial y la muestra en la ventana recibida por JavaFX.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(
            getClass().getResource("/co/edu/poli/jmm/vista/InterfazUsuarioFinal.fxml")
        );

        primaryStage.setTitle("JMM - Juego de Reglas Matematicas");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Inicia el ciclo de vida de JavaFX.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
