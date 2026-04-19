package com.juegomental.matematico.controlador;

import com.juegomental.matematico.modelo.Partida;
import com.juegomental.matematico.servicios.Navegador;
import javafx.fxml.FXML;

public class SelectorNivelController {

    private Partida partida = InicioController.getPartida();

    @FXML
    public void seleccionarNivel1(){ abrirNivel(1); }

    @FXML
    public void seleccionarNivel2(){ abrirNivel(2); }

    @FXML
    public void seleccionarNivel3(){ abrirNivel(3); }

    @FXML
    public void seleccionarNivel4(){ abrirNivel(4); }

    @FXML
    public void seleccionarNivel5(){ abrirNivel(5); }

    @FXML
    public void seleccionarNivel6(){ abrirNivel(6); }

    @FXML
    public void seleccionarNivel7(){ abrirNivel(7); }

    private void abrirNivel(int nivel){

        partida.setNivelActual(nivel);

        Navegador.cambiarVista("InterfazAdivinarRegla.fxml");
    }
}