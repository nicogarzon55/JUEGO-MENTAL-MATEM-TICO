package co.edu.poli.jmm.servicios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton que gestiona la conexión a Supabase PostgreSQL.
 */
public class ConexionBD {

    // ── Configuración PostgreSQL Supabase ────────────────────────────────
    private static final String URL =
            "jdbc:postgresql://aws-0-us-west-2.pooler.supabase.com:5432/postgres";

    private static final String USER =
            "postgres.tvgpvliqzqmfpnrvdmkc";

    private static final String PASS =
            "BjbkPUwHV7ufFfzb";

    // ── Singleton ────────────────────────────────────────────────────────
    private static ConexionBD instancia;
    private Connection conexion;

    private ConexionBD() {
        try {

            // Driver PostgreSQL
            Class.forName("org.postgresql.Driver");

            conexion = DriverManager.getConnection(URL, USER, PASS);

            System.out.println("Conexión exitosa a Supabase");

            inicializarEsquema();

        } catch (ClassNotFoundException e) {

            System.err.println("[ConexionBD] Driver no encontrado: " + e.getMessage());

        } catch (SQLException e) {

            System.err.println("[ConexionBD] Error al conectar: " + e.getMessage());

        }
    }

    /** Retorna la única instancia */
    public static ConexionBD getInstancia() {

        if (instancia == null) {
            instancia = new ConexionBD();
        }

        return instancia;
    }

    /** Retorna la conexión */
    public Connection getConexion() {
        return conexion;
    }

    /** Crear tablas si no existen */
    private void inicializarEsquema() throws SQLException {

        String sqlJugadores =
                "CREATE TABLE IF NOT EXISTS jugadores (" +
                        "id SERIAL PRIMARY KEY," +
                        "nombre VARCHAR(100) NOT NULL," +
                        "puntaje INTEGER NOT NULL DEFAULT 10" +
                        ");";

        String sqlPartidas =
                "CREATE TABLE IF NOT EXISTS partidas (" +
                        "id SERIAL PRIMARY KEY," +
                        "id_jugador INTEGER NOT NULL," +
                        "nivel INTEGER NOT NULL," +
                        "puntaje INTEGER NOT NULL," +
                        "completado BOOLEAN DEFAULT FALSE," +
                        "fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                        "FOREIGN KEY (id_jugador) REFERENCES jugadores(id)" +
                        ");";

        try (var stmt = conexion.createStatement()) {

            stmt.execute(sqlJugadores);
            stmt.execute(sqlPartidas);

        }
    }
}