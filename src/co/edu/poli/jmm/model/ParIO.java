package co.edu.poli.jmm.model;

public class ParIO {

    private int input;
    private int output;
    
    private boolean correcto;

    public boolean isCorrecto() {
        return correcto;
    }

    public void setCorrecto(boolean correcto) {
        this.correcto = correcto;
    }

    public ParIO(int input, int output) {
        this.input = input;
        this.output = output;
    }

    public int getInput() {
        return input;
    }

    public int getOutput() {
        return output;
    }

    // 🔥 ESTE MÉTODO TE FALTA
    public void setOutput(int output) {
        this.output = output;
    }
}