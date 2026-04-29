package co.edu.poli.jmm.controller;

import co.edu.poli.jmm.app.Navegacion;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.control.Label;

public class SelectorNivelController {

    private static int nivelSeleccionado;

    @FXML
    private Label btnTema;
    
    @FXML
    public void abrirReglas() {
        Navegacion.abrirReglas();
    }

    @FXML
    public void initialize() {

        // 🔥 botón tema
        btnTema.setOnMouseClicked(e -> {
            Navegacion.cambiarTema(btnTema.getScene());
        });

        System.out.println("Selector cargado"); // debug
    }

    @FXML
    private void irNivel(MouseEvent event) {

        Node nodo = (Node) event.getSource();

        String id = nodo.getId(); // nivel1Card

        nivelSeleccionado = Integer.parseInt(id.replaceAll("[^0-9]", ""));

        System.out.println("Nivel seleccionado: " + nivelSeleccionado);

        Stage stage = (Stage) nodo.getScene().getWindow();

        Navegacion.cambiarEscena("InterfazJuegoPrincipal.fxml", stage);
    }

    public static int getNivelSeleccionado() {
        return nivelSeleccionado;
    }
    
}