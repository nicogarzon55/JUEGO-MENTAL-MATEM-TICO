package co.edu.poli.jmm.controladores;

import co.edu.poli.jmm.modelo.Juego;
import co.edu.poli.jmm.modelo.Nivel;
import co.edu.poli.jmm.servicios.DAOJugador;
import co.edu.poli.jmm.servicios.DAOPartida;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 * Controlador de InterfazJuegoPrincipal.fxml — Fase 2.
 *
 * El jugador ve una tabla con entradas prefijadas y debe escribir
 * la salida correcta para cada una según la regla que dedujo.
 */
public class JuegoPrincipalController {

    // ── FXML ──────────────────────────────────────────────────────────────────
    @FXML private TableView<FilaFase2>               tabla;
    @FXML private TableColumn<FilaFase2, Number>     colEntrada;
    @FXML private TableColumn<FilaFase2, String>     colSalida;
    @FXML private Label                              lblPuntaje;
    @FXML private Label                              lblNivel;
    @FXML private Button                             btnTema;

    // ── Modelo ────────────────────────────────────────────────────────────────
    private Juego  juego;
    private Nivel  nivelActivo;
    private boolean modoOscuro = false;

    private final ObservableList<FilaFase2> filas = FXCollections.observableArrayList();

    // ── Inner class para las filas de fase 2 ─────────────────────────────────
    public static class FilaFase2 {
        private final SimpleIntegerProperty entrada;
        private final SimpleStringProperty  salidaTexto; // el jugador escribe aquí
        private Integer salidaIngresada = null;
        private Boolean respuestaCorrecta = null;

        public FilaFase2(int entrada) {
            this.entrada     = new SimpleIntegerProperty(entrada);
            this.salidaTexto = new SimpleStringProperty("");
        }

        public int getEntrada()       { return entrada.get(); }
        public String getSalidaTexto(){ return salidaTexto.get(); }
        public void setSalidaTexto(String v) { salidaTexto.set(v); }
        public SimpleIntegerProperty entradaProperty()  { return entrada; }
        public SimpleStringProperty  salidaTextoProperty() { return salidaTexto; }

        public Integer getSalidaIngresada()            { return salidaIngresada; }
        public void setSalidaIngresada(Integer v)      { this.salidaIngresada = v; }
        public Boolean getRespuestaCorrecta()          { return respuestaCorrecta; }
        public void setRespuestaCorrecta(Boolean v)    { this.respuestaCorrecta = v; }
    }

    // ── Inicialización ────────────────────────────────────────────────────────

