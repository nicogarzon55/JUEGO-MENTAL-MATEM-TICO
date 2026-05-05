package co.edu.poli.jmm.servicios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton que gestiona la conexión a la base de datos.
 *
 * Configuración por defecto: SQLite (archivo local jmm_game.db).
 * Para cambiar a MySQL/PostgreSQL, modificar las constantes URL, USER y PASS
 * y agregar el driver correspondiente al classpath.
 */
public class ConexionBD {

    // ── Configuración ─────────────────────────────────────────────────────────
    private static final String URL  = "jdbc:sqlite:jmm_game.db";
    private static final String USER = "";   // SQLite no requiere usuario
    private static final String PASS = "";   // SQLite no requiere contraseña

    // ── Singleton ─────────────────────────────────────────────────────────────
    private static ConexionBD instancia;
    private Connection         conexion;

    private ConexionBD() {
        try {
            // Carga explícita del driver (útil en entornos sin módulos automáticos)
            Class.forName("org.sqlite.JDBC");
            conexion = DriverManager.getConnection(URL, USER, PASS);
            inicializarEsquema();
        } catch (ClassNotFoundException e) {
            System.err.println("[ConexionBD] Driver no encontrado: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("[ConexionBD] Error al conectar: " + e.getMessage());
        }
    }

    /** Retorna la única instancia de la conexión. */
    public static ConexionBD getInstancia() {
        if (instancia == null) {
            instancia = new ConexionBD();
        }
        return instancia;
    }

    /** Retorna el objeto Connection activo. */
    public Connection getConexion() {
        return conexion;
    }

    /** Crea las tablas si no existen. */
    private void inicializarEsquema() throws SQLException {
        String sqlJugadores =
            "CREATE TABLE IF NOT EXISTS jugadores (" +
            "  id      INTEGER PRIMARY KEY AUTOINCREMENT," +
            "  nombre  TEXT    NOT NULL," +
            "  puntaje INTEGER NOT NULL DEFAULT 10" +
            ");";

        String sqlPartidas =
            "CREATE TABLE IF NOT EXISTS partidas (" +
            "  id           INTEGER PRIMARY KEY AUTOINCREMENT," +
            "  id_jugador   INTEGER NOT NULL," +
            "  nivel        INTEGER NOT NULL," +
            "  puntaje      INTEGER NOT NULL," +
            "  completado   INTEGER NOT NULL DEFAULT 0," +
            "  fecha        TEXT    DEFAULT CURRENT_TIMESTAMP," +
            "  FOREIGN KEY (id_jugador) REFERENCES jugadores(id)" +
            ");";

        try (var stmt = conexion.createStatement()) {
            stmt.execute(sqlJugadores);
            stmt.execute(sqlPartidas);
        }
    }
}
