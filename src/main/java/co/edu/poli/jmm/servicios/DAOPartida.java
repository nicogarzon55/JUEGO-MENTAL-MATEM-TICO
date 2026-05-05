package co.edu.poli.jmm.servicios;

import co.edu.poli.jmm.modelo.Jugador;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa una fila de la tabla "partidas".
 * Se usa como DTO sencillo dentro de DAOPartida.
 */
class Partida {
    int     id;
    int     idJugador;
    int     nivel;
    int     puntaje;
    boolean completado;
    String  fecha;
}

/**
 * Acceso a datos para partidas guardadas.
 */
public class DAOPartida implements CRUD<Partida, Integer> {

    private final Connection con;

    public DAOPartida() {
        this.con = ConexionBD.getInstancia().getConexion();
    }

    // ── CRUD ──────────────────────────────────────────────────────────────────

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
     * Guarda la partida final de un jugador.
     * Método de conveniencia llamado desde los controladores.
     */
    public String guardarPartida(Jugador jugador, int nivelNum, boolean completado) {
        Partida p = new Partida();
        p.idJugador  = jugador.getId();
        p.nivel      = nivelNum;
        p.puntaje    = jugador.getPuntaje();
        p.completado = completado;
        return create(p);
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private Partida mapear(ResultSet rs) throws SQLException {
        Partida p = new Partida();
        p.id         = rs.getInt("id");
        p.idJugador  = rs.getInt("id_jugador");
        p.nivel      = rs.getInt("nivel");
        p.puntaje    = rs.getInt("puntaje");
        p.completado = rs.getInt("completado") == 1;
        p.fecha      = rs.getString("fecha");
        return p;
    }
}
