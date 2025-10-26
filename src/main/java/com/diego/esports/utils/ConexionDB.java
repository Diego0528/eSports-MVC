package com.diego.esports.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    private static final String URL = "jdbc:sqlserver://ANDRJINOJIMENEZ\\SMSS:51901;"
            + "databaseName=eSportsdb;"
            + "encrypt=false;"
            + "trustServerCertificate=true";
    private static final String USER = "sa";
    private static final String PASSWORD = "admin2025";


    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Error de conexión: " + e.getMessage());
            throw e;
        }
    }
}