package co.edu.poli.jmm.modelo;

import java.util.Arrays;
import java.util.Random;

/**
 * Representa un nivel del juego.
 * Cada nivel tiene una dificultad, una regla activa y su propia TablaIO.
 */
public class Nivel {

    private int      id;
    private int      dificultad;
    private boolean  completado;
    private Regla[]  reglas;          // pool de reglas disponibles para este nivel
    private TablaIO  tablaIO;

    // Regla seleccionada para la sesión actual
    private Regla reglaActiva;

    // Números prefijados que el jugador debe completar en la fase 2
    private static final int[] ENTRADAS_FASE2 = {1, 3, 5, 7, 10};

    public Nivel() {
        this.tablaIO    = new TablaIO();
        this.completado = false;
    }

    public Nivel(int id, int dificultad) {
        this();
        this.id         = id;
        this.dificultad = dificultad;
        // Cada nivel tiene 3 reglas posibles segun su dificultad.
        this.reglas = new Regla[]{
            new Regla(1, dificultad),
            new Regla(2, dificultad),
            new Regla(3, dificultad)
        };
    }

    // ── Lógica principal ──────────────────────────────────────────────────────

    /**
     * Inicializa el nivel: limpia la tabla y selecciona una regla.
     */
    public boolean iniciar() {
        tablaIO.limpiar();
        completado   = false;
        reglaActiva  = seleccionarRegla();
        return reglaActiva != null;
    }

    /**
     * Elige una regla al azar del pool disponible.
     */
    public Regla seleccionarRegla() {
        if (reglas == null || reglas.length == 0) return null;
        int idx = new Random().nextInt(reglas.length);
        reglaActiva = reglas[idx];
        return reglaActiva;
    }

    /**
     * Verifica si las respuestas del jugador (salidas) coinciden con las salidas
     * correctas según la regla activa.
     *
     * @param respuestasJugador lista de salidas ingresadas por el jugador en fase 2
     * @return true si TODAS las respuestas son correctas
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

    /** Entradas predefinidas que aparecen en la fase 2. */
    public int[] getEntradasFase2() {
        return Arrays.copyOf(ENTRADAS_FASE2, ENTRADAS_FASE2.length);
    }

    // ── Getters / Setters ─────────────────────────────────────────────────────

    public int getId()               { return id; }
    public void setId(int id)        { this.id = id; }

    public int getDificultad()                   { return dificultad; }
    public void setDificultad(int dificultad)    { this.dificultad = dificultad; }

    public boolean isCompletado()                    { return completado; }
    public void setCompletado(boolean completado)    { this.completado = completado; }

    public Regla[] getReglas()              { return reglas; }
    public void setReglas(Regla[] reglas)   { this.reglas = reglas; }

    public TablaIO getTablaIO()                { return tablaIO; }
    public void setTablaIO(TablaIO tablaIO)    { this.tablaIO = tablaIO; }

    public Regla getReglaActiva()                    { return reglaActiva; }
    public void setReglaActiva(Regla reglaActiva)    { this.reglaActiva = reglaActiva; }

    @Override
    public String toString() {
        return "Nivel{id=" + id + ", dificultad=" + dificultad +
               ", completado=" + completado + "}";
    }
}
