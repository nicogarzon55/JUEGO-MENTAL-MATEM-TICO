package co.edu.poli.jmm.dao;

import java.util.ArrayList;
import java.util.List;

import co.edu.poli.jmm.model.Juego;
import co.edu.poli.jmm.service.CRUD;

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