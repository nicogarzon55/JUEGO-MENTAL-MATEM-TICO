package co.edu.poli.jmm.vista;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Ventana reutilizable con la guia del juego.
 * Presenta una descripcion, instrucciones y reglas sin depender de archivos FXML.
 */
public class GameRulesWindow extends VBox {

    private static final String PURPLE = "#8d5cf6";
    private static final String BG_WHITE = "#ffffff";
    private static final String BG_GRAY900 = "#111827";
    private static final String BG_GRAY800 = "#1f2937";
    private static final String BORDER_GRAY = "#e5e7eb";
    private static final String BORDER_700 = "#374151";
    private static final String TEXT_GRAY800 = "#1f2937";
    private static final String TEXT_GRAY700 = "#374151";
    private static final String TEXT_GRAY600 = "#4b5563";
    private static final String TEXT_GRAY100 = "#f3f4f6";
    private static final String TEXT_GRAY300 = "#d1d5db";

    private String activeTab = "description";
    private boolean isDark = true;

    private final VBox windowBox;
    private final Button tabDescription;
    private final Button tabInstructions;
    private final Button tabRules;
    private final ScrollPane scrollPane;
    private final VBox contentArea;
    private final Runnable onClose;

    /** Crea la guia sin accion de cierre externa. */
    public GameRulesWindow() {
        this(null);
    }

    /**
     * Crea la guia y recibe una accion opcional para cerrar la ventana contenedora.
     */
    public GameRulesWindow(Runnable onClose) {
        this.onClose = onClose;

        windowBox = new VBox();
        windowBox.setMaxWidth(768);
        windowBox.setPrefWidth(768);

        HBox header = buildHeader();

        tabDescription = buildTabButton("Descripcion", "description");
        tabInstructions = buildTabButton("Instrucciones", "instructions");
        tabRules = buildTabButton("Reglas", "rules");

        HBox tabBar = new HBox(tabDescription, tabInstructions, tabRules);
        HBox.setHgrow(tabDescription, Priority.ALWAYS);
        HBox.setHgrow(tabInstructions, Priority.ALWAYS);
        HBox.setHgrow(tabRules, Priority.ALWAYS);

        contentArea = new VBox();
        contentArea.setPadding(new Insets(24, 32, 24, 32));
        contentArea.setSpacing(16);

        scrollPane = new ScrollPane(contentArea);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefViewportHeight(500);
        scrollPane.setMaxHeight(500);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        windowBox.getChildren().addAll(header, tabBar, scrollPane, buildFooter());
        getChildren().add(windowBox);
        setAlignment(Pos.CENTER);

        applyTheme();
        renderContent();
    }

