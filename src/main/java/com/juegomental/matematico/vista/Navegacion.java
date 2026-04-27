package com.juegomental.matematico.vista;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Navegacion {

    public static void cambiarEscena(Stage stage, String ruta) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    Navegacion.class.getResource(ruta)
            );
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}