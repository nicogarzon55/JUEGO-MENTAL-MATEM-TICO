package co.edu.poli.jmm.controladores;

import co.edu.poli.jmm.modelo.Juego;
import co.edu.poli.jmm.modelo.Nivel;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Controlador de InterfazJuegoGanado.fxml
 * Muestra el resumen de la partida ganada y ofrece opciones de continuar o reiniciar.
 */
public class JuegoGanadoController {

    // ── FXML ──────────────────────────────────────────────────────────────────
    @FXML private TableView<FilaResultado>           tablaResultados;
    @FXML private TableColumn<FilaResultado, Number> colEntrada;
    @FXML private TableColumn<FilaResultado, Number> colSalida;
    @FXML private Label                              lblTitulo;
    @FXML private Label                              lblPuntaje;
    @FXML private Button                             btnSiguienteNivel;
    @FXML private Button                             btnTema;

    // ── FXML Panel Puntaje Final ───────────────────────────────────────────────
    @FXML private VBox   panelPuntajeFinal;
    @FXML private Label  lblPuntajeFinal;
    @FXML private Label  lblMensajePuntaje;
    @FXML private Button btnVerPuntaje;

    // ── Modelo ────────────────────────────────────────────────────────────────
    private Juego   juego;
    private boolean modoOscuro      = false;
    private boolean puntajeVisible  = false;   // toggle del panel

    private final ObservableList<FilaResultado> filas = FXCollections.observableArrayList();

    // ── Inner class ───────────────────────────────────────────────────────────
    public static class FilaResultado {
        private final SimpleIntegerProperty entrada;
        private final SimpleIntegerProperty salida;

        public FilaResultado(int entrada, int salida) {
            this.entrada = new SimpleIntegerProperty(entrada);
            this.salida  = new SimpleIntegerProperty(salida);
        }
        public SimpleIntegerProperty entradaProperty() { return entrada; }
        public SimpleIntegerProperty salidaProperty()  { return salida; }
    }

    // ── Inicialización ────────────────────────────────────────────────────────

    @FXML
    public void initialize() {
        colEntrada.setCellValueFactory(data -> data.getValue().entradaProperty());
        colSalida.setCellValueFactory(data  -> data.getValue().salidaProperty());

        Label headerEntrada = new Label("Entrada");
        headerEntrada.setStyle("-fx-text-fill: #374151; -fx-font-weight: bold;");
        colEntrada.setGraphic(headerEntrada);

        Label headerSalida = new Label("Salida Correcta");
        headerSalida.setStyle("-fx-text-fill: #374151; -fx-font-weight: bold;");
        colSalida.setGraphic(headerSalida);

        tablaResultados.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tablaResultados.setItems(filas);

        // Panel oculto al inicio (ya viene así desde el FXML, pero lo reforzamos)
        panelPuntajeFinal.setVisible(false);
        panelPuntajeFinal.setManaged(false);
    }

    /** Inyecta el modelo al llegar desde la fase 2. */
    public void setJuego(Juego juego) {
        this.juego = juego;
        cargarTablaResultados();
        actualizarUI();
    }

    // ── Carga de datos ────────────────────────────────────────────────────────

    private void cargarTablaResultados() {
        filas.clear();
        Nivel nivel = juego.getNivelActivo();
        for (int entrada : nivel.getEntradasFase2()) {
            int salida = nivel.getReglaActiva().calcularOutput(entrada);
            filas.add(new FilaResultado(entrada, salida));
        }
    }

    // ── Eventos ───────────────────────────────────────────────────────────────

    /**
     * Muestra u oculta el panel de puntaje final.
     * El botón actúa como toggle: primera pulsación muestra, segunda oculta.
     */
    @FXML
    private void onVerPuntaje() {
        puntajeVisible = !puntajeVisible;

        if (puntajeVisible) {
            int pts = juego != null && juego.getJugador() != null
                    ? juego.getJugador().getPuntaje() : 0;

            lblPuntajeFinal.setText(String.valueOf(pts));
            lblMensajePuntaje.setText(obtenerMensajePorPuntaje(pts));

            panelPuntajeFinal.setVisible(true);
            panelPuntajeFinal.setManaged(true);
            btnVerPuntaje.setText("Ocultar puntaje");
        } else {
            panelPuntajeFinal.setVisible(false);
            panelPuntajeFinal.setManaged(false);
            btnVerPuntaje.setText("¿Quieres ver tu puntaje?");
        }
    }

    @FXML
    private void onSiguienteNivel() {
        Nivel siguiente = juego.avanzarNivel();
        if (siguiente == null) {
            mostrarAlerta("¡Felicidades!", "¡Completaste todos los niveles! Puntaje final: "
                    + juego.getJugador().getPuntaje());
            navegarA("/co/edu/poli/jmm/vista/InterfazUsuarioFinal.fxml", null);
        } else {
            navegarA("/co/edu/poli/jmm/vista/InterfazAdivinarReglaJuego.fxml",
                    controller -> ((AdivinarReglaController) controller).setJuego(juego));
        }
    }

    @FXML
    private void onIntentoNuevo() {
        juego.reiniciarNivel();
        juego.getJugador().setPuntaje(10);
        navegarA("/co/edu/poli/jmm/vista/InterfazAdivinarReglaJuego.fxml",
                controller -> ((AdivinarReglaController) controller).setJuego(juego));
    }

    @FXML
    private void onToggleTema() {
        modoOscuro = !modoOscuro;
        btnTema.setText(modoOscuro ? "☀" : "🌙");
        Scene scene = tablaResultados.getScene();
        scene.getRoot().setStyle(modoOscuro
                ? "-fx-background-color: linear-gradient(to bottom right, #052e16, #14532d);"
                : "-fx-background-color: linear-gradient(to bottom right, #ecfdf5, #d1fae5);");
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private void actualizarUI() {
        if (juego != null && juego.getJugador() != null) {
            int pts = juego.getJugador().getPuntaje();
            if (lblPuntaje != null) lblPuntaje.setText("Puntaje: " + pts);
        }
        Nivel nivel = juego.getNivelActivo();
        if (nivel != null && lblTitulo != null) {
            lblTitulo.setText("¡Descubriste la regla: " +
                    nivel.getReglaActiva().getDescripcion() + "!");
        }
        if (btnSiguienteNivel != null) {
            btnSiguienteNivel.setDisable(!juego.hayNivelSiguiente());
        }
    }

    /**
     * Devuelve un mensaje motivacional según el puntaje obtenido.
     * Ajusta los rangos a tu lógica de negocio.
     */
    private String obtenerMensajePorPuntaje(int pts) {
        if (pts >= 90) return "🌟 ¡Rendimiento perfecto!";
        if (pts >= 70) return "👏 ¡Excelente desempeño!";
        if (pts >= 50) return "👍 ¡Buen trabajo!";
        return "💪 ¡Sigue practicando!";
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
            Stage stage = (Stage) tablaResultados.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            System.err.println("[JuegoGanadoController] Navegación fallida: " + e.getMessage());
            e.printStackTrace();
        }
    }
}