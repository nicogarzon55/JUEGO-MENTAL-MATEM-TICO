package co.edu.poli.jmm.modelo;

/**
 * Representa al jugador que participa en una partida.
 * Guarda su identificador, nombre y puntaje actual.
 */
public class Jugador {

    private int id;
    private String nombre;
    private int puntaje;

    /**
     * Crea un jugador con el puntaje inicial por defecto.
     */
    public Jugador() {
        this.puntaje = 10;
    }

    /**
     * Crea un jugador identificado por la base de datos o por la sesion actual.
     */
    public Jugador(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.puntaje = 10;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getPuntaje() { return puntaje; }
    public void setPuntaje(int puntaje) { this.puntaje = puntaje; }

    /**
     * Resta un punto por intento en la primera fase.
     *
     * @return true si pudo restar el punto; false si el puntaje ya estaba en cero
     */
    public boolean restarPunto() {
        if (puntaje > 0) {
            puntaje--;
            return true;
        }
        return false;
    }

    /** Suma la bonificacion por superar un nivel. */
    public void sumarBonus() {
        puntaje += 5;
    }

    @Override
    public String toString() {
        return "Jugador{id=" + id + ", nombre='" + nombre + "', puntaje=" + puntaje + "}";
    }
}
