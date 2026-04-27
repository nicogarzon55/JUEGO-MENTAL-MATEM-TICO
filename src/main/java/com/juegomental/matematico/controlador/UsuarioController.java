package com.juegomental.matematico.controlador;

import com.juegomental.matematico.vista.Navegacion;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UsuarioController {

    @FXML
    private TextField txf_nameuser;

    @FXML
    public void iniciarJuego() {

        if (txf_nameuser.getText().isEmpty()) {
            System.out.println("Ingrese nombre");
            return;
        }

        Stage stage = (Stage) txf_nameuser.getScene().getWindow();

        Navegacion.cambiarEscena(
                stage,
                "/com/juegomental/matematico/vista/SelectorNiveles.fxml"
        );
    }
}