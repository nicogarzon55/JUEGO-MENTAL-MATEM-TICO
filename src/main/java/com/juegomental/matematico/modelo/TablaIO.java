package com.juegomental.matematico.modelo;

import java.util.ArrayList;
import java.util.List;

public class TablaIO {

    private List<Integer> entradas = new ArrayList<>();
    private List<Integer> salidas = new ArrayList<>();

    public boolean agregarEntrada(int val) {
        return entradas.add(val);
    }

    public boolean agregarSalida(int val) {
        return salidas.add(val);
    }

    public boolean limpiar() {
        entradas.clear();
        salidas.clear();
        return true;
    }

    public List<Integer> getEntradas() { return entradas; }
    public List<Integer> getSalidas() { return salidas; }
}