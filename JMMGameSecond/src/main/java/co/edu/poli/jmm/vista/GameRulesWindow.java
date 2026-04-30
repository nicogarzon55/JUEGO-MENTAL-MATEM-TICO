package co.edu.poli.jmm.vista;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Ventana de Guía del Juego — componente JavaFX reutilizable.
 *
 * Muestra tres pestañas:
 *  - Descripción
 *  - Instrucciones
 *  - Reglas
 *
 * Incluye toggle de modo oscuro/claro.
 * Se puede usar incrustada en cualquier scene o como popup.
 *
 * Uso:
 *   GameRulesWindow guide = new GameRulesWindow(() -> stage.close());
 *   Scene scene = new Scene(guide, 800, 700);
 *   Stage popup = new Stage();
 *   popup.setScene(scene);
 *   popup.show();
 */
public class GameRulesWindow extends VBox {

    // ── Colores ───────────────────────────────────────────────────────────────
    private static final String PURPLE       = "#8d5cf6";

    private static final String BG_WHITE     = "#ffffff";
    private static final String BG_GRAY100   = "#f3f4f6";
    private static final String BORDER_GRAY  = "#e5e7eb";
    private static final String TEXT_GRAY800 = "#1f2937";
    private static final String TEXT_GRAY700 = "#374151";
    private static final String TEXT_GRAY600 = "#4b5563";

    private static final String BG_GRAY900   = "#111827";
    private static final String BG_GRAY800   = "#1f2937";
    private static final String BORDER_700   = "#374151";
    private static final String TEXT_GRAY100 = "#f3f4f6";
    private static final String TEXT_GRAY300 = "#d1d5db";

    // ── Estado ────────────────────────────────────────────────────────────────
    private String  activeTab = "description";
    private boolean isDark    = false;

    // ── Componentes reutilizables ─────────────────────────────────────────────
    private final VBox      windowBox;
    private final HBox      tabBar;
    private final Button    tabDescription;
    private final Button    tabInstructions;
    private final Button    tabRules;
    private final ScrollPane scrollPane;
    private final VBox      contentArea;
    private final HBox      footer;
    private final Runnable  onClose;

    // ── Constructores ─────────────────────────────────────────────────────────

    public GameRulesWindow() { this(null); }

    public GameRulesWindow(Runnable onClose) {
        this.onClose = onClose;

        windowBox = new VBox();
        windowBox.setMaxWidth(768);
        windowBox.setPrefWidth(768);
        windowBox.setStyle(
            "-fx-background-color: " + BG_WHITE + ";" +
            "-fx-background-radius: 10;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 24, 0, 0, 8);"
        );

        HBox header = buildHeader();

        tabDescription  = buildTabButton("📖  Descripción",   "description");
        tabInstructions = buildTabButton("☑  Instrucciones", "instructions");
        tabRules        = buildTabButton("🎮  Reglas",        "rules");

        tabBar = new HBox(tabDescription, tabInstructions, tabRules);
        HBox.setHgrow(tabDescription,  Priority.ALWAYS);
        HBox.setHgrow(tabInstructions, Priority.ALWAYS);
        HBox.setHgrow(tabRules,        Priority.ALWAYS);

        contentArea = new VBox();
        contentArea.setPadding(new Insets(24, 32, 24, 32));
        contentArea.setSpacing(16);

        scrollPane = new ScrollPane(contentArea);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefViewportHeight(500);
        scrollPane.setMaxHeight(500);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        footer = buildFooter();

        windowBox.getChildren().addAll(header, tabBar, scrollPane, footer);
        this.getChildren().add(windowBox);
        this.setAlignment(Pos.CENTER);

        applyTheme();
        renderContent();
    }

    // ── Header ────────────────────────────────────────────────────────────────

