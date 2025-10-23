module com.diego.esports {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.diego.esports to javafx.fxml;
    exports com.diego.esports;
    exports com.diego.esports.application;
    opens com.diego.esports.application to javafx.fxml;
    exports com.diego.esports.controlador;
    opens com.diego.esports.controlador to javafx.fxml;
}