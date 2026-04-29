package co.edu.poli.jmm.model;

public class Regla {

    private int id;
    private int dificultad;

    public Regla(int id, int dificultad) {
        this.id = id;
        this.dificultad = dificultad;
    }

    public int calcularOutput(int input) {

        switch (dificultad) {

            case 1:
            	return input - 9;
            case 2:
            	return input + 20 -9;
            case 3:
                return input + 2;

            case 4:
            	return input * 2;
            case 5:
                return input / 6;

            case 6:
                return input * 2 + 3;

            case 7:
                return (input + 5) * 2;

            default:
                return input;
        }
    }
    
    public int getId() {
        return id;
    }

    public int getDificultad() {
        return dificultad;
    }
}