package com.diego.esports.datos;

import com.diego.esports.modelo.entidades.Usuarios;

public interface intDatos{
    void existe();
    Usuarios encontrarUsuario(String buscar, String contrasena);
    int saberRol(int id);
    void registrarUsuario(String user, String pass, String email, String nick);
    void actualizarJugador(String id, String nombre, String nickname, String email, String estado, String rol, String contrasena);
    void eliminarJugador(int id);
}
