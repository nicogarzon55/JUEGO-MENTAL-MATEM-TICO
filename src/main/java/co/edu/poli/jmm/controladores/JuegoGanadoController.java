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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * Controla la pantalla de victoria.
 * Muestra la regla descubierta, el puntaje y la tabla con respuestas correctas.
 */
public class JuegoGanadoController {

    @FXML private TableView<FilaResultado> tablaResultados;
    @FXML private TableColumn<FilaResultado, Number> colEntrada;
    @FXML private TableColumn<FilaResultado, Number> colSalida;
    @FXML private Label lblTitulo;
    @FXML private Label lblPuntaje;
    @FXML private Button btnSiguienteNivel;
    @FXML private Button btnTema;

    private Juego juego;
    private boolean modoOscuro = false;

    private final ObservableList<FilaResultado> filas = FXCollections.observableArrayList();

    /**
     * Fila de solo lectura para mostrar las salidas correctas.
     */
    public static class FilaResultado {
        private final SimpleIntegerProperty entrada;
        private final SimpleIntegerProperty salida;

        public FilaResultado(int entrada, int salida) {
            this.entrada = new SimpleIntegerProperty(entrada);
            this.salida = new SimpleIntegerProperty(salida);
        }

        public SimpleIntegerProperty entradaProperty() { return entrada; }
        public SimpleIntegerProperty salidaProperty() { return salida; }
    }

    /**
     * Configura la tabla de resumen.
     */
    @FXML
    public void initialize() {
        colEntrada.setCellValueFactory(data -> data.getValue().entradaProperty());
        colSalida.setCellValueFactory(data -> data.getValue().salidaProperty());

        Label headerEntrada = new Label("Entrada");
        headerEntrada.setStyle("-fx-text-fill: #374151; -fx-font-weight: bold;");
        colEntrada.setGraphic(headerEntrada);

        Label headerSalida = new Label("Salida correcta");
        headerSalida.setStyle("-fx-text-fill: #374151; -fx-font-weight: bold;");
        colSalida.setGraphic(headerSalida);

        tablaResultados.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tablaResultados.setItems(filas);
    }

    /**
     * Recibe el modelo desde la fase de validacion y actualiza la pantalla.
     */
    public void setJuego(Juego juego) {
        this.juego = juego;
        cargarTablaResultados();
        actualizarUI();
    }

    /**
     * Calcula las salidas correctas que se muestran como resumen.
     */
    private void cargarTablaResultados() {
        filas.clear();
        Nivel nivel = juego.getNivelActivo();
        for (int entrada : nivel.getEntradasFase2()) {
            int salida = nivel.getReglaActiva().calcularOutput(entrada);
            filas.add(new FilaResultado(entrada, salida));
        }
    }

    /**
     * Avanza al siguiente nivel o finaliza el juego si ya no quedan niveles.
     */
    @FXML
    private void onSiguienteNivel() {
        Nivel siguiente = juego.avanzarNivel();
        if (siguiente == null) {
            mostrarAlerta("Felicidades", "Completaste todos los niveles. Puntaje final: "
                + juego.getJugador().getPuntaje());
            navegarA("/co/edu/poli/jmm/vista/InterfazUsuarioFinal.fxml", null);
        } else {
            navegarA("/co/edu/poli/jmm/vista/InterfazAdivinarReglaJuego.fxml",
                controller -> ((AdivinarReglaController) controller).setJuego(juego));
        }
    }

    /**
     * Reinicia el nivel actual con el puntaje base.
     */
    @FXML
    private void onIntentoNuevo() {
        juego.reiniciarNivel();
        juego.getJugador().setPuntaje(10);
        navegarA("/co/edu/poli/jmm/vista/InterfazAdivinarReglaJuego.fxml",
            controller -> ((AdivinarReglaController) controller).setJuego(juego));
    }

    /**
     * Alterna el color de fondo de la pantalla de victoria.
     */
    @FXML
    private void onToggleTema() {
        modoOscuro = !modoOscuro;
        btnTema.setText(modoOscuro ? "Claro" : "Oscuro");
        Scene scene = tablaResultados.getScene();
        scene.getRoot().setStyle(modoOscuro
            ? "-fx-background-color: linear-gradient(to bottom right, #052e16, #14532d);"
            : "-fx-background-color: linear-gradient(to bottom right, #ecfdf5, #d1fae5);");
    }

    /**
     * Actualiza los textos principales de la pantalla.
     */
    private void actualizarUI() {
        if (juego != null && juego.getJugador() != null && lblPuntaje != null) {
            lblPuntaje.setText("Puntaje: " + juego.getJugador().getPuntaje());
        }
        Nivel nivel = juego.getNivelActivo();
        if (nivel != null && lblTitulo != null) {
            lblTitulo.setText("Descubriste la regla: "
                + nivel.getReglaActiva().getDescripcion());
        }
        if (btnSiguienteNivel != null) {
            btnSiguienteNivel.setDisable(!juego.hayNivelSiguiente());
        }
    }

    /**
     * Muestra un cuadro de mensaje sencillo.
     */
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Permite configurar el controlador de la vista destino antes de mostrarla.
     */
    @FunctionalInterface
    interface ControllerCallback {
        void configure(Object c);
    }

    /**
     * Cambia de pantalla sin perder el estado del juego.
     */
    private void navegarA(String fxml, ControllerCallback callback) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();
            if (callback != null) callback.configure(loader.getController());
            Stage stage = (Stage) tablaResultados.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            System.err.println("[JuegoGanadoController] Navegacion fallida: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