    @FXML
    public void initialize() {
        colEntrada.setCellValueFactory(data -> data.getValue().entradaProperty());
        colEntrada.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Number item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(String.valueOf(item.intValue()));
                    setAlignment(javafx.geometry.Pos.CENTER);
                    aplicarEstiloResultado(this, getTableRow().getItem());
                }
            }
        });

        // Columna editable de salida
        colSalida.setCellValueFactory(data -> data.getValue().salidaTextoProperty());
        colSalida.setCellFactory(col -> new TableCell<>() {
            private final TextField tf = new TextField();
            {
                tf.setNodeOrientation(javafx.geometry.NodeOrientation.LEFT_TO_RIGHT);
                tf.setAlignment(javafx.geometry.Pos.CENTER);
                tf.setTextFormatter(new TextFormatter<>(change ->
                    change.getControlNewText().matches("-?\\d*") ? change : null
                ));
                tf.textProperty().addListener((obs, v, n) -> {
                    if (getTableRow() != null && getTableRow().getItem() != null) {
                        getTableRow().getItem().setSalidaTexto(n);
                    }
                });
            }
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setStyle("");
                } else {
                    String value = item == null ? "" : item;
                    if (!tf.isFocused() && !tf.getText().equals(value)) {
                        tf.setText(value);
                        tf.positionCaret(tf.getText().length());
                    }
                    aplicarEstiloResultado(this, getTableRow().getItem());
                    tf.setStyle(estiloCampoResultado(getTableRow().getItem()));
                    setGraphic(tf);
                }
            }
        });
        colSalida.setEditable(true);
        tabla.setEditable(true);
        tabla.setNodeOrientation(javafx.geometry.NodeOrientation.LEFT_TO_RIGHT);
        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Encabezados claros para los títulos de la tabla
        javafx.scene.control.Label headerEntrada = new javafx.scene.control.Label("ENTRADA");
        headerEntrada.setStyle("-fx-text-fill: #374151; -fx-font-weight: bold; -fx-font-size: 14;");
        colEntrada.setGraphic(headerEntrada);
        javafx.scene.control.Label headerSalida = new javafx.scene.control.Label("SALIDA (escribe aquí)");
        headerSalida.setStyle("-fx-text-fill: #374151; -fx-font-weight: bold; -fx-font-size: 14;");
        colSalida.setGraphic(headerSalida);

        tabla.setItems(filas);
    }

    /** Inyecta el modelo desde la pantalla anterior. */
    public void setJuego(Juego juego) {
        this.juego       = juego;
        this.nivelActivo = juego.getNivelActivo();
        cargarEntradasFase2();
        actualizarUI();
    }

    // ── Carga de datos ────────────────────────────────────────────────────────

    private void cargarEntradasFase2() {
        filas.clear();
        for (int entrada : nivelActivo.getEntradasFase2()) {
            filas.add(new FilaFase2(entrada));
        }
    }

    // ── Eventos ───────────────────────────────────────────────────────────────

    @FXML
    private void onRevisarRespuestas() {
        // Recoger las respuestas ingresadas
        int[] respuestas = new int[filas.size()];
        for (int i = 0; i < filas.size(); i++) {
            String texto = filas.get(i).getSalidaTexto().trim();
            if (texto.isBlank()) {
                mostrarAlerta("Campos incompletos", "Por favor completa todos los valores de salida.");
                return;
            }
            try {
                respuestas[i] = Integer.parseInt(texto);
                filas.get(i).setSalidaIngresada(respuestas[i]);
            } catch (NumberFormatException e) {
                mostrarAlerta("Valor inválido", "La salida en la fila " + (i + 1) + " no es un número.");
                return;
            }
        }

        // Verificar con el modelo
        boolean gano = nivelActivo.verificarRespuestas(respuestas);

        // Colorear filas (verde=correcto, rojo=incorrecto)
        colorearFilas(respuestas);

        if (gano) {
            // Sumar 5 puntos bonus
            juego.getJugador().sumarBonus();

            // Guardar en BD
            new DAOJugador().updatePuntaje(juego.getJugador());
            new DAOPartida().guardarPartida(juego.getJugador(), juego.getNivelActual() + 1, true);

            // Navegar a pantalla de victoria
            navegarA("/co/edu/poli/jmm/vista/InterfazJuegoGanado.fxml",
                     controller -> ((JuegoGanadoController) controller).setJuego(juego));
        } else {
            // Guardar puntaje actual y mostrar derrota
            new DAOPartida().guardarPartida(juego.getJugador(), juego.getNivelActual() + 1, false);
            mostrarAlerta("Incorrecto", "Algunas respuestas no son correctas. " +
                          "Puntaje guardado: " + juego.getJugador().getPuntaje());
        }
    }

    @FXML
    private void onRegresar() {
        navegarA("/co/edu/poli/jmm/vista/InterfazAdivinarReglaJuego.fxml",
                 controller -> ((AdivinarReglaController) controller).setJuego(juego));
    }

    @FXML
    private void onToggleTema() {
        modoOscuro = !modoOscuro;
        btnTema.setText(modoOscuro ? "☀" : "🌙");
        // Aplicar estilos de tema oscuro/claro a la escena
        Scene scene = tabla.getScene();
        if (modoOscuro) {
            scene.getRoot().setStyle("-fx-background-color: #111827;");
        } else {
            scene.getRoot().setStyle("-fx-background-color: #f3f4f6;");
        }
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private void colorearFilas(int[] respuestas) {
        for (int i = 0; i < filas.size(); i++) {
            int correcto = nivelActivo.getReglaActiva().calcularOutput(filas.get(i).getEntrada());
            boolean ok   = i < respuestas.length && respuestas[i] == correcto;
            filas.get(i).setRespuestaCorrecta(ok);
        }
        tabla.refresh();
    }

    private void aplicarEstiloResultado(TableCell<FilaFase2, ?> cell, FilaFase2 fila) {
        Boolean correcta = fila == null ? null : fila.getRespuestaCorrecta();
        if (correcta == null) {
            cell.setStyle("-fx-background-color: #1f2937; -fx-text-fill: #e5e7eb;");
            return;
        }

        String fondo = correcta ? "#14532d" : "#7f1d1d";
        cell.setStyle("-fx-background-color: " + fondo + "; -fx-text-fill: white; -fx-font-weight: bold;");
    }

    private String estiloCampoResultado(FilaFase2 fila) {
        Boolean correcta = fila == null ? null : fila.getRespuestaCorrecta();
        if (correcta == null) {
            return "-fx-background-color: #1f2937; -fx-text-fill: white; "
                + "-fx-prompt-text-fill: #9ca3af; -fx-border-color: #374151;";
        }

        String fondo = correcta ? "#14532d" : "#7f1d1d";
        return "-fx-background-color: " + fondo + "; -fx-text-fill: white; "
            + "-fx-font-weight: bold; -fx-border-color: transparent;";
    }

    private void actualizarUI() {
        if (juego != null && juego.getJugador() != null) {
            lblPuntaje.setText("Puntos: " + juego.getJugador().getPuntaje());
        }
        if (nivelActivo != null) {
            lblNivel.setText("Nivel " + (juego.getNivelActual() + 1) + " — Completa los valores de Salida");
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // ── Navegación ────────────────────────────────────────────────────────────

    @FunctionalInterface
    interface ControllerCallback { void configure(Object c); }

    private void navegarA(String fxml, ControllerCallback callback) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();
            if (callback != null) callback.configure(loader.getController());
            Stage stage = (Stage) tabla.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            System.err.println("[JuegoPrincipalController] Navegación fallida: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
