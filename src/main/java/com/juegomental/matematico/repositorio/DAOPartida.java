package com.juegomental.matematico.repositorio;

import java.util.ArrayList;
import java.util.List;

import com.juegomental.matematico.modelo.Juego;
import com.juegomental.matematico.servicios.CRUD;

public class DAOPartida implements CRUD<Juego, Integer> {

    private List<Juego> lista = new ArrayList<>();

    @Override
    public String create(Juego j) {
        lista.add(j);
        return "OK";
    }

    @Override
    public Juego readOne(Integer id) {
        return lista.get(id);
    }

    @Override
    public List<Juego> readAll() {
        return lista;
    }
}