package co.edu.poli.jmm.modelo;

/**
 * Representa al jugador del juego.
 */
public class Jugador {

    private int    id;
    private String nombre;
    private int    puntaje;

    public Jugador() {
        this.puntaje = 10; // puntos iniciales
    }

    public Jugador(int id, String nombre) {
        this.id      = id;
        this.nombre  = nombre;
        this.puntaje = 10;
    }

    // ── Getters / Setters ─────────────────────────────────────────────────────

    public int getId()               { return id; }
    public void setId(int id)        { this.id = id; }

    public String getNombre()               { return nombre; }
    public void setNombre(String nombre)    { this.nombre = nombre; }

    public int getPuntaje()              { return puntaje; }
    public void setPuntaje(int puntaje)  { this.puntaje = puntaje; }

    /** Resta 1 punto por cada intento en fase 1. Retorna false si ya no tiene puntos. */
    public boolean restarPunto() {
        if (puntaje > 0) {
            puntaje--;
            return true;
        }
        return false;
    }

    /** Suma 5 puntos de bonificación al ganar. */
    public void sumarBonus() {
        puntaje += 5;
    }

    @Override
    public String toString() {
        return "Jugador{id=" + id + ", nombre='" + nombre + "', puntaje=" + puntaje + "}";
    }
}
