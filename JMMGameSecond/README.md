# JMM – Juego de Reglas Matemáticas
**Java 17 + JavaFX 21 + SQLite | Patrón MVC**

---

## Estructura del proyecto

```
JMMGame/
├── pom.xml
└── src/main/
    ├── java/co/edu/poli/jmm/
    │   ├── Main.java                          ← Punto de entrada
    │   ├── modelo/
    │   │   ├── Juego.java                     ← Coordinador principal
    │   │   ├── Jugador.java                   ← Entidad jugador
    │   │   ├── Nivel.java                     ← Nivel con regla y tabla
    │   │   ├── Regla.java                     ← Lógica de transformación
    │   │   └── TablaIO.java                   ← Pares entrada/salida
    │   ├── servicios/
    │   │   ├── CRUD.java                      ← Interfaz genérica
    │   │   ├── ConexionBD.java                ← Singleton SQLite
    │   │   ├── DAOJugador.java                ← Persistencia jugadores
    │   │   └── DAOPartida.java                ← Persistencia partidas
    │   ├── controladores/
    │   │   ├── UsuarioController.java         ← Pantalla de bienvenida
    │   │   ├── AdivinarReglaController.java   ← Fase 1: probar números
    │   │   ├── JuegoPrincipalController.java  ← Fase 2: completar tabla
    │   │   └── JuegoGanadoController.java     ← Pantalla de victoria
    │   └── vista/
    │       └── GameRulesWindow.java           ← Componente guía del juego
    └── resources/co/edu/poli/jmm/vista/
        ├── InterfazUsuarioFinal.fxml
        ├── InterfazAdivinarReglaJuego.fxml
        ├── InterfazJuegoPrincipal.fxml
        └── InterfazJuegoGanado.fxml
```

---

## Flujo del juego

```
[Bienvenida] → ingresa nombre
     ↓
[Fase 1 – Adivinar Regla]
  • Ingresa números, observa la salida
  • Cada intento -1 punto (inicia con 10)
  • Si llega a 0 → DERROTA
     ↓  (cuando cree conocer la regla)
[Fase 2 – Validar Regla]
  • Tabla con 5 entradas prefijadas
  • Jugador escribe las salidas
  • Revisa → verde=correcto / rojo=incorrecto
     ↓  (si acierta todo)
[Pantalla Victoria] → +5 puntos bonus
  • Siguiente nivel  (niveles: Fácil→Medio→Difícil)
  • Intento nuevo
```

---

## Reglas matemáticas por nivel

| Nivel | Dificultad | Fórmula    |
|-------|-----------|------------|
| 1     | Fácil     | y = 2x     |
| 2     | Medio     | y = x² + 1 |
| 3     | Difícil   | y = 3x − 5 |

---

## Cómo ejecutar

### Requisitos
- Java 17+
- Maven 3.8+

### Comandos
```bash
# Compilar
mvn clean compile

# Ejecutar
mvn javafx:run

# Crear JAR
mvn package
java -jar target/jmm-game-1.0-SNAPSHOT.jar
```

---

## Base de datos

La aplicación crea automáticamente `jmm_game.db` (SQLite) en el directorio de trabajo.

**Tablas:**
- `jugadores` — id, nombre, puntaje
- `partidas`  — id, id_jugador, nivel, puntaje, completado, fecha

Para cambiar a MySQL, modificar las constantes `URL`, `USER` y `PASS` en `ConexionBD.java`.

---

## Puntos pendientes / extensiones sugeridas

- [ ] Pantalla de **derrota** dedicada (actualmente muestra alerta y vuelve al inicio)
- [ ] **Ranking/Leaderboard** usando `DAOJugador.readAll()`
- [ ] Más reglas matemáticas (agregar casos en `Regla.calcularOutput()`)
- [ ] Animaciones de transición entre pantallas
- [ ] Sonidos con `javafx.scene.media`
