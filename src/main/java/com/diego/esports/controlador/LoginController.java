package com.diego.esports.controlador;


import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.paint.*;
import javafx.util.Duration;

public class LoginController {
    @FXML
    public Pane backgroundPane;
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    private Timeline gradientAnimation;

    @FXML
    public void initialize() {
        createAnimatedBackground();
        loginButton.setOnAction(e -> handleLogin());
    }

    private void handleLogin() {
        String user = usernameField.getText();
        String pass = passwordField.getText();
        System.out.println("Usuario: " + user + " | Contraseña: " + pass);
        // Aquí luego podrás cambiar a tu dashboard principal:
        // MainApp.setRoot("main_layout");
    }

    private void createAnimatedBackground() {
        // 🎨 Gradiente inicial
        Stop[] stops1 = new Stop[] {
                new Stop(0, Color.web("#8A2BE2")),
                new Stop(1, Color.web("#1E1E26"))
        };
        Stop[] stops2 = new Stop[] {
                new Stop(0, Color.web("#4B0082")),
                new Stop(1, Color.web("#0D0D10"))
        };

        LinearGradient gradient1 = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, stops1);
        LinearGradient gradient2 = new LinearGradient(1, 1, 0, 0, true, CycleMethod.NO_CYCLE, stops2);

        backgroundPane.setBackground(new Background(new BackgroundFill(gradient1, null, null)));

        // 🌈 Animación de transición entre gradientes
        gradientAnimation = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(backgroundPane.backgroundProperty(),
                                new Background(new BackgroundFill(gradient1, null, null)))
                ),
                new KeyFrame(Duration.seconds(6),
                        new KeyValue(backgroundPane.backgroundProperty(),
                                new Background(new BackgroundFill(gradient2, null, null)))
                ),
                new KeyFrame(Duration.seconds(12),
                        new KeyValue(backgroundPane.backgroundProperty(),
                                new Background(new BackgroundFill(gradient1, null, null)))
                )
        );

        gradientAnimation.setCycleCount(Timeline.INDEFINITE);
        gradientAnimation.setAutoReverse(true);
        gradientAnimation.play();
    }
}