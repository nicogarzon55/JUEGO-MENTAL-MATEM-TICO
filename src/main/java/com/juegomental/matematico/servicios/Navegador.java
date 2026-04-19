package com.juegomental.matematico.servicios;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Navegador {

    private static Stage stage;

    public static void setStage(Stage s){
        stage = s;
    }

    public static void cambiarVista(String fxml){

        try{

            Parent root = FXMLLoader.load(
                    Navegador.class.getResource(
                            "/com/juegomental/matematico/vista/" + fxml
                    )
            );

            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.show();

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}