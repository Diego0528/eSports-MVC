package com.diego.esports.application;

import com.diego.esports.datos.impDatos;
import com.diego.esports.datos.intDatos;
import com.diego.esports.utils.Paths;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load((getClass().getResource(Paths.LoginView)));
        Scene scene = new Scene(root);


        primaryStage.setTitle("eSports");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}