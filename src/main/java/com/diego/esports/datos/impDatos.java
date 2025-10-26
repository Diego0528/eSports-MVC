package com.diego.esports.datos;


import com.diego.esports.modelo.entidades.Jugador;
import com.diego.esports.modelo.entidades.Usuarios;
import com.diego.esports.utils.ConexionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class impDatos implements intDatos {
    public void existe() {
        if (Existe()) {
            System.out.println("Conexión a la base de datos establecida...\n");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } else {
            System.out.println("Error al conectar con la base de datos\n");
            System.exit(1);
        }
    }
    public boolean Existe() {
        try (Connection conn = ConexionDB.getConnection()) {
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public Usuarios encontrarUsuario(String usuario, String contrasena) {
        String sql = "SELECT * FROM Usuario WHERE nombre_usuario = ? AND contraseña = ?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario);
            stmt.setString(2, contrasena);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Usuarios(
                            rs.getInt("id_usuario"),
                            rs.getString("nombre_usuario"),
                            rs.getString("contraseña")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int saberRol(int Id){
        int rol = 0;
        String sql = "SELECT * FROM UsuarioRol WHERE id_usuario = ?";
        try (Connection conn = ConexionDB.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, Id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    rol = rs.getInt("id_rol");
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return rol;
    }

    public List<Jugador> listarJugadores(){
        List<Jugador> listaJugadores = new ArrayList<>();
        String sql = "SELECT * FROM dbo.vw_JugadoresDetalle";

        try (Connection conn = ConexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Jugador jugador = new Jugador();
                jugador.setId(rs.getInt("id_jugador"));
                jugador.setNickname(rs.getString("nickname"));
                jugador.setNombre(rs.getString("nombre_usuario"));
                jugador.setEmail(rs.getString("email"));
                jugador.setRol(rs.getString("rol"));
                jugador.setEquipo(rs.getString("nombre_equipo"));
                listaJugadores.add(jugador);
            }

        }catch (SQLException e){
            System.out.println("Error al listar jugadores: " + e.getMessage());
        }

        return listaJugadores;
    }
}