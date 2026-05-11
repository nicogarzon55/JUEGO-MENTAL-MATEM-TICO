package co.edu.poli.jmm.servicios;

import co.edu.poli.jmm.modelo.Jugador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO interno que representa una fila de la tabla partidas.
 */
class Partida {
    int id;
    int idJugador;
    int nivel;
    int puntaje;
    boolean completado;
    String fecha;
}

/**
 * Acceso a datos de partidas jugadas.
 * Permite guardar resultados y consultar el historial.
 */
public class DAOPartida implements CRUD<Partida, Integer> {

    private final Connection con;

    /**
     * Usa la conexion compartida de la aplicacion.
     */
    public DAOPartida() {
        this.con = ConexionBD.getInstancia().getConexion();
    }

    /**
     * Inserta una partida y asigna el id generado.
     */
    @Override
    public String create(Partida p) {
        String sql = "INSERT INTO partidas (id_jugador, nivel, puntaje, completado) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, p.idJugador);
            ps.setInt(2, p.nivel);
            ps.setInt(3, p.puntaje);
            ps.setInt(4, p.completado ? 1 : 0);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) p.id = rs.getInt(1);
            }
            return "OK: Partida guardada id=" + p.id;
        } catch (SQLException e) {
            return "ERROR: " + e.getMessage();
        }
    }

    /**
     * Busca una partida por id.
     */
    @Override
    public Partida readOne(Integer id) {
        String sql = "SELECT * FROM partidas WHERE id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) {
            System.err.println("[DAOPartida] readOne: " + e.getMessage());
        }
        return null;
    }

    /**
     * Lista las partidas mas recientes primero.
     */
    @Override
    public List<Partida> readAll() {
        List<Partida> lista = new ArrayList<>();
        String sql = "SELECT * FROM partidas ORDER BY fecha DESC";
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("[DAOPartida] readAll: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Guarda el resultado final de un nivel para el jugador indicado.
     */
    public String guardarPartida(Jugador jugador, int nivelNum, boolean completado) {
        Partida p = new Partida();
        p.idJugador = jugador.getId();
        p.nivel = nivelNum;
        p.puntaje = jugador.getPuntaje();
        p.completado = completado;
        return create(p);
    }

    /**
     * Convierte la fila actual del ResultSet en una Partida.
     */
    private Partida mapear(ResultSet rs) throws SQLException {
        Partida p = new Partida();
        p.id = rs.getInt("id");
        p.idJugador = rs.getInt("id_jugador");
        p.nivel = rs.getInt("nivel");
        p.puntaje = rs.getInt("puntaje");
        p.completado = rs.getInt("completado") == 1;
        p.fecha = rs.getString("fecha");
        return p;
    }
}
