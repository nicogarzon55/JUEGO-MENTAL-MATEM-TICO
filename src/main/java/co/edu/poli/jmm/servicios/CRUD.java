package co.edu.poli.jmm.servicios;

import java.util.List;

/**
 * Interfaz genérica de operaciones CRUD.
 *
 * @param <T> tipo de entidad
 * @param <K> tipo de clave primaria
 */
public interface CRUD<T, K> {

    /** Persiste la entidad y retorna un mensaje de resultado. */
    String create(T t);

    /** Busca y retorna una entidad por su clave primaria. */
    T readOne(K id);

    /** Retorna todas las entidades almacenadas. */
    List<T> readAll();
}
