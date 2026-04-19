package com.juegomental.matematico.controlador;

import com.juegomental.matematico.servicios.Navegador;
import javafx.fxml.FXML;

public class ReglasController {

    @FXML
    public void irSelectorNivel(){

        Navegador.cambiarVista("InterfazSelectorNiveles.fxml");

    }
}