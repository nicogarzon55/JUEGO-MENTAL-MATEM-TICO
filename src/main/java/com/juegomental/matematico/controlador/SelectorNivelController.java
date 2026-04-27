package com.juegomental.matematico.controlador;

import com.juegomental.matematico.vista.Navegacion;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class SelectorNivelController {

    private void irNivel(Button btn) {
        Stage stage = (Stage) btn.getScene().getWindow();

        Navegacion.cambiarEscena(
                stage,
                "/com/juegomental/matematico/vista/InterfazJuegoPrincipal.fxml"
        );
    }

    @FXML public void irNivel1() { }
    @FXML public void irNivel2() { }
    @FXML public void irNivel3() { }
    @FXML public void irNivel4() { }
    @FXML public void irNivel5() { }
    @FXML public void irNivel6() { }
    @FXML public void irNivel7() { }
}