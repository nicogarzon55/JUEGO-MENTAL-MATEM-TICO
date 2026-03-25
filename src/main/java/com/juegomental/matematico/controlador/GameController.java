package com.juegomental.matematico.controlador;

import com.juegomental.matematico.modelo.Partida;

public class GameController {

    private Partida partida;

    public GameController() {
        partida = new Partida();
    }

    public void iniciarJuego() {
        partida = new Partida();
    }

    public void seleccionarNivel(int nivel) {
        partida.iniciarNivel(nivel);
    }

    public int obtenerNumeroInicial() {
        return partida.getNumeroInicial();
    }

    public int obtenerPuntos() {
        return partida.getPuntos();
    }

    public int verificarRespuesta(int respuestaUsuario) {
        return partida.evaluarRespuesta(respuestaUsuario);
    }

    public void usarIntento() {
        partida.usarIntento();
    }
}