package co.edu.poli.jmm.controladores;

import co.edu.poli.jmm.modelo.Juego;
import co.edu.poli.jmm.modelo.Nivel;
import co.edu.poli.jmm.modelo.TablaIO;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 * Controlador de InterfazAdivinarReglaJuego.fxml — Fase 1.
 *
 * El jugador ingresa números y observa los resultados para deducir la regla.
 * Cada intento resta 1 punto. Si llega a 0 puntos → pierde.
 */
public class AdivinarReglaController {

    // ── FXML ──────────────────────────────────────────────────────────────────
    @FXML private TextField              txtEntrada;
    @FXML private Button                 btnIngresar;
    @FXML private Button                 btnConozcaRegla;
    @FXML private TableView<FilaTabla>   tablaVista;
    @FXML private TableColumn<FilaTabla, Number> colEntrada;
    @FXML private TableColumn<FilaTabla, Number> colSalida;
    @FXML private Label                  lblPuntaje;
    @FXML private Label                  lblNivel;

    // ── Modelo ────────────────────────────────────────────────────────────────
    private Juego juego;
    private Nivel nivelActivo;
    private final ObservableList<FilaTabla> filas = FXCollections.observableArrayList();

    // ── Inner class para la tabla ─────────────────────────────────────────────
    public static class FilaTabla {
        private final SimpleIntegerProperty entrada;
        private final SimpleIntegerProperty salida;

        public FilaTabla(int entrada, int salida) {
            this.entrada = new SimpleIntegerProperty(entrada);
            this.salida  = new SimpleIntegerProperty(salida);
        }
        public int getEntrada()  { return entrada.get(); }
        public int getSalida()   { return salida.get(); }
        public SimpleIntegerProperty entradaProperty() { return entrada; }
        public SimpleIntegerProperty salidaProperty()  { return salida; }
    }

    // ── Inicialización ────────────────────────────────────────────────────────

    @FXML
    public void initialize() {
        colEntrada.setCellValueFactory(data -> data.getValue().entradaProperty());
        colSalida.setCellValueFactory(data  -> data.getValue().salidaProperty());

        // Encabezados oscuros para los títulos de la tabla
        javafx.scene.control.Label headerEntrada = new javafx.scene.control.Label("ENTRADA");
        headerEntrada.setStyle("-fx-text-fill: #374151; -fx-font-weight: bold;");
        colEntrada.setGraphic(headerEntrada);
        javafx.scene.control.Label headerSalida = new javafx.scene.control.Label("SALIDA");
        headerSalida.setStyle("-fx-text-fill: #374151; -fx-font-weight: bold;");
        colSalida.setGraphic(headerSalida);

        tablaVista.setItems(filas);

        // Solo números enteros permitidos en el campo de texto
        txtEntrada.textProperty().addListener((obs, viejo, nuevo) -> {
            if (!nuevo.matches("-?\\d*")) txtEntrada.setText(viejo);
        });
    }

    /** Llamado desde UsuarioController para inyectar el modelo. */
    public void setJuego(Juego juego) {
        this.juego       = juego;
        this.nivelActivo = juego.getNivelActivo();
        actualizarUI();
    }

    // ── Eventos ───────────────────────────────────────────────────────────────

    @FXML
    private void onIngresar() {
        String texto = txtEntrada.getText().trim();
        if (texto.isBlank()) return;

        int entrada;
        try {
            entrada = Integer.parseInt(texto);
        } catch (NumberFormatException e) {
            mostrarAlerta("Entrada inválida", "Por favor ingresa un número entero.");
            return;
        }

        // Verificar si el jugador tiene puntos
        if (!juego.jugadorTienePuntos()) {
            mostrarDerrota();
            return;
        }

        // Calcular salida según la regla del nivel
        int salida = nivelActivo.getReglaActiva().calcularOutput(entrada);

        // Registrar en el modelo
        TablaIO tabla = nivelActivo.getTablaIO();
        tabla.agregarEntrada(entrada);
        tabla.agregarSalida(salida);

        // Restar punto
        juego.getJugador().restarPunto();

        // Actualizar vista
        filas.add(new FilaTabla(entrada, salida));
        txtEntrada.clear();
        actualizarUI();

        // Si se quedó sin puntos después del intento → derrota
        if (!juego.jugadorTienePuntos()) {
            mostrarAlerta("¡Sin puntos!", "Te quedaste sin puntos. ¡Inténtalo de nuevo!");
            mostrarDerrota();
        }
    }

    @FXML
    private void onConozcaRegla() {
        // Pasar a la fase 2 de validación
        navegarA("/co/edu/poli/jmm/vista/InterfazJuegoPrincipal.fxml",
                 controller -> ((JuegoPrincipalController) controller).setJuego(juego));
    }

    @FXML
    private void onVolver() {
        navegarA("/co/edu/poli/jmm/vista/InterfazSelectorNivel.fxml",
                 controller -> ((SelectorNivelController) controller).setJuego(juego));
    }

    // ── Helpers de UI ─────────────────────────────────────────────────────────

    private void actualizarUI() {
        if (juego != null && juego.getJugador() != null) {
            lblPuntaje.setText("Puntos: " + juego.getJugador().getPuntaje());
        }
        if (nivelActivo != null) {
            lblNivel.setText("Nivel " + (juego.getNivelActual() + 1) +
                             " — Dificultad: " + labelDificultad(nivelActivo.getDificultad()));
        }
    }

    private String labelDificultad(int d) {
        return switch (d) {
            case 1  -> "Fácil";
            case 2  -> "Medio";
            case 3  -> "Difícil";
            default -> "?";
        };
    }

    private void mostrarDerrota() {
        // TODO: navegar a pantalla de derrota (si se crea) o mostrar alerta
        mostrarAlerta("Derrota", "Te quedaste sin puntos. El juego ha terminado.");
        navegarA("/co/edu/poli/jmm/vista/InterfazSelectorNivel.fxml",
                 controller -> ((SelectorNivelController) controller).setJuego(juego));
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
            Stage stage = (Stage) txtEntrada.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            System.err.println("[AdivinarReglaController] Navegación fallida: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
