package co.edu.poli.jmm.servicios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Administra una unica conexion SQLite para el juego.
 * Al crearse tambien asegura que existan las tablas necesarias.
 */
public class ConexionBD {

    private static final String URL = "jdbc:sqlite:jmm_game.db";
    private static final String USER = "";
    private static final String PASS = "";

    private static ConexionBD instancia;
    private Connection conexion;

    /**
     * Abre la conexion y prepara el esquema local de la base de datos.
     */
    private ConexionBD() {
        try {
            Class.forName("org.sqlite.JDBC");
            conexion = DriverManager.getConnection(URL, USER, PASS);
            inicializarEsquema();
        } catch (ClassNotFoundException e) {
            System.err.println("[ConexionBD] Driver no encontrado: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("[ConexionBD] Error al conectar: " + e.getMessage());
        }
    }

    /**
     * Retorna la instancia compartida de conexion.
     */
    public static ConexionBD getInstancia() {
        if (instancia == null) {
            instancia = new ConexionBD();
        }
        return instancia;
    }

    /**
     * Entrega la conexion activa para que los DAO ejecuten sus consultas.
     */
    public Connection getConexion() {
        return conexion;
    }

    /**
     * Crea las tablas de jugadores y partidas si aun no existen.
     */
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
