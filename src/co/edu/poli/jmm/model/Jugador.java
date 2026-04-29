package co.edu.poli.jmm.model;

public class Jugador {

    private int id;
    private String nombre;
    private int puntaje;

    public Jugador(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.puntaje = 0;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public int getPuntaje() { return puntaje; }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }
}