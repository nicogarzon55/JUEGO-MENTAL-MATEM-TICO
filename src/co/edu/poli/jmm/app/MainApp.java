package co.edu.poli.jmm.app;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {

        Navegacion.cambiarEscena("InterfazUsuario.fxml", stage);

        stage.setTitle("JMM - Juego Mental Matemático");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}