package com.gamerules;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * GameRulesWindow — JavaFX equivalent of the React GameRulesWindow component.
 *
 * Visual parity targets:
 *  - Purple (#8d5cf6) header with Gamepad icon, dark-mode toggle, and optional close button
 *  - Three tabs: Descripción | Instrucciones | Reglas
 *  - Scrollable content area (max-height 500 px)
 *  - Light / dark mode toggle
 *  - Footer with "¡Entendido!" button
 */
public class GameRulesWindow extends VBox {

    // ── Colours ────────────────────────────────────────────────────────────────
    private static final String PURPLE        = "#8d5cf6";
    private static final String PURPLE_DARK   = "#7c4de0";

    // Light palette
    private static final String BG_WHITE      = "#ffffff";
    private static final String BG_GRAY50     = "#f9fafb";
    private static final String BG_GRAY100    = "#f3f4f6";
    private static final String BORDER_GRAY   = "#e5e7eb";
    private static final String TEXT_GRAY800  = "#1f2937";
    private static final String TEXT_GRAY700  = "#374151";
    private static final String TEXT_GRAY600  = "#4b5563";

    // Dark palette
    private static final String BG_GRAY900    = "#111827";
    private static final String BG_GRAY800    = "#1f2937";
    private static final String BORDER_GRAY700= "#374151";
    private static final String TEXT_GRAY100  = "#f3f4f6";
    private static final String TEXT_GRAY300  = "#d1d5db";
    private static final String TEXT_GRAY400  = "#9ca3af";

    // ── State ──────────────────────────────────────────────────────────────────
    private String activeTab = "description"; // "description" | "instructions" | "rules"
    private boolean isDark   = false;

    // ── Mutable regions ───────────────────────────────────────────────────────
    private final VBox    windowBox;          // outer card (background changes)
    private final HBox    tabBar;             // tab buttons row
    private final Button  tabDescription;
    private final Button  tabInstructions;
    private final Button  tabRules;
    private final ScrollPane scrollPane;      // wraps content
    private final VBox    contentArea;        // content inside scroll
    private final HBox    footer;
    private final Button  darkModeBtn;
    private final Runnable onClose;           // nullable

    public GameRulesWindow() {
        this(null);
    }

    public GameRulesWindow(Runnable onClose) {
        this.onClose = onClose;

        // ── Outer card ────────────────────────────────────────────────────────
        windowBox = new VBox();
        windowBox.setMaxWidth(768);
        windowBox.setPrefWidth(768);
        windowBox.setStyle(
            "-fx-background-color: " + BG_WHITE + ";" +
            "-fx-background-radius: 10;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 24, 0, 0, 8);"
        );

        // ── Header ────────────────────────────────────────────────────────────
        HBox header = buildHeader();

        // ── Tabs ──────────────────────────────────────────────────────────────
        tabDescription  = buildTabButton("📖  Descripción",  "description");
        tabInstructions = buildTabButton("☑  Instrucciones", "instructions");
        tabRules        = buildTabButton("🎮  Reglas",        "rules");

        tabBar = new HBox(tabDescription, tabInstructions, tabRules);
        HBox.setHgrow(tabDescription,  Priority.ALWAYS);
        HBox.setHgrow(tabInstructions, Priority.ALWAYS);
        HBox.setHgrow(tabRules,        Priority.ALWAYS);

        // ── Content ───────────────────────────────────────────────────────────
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

        // ── Footer ────────────────────────────────────────────────────────────
        footer = buildFooter();

        // Grab the dark-mode button reference from header for refresh
        darkModeBtn = (Button) ((HBox) header.getChildren().get(1)).getChildren().get(0);

        windowBox.getChildren().addAll(header, tabBar, scrollPane, footer);

        this.getChildren().add(windowBox);
        this.setAlignment(Pos.CENTER);

        // Initial render
        applyTheme();
        renderContent();
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  Header
    // ══════════════════════════════════════════════════════════════════════════
    private HBox buildHeader() {
        // Left: icon + title
        Label icon  = new Label("🎮");
        icon.setFont(Font.font(20));
        Label title = new Label("Guía del Juego");
        title.setFont(Font.font("System", FontWeight.BOLD, 22));
        title.setTextFill(Color.WHITE);

        HBox left = new HBox(8, icon, title);
        left.setAlignment(Pos.CENTER_LEFT);

        // Right: dark-mode toggle + optional close
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
        btn.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-text-fill: white;" +
            "-fx-cursor: hand;" +
            "-fx-background-radius: 50;" +
            "-fx-padding: 6;"
        );
        btn.setOnMouseEntered(e -> btn.setStyle(
            "-fx-background-color: rgba(255,255,255,0.2);" +
            "-fx-text-fill: white;" +
            "-fx-cursor: hand;" +
            "-fx-background-radius: 50;" +
            "-fx-padding: 6;"
        ));
        btn.setOnMouseExited(e -> btn.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-text-fill white;" +
            "-fx-text-fill: white;" +
            "-fx-cursor: hand;" +
            "-fx-background-radius: 50;" +
            "-fx-padding: 6;"
        ));
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  Tabs
    // ══════════════════════════════════════════════════════════════════════════
    private Button buildTabButton(String label, String tabId) {
        Button btn = new Button(label);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setFont(Font.font("System", FontWeight.MEDIUM, 14));
        btn.setPadding(new Insets(12, 24, 12, 24));
        btn.setOnAction(e -> {
            activeTab = tabId;
            refreshTabs();
            renderContent();
        });
        return btn;
    }

    private void refreshTabs() {
        styleTabButton(tabDescription,  "description");
        styleTabButton(tabInstructions, "instructions");
        styleTabButton(tabRules,        "rules");

        String tabBg     = isDark ? BG_GRAY800 : "#f9fafb";
        String tabBorder = isDark ? BORDER_GRAY700 : BORDER_GRAY;
        tabBar.setStyle(
            "-fx-background-color: " + tabBg + ";" +
            "-fx-border-color: transparent transparent " + tabBorder + " transparent;" +
            "-fx-border-width: 0 0 1 0;"
        );
    }

    private void styleTabButton(Button btn, String tabId) {
        boolean active = tabId.equals(activeTab);

        String bg, textColor, border;
        if (active) {
            bg        = isDark ? BG_GRAY900 : BG_WHITE;
            textColor = isDark ? "#a78bfa" : "#7c3aed";
            border    = isDark ? "#a78bfa"  : "#7c3aed";
        } else {
            bg        = isDark ? BG_GRAY800 : "#f9fafb";
            textColor = isDark ? TEXT_GRAY400 : TEXT_GRAY600;
            border    = "transparent";
        }

        btn.setStyle(
            "-fx-background-color: " + bg + ";" +
            "-fx-text-fill: " + textColor + ";" +
            "-fx-font-weight: " + (active ? "bold" : "normal") + ";" +
            "-fx-border-color: transparent transparent " + border + " transparent;" +
            "-fx-border-width: 0 0 2 0;" +
            "-fx-cursor: hand;" +
            "-fx-padding: 12 24 12 24;" +
            "-fx-background-radius: 0;"
        );
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  Footer
    // ══════════════════════════════════════════════════════════════════════════
    private HBox buildFooter() {
        Button understood = new Button("¡Entendido!");
        understood.setFont(Font.font("System", FontWeight.BOLD, 14));
        understood.setPadding(new Insets(10, 24, 10, 24));
        understood.setStyle(
            "-fx-background-color: " + PURPLE + ";" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 8;" +
            "-fx-cursor: hand;"
        );
        understood.setOnMouseEntered(e -> understood.setStyle(
            "-fx-background-color: " + PURPLE_DARK + ";" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 8;" +
            "-fx-cursor: hand;"
        ));
        understood.setOnMouseExited(e -> understood.setStyle(
            "-fx-background-color: " + PURPLE + ";" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 8;" +
            "-fx-cursor: hand;"
        ));
        if (onClose != null) {
            understood.setOnAction(e -> onClose.run());
        }

        HBox footer = new HBox(understood);
        footer.setAlignment(Pos.CENTER_RIGHT);
        footer.setPadding(new Insets(16, 32, 16, 32));
        footer.setSpacing(12);
        return footer;
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  Theme application
    // ══════════════════════════════════════════════════════════════════════════
    private void applyTheme() {
        // Card background
        windowBox.setStyle(
            "-fx-background-color: " + (isDark ? BG_GRAY900 : BG_WHITE) + ";" +
            "-fx-background-radius: 10;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 24, 0, 0, 8);"
        );

        // Tab bar
        refreshTabs();

        // Footer
        String footerBg     = isDark ? BG_GRAY800 : BG_GRAY50;
        String footerBorder = isDark ? BORDER_GRAY700 : BORDER_GRAY;
        footer.setStyle(
            "-fx-background-color: " + footerBg + ";" +
            "-fx-border-color: " + footerBorder + " transparent transparent transparent;" +
            "-fx-border-width: 1 0 0 0;"
        );

        // Content scroll area
        String contentBg = isDark ? BG_GRAY900 : BG_WHITE;
        scrollPane.setStyle(
            "-fx-background: " + contentBg + ";" +
            "-fx-background-color: " + contentBg + ";"
        );
        contentArea.setStyle("-fx-background-color: " + contentBg + ";");
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  Content rendering — dispatches to tab-specific builders
    // ══════════════════════════════════════════════════════════════════════════
    private void renderContent() {
        contentArea.getChildren().clear();
        switch (activeTab) {
            case "description"   -> buildDescriptionTab();
            case "instructions"  -> buildInstructionsTab();
            case "rules"         -> buildRulesTab();
        }
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  TAB: Descripción
    // ══════════════════════════════════════════════════════════════════════════
    private void buildDescriptionTab() {
        Label heading = sectionHeading("Descripción General");

        Label body = bodyText(
            "Este es un juego de lógica y matemáticas en el que el objetivo principal es descubrir " +
            "la regla oculta que transforma los números de entrada en resultados de salida. El jugador " +
            "deberá usar su ingenio, probar diferentes números y analizar los resultados hasta encontrar " +
            "la regla correcta. Una vez descubierta, pondrá a prueba su conocimiento en una segunda fase " +
            "para confirmar que realmente entendió la lógica."
        );

        // Purple feature box
        VBox featureBox = new VBox(8);
        featureBox.setPadding(new Insets(16));
        featureBox.setStyle(
            "-fx-background-color: " + (isDark ? "#3b0764" : "#f5f3ff") + ";" +
            "-fx-border-color: " + (isDark ? "#7c3aed" : "#7c3aed") + ";" +
            "-fx-border-width: 0 0 0 4;" +
            "-fx-background-radius: 4;"
        );

        Label featureTitle = new Label("Características del Juego");
        featureTitle.setFont(Font.font("System", FontWeight.BOLD, 14));
        featureTitle.setTextFill(Color.web(isDark ? "#c4b5fd" : "#4c1d95"));
        featureTitle.setPadding(new Insets(0, 0, 4, 0));

        featureBox.getChildren().add(featureTitle);
        featureBox.getChildren().add(featureItem("🧩", "Desafío Mental",
            "Usa tu razonamiento lógico y habilidades matemáticas para descubrir patrones ocultos."));
        featureBox.getChildren().add(featureItem("🎯", "Dos Fases de Juego",
            "Primero descubre la regla probando números, luego demuestra que la entendiste aplicándola."));
        featureBox.getChildren().add(featureItem("⚡", "Sistema de Puntuación",
            "Comienza con 10 puntos y gana 5 adicionales si completas el desafío exitosamente."));
        featureBox.getChildren().add(featureItem("🔄", "Feedback Instantáneo",
            "Recibe resultados inmediatos de tus intentos con indicadores visuales de aciertos y errores."));

        contentArea.getChildren().addAll(heading, body, featureBox);
    }

    private VBox featureItem(String emoji, String title, String description) {
        Label emojiLabel = new Label(emoji);
        emojiLabel.setFont(Font.font(16));

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 13));
        titleLabel.setTextFill(Color.web(isDark ? "#a78bfa" : "#5b21b6"));

        HBox titleRow = new HBox(6, emojiLabel, titleLabel);
        titleRow.setAlignment(Pos.CENTER_LEFT);

        Label desc = new Label(description);
        desc.setFont(Font.font(13));
        desc.setTextFill(Color.web(isDark ? TEXT_GRAY300 : TEXT_GRAY700));
        desc.setWrapText(true);
        desc.setPadding(new Insets(0, 0, 0, 28));

        VBox item = new VBox(2, titleRow, desc);
        item.setPadding(new Insets(4, 0, 4, 0));
        return item;
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  TAB: Instrucciones
    // ══════════════════════════════════════════════════════════════════════════
    private void buildInstructionsTab() {
        Label heading = sectionHeading("Cómo Jugar");
        Label intro   = bodyText("El juego se divide en dos fases claramente diferenciadas:");

        VBox phase1 = buildPhaseBox(
            "Primera Fase – Descubrir la Regla",
            new String[]{
                "El jugador escribe un número en la tabla.",
                "El juego muestra automáticamente el resultado de ese número aplicando la regla secreta.",
                "El jugador puede probar tantos números como quiera para deducir la regla.",
                "Cada intento cuesta 1 punto de los 10 iniciales."
            }
        );

        VBox phase2 = buildPhaseBox(
            "Segunda Fase – Validar la Regla",
            new String[]{
                "Cuando el jugador crea haber descubierto la regla, pasa a la siguiente pantalla.",
                "Allí verá una tabla con varios números ya colocados.",
                "Su tarea será escribir la salida correcta para cada número según la regla que dedujo.",
                "Si la respuesta es correcta, la casilla se marcará en verde; si es incorrecta, en rojo."
            }
        );

        contentArea.getChildren().addAll(heading, intro, phase1, phase2);
    }

    private VBox buildPhaseBox(String phaseTitle, String[] steps) {
        VBox box = new VBox(12);
        box.setPadding(new Insets(16));
        box.setStyle(
            "-fx-background-color: " + (isDark ? "#172554" : "#eff6ff") + ";" +
            "-fx-border-color: " + (isDark ? "#1e40af" : "#93c5fd") + ";" +
            "-fx-border-radius: 8;" +
            "-fx-background-radius: 8;"
        );

        Label title = new Label(phaseTitle);
        title.setFont(Font.font("System", FontWeight.BOLD, 14));
        title.setTextFill(Color.web(isDark ? "#93c5fd" : "#1e3a8a"));
        title.setPadding(new Insets(0, 0, 4, 0));
        box.getChildren().add(title);

        for (int i = 0; i < steps.length; i++) {
            box.getChildren().add(buildNumberedStep(i + 1, steps[i]));
        }
        return box;
    }

    private HBox buildNumberedStep(int number, String text) {
        // Purple circle with number
        StackPane circle = new StackPane();
        circle.setPrefSize(28, 28);
        circle.setMinSize(28, 28);
        circle.setMaxSize(28, 28);
        circle.setStyle(
            "-fx-background-color: " + PURPLE + ";" +
            "-fx-background-radius: 50;"
        );
        Label numLabel = new Label(String.valueOf(number));
        numLabel.setFont(Font.font("System", FontWeight.BOLD, 12));
        numLabel.setTextFill(Color.WHITE);
        circle.getChildren().add(numLabel);

        Label stepText = new Label(text);
        stepText.setFont(Font.font(13));
        stepText.setTextFill(Color.web(isDark ? TEXT_GRAY300 : TEXT_GRAY700));
        stepText.setWrapText(true);
        HBox.setHgrow(stepText, Priority.ALWAYS);

        HBox row = new HBox(10, circle, stepText);
        row.setAlignment(Pos.TOP_LEFT);
        return row;
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  TAB: Reglas
    // ══════════════════════════════════════════════════════════════════════════
    private void buildRulesTab() {
        Label heading = sectionHeading("Reglas del Juego");
        Label intro   = bodyText("Presta atención a estas reglas importantes para jugar correctamente:");

        VBox rules = new VBox(10);

        rules.getChildren().add(ruleCard(
            "⭐", "Puntos Iniciales", "yellow",
            "Cada jugador empieza con 10 puntos al inicio de la partida.",
            false
        ));
        rules.getChildren().add(ruleCard(
            "💡", "Costo de Intentos", "blue",
            "Cada número probado en la primera fase resta 1 punto de tu puntaje.",
            false
        ));
        rules.getChildren().add(ruleCard(
            "❌", "Condición de Derrota", "red",
            "Si el jugador se queda sin puntos antes de adivinar la regla, pierde la partida.",
            false
        ));
        rules.getChildren().add(ruleCard(
            "🏆", "Bonificación por Victoria", "green",
            "Si logras adivinar la regla y completas correctamente la segunda fase, ganas 5 puntos adicionales.",
            false
        ));
        rules.getChildren().add(ruleCard(
            "⚠", "Validación Incorrecta", "red",
            "Si pasas a la segunda fase pero no logras aplicar la regla correctamente, pierdes y se guarda el puntaje conseguido hasta ese momento.",
            false
        ));

        // Tip box (left-border style)
        VBox tipBox = new VBox(4);
        tipBox.setPadding(new Insets(12, 16, 12, 16));
        tipBox.setStyle(
            "-fx-background-color: " + (isDark ? "#422006" : "#fefce8") + ";" +
            "-fx-border-color: " + (isDark ? "#ca8a04" : "#eab308") + ";" +
            "-fx-border-width: 0 0 0 4;" +
            "-fx-background-radius: 4;"
        );
        Label tipTitle = new Label("💡  Consejo");
        tipTitle.setFont(Font.font("System", FontWeight.BOLD, 14));
        tipTitle.setTextFill(Color.web(isDark ? "#fde047" : "#713f12"));

        Label tipText = new Label(
            "Piensa estratégicamente antes de probar números. Usa intentos que te den la mayor " +
            "información posible sobre la regla para maximizar tu puntaje final."
        );
        tipText.setFont(Font.font(13));
        tipText.setStyle("-fx-font-style: italic;");
        tipText.setTextFill(Color.web(isDark ? "#fef08a" : "#713f12"));
        tipText.setWrapText(true);
        tipText.setPadding(new Insets(0, 0, 0, 24));

        tipBox.getChildren().addAll(tipTitle, tipText);
        rules.getChildren().add(tipBox);

        contentArea.getChildren().addAll(heading, intro, rules);
    }

    private VBox ruleCard(String emoji, String title, String color,
                          String description, boolean leftBorder) {
        String bgLight, bgDark, borderLight, borderDark, titleLight, titleDark;
        switch (color) {
            case "red" -> {
                bgLight = "#fef2f2"; bgDark = "#450a0a";
                borderLight = "#fecaca"; borderDark = "#991b1b";
                titleLight = "#991b1b"; titleDark = "#fca5a5";
            }
            case "green" -> {
                bgLight = "#f0fdf4"; bgDark = "#052e16";
                borderLight = "#bbf7d0"; borderDark = "#166534";
                titleLight = "#166534"; titleDark = "#86efac";
            }
            case "yellow" -> {
                bgLight = "#fefce8"; bgDark = "#422006";
                borderLight = "#fde68a"; borderDark = "#a16207";
                titleLight = "#713f12"; titleDark = "#fde047";
            }
            default -> { // blue or neutral
                bgLight = "#f9fafb"; bgDark = BG_GRAY800;
                borderLight = "#e5e7eb"; borderDark = BORDER_GRAY700;
                titleLight = TEXT_GRAY800; titleDark = TEXT_GRAY100;
            }
        }

        VBox card = new VBox(4);
        card.setPadding(new Insets(12, 16, 12, 16));
        card.setStyle(
            "-fx-background-color: " + (isDark ? bgDark : bgLight) + ";" +
            "-fx-border-color: " + (isDark ? borderDark : borderLight) + ";" +
            "-fx-border-radius: 8;" +
            "-fx-background-radius: 8;"
        );

        Label titleLabel = new Label(emoji + "  " + title);
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        titleLabel.setTextFill(Color.web(isDark ? titleDark : titleLight));

        Label desc = new Label(description);
        desc.setFont(Font.font(13));
        desc.setTextFill(Color.web(isDark ? TEXT_GRAY300 : TEXT_GRAY700));
        desc.setWrapText(true);
        desc.setPadding(new Insets(0, 0, 0, 24));

        card.getChildren().addAll(titleLabel, desc);
        return card;
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  Shared helpers
    // ══════════════════════════════════════════════════════════════════════════
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
