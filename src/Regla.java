import java.util.Random;
/**
 * Clase que representa una regla del juego.
 * 
 * Su función es seleccionar una regla de acuerdo con el nivel de dificultad
 * y aplicarla a un valor de entrada (In) para generar un valor de salida (Out).
 * 
 * Niveles:
 * 1 y 2 -> Fácil
 * 3 y 4 -> Intermedio
 * 5 y 6 -> Difícil
 */
public class Regla {

    private int nivel;
    private int reglaSeleccionada;
    private Random random;

    /**
     * Constructor de la clase Regla.
     * Recibe el nivel seleccionado y asigna
     * una regla aleatoria según su dificultad.
     */
    public Regla(int nivel) {
        this.nivel = nivel;
        this.random = new Random();
        this.reglaSeleccionada = generarReglaAleatoria();
    }

    /**
     * Genera una regla aleatoria según el nivel de dificultad.
     * Devuelve un número que identifica cuál regla fue seleccionada.
     */
    private int generarReglaAleatoria() {
        if (nivel == 1 || nivel == 2) {
            return random.nextInt(3) + 1; // reglas 1 a 3 - Fácil
        } else if (nivel == 3 || nivel == 4) {
            return random.nextInt(3) + 4; // reglas 4 a 6 - Intermedia
        } else {
            return random.nextInt(3) + 7; // reglas 7 a 9 - Difícil
        }
    }

    /**
     * Calcula el valor de salida (Out) a partir de un valor de entrada (In),
     * aplicando la regla asignada al nivel.
     */
    public int calcularOut(int in) {
        switch (reglaSeleccionada) {
            // Fácil
            case 1:
                return in + 2;
            case 2:
                return in * 2;
            case 3:
                return in - 1;

            // Intermedio
            case 4:
                return in * 2 + 3;
            case 5:
                return in * in;
            case 6:
                return in + 10;

            // Difícil
            case 7:
                return (in * 3) - 2;
            case 8:
                return (in * in) + 1;
            case 9:
                return (in + 4) * 2;

            default:
                return in;
        }
    }

    /**
     * Retorna el nivel asociado a la regla.
     */
    public int getNivel() {
        return nivel;
    }

    /**
     * Retorna el identificador de la regla seleccionada.
     */
    public int getReglaSeleccionada() {
        return reglaSeleccionada;
    }
}