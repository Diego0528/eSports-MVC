package com.diego.esports.modelo.entidades;

public class Juego {
    private int id;
    private String nombre;
    private String descripcion;
    private String estado;
    private String modalidad;
    private String plataforma;
    private String imagen;
    private String fecha;

    public Juego(){}
    public Juego(int id, String nombre, String descripcion, String estado, String modalidad, String plataforma, String imagen, String fecha) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = estado;
        this.modalidad = modalidad;
        this.plataforma = plataforma;
        this.imagen = imagen;
        this.fecha = fecha;
    }
    public Juego(String nombre, String imagen){
        this.nombre = nombre;
        this.imagen = imagen;
    }

    public int getId() {return id;}
    public String getNombre() {return nombre;}
    public String getDescripcion() {return descripcion;}
    public String getEstado() {return estado;}
    public String getModalidad() {return modalidad;}
    public String getPlataforma() {return plataforma;}
    public String getImagen() {return imagen;}
    public String getFecha() {return fecha;}

    public void setId(int id) {this.id = id;}
    public void setNombre(String nombre) {this.nombre = nombre;}
    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}
    public void setEstado(String estado) {this.estado = estado;}
    public void setModalidad(String modalidad) {this.modalidad = modalidad;}
    public void setPlataforma(String plataforma) {this.plataforma = plataforma;}
    public void setImagen(String imagen) {this.imagen = imagen;}
    public void setFecha(String fecha) {this.fecha = fecha;}


}