    /**
     * Construye el encabezado con titulo, cambio de tema y cierre opcional.
     */
    private HBox buildHeader() {
        Label title = new Label("Guia del Juego");
        title.setFont(Font.font("System", FontWeight.BOLD, 22));
        title.setTextFill(Color.WHITE);

        HBox left = new HBox(title);
        left.setAlignment(Pos.CENTER_LEFT);

        Button themeBtn = new Button("Claro");
        themeBtn.setFont(Font.font(13));
        styleHeaderButton(themeBtn);
        themeBtn.setOnAction(e -> {
            isDark = !isDark;
            themeBtn.setText(isDark ? "Claro" : "Oscuro");
            applyTheme();
            renderContent();
        });

        HBox right = new HBox(8, themeBtn);
        right.setAlignment(Pos.CENTER_RIGHT);

        if (onClose != null) {
            Button closeBtn = new Button("X");
            closeBtn.setFont(Font.font(13));
            styleHeaderButton(closeBtn);
            closeBtn.setOnAction(e -> onClose.run());
            right.getChildren().add(closeBtn);
        }

        HBox header = new HBox(left, right);
        HBox.setHgrow(left, Priority.ALWAYS);
        HBox.setHgrow(right, Priority.ALWAYS);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(16, 24, 16, 24));
        header.setStyle("-fx-background-color: " + PURPLE + ";");
        return header;
    }

    /**
     * Aplica el mismo estilo a botones pequenos del encabezado.
     */
    private void styleHeaderButton(Button btn) {
        String base = "-fx-background-color: transparent; -fx-text-fill: white;"
            + "-fx-cursor: hand; -fx-background-radius: 50; -fx-padding: 6 10 6 10;";
        btn.setStyle(base);
        btn.setOnMouseEntered(e -> btn.setStyle(
            "-fx-background-color: rgba(255,255,255,0.2); -fx-text-fill: white;"
                + "-fx-cursor: hand; -fx-background-radius: 50; -fx-padding: 6 10 6 10;"));
        btn.setOnMouseExited(e -> btn.setStyle(base));
    }

    /**
     * Crea un boton de pestana y enlaza su accion.
     */
    private Button buildTabButton(String label, String tabId) {
        Button btn = new Button(label);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setPadding(new Insets(12));
        btn.setFont(Font.font("System", FontWeight.BOLD, 13));
        btn.setOnAction(e -> {
            activeTab = tabId;
            renderContent();
            applyTabStyles();
        });
        return btn;
    }

    /**
     * Marca visualmente la pestana activa.
     */
    private void applyTabStyles() {
        for (Button b : new Button[]{tabDescription, tabInstructions, tabRules}) {
            boolean active = switch (activeTab) {
                case "description" -> b == tabDescription;
                case "instructions" -> b == tabInstructions;
                case "rules" -> b == tabRules;
                default -> false;
            };
            b.setStyle(
                "-fx-background-color: " + (active ? PURPLE : (isDark ? BG_GRAY800 : BG_WHITE)) + ";"
                    + "-fx-text-fill: " + (active ? "white" : (isDark ? TEXT_GRAY300 : TEXT_GRAY600)) + ";"
                    + "-fx-border-color: " + (isDark ? BORDER_700 : BORDER_GRAY) + ";"
                    + "-fx-border-width: 0 0 1 0;"
            );
        }
    }

    /**
     * Aplica colores de tema al contenedor principal.
     */
    private void applyTheme() {
        windowBox.setStyle(
            "-fx-background-color: " + (isDark ? BG_GRAY900 : BG_WHITE) + ";"
                + "-fx-background-radius: 10;"
                + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 24, 0, 0, 8);"
        );
        scrollPane.setStyle(
            "-fx-background: " + (isDark ? BG_GRAY900 : BG_WHITE) + ";"
                + "-fx-background-color: " + (isDark ? BG_GRAY900 : BG_WHITE) + ";"
        );
        contentArea.setStyle("-fx-background-color: " + (isDark ? BG_GRAY900 : BG_WHITE) + ";");
        applyTabStyles();
    }

    /**
     * Dibuja el contenido de la pestana seleccionada.
     */
    private void renderContent() {
        contentArea.getChildren().clear();
        switch (activeTab) {
            case "description" -> buildDescriptionTab();
            case "instructions" -> buildInstructionsTab();
            case "rules" -> buildRulesTab();
            default -> buildDescriptionTab();
        }
    }

    /**
     * Explica el objetivo general del juego.
     */
    private void buildDescriptionTab() {
        Label heading = sectionHeading("Descripcion general");
        Label body = bodyText(
            "Este es un juego de logica y matematicas. El objetivo es descubrir la regla "
                + "oculta que transforma cada numero de entrada en una salida.\n\n"
                + "Para lograrlo, el jugador prueba valores, observa los resultados y luego "
                + "confirma su respuesta completando una tabla final."
        );
        contentArea.getChildren().addAll(heading, body);
    }

    /**
     * Presenta las dos fases de la partida.
     */
    private void buildInstructionsTab() {
        Label heading = sectionHeading("Instrucciones de juego");
        Label intro = bodyText("La partida se juega en dos momentos:");

        VBox phase1 = buildPhaseBox("Primera fase: descubrir la regla", new String[]{
            "Escribe un numero de entrada.",
            "El juego calcula la salida aplicando la regla secreta.",
            "Usa las pruebas para deducir la formula.",
            "Cada intento resta un punto."
        });

        VBox phase2 = buildPhaseBox("Segunda fase: validar la regla", new String[]{
            "Cuando creas conocer la regla, pasa a la pantalla de validacion.",
            "Completa las salidas de las entradas fijas.",
            "Si todas las respuestas son correctas, superas el nivel.",
            "Si hay errores, se guarda el puntaje alcanzado."
        });

        contentArea.getChildren().addAll(heading, intro, phase1, phase2);
    }

    /**
     * Crea una caja con pasos numerados.
     */
    private VBox buildPhaseBox(String phaseTitle, String[] steps) {
        VBox box = new VBox(12);
        box.setPadding(new Insets(16));
        box.setStyle(
            "-fx-background-color: " + (isDark ? "#172554" : "#eff6ff") + ";"
                + "-fx-border-color: " + (isDark ? "#1e40af" : "#93c5fd") + ";"
                + "-fx-border-radius: 8; -fx-background-radius: 8;"
        );

        Label title = new Label(phaseTitle);
        title.setFont(Font.font("System", FontWeight.BOLD, 14));
        title.setTextFill(Color.web(isDark ? "#93c5fd" : "#1e3a8a"));
        box.getChildren().add(title);

        for (int i = 0; i < steps.length; i++) {
            box.getChildren().add(buildStep(i + 1, steps[i]));
        }
        return box;
    }

    /**
     * Crea una fila de instruccion con numero y texto.
     */
    private HBox buildStep(int number, String text) {
        StackPane circle = new StackPane();
        circle.setPrefSize(28, 28);
        circle.setMinSize(28, 28);
        circle.setMaxSize(28, 28);
        circle.setStyle("-fx-background-color: " + PURPLE + "; -fx-background-radius: 50;");

        Label num = new Label(String.valueOf(number));
        num.setFont(Font.font("System", FontWeight.BOLD, 12));
        num.setTextFill(Color.WHITE);
        circle.getChildren().add(num);

        Label stepText = bodyText(text);
        HBox.setHgrow(stepText, Priority.ALWAYS);

        HBox row = new HBox(10, circle, stepText);
        row.setAlignment(Pos.TOP_LEFT);
        return row;
    }

    /**
     * Lista las reglas de puntaje y derrota.
     */
    private void buildRulesTab() {
        Label heading = sectionHeading("Reglas del juego");
        Label intro = bodyText("Estas son las condiciones principales de la partida:");

        VBox rules = new VBox(10);
        rules.getChildren().addAll(
            ruleCard("Puntos iniciales", "yellow",
                "Cada jugador empieza con 10 puntos."),
            ruleCard("Costo de intentos", "blue",
                "Cada numero probado en la primera fase resta 1 punto."),
            ruleCard("Condicion de derrota", "red",
                "Si el jugador se queda sin puntos, termina la partida."),
            ruleCard("Bonificacion por victoria", "green",
                "Al superar un nivel se suman 5 puntos adicionales."),
            ruleCard("Validacion incorrecta", "red",
                "Si falla la segunda fase, se guarda el puntaje conseguido.")
        );
        contentArea.getChildren().addAll(heading, intro, rules);
    }

    /**
     * Crea una tarjeta de regla con color segun su tipo.
     */
    private VBox ruleCard(String title, String color, String description) {
        String bgL;
        String bgD;
        String borderL;
        String borderD;
        String titleL;
        String titleD;

        switch (color) {
            case "red" -> {
                bgL = "#fef2f2"; bgD = "#450a0a"; borderL = "#fecaca";
                borderD = "#991b1b"; titleL = "#991b1b"; titleD = "#fca5a5";
            }
            case "green" -> {
                bgL = "#f0fdf4"; bgD = "#052e16"; borderL = "#bbf7d0";
                borderD = "#166534"; titleL = "#166534"; titleD = "#86efac";
            }
            case "yellow" -> {
                bgL = "#fefce8"; bgD = "#422006"; borderL = "#fde68a";
                borderD = "#a16207"; titleL = "#713f12"; titleD = "#fde047";
            }
            default -> {
                bgL = "#f9fafb"; bgD = BG_GRAY800; borderL = BORDER_GRAY;
                borderD = BORDER_700; titleL = TEXT_GRAY800; titleD = TEXT_GRAY100;
            }
        }

        VBox card = new VBox(4);
        card.setPadding(new Insets(12, 16, 12, 16));
        card.setStyle(
            "-fx-background-color: " + (isDark ? bgD : bgL) + ";"
                + "-fx-border-color: " + (isDark ? borderD : borderL) + ";"
                + "-fx-border-radius: 8; -fx-background-radius: 8;"
        );

        Label t = new Label(title);
        t.setFont(Font.font("System", FontWeight.BOLD, 14));
        t.setTextFill(Color.web(isDark ? titleD : titleL));

        Label d = bodyText(description);
        card.getChildren().addAll(t, d);
        return card;
    }

    /**
     * Construye el pie de la guia.
     */
    private HBox buildFooter() {
        Button btn = new Button("Entendido");
        btn.setFont(Font.font("System", FontWeight.BOLD, 14));
        btn.setStyle(
            "-fx-background-color: " + PURPLE + "; -fx-text-fill: white;"
                + "-fx-background-radius: 8; -fx-cursor: hand;"
        );
        btn.setPadding(new Insets(10, 28, 10, 28));
        if (onClose != null) btn.setOnAction(e -> onClose.run());

        HBox footer = new HBox(btn);
        footer.setAlignment(Pos.CENTER_RIGHT);
        footer.setPadding(new Insets(16, 24, 16, 24));
        footer.setStyle("-fx-border-color: " + BORDER_GRAY + "; -fx-border-width: 1 0 0 0;");
        return footer;
    }

    /**
     * Crea titulos internos de seccion.
     */
    private Label sectionHeading(String text) {
        Label lbl = new Label(text);
        lbl.setFont(Font.font("System", FontWeight.BOLD, 18));
        lbl.setTextFill(Color.web(isDark ? TEXT_GRAY100 : TEXT_GRAY800));
        lbl.setPadding(new Insets(0, 0, 4, 0));
        return lbl;
    }

    /**
     * Crea texto normal con ajuste de linea.
     */
    private Label bodyText(String text) {
        Label lbl = new Label(text);
        lbl.setFont(Font.font(14));
        lbl.setTextFill(Color.web(isDark ? TEXT_GRAY300 : TEXT_GRAY700));
        lbl.setWrapText(true);
        lbl.setLineSpacing(3);
        return lbl;
    }
}
