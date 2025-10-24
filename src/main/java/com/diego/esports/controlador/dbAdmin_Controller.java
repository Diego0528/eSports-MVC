package com.diego.esports.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

public class dbAdmin_Controller {

    @FXML
    private StackPane contentArea;

    @FXML
    private TextField searchField;

    public void initialize() {
        System.out.println("Dashboard loaded successfully!");
    }
}
