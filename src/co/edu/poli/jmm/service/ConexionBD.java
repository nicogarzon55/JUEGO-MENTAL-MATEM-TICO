package co.edu.poli.jmm.service;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionBD {

    private static ConexionBD instancia;
    private Connection conexion;

    private ConexionBD() {
        try {
            conexion = DriverManager.getConnection("jdbc:sqlite:juego.db");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ConexionBD getInstancia() {
        if (instancia == null) {
            instancia = new ConexionBD();
        }
        return instancia;
    }

    public Connection getConexion() {
        return conexion;
    }
}