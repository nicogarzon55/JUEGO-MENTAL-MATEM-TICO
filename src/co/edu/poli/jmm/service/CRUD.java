package co.edu.poli.jmm.service;

import java.util.List;

public interface CRUD<T, K> {

    String create(T t);

    T readOne(K id);

    List<T> readAll();
}