package com.diego.esports.modelo.entidades;

public class Jugador {
    private int id;
    private String nickname;
    private String nombre;
    private String email;
    private String rol;
    private String estado;
    private String contrasena;


    public Jugador(){}
    public Jugador(int id, String nickname, String nombre, String email, String rol, String contrasena, String estado) {
        this.id = id;
        this.nickname = nickname;
        this.nombre = nombre;
        this.email = email;
        this.rol = rol;
        this.estado = estado;
        this.contrasena = contrasena;
    }

    public int getId() {return id;}
    public String getNickname() {return nickname;}
    public String getNombre() {return nombre;}
    public String getEmail() {return email;}
    public String getRol() {return rol;}
    public String getContrasena() {return contrasena;}
    public String getEstado() {return estado;}

    public void setId(int id) {this.id = id;}
    public void setNickname(String nickname) {this.nickname = nickname;}
    public void setNombre(String nombre) {this.nombre = nombre;}
    public void setEmail(String email) {this.email = email;}
    public void setRol(String rol) {this.rol = rol;}
    public void setContrasena(String contrasena) {this.contrasena = contrasena;}
    public void setEstado(String estado) {this.estado = estado;}
}
