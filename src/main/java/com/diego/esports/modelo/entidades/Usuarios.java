package com.diego.esports.modelo.entidades;

public class Usuarios {
    private int id;
    private String nombreUsuario;
    private String contrasena;

    public Usuarios(int id, String nombreUsuario, String contrasena) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
    }

    public int getId() { return id; }
    public String getNombreUsuario() { return nombreUsuario; }
    public String getContrasena() { return contrasena; }
}
