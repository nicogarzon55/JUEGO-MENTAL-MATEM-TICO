package com.juegomental.matematico.modelo;

import java.util.Random;

public class Partida {

    private int nivel;
    private int puntos;
    private int numeroInicial;
    private int numeroObjetivo;
    private int operador;
    private int valorOperacion;

    Random random = new Random();

    public Partida() {
        this.nivel = 1;
        this.puntos = 10;
    }

    public void iniciarNivel(int nivelSeleccionado) {

        this.nivel = nivelSeleccionado;

        numeroInicial = random.nextInt(20) + 1;

        switch (nivel) {

            case 1:
                operador = random.nextInt(2); // 0 suma / 1 resta
                valorOperacion = random.nextInt(10) + 1;

                if (operador == 0) {
                    numeroObjetivo = numeroInicial + valorOperacion;
                } else {
                    numeroObjetivo = numeroInicial - valorOperacion;
                }
                break;

            case 2:
                valorOperacion = random.nextInt(5) + 2;
                numeroObjetivo = numeroInicial * valorOperacion;
                break;

            case 3:
                valorOperacion = random.nextInt(5) + 2;
                numeroInicial = valorOperacion * (random.nextInt(10) + 1);
                numeroObjetivo = numeroInicial / valorOperacion;
                break;

            case 4:
                numeroInicial = (random.nextInt(10) + 1);
                numeroObjetivo = (int) Math.sqrt(numeroInicial * numeroInicial);
                break;

            case 5:
                valorOperacion = random.nextInt(5) + 2;

                int paso1 = numeroInicial * valorOperacion;
                int paso2 = paso1 / valorOperacion;
                numeroObjetivo = paso2 - (paso2 / 2);
                break;
        }
    }

    public int evaluarRespuesta(int respuesta) {

        if (respuesta == numeroObjetivo) {
            puntos += 25;
            return 1;
        }

        return 0;
    }

    public void usarIntento() {

        puntos -= 2;

        if (puntos < 0) {
            puntos = 0;
        }
    }

    public int getNumeroInicial() {
        return numeroInicial;
    }

    public int getNumeroObjetivo() {
        return numeroObjetivo;
    }

    public int getPuntos() {
        return puntos;
    }

    public int getNivel() {
        return nivel;
    }
}