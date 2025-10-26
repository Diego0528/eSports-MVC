package com.diego.esports.modelo.entidades;

import javafx.beans.property.*;

public class Jugador {
    private int id;
    private String nickname;
    private String nombre;
    private String email;
    private String rol;
    private String equipo;


    public Jugador(){}
    public Jugador(int id, String nickname, String nombre, String email, String rol, String equipo) {
        this.id = id;
        this.nickname = nickname;
        this.nombre = nombre;
        this.email = email;
        this.rol = rol;
        this.equipo = equipo;
    }

    public int getId() {return id;}
    public String getNickname() {return nickname;}
    public String getNombre() {return nombre;}
    public String getEmail() {return email;}
    public String getRol() {return rol;}
    public String getEquipo() {return equipo;}

    public void setId(int id) {this.id = id;}
    public void setNickname(String nickname) {this.nickname = nickname;}
    public void setNombre(String nombre) {this.nombre = nombre;}
    public void setEmail(String email) {this.email = email;}
    public void setRol(String rol) {this.rol = rol;}
    public void setEquipo(String equipo) {this.equipo = equipo;}
}
