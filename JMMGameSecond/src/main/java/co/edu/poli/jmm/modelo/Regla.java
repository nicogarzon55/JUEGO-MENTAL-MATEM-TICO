package co.edu.poli.jmm.modelo;

/**
 * Representa una regla matemática que transforma un número de entrada en uno de salida.
 *
 * Dificultades disponibles:
 *  1 – Fácil   : salida = entrada * 2
 *  2 – Medio   : salida = entrada^2 + 1
 *  3 – Difícil : salida = (entrada * 3) - 5
 *
 * Si se necesitan más reglas, agregar casos en calcularOutput() y en el enum/constantes.
 */
public class Regla {

    // ── Constantes de dificultad ──────────────────────────────────────────────
    public static final int FACIL   = 1;
    public static final int MEDIO   = 2;
    public static final int DIFICIL = 3;

    private int id;
    private int dificultad;

    public Regla() {}

    public Regla(int id, int dificultad) {
        this.id          = id;
        this.dificultad  = dificultad;
    }

    // ── Lógica principal ──────────────────────────────────────────────────────

    /**
     * Aplica la regla al número de entrada y retorna la salida.
     * @param input número ingresado por el jugador
     * @return resultado de aplicar la regla
     */
    public int calcularOutput(int input) {
        return switch (dificultad) {
            case FACIL   -> input * 2;                  // y = 2x
            case MEDIO   -> (input * input) + 1;        // y = x² + 1
            case DIFICIL -> (input * 3) - 5;            // y = 3x - 5
            default      -> input;                      // identidad (fallback)
        };
    }

    /** Descripción legible de la regla (para mostrar al ganar). */
    public String getDescripcion() {
        return switch (dificultad) {
            case FACIL   -> "y = 2x";
            case MEDIO   -> "y = x² + 1";
            case DIFICIL -> "y = 3x − 5";
            default      -> "y = x";
        };
    }

    // ── Getters / Setters ─────────────────────────────────────────────────────

    public int getId()                   { return id; }
    public void setId(int id)            { this.id = id; }

    public int getDificultad()                   { return dificultad; }
    public void setDificultad(int dificultad)    { this.dificultad = dificultad; }

    @Override
    public String toString() {
        return "Regla{id=" + id + ", dificultad=" + dificultad +
               ", descripcion='" + getDescripcion() + "'}";
    }
}
