package com.diego.esports.controlador;

import com.diego.esports.modelo.dao.SceneManager;
import com.diego.esports.utils.Paths;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class dbAdmin_Controller {


    public ImageView bannerImage;
    public ImageView logoImage;
    public Button followBtn;
    public Button createTournamentBtn;
    public TabPane tabPane;
    public Tab tabOverview;
    @FXML private Button btnCrudJugadores;
    @FXML private StackPane contentArea;
    @FXML private BorderPane root;
    @FXML private TextField searchField;
    @FXML private Button btnCrudJuegos;
    @FXML private Button btnInicio;
    @FXML private Button btnPanelJuegos;
    @FXML private Button btnSalir;

    @FXML
    void CrudJuegos(ActionEvent event){JuegosCtrl();}
    @FXML
    void CrudJugadores(ActionEvent event) {
        JugadoresCtrl();
    }
    @FXML
    void Home(ActionEvent event) {irInicio();}
    @FXML
    void salirLogin(ActionEvent event){salir();}

    public void initialize() {
        System.out.println("Dashboard loaded successfully!");

    }

    private void JugadoresCtrl() {
        SceneManager.cambiarContenido(contentArea, Paths.JugadoresCTRL);
    }

    private void JuegosCtrl(){
        SceneManager.cambiarContenido(contentArea, Paths.JuegosCTRL);
    }

    private void irInicio(){SceneManager.cambiarEscena(Paths.AdminView);}

    private void salir(){SceneManager.cambiarEscena(Paths.LoginView);
    }

}
