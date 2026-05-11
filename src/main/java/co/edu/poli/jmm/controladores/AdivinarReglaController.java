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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controla la primera fase del nivel.
 * El jugador prueba entradas, observa salidas y deduce la regla oculta.
 */
public class AdivinarReglaController {

    @FXML private TextField txtEntrada;
    @FXML private Button btnIngresar;
    @FXML private Button btnConozcaRegla;
    @FXML private TableView<FilaTabla> tablaVista;
    @FXML private TableColumn<FilaTabla, Number> colEntrada;
    @FXML private TableColumn<FilaTabla, Number> colSalida;
    @FXML private Label lblPuntaje;
    @FXML private Label lblNivel;

    private Juego juego;
    private Nivel nivelActivo;
    private final ObservableList<FilaTabla> filas = FXCollections.observableArrayList();

    /**
     * Fila visible en la tabla de pruebas de la primera fase.
     */
    public static class FilaTabla {
        private final SimpleIntegerProperty entrada;
        private final SimpleIntegerProperty salida;

        public FilaTabla(int entrada, int salida) {
            this.entrada = new SimpleIntegerProperty(entrada);
            this.salida = new SimpleIntegerProperty(salida);
        }

        public int getEntrada() { return entrada.get(); }
        public int getSalida() { return salida.get(); }
        public SimpleIntegerProperty entradaProperty() { return entrada; }
        public SimpleIntegerProperty salidaProperty() { return salida; }
    }

    /**
     * Prepara la tabla y restringe el campo de entrada a numeros enteros.
     */
    @FXML
    public void initialize() {
        colEntrada.setCellValueFactory(data -> data.getValue().entradaProperty());
        colSalida.setCellValueFactory(data -> data.getValue().salidaProperty());
        colEntrada.setCellFactory(col -> crearCeldaCentrada());
        colSalida.setCellFactory(col -> crearCeldaCentrada());

        Label headerEntrada = new Label("ENTRADA");
        headerEntrada.setStyle("-fx-text-fill: #374151; -fx-font-weight: bold;");
        headerEntrada.setMaxWidth(Double.MAX_VALUE);
        headerEntrada.setAlignment(javafx.geometry.Pos.CENTER);
        colEntrada.setGraphic(headerEntrada);

        Label headerSalida = new Label("SALIDA");
        headerSalida.setStyle("-fx-text-fill: #374151; -fx-font-weight: bold;");
        headerSalida.setMaxWidth(Double.MAX_VALUE);
        headerSalida.setAlignment(javafx.geometry.Pos.CENTER);
        colSalida.setGraphic(headerSalida);

        tablaVista.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tablaVista.setItems(filas);

        txtEntrada.textProperty().addListener((obs, viejo, nuevo) -> {
            if (!nuevo.matches("-?\\d*")) txtEntrada.setText(viejo);
        });
    }

    /**
     * Crea celdas centradas para mostrar entradas y salidas.
     */
    private TableCell<FilaTabla, Number> crearCeldaCentrada() {
        return new TableCell<>() {
            @Override
            protected void updateItem(Number item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(String.valueOf(item.intValue()));
                    setAlignment(javafx.geometry.Pos.CENTER);
                    setStyle("-fx-text-fill: #e5e7eb; -fx-font-size: 16; -fx-font-weight: bold;");
                }
            }
        };
    }

    /**
     * Recibe el modelo desde la pantalla anterior.
     */
    public void setJuego(Juego juego) {
        this.juego = juego;
        this.nivelActivo = juego.getNivelActivo();
        actualizarUI();
    }

    /**
     * Procesa una entrada del jugador, calcula la salida y descuenta un punto.
     */
    @FXML
    private void onIngresar() {
        String texto = txtEntrada.getText().trim();
        if (texto.isBlank()) return;

        int entrada;
        try {
            entrada = Integer.parseInt(texto);
        } catch (NumberFormatException e) {
            mostrarAlerta("Entrada invalida", "Por favor ingresa un numero entero.");
            return;
        }

        if (!juego.jugadorTienePuntos()) {
            mostrarDerrota();
            return;
        }

        int salida = nivelActivo.getReglaActiva().calcularOutput(entrada);

        TablaIO tabla = nivelActivo.getTablaIO();
        tabla.agregarEntrada(entrada);
        tabla.agregarSalida(salida);

        juego.getJugador().restarPunto();

        filas.add(new FilaTabla(entrada, salida));
        txtEntrada.clear();
        actualizarUI();

        if (!juego.jugadorTienePuntos()) {
            mostrarAlerta("Sin puntos", "Te quedaste sin puntos. Intentalo de nuevo.");
            mostrarDerrota();
        }
    }

    /**
     * Avanza a la fase en la que el jugador valida la regla que dedujo.
     */
    @FXML
    private void onConozcaRegla() {
        navegarA("/co/edu/poli/jmm/vista/InterfazJuegoPrincipal.fxml",
            controller -> ((JuegoPrincipalController) controller).setJuego(juego));
    }

    /**
     * Regresa al selector de niveles.
     */
    @FXML
    private void onVolver() {
        navegarA("/co/edu/poli/jmm/vista/InterfazSelectorNivel.fxml",
            controller -> ((SelectorNivelController) controller).setJuego(juego));
    }

    /**
     * Actualiza etiquetas de puntaje, nivel y dificultad.
     */
    private void actualizarUI() {
        if (juego != null && juego.getJugador() != null) {
            lblPuntaje.setText("Puntos: " + juego.getJugador().getPuntaje());
        }
        if (nivelActivo != null) {
            lblNivel.setText("Nivel " + (juego.getNivelActual() + 1)
                + " - Dificultad: " + labelDificultad(nivelActivo.getDificultad()));
        }
    }

    /**
     * Convierte el valor numerico de dificultad en un texto visible.
     */
    private String labelDificultad(int d) {
        return switch (d) {
            case 1 -> "Facil";
            case 2 -> "Medio";
            case 3 -> "Dificil";
            default -> "?";
        };
    }

    /**
     * Informa la derrota y vuelve al selector de niveles.
     */
    private void mostrarDerrota() {
        mostrarAlerta("Derrota", "Te quedaste sin puntos. El juego ha terminado.");
        navegarA("/co/edu/poli/jmm/vista/InterfazSelectorNivel.fxml",
            controller -> ((SelectorNivelController) controller).setJuego(juego));
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
            Stage stage = (Stage) txtEntrada.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            System.err.println("[AdivinarReglaController] Navegacion fallida: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
