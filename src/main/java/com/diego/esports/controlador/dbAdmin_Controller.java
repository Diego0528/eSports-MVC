package com.diego.esports.controlador;

import com.diego.esports.modelo.dao.SceneManager;
import com.diego.esports.utils.Paths;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
public class dbAdmin_Controller {


    @FXML
    private Button btnCrudJugadores;

    @FXML
    private StackPane contentArea;

    @FXML
    private BorderPane root;

    @FXML
    private TextField searchField;


    @FXML
    void CrudJugadores(ActionEvent event) {
        JugadoresCtrl();
    }

    public void initialize() {
        System.out.println("Dashboard loaded successfully!");
    }

    private void JugadoresCtrl() {
        SceneManager.cambiarEscena(Paths.JugadoresCTRL);
    }
}
