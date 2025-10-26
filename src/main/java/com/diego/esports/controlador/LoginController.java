package com.diego.esports.controlador;


import com.diego.esports.datos.impDatos;
import com.diego.esports.datos.intDatos;
import com.diego.esports.modelo.dao.SceneManager;
import com.diego.esports.modelo.entidades.Usuarios;
import com.diego.esports.utils.Paths;
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

public class LoginController {
    private intDatos datos = new impDatos();
    //Objetos FXML
    @FXML
    public Pane backgroundPane;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Label lblRegistrarse;

    //Acciones FXML


    //Inicializacion
    @FXML
    public void initialize() {
        createAnimatedBackground();
        loginButton.setOnAction(e -> iniciarSesion());
        lblRegistrarse.setOnMouseClicked(e -> System.out.println("Registrarse"));
        datos.existe();

    }

    private Timeline gradientAnimation;

    private void iniciarSesion() {
        String user = usernameField.getText();
        String pass = passwordField.getText();

        Usuarios usuario = datos.encontrarUsuario(user, pass);

        if (usuario != null) {
            int rol = datos.saberRol(usuario.getId());


            switch (rol) {
                case 1:
                    System.out.println("Inicio correcto Admin: " + rol);
                    SceneManager.cambiarEscena(Paths.AdminView);
                    break;
                case 2:
                    System.out.println("Inicio correcto organizador: " + rol );
                    SceneManager.cambiarEscena(Paths.OrganizadorView);
                    break;
                case 3:
                    System.out.println("Inicio correcto jugador: " + rol);
                    SceneManager.cambiarEscena(Paths.JugadorView);
                    break;
                default:
                    System.out.println("Rol desconocido");
            }
        } else {
            System.out.println("Usuario o contraseña incorrectos");
        }
    }


    private void createAnimatedBackground() {
        // 🌈 Creamos un gradiente inicial con colores vivos
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