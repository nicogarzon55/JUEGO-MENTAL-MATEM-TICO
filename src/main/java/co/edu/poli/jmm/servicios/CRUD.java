package co.edu.poli.jmm.servicios;

import java.util.List;

/**
 * Contrato basico para clases de acceso a datos.
 *
 * @param <T> tipo de entidad que se almacena
 * @param <K> tipo de clave con la que se consulta
 */
public interface CRUD<T, K> {

    /** Guarda una entidad y retorna un mensaje con el resultado. */
    String create(T t);

    /** Busca una entidad por su clave primaria. */
    T readOne(K id);

    /** Lista todas las entidades disponibles. */
    List<T> readAll();
}
