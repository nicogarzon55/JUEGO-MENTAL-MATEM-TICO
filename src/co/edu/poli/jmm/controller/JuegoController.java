package co.edu.poli.jmm.controller;

import co.edu.poli.jmm.app.Navegacion;
import co.edu.poli.jmm.model.ParIO;
import co.edu.poli.jmm.model.Regla;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

public class JuegoController {

	// 🔹 COMPONENTES UI
	@FXML
	private TableView<ParIO> tabla;
	@FXML
	private TableColumn<ParIO, Integer> colInput;
	@FXML
	private TableColumn<ParIO, Integer> colOutput;
	@FXML
	private TextField txtInput;
	@FXML
	private Label lblNivel;
	@FXML
	private Button btnAccion; // botón principal (Go / Validar)
	

	// 🔹 DATOS
	private ObservableList<ParIO> datos = FXCollections.observableArrayList();
	private Regla regla;

	// 🔹 ESTADO
	private boolean modoAdivinar = false;

	// =========================
	// 🚀 INICIALIZACIÓN
	// =========================
	@FXML
	public void initialize() {

		int nivel = SelectorNivelController.getNivelSeleccionado();

		lblNivel.setText("Nivel " + nivel + " - Descubre la regla");

		regla = new Regla(nivel, nivel);

		// Configurar tabla
		colInput.setCellValueFactory(new PropertyValueFactory<>("input"));
		colOutput.setCellValueFactory(new PropertyValueFactory<>("output"));

		tabla.setItems(datos);
		tabla.setEditable(false);

		// Pintar tabla
		colOutput.setCellFactory(column -> new TableCell<ParIO, Integer>() {
			@Override
			protected void updateItem(Integer item, boolean empty) {
				super.updateItem(item, empty);

				if (empty || item == null) {
					setText(null);
					setStyle("");
					return;
				}

				setText(item.toString());

				// 🔥 SOLO pintar en modo adivinar
				if (modoAdivinar) {

					ParIO fila = getTableView().getItems().get(getIndex());

					if (fila.isCorrecto()) {
						setStyle("-fx-background-color: lightgreen;");
					} else {
						setStyle("-fx-background-color: lightcoral;");
					}

				} else {
					setStyle(""); // 🔥 modo explorar = sin color
				}
			}
		});
	}

	// =========================
	// 🔵 MODO EXPLORAR
	// =========================
	private void agregar() {

		try {
			int input = Integer.parseInt(txtInput.getText());

			int output = regla.calcularOutput(input);

			System.out.println("Agregando: " + input + " -> " + output);

			datos.add(new ParIO(input, output));

			txtInput.clear();

		} catch (Exception e) {
			System.out.println("Entrada inválida");
		}
	}

	// =========================
	// 🟣 CAMBIAR A MODO ADIVINAR
	// =========================
	@FXML
	public void irAdivinar() {

		modoAdivinar = true;

		datos.clear();

		// Generar inputs aleatorios
		for (int i = 0; i < 5; i++) {
			int input = (int) (Math.random() * 10) + 1;
			datos.add(new ParIO(input, 0));
		}

		// Activar edición
		tabla.setEditable(true);

		colOutput.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

		colOutput.setOnEditCommit(event -> {
			event.getRowValue().setOutput(event.getNewValue());
		});

		// UI
		btnAccion.setText("Validar");
		txtInput.setDisable(true); // 🔥 bloquea input en este modo
	}

	// =========================
	// 🎯 BOTÓN PRINCIPAL
	// =========================
	@FXML
	public void accionPrincipal() {

		if (!modoAdivinar) {
			agregar();
		} else {
			validar();
		}
	}

	// =========================
	// ✅ VALIDACIÓN
	// =========================
	private void validar() {

		boolean correcto = true;

		for (ParIO fila : datos) {

			int esperado = regla.calcularOutput(fila.getInput());

			if (fila.getOutput() == esperado) {
				fila.setCorrecto(true);
			} else {
				fila.setCorrecto(false);
				correcto = false;
			}
		}

		tabla.refresh(); // 🔥 MUY IMPORTANTE

		if (correcto) {
			Stage stage = (Stage) tabla.getScene().getWindow();
			Navegacion.cambiarEscena("InterfazResultado.fxml", stage);
		}
	}

	// =========================
	// 🔙 NAVEGACIÓN
	// =========================
	@FXML
	public void regresar() {
		Stage stage = (Stage) tabla.getScene().getWindow();
		Navegacion.cambiarEscena("SelectorNiveles.fxml", stage);
	}
	
	
	
	
	@FXML
	public void abrirReglas() {
	    Navegacion.abrirReglas();
	}
}