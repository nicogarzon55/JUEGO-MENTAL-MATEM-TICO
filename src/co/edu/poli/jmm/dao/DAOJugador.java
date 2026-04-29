package co.edu.poli.jmm.dao;

import java.util.ArrayList;
import java.util.List;

import co.edu.poli.jmm.model.Jugador;
import co.edu.poli.jmm.service.CRUD;

public class DAOJugador implements CRUD<Jugador, Integer> {

    private List<Jugador> lista = new ArrayList<>();

    @Override
    public String create(Jugador j) {
        lista.add(j);
        return "OK";
    }

    @Override
    public Jugador readOne(Integer id) {
        return lista.stream().filter(j -> j.getId() == id).findFirst().orElse(null);
    }

    @Override
    public List<Jugador> readAll() {
        return lista;
    }
}