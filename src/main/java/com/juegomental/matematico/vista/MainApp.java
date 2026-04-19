package com.juegomental.matematico.vista;

import com.juegomental.matematico.servicios.Navegador;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/juegomental/matematico/vista/InterfazJuegoInicio.fxml")
        );

        Scene scene = new Scene(loader.load());

        stage.setTitle("Juego Mental Matemático");
        stage.setScene(scene);

        Navegador.setStage(stage);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}