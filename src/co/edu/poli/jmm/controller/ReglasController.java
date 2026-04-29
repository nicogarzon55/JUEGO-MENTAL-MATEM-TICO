package co.edu.poli.jmm.controller;

import co.edu.poli.jmm.app.Navegacion;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ReglasController {

    @FXML
    private VBox contenido;

    @FXML
    private Label btnTema;

    @FXML
    public void initialize() {

        mostrarDescripcion();

        btnTema.setOnMouseClicked(e -> {
            Navegacion.cambiarTema(btnTema.getScene());
        });
    }

    // =========================
    // CONTENIDO DINÁMICO
    // =========================

    @FXML
    public void mostrarDescripcion() {

        contenido.getChildren().clear();

        contenido.getChildren().add(new Label(
            "Descubre la regla matemática oculta que transforma los números.\n" +
            "Prueba diferentes valores y analiza los resultados."
        ));
    }

    @FXML
    public void mostrarInstrucciones() {

        contenido.getChildren().clear();

        contenido.getChildren().add(new Label("1. Ingresa números"));
        contenido.getChildren().add(new Label("2. Observa resultados"));
        contenido.getChildren().add(new Label("3. Descubre la regla"));
        contenido.getChildren().add(new Label("4. Valídala"));
    }

    @FXML
    public void mostrarReglas() {

        contenido.getChildren().clear();

        contenido.getChildren().add(new Label("Empiezas con 10 puntos"));
        contenido.getChildren().add(new Label("Cada intento cuesta 1 punto"));
        contenido.getChildren().add(new Label("Si aciertas ganas puntos extra"));
    }

    // =========================
    // CERRAR
    // =========================

    @FXML
    public void cerrar() {

        Stage stage = (Stage) contenido.getScene().getWindow();

        Navegacion.cambiarEscena("SelectorNiveles.fxml", stage);
    }
}