module esports {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;

    opens com.diego.esports.application to javafx.fxml;
    opens com.diego.esports.controlador to javafx.fxml;
    opens com.diego.esports.utils to javafx.base;
    exports com.diego.esports.application;
    exports com.diego.esports.controlador;
    exports com.diego.esports.utils;

}