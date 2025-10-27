package com.diego.esports.datos;


import com.diego.esports.modelo.entidades.Jugador;
import com.diego.esports.modelo.entidades.Usuarios;
import com.diego.esports.utils.ConexionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class impDatos implements intDatos {
    public void existe() {
        if (Existe()) {
            System.out.println("Conexión a la base de datos establecida...\n");
//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//            }
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

    @Override
    public void registrarUsuario(String user, String pass, String email, String nick) {
        String sql = "{CALL sp_AgregarJugador(?, ?, ?, ?)}";

        try (Connection conn = ConexionDB.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, user);
            stmt.setString(2, email);
            stmt.setString(3, pass);
            stmt.setString(4, nick);
            stmt.execute();

            System.out.println("Usuario registrado correctamente.");

        } catch (SQLException e) {
            System.err.println("Error al registrar usuario: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void actualizarJugador(String id, String nombre, String nickname, String email, String estado, String rol, String contrasena) {

        int rolUsuario;
        if (Objects.equals(rol, "admin")) {
            rolUsuario = 3;
        } else if (Objects.equals(rol, "organizador")) {
            rolUsuario = 2;
        } else {
            rolUsuario = 1;
        }

        String sql = "{CALL sp_ActualizarJugador(?, ?, ?, ?, ?, ?, ?)}";

        try (Connection conn = ConexionDB.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, Integer.parseInt(id));
            stmt.setString(2, nombre);
            stmt.setString(3, email);
            stmt.setString(4, nickname);
            stmt.setString(5, estado);
            stmt.setInt(6, rolUsuario);
            stmt.setString(7, contrasena);

            stmt.execute();
            System.out.println("Usuario actualizado correctamente.");

        } catch (SQLException e) {
            System.err.println("Error al actualizar usuario: " + e.getMessage());
            throw new RuntimeException(e);
        }
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
                jugador.setContrasena(rs.getString("nombre_equipo"));
                listaJugadores.add(jugador);
            }

        }catch (SQLException e){
            System.out.println("Error al listar jugadores: " + e.getMessage());
        }

        return listaJugadores;
    }
    @Override
    public void eliminarJugador(int id) {
        String sql = "{CALL dbo.sp_EliminarJugador(?)}";
        try (Connection conn = ConexionDB.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, id);
            stmt.execute();

            System.out.println("Usuario eliminado correctamente.");

        } catch (SQLException e) {
            int errorCode = e.getErrorCode();
            String message = e.getMessage();
            if (errorCode == 51011) {
                System.err.println("No se puede eliminar un administrador. (SQL Error " + errorCode + ")");
            } else if (errorCode == 51010) {
                System.err.println("Usuario no encontrado. (SQL Error " + errorCode + ")");
            } else {
                System.err.println("Error al eliminar usuario: " + message + " (SQL Error " + errorCode + ")");
            }
            throw new RuntimeException(e);
        }
    }

}