package com.juegomental.matematico.modelo;

public class Nivel {

    private int id;
    private int dificultad;
    private boolean completado;
    private Regla regla;
    private TablaIO tablaIO;

    public Nivel(int id, int dificultad) {
        this.id = id;
        this.dificultad = dificultad;
        this.completado = false;
        this.regla = new Regla(id, dificultad);
        this.tablaIO = new TablaIO();
    }

    public boolean iniciar() {
        tablaIO.limpiar();
        return true;
    }

    public Regla seleccionarRegla() {
        return regla;
    }

    public boolean verificarRespuestas() {

        for (int i = 0; i < tablaIO.getEntradas().size(); i++) {

            int entrada = tablaIO.getEntradas().get(i);
            int salidaUsuario = tablaIO.getSalidas().get(i);

            int correcta = regla.calcularOutput(entrada);

            if (salidaUsuario != correcta) {
                return false;
            }
        }

        completado = true;
        return true;
    }

    public TablaIO getTablaIO() {
        return tablaIO;
    }
}