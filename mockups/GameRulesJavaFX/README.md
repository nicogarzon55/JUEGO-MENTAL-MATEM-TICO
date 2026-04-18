# GameRulesWindow – JavaFX

Puerto fiel del componente React `GameRulesWindow` a JavaFX puro.

## Requisitos

| Herramienta | Versión mínima |
|-------------|---------------|
| Java JDK    | 17 (LTS) o superior |
| Maven       | 3.8+          |
| JavaFX SDK  | 21 (descargado automáticamente por Maven) |

> **Nota:** No necesitas instalar JavaFX manualmente. Maven descarga las dependencias automáticamente mediante los artefactos de `org.openjfx`.

---

## Estructura del proyecto

```
GameRulesJavaFX/
├── pom.xml
└── src/
    └── main/
        └── java/
            ├── module-info.java
            └── com/gamerules/
                ├── Main.java              ← Punto de entrada
                └── GameRulesWindow.java   ← Componente principal
```

---

## Compilar y ejecutar

### Opción 1 – Maven (recomendado)

```bash
# Desde la carpeta GameRulesJavaFX/
mvn clean javafx:run
```

### Opción 2 – Compilar JAR y ejecutar

```bash
mvn clean package
java -jar target/game-rules-window-1.0.0.jar
```

---

## Correspondencia React → JavaFX

| React (Tailwind / TSX)        | JavaFX                              |
|-------------------------------|-------------------------------------|
| `useState`                    | campos `boolean isDark`, `String activeTab` |
| `className` condicional       | `setStyle(...)` con strings CSS inline |
| `border-b-2 border-purple-600`| `-fx-border-width: 0 0 2 0`        |
| `flex items-center gap-3`     | `HBox(gap)` + `setAlignment(CENTER_LEFT)` |
| `rounded-full`                | `-fx-background-radius: 50`         |
| `space-y-4`                   | `VBox.setSpacing(16)`              |
| `max-h-[500px] overflow-y-auto`| `ScrollPane.setMaxHeight(500)`     |
| `hover:bg-white/20`           | `setOnMouseEntered / Exited`       |
| Lucide icons                  | Emojis Unicode equivalentes        |

---

## Paleta de colores

| Token                | Valor hex  |
|----------------------|------------|
| Purple (primario)    | `#8d5cf6`  |
| Purple hover         | `#7c4de0`  |
| Background light     | `#ffffff`  |
| Background dark      | `#111827`  |
| Border light         | `#e5e7eb`  |
| Border dark          | `#374151`  |

---

## Funcionalidades implementadas

- ✅ Header con color morado `#8d5cf6`
- ✅ Botón de modo oscuro/claro (toggle)
- ✅ Botón de cierre (opcional, vía constructor)
- ✅ Tres pestañas: Descripción, Instrucciones, Reglas
- ✅ Pestaña activa con subrayado morado
- ✅ Contenido scrollable (altura máx. 500 px)
- ✅ Modo oscuro completo en todos los elementos
- ✅ Tarjetas de reglas con colores semánticos (rojo, verde, azul, amarillo)
- ✅ Pasos numerados con círculos morados
- ✅ Footer con botón "¡Entendido!"
