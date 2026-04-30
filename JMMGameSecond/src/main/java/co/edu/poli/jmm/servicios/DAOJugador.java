package co.edu.poli.jmm.servicios;

import co.edu.poli.jmm.modelo.Jugador;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Acceso a datos para la entidad {@link Jugador}.
 */
public class DAOJugador implements CRUD<Jugador, Integer> {

    private final Connection con;

    public DAOJugador() {
        this.con = ConexionBD.getInstancia().getConexion();
    }

    // ── CRUD ──────────────────────────────────────────────────────────────────

    @Override
    public String create(Jugador jugador) {
        String sql = "INSERT INTO jugadores (nombre, puntaje) VALUES (?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, jugador.getNombre());
            ps.setInt(2, jugador.getPuntaje());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    jugador.setId(rs.getInt(1));
                }
            }
            return "OK: Jugador '" + jugador.getNombre() + "' guardado con id=" + jugador.getId();
        } catch (SQLException e) {
            return "ERROR: " + e.getMessage();
        }
    }

    @Override
    public Jugador readOne(Integer id) {
        String sql = "SELECT * FROM jugadores WHERE id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("[DAOJugador] readOne: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Jugador> readAll() {
        List<Jugador> lista = new ArrayList<>();
        String sql = "SELECT * FROM jugadores ORDER BY puntaje DESC";
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.err.println("[DAOJugador] readAll: " + e.getMessage());
        }
        return lista;
    }

    /** Actualiza el puntaje de un jugador existente. */
    public String updatePuntaje(Jugador jugador) {
        String sql = "UPDATE jugadores SET puntaje = ? WHERE id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, jugador.getPuntaje());
            ps.setInt(2, jugador.getId());
            int filas = ps.executeUpdate();
            return filas > 0 ? "OK" : "No se encontró el jugador";
        } catch (SQLException e) {
            return "ERROR: " + e.getMessage();
        }
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private Jugador mapear(ResultSet rs) throws SQLException {
        Jugador j = new Jugador();
        j.setId(rs.getInt("id"));
        j.setNombre(rs.getString("nombre"));
        j.setPuntaje(rs.getInt("puntaje"));
        return j;
    }
}
