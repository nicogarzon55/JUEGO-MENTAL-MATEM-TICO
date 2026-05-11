package co.edu.poli.jmm.modelo;

/**
 * Coordina una sesion de juego.
 * Contiene el jugador activo, los niveles disponibles y el estado global.
 */
public class Juego {

    private String nombreJugador;
    private int nivelActual;
    private Jugador jugador;
    private Nivel[] nivel;

    /**
     * Crea los siete niveles del recorrido y deja seleccionado el primero.
     */
    public Juego() {
        // Niveles 1-2: facil, 3-4: medio, 5-7: dificil.
        nivel = new Nivel[]{
            new Nivel(1, Regla.FACIL),
            new Nivel(2, Regla.FACIL),
            new Nivel(3, Regla.MEDIO),
            new Nivel(4, Regla.MEDIO),
            new Nivel(5, Regla.DIFICIL),
            new Nivel(6, Regla.DIFICIL),
            new Nivel(7, Regla.DIFICIL)
        };
        nivelActual = 0;
    }

    /**
     * Inicia una nueva partida con el nombre recibido.
     * Crea el jugador y prepara el nivel seleccionado.
     */
    public boolean iniciarJuego() {
        jugador = new Jugador(1, nombreJugador);
        return nivel[nivelActual].iniciar();
    }

    /**
     * Suma o resta puntos sin permitir que el puntaje quede por debajo de cero.
     */
    public int actualizarPuntaje(int pts) {
        int nuevo = jugador.getPuntaje() + pts;
        jugador.setPuntaje(Math.max(0, nuevo));
        return jugador.getPuntaje();
    }

    /**
     * Avanza al siguiente nivel si existe.
     *
     * @return nuevo nivel activo, o null si ya no hay mas niveles
     */
    public Nivel avanzarNivel() {
        nivelActual++;
        if (nivelActual < nivel.length) {
            nivel[nivelActual].iniciar();
            return nivel[nivelActual];
        }
        return null;
    }

    /**
     * Reinicia el nivel actual sin cambiar el puntaje.
     */
    public Nivel reiniciarNivel() {
        nivel[nivelActual].iniciar();
        return nivel[nivelActual];
    }

    /** Retorna el nivel activo. */
    public Nivel getNivelActivo() {
        return nivel[nivelActual];
    }

    /** Indica si el jugador aun puede seguir intentando. */
    public boolean jugadorTienePuntos() {
        return jugador != null && jugador.getPuntaje() > 0;
    }

    /** Indica si hay un nivel siguiente disponible. */
    public boolean hayNivelSiguiente() {
        return nivelActual + 1 < nivel.length;
    }

    public String getNombreJugador() { return nombreJugador; }
    public void setNombreJugador(String nombreJugador) { this.nombreJugador = nombreJugador; }

    public int getNivelActual() { return nivelActual; }
    public void setNivelActual(int nivelActual) { this.nivelActual = nivelActual; }

    public Jugador getJugador() { return jugador; }
    public void setJugador(Jugador jugador) { this.jugador = jugador; }

    public Nivel[] getNivel() { return nivel; }
    public void setNivel(Nivel[] nivel) { this.nivel = nivel; }
}
