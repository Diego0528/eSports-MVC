package com.diego.esports.controlador;
import com.diego.esports.datos.impDatos;
import com.diego.esports.datos.intDatos;
import com.diego.esports.modelo.dao.SceneManager;
import com.diego.esports.modelo.entidades.Usuarios;
import com.diego.esports.utils.Paths;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.*;
import javafx.util.Duration;

public class RegistroController {
    private intDatos datos = new impDatos();
    //Objetos FXML
    @FXML public Pane backgroundPane;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Label lblRegistrarse;
    @FXML private Label lblTitulo;
    @FXML private TextField emailField;
    @FXML private TextField nicknameField;
    @FXML private Button registerButton;

    //Acciones FXML
    private Timeline gradientAnimation;
    @FXML
    void registrarUsuario(ActionEvent event) {
        String user = usernameField.getText();
        String pass = passwordField.getText();
        String email = emailField.getText();
        String nick = nicknameField.getText();
        datos.registrarUsuario(user, pass, email, nick);
        limpiarCampos();
        SceneManager.cambiarEscena(Paths.LoginView);
    }
    //Inicializacion
    @FXML
    public void initialize() {
        createAnimatedBackground();
        lblTitulo.setOnMouseClicked(e -> SceneManager.cambiarEscena(Paths.LoginView));
        datos.existe();

    }

    private void limpiarCampos() {
        usernameField.clear();
        passwordField.clear();
        emailField.clear();
        nicknameField.clear();
    }
    @FXML
    void irCasa(ActionEvent event) {
        SceneManager.cambiarEscena(Paths.LoginView);
    }

    private void createAnimatedBackground() {
        //Creamos un gradiente inicial con colores vivos
        Stop[] stops = new Stop[] {
                new Stop(0, Color.web("#8A2BE2")),
                new Stop(0.5, Color.web("#4B0082")),
                new Stop(1, Color.web("#1E1E26"))
        };

        LinearGradient gradient = new LinearGradient(
                0, 0, 1, 1, true, CycleMethod.NO_CYCLE, stops);

        backgroundPane.setBackground(new Background(new BackgroundFill(gradient, null, null)));
        //Timeline para "mover" las posiciones del gradiente
        final double[] offset = {0.0};

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, e -> {
                    double shift = offset[0];
                    Stop[] animatedStops = new Stop[] {
                            new Stop(0, Color.web("#8A2BE2")),
                            new Stop(0.5, Color.web("#A64DFF")),
                            new Stop(1, Color.web("#4B0082"))
                    };
                    // Cambia la dirección del gradiente progresivamente
                    LinearGradient movingGradient = new LinearGradient(
                            Math.sin(shift) * 0.5 + 0.5,
                            Math.cos(shift) * 0.5 + 0.5,
                            Math.cos(shift) * -0.5 + 0.5,
                            Math.sin(shift) * -0.5 + 0.5,
                            true,
                            CycleMethod.NO_CYCLE,
                            animatedStops
                    );
                    backgroundPane.setBackground(new Background(new BackgroundFill(movingGradient, null, null)));
                    offset[0] += 0.015; // velocidad del movimiento
                }),
                new KeyFrame(Duration.millis(80)) // actualiza cada 80 ms
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }



}