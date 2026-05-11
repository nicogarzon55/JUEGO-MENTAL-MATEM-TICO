# 🧠 JMM – Juego Mental Matemático

Aplicación de escritorio desarrollada en **Java 17 + JavaFX 21 + PostgreSQL**, basada en arquitectura **MVC**, donde el jugador debe descubrir reglas matemáticas ocultas mediante lógica y deducción.

---

# 📸 Vista general

JMM propone un sistema de aprendizaje interactivo dividido en dos fases:

1. **Exploración de patrones**
2. **Validación de la regla matemática**

El jugador analiza entradas y salidas generadas por el sistema para deducir la fórmula correcta antes de quedarse sin puntos.

---

# ✨ Características principales

- 🎮 Juego interactivo por niveles
- 🧠 Reglas matemáticas dinámicas
- 📊 Sistema de puntaje
- 🧩 Arquitectura MVC
- 🎨 Interfaz gráfica moderna con JavaFX
- 📑 Validación visual de respuestas
- 🔄 Navegación entre escenas
- 📚 Ventana integrada de reglas del juego

---

# 🛠 Tecnologías utilizadas

| Tecnología | Uso |
|---|---|
| Java 17 | Lógica principal |
| JavaFX 21 | Interfaz gráfica |
| Maven | Gestión de dependencias |
| PostgreSQL | Base de datos |
| JDBC | Persistencia de datos |
| CSS | Estilos visuales |
| FXML | Diseño de interfaces |

---

# 🏗 Arquitectura del proyecto

El proyecto implementa el patrón **MVC (Modelo - Vista - Controlador)**:

## Modelo
Contiene la lógica del juego y las entidades principales.

- `Juego`
- `Jugador`
- `Nivel`
- `Regla`
- `TablaIO`

## Vista
Interfaces gráficas construidas con JavaFX y FXML.

- Interfaces `.fxml`
- `GameRulesWindow`

## Controladores
Gestionan la interacción entre la interfaz y la lógica.

- `UsuarioController`
- `AdivinarReglaController`
- `JuegoPrincipalController`
- `JuegoGanadoController`
- `SelectorNivelController`

## Servicios
Persistencia y acceso a datos.

- `ConexionBD`
- `DAOJugador`
- `DAOPartida`
- `CRUD`

---

# 📂 Estructura del proyecto

```text
JMMGame/
├── pom.xml
└── src/main/
    ├── java/co/edu/poli/jmm/
    │   ├── Main.java
    │   ├── Navegacion.java
    │   ├── modelo/
    │   ├── controladores/
    │   ├── servicios/
    │   └── vista/
    └── resources/
```

---

# 🎮 Flujo del juego

```text
Inicio
  ↓
Ingreso de nombre
  ↓
Selección de nivel
  ↓
Fase 1 → Descubrir patrón
  ↓
Fase 2 → Validar respuestas
  ↓
Resultado final
  ↓
Siguiente nivel o reinicio
```

---

# 📐 Reglas matemáticas implementadas

## 🟢 Niveles fáciles
- `y = 2x`
- `y = x + 5`
- `y = x - 1`

## 🟡 Niveles medios
- `y = x² + 1`
- `y = 3x + 2`
- `y = 2x - 3`

## 🔴 Niveles difíciles
- `y = 3x - 5`
- `y = x² - x + 2`
- `y = 2x² + 1`

---

## Tablas utilizadas

### jugadores
| Campo | Tipo |
|---|---|
| id | INTEGER |
| nombre | TEXT |
| puntaje | INTEGER |

### partidas
| Campo | Tipo |
|---|---|
| id | INTEGER |
| id_jugador | INTEGER |
| nivel | INTEGER |
| puntaje | INTEGER |
| completado | BOOLEAN |
| fecha | TEXT |

---

# ▶ Cómo ejecutar el proyecto

## Requisitos

- Java 17 o superior
- Maven 3.8+

## Clonar repositorio

```bash
git clone https://github.com/TU-USUARIO/TU-REPOSITORIO.git
```

## Ejecutar aplicación

```bash
mvn clean javafx:run
```

## Generar JAR

```bash
mvn clean package
```

---

# 🧩 Componentes destacados

## 🎯 Sistema de reglas dinámicas
Cada nivel selecciona aleatoriamente una regla matemática desde un conjunto predefinido.

## 📊 Sistema de puntaje
- El jugador inicia con 10 puntos
- Cada intento incorrecto reduce el puntaje
- Completar niveles otorga puntos bonus

## 🎨 Interfaz JavaFX
La interfaz implementa:
- tablas dinámicas
- validaciones visuales
- ventanas modales
- estilos personalizados

---

# 📚 Conceptos aplicados

- Programación orientada a objetos
- Arquitectura MVC
- DAO Pattern
- Singleton Pattern
- JavaFX Properties
- Persistencia con JDBC
- Manejo de escenas JavaFX
- Modularización del código

---

# 🚀 Posibles mejoras futuras

- 🏆 Sistema de ranking
- 🔊 Sonidos y efectos
- 📈 Estadísticas de partidas
- 🎞 Animaciones JavaFX
- 🌐 Modo multijugador
- 🧮 Más reglas matemáticas

---

# 📄 Licencia

Proyecto desarrollado con fines educativos.