    private HBox buildHeader() {
        Label icon  = new Label("🎮");
        icon.setFont(Font.font(20));
        Label title = new Label("Guía del Juego");
        title.setFont(Font.font("System", FontWeight.BOLD, 22));
        title.setTextFill(Color.WHITE);

        HBox left = new HBox(8, icon, title);
        left.setAlignment(Pos.CENTER_LEFT);

        Button dmBtn = new Button("🌙");
        dmBtn.setFont(Font.font(15));
        styleIconButton(dmBtn);
        dmBtn.setOnAction(e -> {
            isDark = !isDark;
            dmBtn.setText(isDark ? "☀" : "🌙");
            applyTheme();
            renderContent();
        });

        HBox right = new HBox(6, dmBtn);
        right.setAlignment(Pos.CENTER_RIGHT);

        if (onClose != null) {
            Button closeBtn = new Button("✕");
            closeBtn.setFont(Font.font(14));
            styleIconButton(closeBtn);
            closeBtn.setOnAction(e -> onClose.run());
            right.getChildren().add(closeBtn);
        }

        HBox header = new HBox(left, right);
        HBox.setHgrow(left,  Priority.ALWAYS);
        HBox.setHgrow(right, Priority.ALWAYS);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(16, 24, 16, 24));
        header.setStyle("-fx-background-color: " + PURPLE + ";");
        return header;
    }

    private void styleIconButton(Button btn) {
        String base =
            "-fx-background-color: transparent; -fx-text-fill: white;" +
            "-fx-cursor: hand; -fx-background-radius: 50; -fx-padding: 6;";
        btn.setStyle(base);
        btn.setOnMouseEntered(e -> btn.setStyle(
            "-fx-background-color: rgba(255,255,255,0.2); -fx-text-fill: white;" +
            "-fx-cursor: hand; -fx-background-radius: 50; -fx-padding: 6;"));
        btn.setOnMouseExited(e -> btn.setStyle(base));
    }

    // ── Tabs ──────────────────────────────────────────────────────────────────

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

    private void applyTabStyles() {
        for (Button b : new Button[]{tabDescription, tabInstructions, tabRules}) {
            boolean active = switch (activeTab) {
                case "description"   -> b == tabDescription;
                case "instructions"  -> b == tabInstructions;
                case "rules"         -> b == tabRules;
                default              -> false;
            };
            b.setStyle(
                "-fx-background-color: " + (active ? PURPLE : (isDark ? BG_GRAY800 : BG_WHITE)) + ";" +
                "-fx-text-fill: " + (active ? "white" : (isDark ? TEXT_GRAY300 : TEXT_GRAY600)) + ";" +
                "-fx-border-color: " + (isDark ? BORDER_700 : BORDER_GRAY) + ";" +
                "-fx-border-width: 0 0 1 0;"
            );
        }
    }

    // ── Tema ──────────────────────────────────────────────────────────────────

    private void applyTheme() {
        windowBox.setStyle(
            "-fx-background-color: " + (isDark ? BG_GRAY900 : BG_WHITE) + ";" +
            "-fx-background-radius: 10;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 24, 0, 0, 8);"
        );
        scrollPane.setStyle(
            "-fx-background: " + (isDark ? BG_GRAY900 : BG_WHITE) + ";" +
            "-fx-background-color: " + (isDark ? BG_GRAY900 : BG_WHITE) + ";"
        );
        contentArea.setStyle("-fx-background-color: " + (isDark ? BG_GRAY900 : BG_WHITE) + ";");
        applyTabStyles();
    }

    // ── Contenido ─────────────────────────────────────────────────────────────

    private void renderContent() {
        contentArea.getChildren().clear();
        switch (activeTab) {
            case "description"  -> buildDescriptionTab();
            case "instructions" -> buildInstructionsTab();
            case "rules"        -> buildRulesTab();
        }
    }

    private void buildDescriptionTab() {
        Label heading = sectionHeading("Descripción General");
        Label body    = bodyText(
            "Este es un juego de lógica y matemáticas en el que el objetivo principal es " +
            "descubrir la regla oculta que transforma los números de entrada en resultados " +
            "de salida. El jugador deberá usar su ingenio, probar diferentes números y " +
            "analizar los resultados hasta encontrar la regla correcta.\n\n" +
            "Una vez descubierta, pondrá a prueba su conocimiento en una segunda fase para " +
            "confirmar que realmente entendió la lógica."
        );
        contentArea.getChildren().addAll(heading, body);
    }

    private void buildInstructionsTab() {
        Label heading = sectionHeading("Instrucciones de Juego");
        Label intro   = bodyText("Sigue estos pasos para jugar correctamente:");

        VBox phase1 = buildPhaseBox("Primera Fase – Descubrir la Regla", new String[]{
            "El jugador escribe un número en la tabla.",
            "El juego muestra automáticamente el resultado aplicando la regla secreta.",
            "El jugador puede probar tantos números como quiera para deducir la regla.",
            "Cada intento cuesta 1 punto de los 10 iniciales."
        });

        VBox phase2 = buildPhaseBox("Segunda Fase – Validar la Regla", new String[]{
            "Cuando el jugador crea haber descubierto la regla, pasa a la siguiente pantalla.",
            "Allí verá una tabla con varios números ya colocados.",
            "Su tarea será escribir la salida correcta para cada número según la regla deducida.",
            "Si la respuesta es correcta, la casilla se marcará en verde; si es incorrecta, en rojo."
        });

        contentArea.getChildren().addAll(heading, intro, phase1, phase2);
    }

    private VBox buildPhaseBox(String phaseTitle, String[] steps) {
        VBox box = new VBox(12);
        box.setPadding(new Insets(16));
        box.setStyle(
            "-fx-background-color: " + (isDark ? "#172554" : "#eff6ff") + ";" +
            "-fx-border-color: " + (isDark ? "#1e40af" : "#93c5fd") + ";" +
            "-fx-border-radius: 8; -fx-background-radius: 8;"
        );
        Label title = new Label(phaseTitle);
        title.setFont(Font.font("System", FontWeight.BOLD, 14));
        title.setTextFill(Color.web(isDark ? "#93c5fd" : "#1e3a8a"));
        box.getChildren().add(title);
        for (int i = 0; i < steps.length; i++) box.getChildren().add(buildStep(i + 1, steps[i]));
        return box;
    }

    private HBox buildStep(int number, String text) {
        StackPane circle = new StackPane();
        circle.setPrefSize(28, 28); circle.setMinSize(28, 28); circle.setMaxSize(28, 28);
        circle.setStyle("-fx-background-color: " + PURPLE + "; -fx-background-radius: 50;");
        Label num = new Label(String.valueOf(number));
        num.setFont(Font.font("System", FontWeight.BOLD, 12));
        num.setTextFill(Color.WHITE);
        circle.getChildren().add(num);

        Label stepText = new Label(text);
        stepText.setFont(Font.font(13));
        stepText.setTextFill(Color.web(isDark ? TEXT_GRAY300 : TEXT_GRAY700));
        stepText.setWrapText(true);
        HBox.setHgrow(stepText, Priority.ALWAYS);

        HBox row = new HBox(10, circle, stepText);
        row.setAlignment(Pos.TOP_LEFT);
        return row;
    }

    private void buildRulesTab() {
        Label heading = sectionHeading("Reglas del Juego");
        Label intro   = bodyText("Presta atención a estas reglas importantes para jugar correctamente:");

        VBox rules = new VBox(10);
        rules.getChildren().addAll(
            ruleCard("⭐", "Puntos Iniciales",      "yellow",
                "Cada jugador empieza con 10 puntos al inicio de la partida."),
            ruleCard("💡", "Costo de Intentos",      "blue",
                "Cada número probado en la primera fase resta 1 punto de tu puntaje."),
            ruleCard("❌", "Condición de Derrota",   "red",
                "Si el jugador se queda sin puntos antes de adivinar la regla, pierde la partida."),
            ruleCard("🏆", "Bonificación por Victoria", "green",
                "Si logras adivinar la regla y completas correctamente la segunda fase, ganas 5 puntos adicionales."),
            ruleCard("⚠", "Validación Incorrecta",  "red",
                "Si pasas a la segunda fase pero no logras aplicar la regla, pierdes y se guarda el puntaje conseguido.")
        );
        contentArea.getChildren().addAll(heading, intro, rules);
    }

    private VBox ruleCard(String emoji, String title, String color, String description) {
        String bgL, bgD, borderL, borderD, titleL, titleD;
        switch (color) {
            case "red"    -> { bgL="#fef2f2"; bgD="#450a0a"; borderL="#fecaca"; borderD="#991b1b"; titleL="#991b1b"; titleD="#fca5a5"; }
            case "green"  -> { bgL="#f0fdf4"; bgD="#052e16"; borderL="#bbf7d0"; borderD="#166534"; titleL="#166534"; titleD="#86efac"; }
            case "yellow" -> { bgL="#fefce8"; bgD="#422006"; borderL="#fde68a"; borderD="#a16207"; titleL="#713f12"; titleD="#fde047"; }
            default       -> { bgL="#f9fafb"; bgD=BG_GRAY800; borderL=BORDER_GRAY; borderD=BORDER_700; titleL=TEXT_GRAY800; titleD=TEXT_GRAY100; }
        }
        VBox card = new VBox(4);
        card.setPadding(new Insets(12, 16, 12, 16));
        card.setStyle(
            "-fx-background-color: " + (isDark ? bgD : bgL) + ";" +
            "-fx-border-color: " + (isDark ? borderD : borderL) + ";" +
            "-fx-border-radius: 8; -fx-background-radius: 8;"
        );
        Label t = new Label(emoji + "  " + title);
        t.setFont(Font.font("System", FontWeight.BOLD, 14));
        t.setTextFill(Color.web(isDark ? titleD : titleL));
        Label d = new Label(description);
        d.setFont(Font.font(13));
        d.setTextFill(Color.web(isDark ? TEXT_GRAY300 : TEXT_GRAY700));
        d.setWrapText(true);
        d.setPadding(new Insets(0, 0, 0, 24));
        card.getChildren().addAll(t, d);
        return card;
    }

    // ── Footer ────────────────────────────────────────────────────────────────

    private HBox buildFooter() {
        Button btn = new Button("¡Entendido!");
        btn.setFont(Font.font("System", FontWeight.BOLD, 14));
        btn.setStyle(
            "-fx-background-color: " + PURPLE + "; -fx-text-fill: white;" +
            "-fx-background-radius: 8; -fx-cursor: hand;"
        );
        btn.setPadding(new Insets(10, 28, 10, 28));
        if (onClose != null) btn.setOnAction(e -> onClose.run());

        HBox f = new HBox(btn);
        f.setAlignment(Pos.CENTER_RIGHT);
        f.setPadding(new Insets(16, 24, 16, 24));
        f.setStyle("-fx-border-color: " + BORDER_GRAY + "; -fx-border-width: 1 0 0 0;");
        return f;
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private Label sectionHeading(String text) {
        Label lbl = new Label(text);
        lbl.setFont(Font.font("System", FontWeight.BOLD, 18));
        lbl.setTextFill(Color.web(isDark ? TEXT_GRAY100 : TEXT_GRAY800));
        lbl.setPadding(new Insets(0, 0, 4, 0));
        return lbl;
    }

    private Label bodyText(String text) {
        Label lbl = new Label(text);
        lbl.setFont(Font.font(14));
        lbl.setTextFill(Color.web(isDark ? TEXT_GRAY300 : TEXT_GRAY700));
        lbl.setWrapText(true);
        lbl.setLineSpacing(3);
        return lbl;
    }
}
