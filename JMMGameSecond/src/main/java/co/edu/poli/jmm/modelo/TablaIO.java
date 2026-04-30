package co.edu.poli.jmm.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Almacena los pares entrada→salida que el jugador ha probado o que
 * debe completar en la fase 2.
 */
public class TablaIO {

    private List<Integer> entradas;
    private List<Integer> salidas;

    public TablaIO() {
        this.entradas = new ArrayList<>();
        this.salidas  = new ArrayList<>();
    }

    // ── Operaciones ───────────────────────────────────────────────────────────

    /**
     * Agrega un valor de entrada.
     * @return true si se agregó correctamente
     */
    public boolean agregarEntrada(int val) {
        return entradas.add(val);
    }

    /**
     * Agrega un valor de salida.
     * @return true si se agregó correctamente
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

    /** Número de filas registradas. */
    public int size() {
        return entradas.size();
    }

    // ── Getters / Setters ─────────────────────────────────────────────────────

    public List<Integer> getEntradas()                   { return entradas; }
    public void setEntradas(List<Integer> entradas)      { this.entradas = entradas; }

    public List<Integer> getSalidas()                    { return salidas; }
    public void setSalidas(List<Integer> salidas)        { this.salidas = salidas; }

    @Override
    public String toString() {
        return "TablaIO{entradas=" + entradas + ", salidas=" + salidas + "}";
    }
}
