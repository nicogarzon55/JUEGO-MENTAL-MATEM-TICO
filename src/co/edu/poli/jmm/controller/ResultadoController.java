package co.edu.poli.jmm.controller;

import co.edu.poli.jmm.app.Navegacion;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class ResultadoController {

    @FXML
    private TableView<?> tablaResultados;

    @FXML
    public void verPuntaje() {
        System.out.println("Puntaje mostrado");
    }

    @FXML
    public void irSelector() {

        Stage stage = (Stage) tablaResultados.getScene().getWindow();

        Navegacion.cambiarEscena("SelectorNiveles.fxml", stage);
    }

    @FXML
    public void siguienteNivel() {

        Stage stage = (Stage) tablaResultados.getScene().getWindow();

        System.out.println("Siguiente nivel");

        Navegacion.cambiarEscena("InterfazJuegoPrincipal.fxml", stage);
    }
    
    @FXML
    public void abrirReglas() {
        Navegacion.abrirReglas();
    }
}