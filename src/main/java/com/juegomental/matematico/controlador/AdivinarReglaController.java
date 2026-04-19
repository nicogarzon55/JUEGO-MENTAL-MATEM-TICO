package com.juegomental.matematico.controlador;

import com.juegomental.matematico.modelo.Partida;
import com.juegomental.matematico.servicios.LogicaJuegoService;
import com.juegomental.matematico.servicios.Navegador;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AdivinarReglaController {

    @FXML
    private TextField campoEntrada;

    private LogicaJuegoService logica = new LogicaJuegoService();

    private Partida partida = InicioController.getPartida();

    @FXML
    public void ingresarNumero(){

        int numero = Integer.parseInt(campoEntrada.getText());

        int resultado = logica.aplicarRegla(
                numero,
                partida.getReglaActual(),
                partida.getNivelActual()
        );

        System.out.println("Resultado: " + resultado);
    }

    @FXML
    public void irJuegoPrincipal(){

        Navegador.cambiarVista("InterfazJuegoPrincipal.fxml");

    }

}