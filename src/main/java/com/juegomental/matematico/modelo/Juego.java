package com.juegomental.matematico.modelo;

public class Juego {

    private String nombreJugador;
    private int nivelActual;
    private Jugador jugador;
    private Nivel[] niveles;

    public Juego(String nombreJugador) {
        this.nombreJugador = nombreJugador;
        this.jugador = new Jugador(1, nombreJugador);
        this.niveles = new Nivel[7];

        for (int i = 0; i < 7; i++) {
            niveles[i] = new Nivel(i + 1, i + 1);
        }

        nivelActual = 0;
    }

    public boolean iniciarJuego() {
        return niveles[nivelActual].iniciar();
    }

    public int actualizarPuntaje(int pts) {
        jugador.setPuntaje(jugador.getPuntaje() + pts);
        return jugador.getPuntaje();
    }

    public Nivel avanzarNivel() {
        if (nivelActual < niveles.length - 1) {
            nivelActual++;
        }
        return niveles[nivelActual];
    }

    public Nivel reiniciarNivel() {
        niveles[nivelActual].iniciar();
        return niveles[nivelActual];
    }

    public Nivel getNivelActual() {
        return niveles[nivelActual];
    }
}