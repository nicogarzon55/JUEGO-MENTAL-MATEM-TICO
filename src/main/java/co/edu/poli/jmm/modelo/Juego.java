package co.edu.poli.jmm.modelo;

/**
 * Clase principal que coordina la sesión de juego.
 * Contiene el jugador activo, los niveles disponibles y el estado global.
 */
public class Juego {

    private String  nombreJugador;
    private int     nivelActual;
    private Jugador jugador;
    private Nivel[] nivel;

    public Juego() {
        // 7 niveles: 1-2 Fácil, 3-4 Medio, 5-6-7 Difícil
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

    // ── Lógica principal ──────────────────────────────────────────────────────

    /**
     * Inicia una nueva partida con el nombre recibido.
     * Crea el jugador y arranca el nivel seleccionado.
     */
    public boolean iniciarJuego() {
        jugador = new Jugador(1, nombreJugador);
        return nivel[nivelActual].iniciar();
    }

    /**
     * Actualiza el puntaje del jugador sumando o restando pts.
     */
    public int actualizarPuntaje(int pts) {
        int nuevo = jugador.getPuntaje() + pts;
        jugador.setPuntaje(Math.max(0, nuevo));
        return jugador.getPuntaje();
    }

    /**
     * Avanza al siguiente nivel si existe.
     * @return el nuevo Nivel, o null si no hay más niveles
     */
    public Nivel avanzarNivel() {
        nivelActual++;
        if (nivelActual < nivel.length) {
            nivel[nivelActual].iniciar();
            return nivel[nivelActual];
        }
        return null; // juego completado
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

    /** Retorna true si el jugador aún tiene puntos. */
    public boolean jugadorTienePuntos() {
        return jugador != null && jugador.getPuntaje() > 0;
    }

    /** Retorna true si hay un nivel siguiente disponible. */
    public boolean hayNivelSiguiente() {
        return nivelActual + 1 < nivel.length;
    }

    // ── Getters / Setters ─────────────────────────────────────────────────────

    public String getNombreJugador()                      { return nombreJugador; }
    public void setNombreJugador(String nombreJugador)    { this.nombreJugador = nombreJugador; }

    public int getNivelActual()                  { return nivelActual; }
    public void setNivelActual(int nivelActual)  { this.nivelActual = nivelActual; }

    public Jugador getJugador()              { return jugador; }
    public void setJugador(Jugador jugador)  { this.jugador = jugador; }

    public Nivel[] getNivel()              { return nivel; }
    public void setNivel(Nivel[] nivel)    { this.nivel = nivel; }
}
