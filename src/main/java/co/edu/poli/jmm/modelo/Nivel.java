package co.edu.poli.jmm.modelo;

import java.util.Arrays;
import java.util.Random;

/**
 * Representa un nivel jugable.
 * Cada nivel define una dificultad, un grupo de reglas posibles y la tabla de pruebas.
 */
public class Nivel {

    private int id;
    private int dificultad;
    private boolean completado;
    private Regla[] reglas;
    private TablaIO tablaIO;

    /** Regla elegida para la ronda actual del nivel. */
    private Regla reglaActiva;

    /** Entradas que el jugador debe resolver en la segunda fase. */
    private static final int[] ENTRADAS_FASE2 = {1, 3, 5, 7, 10};

    /**
     * Crea un nivel vacio con una tabla lista para recibir datos.
     */
    public Nivel() {
        this.tablaIO = new TablaIO();
        this.completado = false;
    }

    /**
     * Crea un nivel con tres reglas posibles para la dificultad indicada.
     */
    public Nivel(int id, int dificultad) {
        this();
        this.id = id;
        this.dificultad = dificultad;
        this.reglas = new Regla[]{
            new Regla(1, dificultad),
            new Regla(2, dificultad),
            new Regla(3, dificultad)
        };
    }

    /**
     * Limpia el estado del nivel y selecciona una regla nueva.
     */
    public boolean iniciar() {
        tablaIO.limpiar();
        completado = false;
        reglaActiva = seleccionarRegla();
        return reglaActiva != null;
    }

    /**
     * Elige una regla al azar entre las reglas disponibles del nivel.
     */
    public Regla seleccionarRegla() {
        if (reglas == null || reglas.length == 0) return null;
        int idx = new Random().nextInt(reglas.length);
        reglaActiva = reglas[idx];
        return reglaActiva;
    }

    /**
     * Verifica si las salidas escritas por el jugador coinciden con la regla activa.
     *
     * @param respuestasJugador salidas ingresadas en la segunda fase
     * @return true si todas las respuestas son correctas
     */
    public boolean verificarRespuestas(int[] respuestasJugador) {
        if (reglaActiva == null) return false;
        for (int i = 0; i < ENTRADAS_FASE2.length; i++) {
            int correcto = reglaActiva.calcularOutput(ENTRADAS_FASE2[i]);
            if (i >= respuestasJugador.length || respuestasJugador[i] != correcto) {
                return false;
            }
        }
        completado = true;
        return true;
    }

    /** Retorna una copia de las entradas usadas en la segunda fase. */
    public int[] getEntradasFase2() {
        return Arrays.copyOf(ENTRADAS_FASE2, ENTRADAS_FASE2.length);
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getDificultad() { return dificultad; }
    public void setDificultad(int dificultad) { this.dificultad = dificultad; }

    public boolean isCompletado() { return completado; }
    public void setCompletado(boolean completado) { this.completado = completado; }

    public Regla[] getReglas() { return reglas; }
    public void setReglas(Regla[] reglas) { this.reglas = reglas; }

    public TablaIO getTablaIO() { return tablaIO; }
    public void setTablaIO(TablaIO tablaIO) { this.tablaIO = tablaIO; }

    public Regla getReglaActiva() { return reglaActiva; }
    public void setReglaActiva(Regla reglaActiva) { this.reglaActiva = reglaActiva; }

    @Override
    public String toString() {
        return "Nivel{id=" + id + ", dificultad=" + dificultad
            + ", completado=" + completado + "}";
    }
}
