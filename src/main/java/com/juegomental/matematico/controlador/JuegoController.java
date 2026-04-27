package com.juegomental.matematico.controlador;

import com.juegomental.matematico.vista.Navegacion;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class JuegoController {

    @FXML
    private TableView<?> tabla;

    @FXML
    public void verificar() {
        boolean correcto = true;

        if (!correcto) {
            tabla.setStyle("-fx-border-color: red; -fx-border-width: 3;");
        } else {
            tabla.setStyle("-fx-border-color: green; -fx-border-width: 3;");
        }
    }

    @FXML
    public void regresar() {
        Stage stage = (Stage) tabla.getScene().getWindow();

        Navegacion.cambiarEscena(
                stage,
                "/com/juegomental/matematico/vista/SelectorNiveles.fxml"
        );
    }

    @FXML
    public void volver() {
        regresar();
    }
}