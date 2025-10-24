package com.diego.esports.modelo.entidades;

public class Usuarios {
    private int id;
    private String nombreUsuario;
    private String contrasena;
    private String rol;

    public Usuarios(int id, String nombreUsuario, String contrasena, String rol) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.rol = rol;
    }

    public int getId() { return id; }
    public String getNombreUsuario() { return nombreUsuario; }
    public String getContrasena() { return contrasena; }
    public String getRol() { return rol; }
}
