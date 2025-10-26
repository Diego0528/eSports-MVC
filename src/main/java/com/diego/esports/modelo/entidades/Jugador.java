package com.diego.esports.modelo.entidades;

import javafx.beans.property.*;

public class Jugador {
    private final IntegerProperty id;
    private final StringProperty nombre;
    private final StringProperty nickname;
    private final StringProperty equipo;
    private final StringProperty pais;

    public Jugador(int id, String nombre, String nickname, String equipo, String pais) {
        this.id = new SimpleIntegerProperty(id);
        this.nombre = new SimpleStringProperty(nombre);
        this.nickname = new SimpleStringProperty(nickname);
        this.equipo = new SimpleStringProperty(equipo);
        this.pais = new SimpleStringProperty(pais);
    }

    public int getId() { return id.get(); }
    public String getNombre() { return nombre.get(); }
    public String getNickname() { return nickname.get(); }
    public String getEquipo() { return equipo.get(); }
    public String getPais() { return pais.get(); }

    public IntegerProperty idProperty() { return id; }
    public StringProperty nombreProperty() { return nombre; }
    public StringProperty nicknameProperty() { return nickname; }
    public StringProperty equipoProperty() { return equipo; }
    public StringProperty paisProperty() { return pais; }
}
