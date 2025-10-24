package com.diego.esports.datos;

import com.diego.esports.modelo.entidades.Usuarios;

public interface intDatos{
    void existe();
    Usuarios encontrarUsuario(String buscar, String contrasena);
}
