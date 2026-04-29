package co.edu.poli.jmm.controller;

import co.edu.poli.jmm.app.Navegacion;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class UsuarioController {

    @FXML
    private TextField txf_nameuser;

    @FXML
    private Label btnTema;
    
    @FXML
    private Label nameLabel;

    @FXML
    public void initialize() {

        // 🔥 mostrar nombre en tiempo real
        txf_nameuser.textProperty().addListener((obs, oldValue, newValue) -> {

            if (newValue == null || newValue.trim().isEmpty()) {
                nameLabel.setText("Usuario");
            } else {
                nameLabel.setText(newValue);
            }
        });

        // 🌙 botón tema
        btnTema.setOnMouseClicked(e -> {
            Navegacion.cambiarTema(btnTema.getScene());
        });
    }
    
    @FXML
    public void abrirReglas() {
        Navegacion.abrirReglas();
    }

    @FXML
    public void iniciarJuego() {

        if (txf_nameuser.getText().isEmpty()) {
            System.out.println("Ingrese nombre");
            return;
        }

        Stage stage = (Stage) txf_nameuser.getScene().getWindow();

        Navegacion.cambiarEscena("SelectorNiveles.fxml", stage);
    }
    
}