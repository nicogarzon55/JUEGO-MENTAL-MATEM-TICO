package co.edu.poli.jmm.modelo;

/**
 * Representa una regla matematica que transforma un numero de entrada
 * en un numero de salida.
 */
public class Regla {

    public static final int FACIL   = 1;
    public static final int MEDIO   = 2;
    public static final int DIFICIL = 3;

    private int id;
    private int dificultad;

    public Regla() {}

    public Regla(int id, int dificultad) {
        this.id = id;
        this.dificultad = dificultad;
    }

    /**
     * Aplica una de las tres reglas disponibles para la dificultad.
     */
    public int calcularOutput(int input) {
        return switch (dificultad) {
            case FACIL -> switch (id) {
                case 1  -> input * 2;           // y = 2x
                case 2  -> input + 5;           // y = x + 5
                case 3  -> input - 1;           // y = x - 1
                default -> input;
            };
            case MEDIO -> switch (id) {
                case 1  -> (input * input) + 1; // y = x^2 + 1
                case 2  -> (input * 3) + 2;     // y = 3x + 2
                case 3  -> (input * 2) - 3;     // y = 2x - 3
                default -> input;
            };
            case DIFICIL -> switch (id) {
                case 1  -> (input * 3) - 5;             // y = 3x - 5
                case 2  -> (input * input) - input + 2; // y = x^2 - x + 2
                case 3  -> (input * input * 2) + 1;     // y = 2x^2 + 1
                default -> input;
            };
            default -> input;
        };
    }

    /**
     * Descripcion legible de la regla para mostrarla al ganar.
     */
    public String getDescripcion() {
        return switch (dificultad) {
            case FACIL -> switch (id) {
                case 1  -> "y = 2x";
                case 2  -> "y = x + 5";
                case 3  -> "y = x - 1";
                default -> "y = x";
            };
            case MEDIO -> switch (id) {
                case 1  -> "y = x^2 + 1";
                case 2  -> "y = 3x + 2";
                case 3  -> "y = 2x - 3";
                default -> "y = x";
            };
            case DIFICIL -> switch (id) {
                case 1  -> "y = 3x - 5";
                case 2  -> "y = x^2 - x + 2";
                case 3  -> "y = 2x^2 + 1";
                default -> "y = x";
            };
            default -> "y = x";
        };
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getDificultad() { return dificultad; }
    public void setDificultad(int dificultad) { this.dificultad = dificultad; }

    @Override
    public String toString() {
        return "Regla{id=" + id + ", dificultad=" + dificultad +
               ", descripcion='" + getDescripcion() + "'}";
    }
}
