package com.juegomental.matematico.servicios;

import java.util.Random;

public class LogicaJuegoService {

    private Random random = new Random();

    public int generarRegla(int nivel){

        switch(nivel){

            case 1: // SUMA
                return random.nextInt(10) + 1;

            case 2: // RESTA
                return random.nextInt(10) + 1;

            case 3: // MULTIPLICACION
                return random.nextInt(5) + 2;

            case 4: // DIVISION
                return random.nextInt(5) + 2;

            case 5: // SUMA Y MULTIPLICACION
                return random.nextInt(5) + 2;

            case 6:
                return random.nextInt(10) + 3;

            case 7:
                return random.nextInt(15) + 5;

            default:
                return 1;
        }
    }

    public int aplicarRegla(int numero, int regla, int nivel){

        switch(nivel){

            case 1:
                return numero + regla;

            case 2:
                return numero - regla;

            case 3:
                return numero * regla;

            case 4:
                return numero / regla;

            case 5:
                return (numero + regla) * 2;

            case 6:
                return (numero * regla) + 3;

            case 7:
                return (numero * regla) - 2;

            default:
                return numero;
        }
    }

}