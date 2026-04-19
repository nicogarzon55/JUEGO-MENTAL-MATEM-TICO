package com.juegomental.matematico.controlador;

import com.juegomental.matematico.modelo.Partida;
import com.juegomental.matematico.servicios.LogicaJuegoService;

public class InicioController {

    private static Partida partida = new Partida();

    public static Partida getPartida(){
        return partida;
    }

    public void iniciarNivel(int nivel){

        LogicaJuegoService logica = new LogicaJuegoService();

        partida.setNivelActual(nivel);

        int regla = logica.generarRegla(nivel);

        partida.setReglaActual(regla);
    }
}