package com.diego.esports.modelo.entidades;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.diego.esports.utils.ConexionDB;

public class Jugador {/*

    public List<Jugador> obtenerTodos() {
        List<Jugador> lista = new ArrayList<>();
        String sql = "SELECT idJugador, nombre, equipo FROM Jugadores";

        try (Connection conn = ConexionSQL.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new Jugador(
                        rs.getInt("idJugador"),
                        rs.getString("nombre"),
                        rs.getString("equipo")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }*/
}
