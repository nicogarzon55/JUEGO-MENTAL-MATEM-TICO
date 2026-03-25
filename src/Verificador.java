/**
 * Clase encargada de validar las respuestas del jugador.
 * Compara el valor de salida generado por la regla
 * con el valor ingresado por el usuario.
 */

public class Verificador {

    private Regla regla;

    /**
     * Constructor que recibe la regla actual del juego.
     */
    public Verificador(Regla regla) {
        this.regla = regla;
    }

    /**
     * Verifica si el valor de salida ingresado por el jugador es correcto.
     */
    public boolean validarOut(int in, int outJugador) {
        int outCorrecto = regla.calcularOut(in);
        return outCorrecto == outJugador;
    }
}