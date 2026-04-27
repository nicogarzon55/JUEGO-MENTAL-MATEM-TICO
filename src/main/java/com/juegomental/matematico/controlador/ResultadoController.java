package com.juegomental.matematico.controlador;

import com.juegomental.matematico.vista.Navegacion;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class ResultadoController {

    @FXML
    private TableView<?> tablaResultados;

    @FXML
    public void verPuntaje() {
        System.out.println("Puntaje mostrado");
    }

    @FXML
    public void irSelector() {
        Stage stage = (Stage) tablaResultados.getScene().getWindow();

        Navegacion.cambiarEscena(
                stage,
                "/com/juegomental/matematico/vista/SelectorNiveles.fxml"
        );
    }

    @FXML
    public void siguienteNivel() {
        System.out.println("Siguiente nivel");
    }
}