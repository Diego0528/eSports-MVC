package com.diego.esports.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.control.TextField;

public class MainController {

    @FXML
    private StackPane contentArea;

    @FXML
    private TextField searchField;

    public void initialize() {
        System.out.println("Dashboard loaded successfully!");
    }
}
