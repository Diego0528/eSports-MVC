module esports {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires java.desktop;

    opens com.diego.esports.application to javafx.fxml;
    opens com.diego.esports.controlador to javafx.fxml;
    opens com.diego.esports.utils to javafx.base;
    opens com.diego.esports.modelo.dao to javafx.base;
    opens com.diego.esports.modelo.entidades to javafx.base;
    opens com.diego.esports.datos to javafx.base;
    exports com.diego.esports.application;
    exports com.diego.esports.controlador;
    exports com.diego.esports.utils;
    exports com.diego.esports.modelo.dao;
    exports com.diego.esports.modelo.entidades;
    exports com.diego.esports.datos;


}