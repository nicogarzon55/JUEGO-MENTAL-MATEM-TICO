package co.edu.poli.jmm.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Guarda las entradas y salidas usadas durante un nivel.
 * Se usa como historial de pruebas en la primera fase.
 */
public class TablaIO {

    private List<Integer> entradas;
    private List<Integer> salidas;

    /**
     * Crea una tabla vacia para registrar pruebas.
     */
    public TablaIO() {
        this.entradas = new ArrayList<>();
        this.salidas = new ArrayList<>();
    }

    /**
     * Agrega un valor de entrada.
     *
     * @return true si se agrego correctamente
     */
    public boolean agregarEntrada(int val) {
        return entradas.add(val);
    }

    /**
     * Agrega un valor de salida.
     *
     * @return true si se agrego correctamente
     */
    public boolean agregarSalida(int val) {
        return salidas.add(val);
    }

    /** Limpia ambas listas. */
    public boolean limpiar() {
        entradas.clear();
        salidas.clear();
        return true;
    }

    /** Numero de entradas registradas. */
    public int size() {
        return entradas.size();
    }

    public List<Integer> getEntradas() { return entradas; }
    public void setEntradas(List<Integer> entradas) { this.entradas = entradas; }

    public List<Integer> getSalidas() { return salidas; }
    public void setSalidas(List<Integer> salidas) { this.salidas = salidas; }

    @Override
    public String toString() {
        return "TablaIO{entradas=" + entradas + ", salidas=" + salidas + "}";
    }
}
