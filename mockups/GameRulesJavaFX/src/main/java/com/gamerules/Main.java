package com.gamerules;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        GameRulesWindow gameRulesWindow = new GameRulesWindow();

        StackPane root = new StackPane(gameRulesWindow);
        root.setStyle(
            "-fx-background-color: linear-gradient(to bottom right, #f3f4f6, #e5e7eb);"
        );
        root.setPadding(new javafx.geometry.Insets(32));

        Scene scene = new Scene(root, 900, 700);
        scene.setFill(Color.web("#f3f4f6"));

        primaryStage.setTitle("Juego de Reglas - Lógica y Matemáticas");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(700);
        primaryStage.setMinHeight(600);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
