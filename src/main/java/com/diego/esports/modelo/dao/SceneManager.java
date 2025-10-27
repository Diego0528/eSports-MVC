package com.diego.esports.modelo.dao;

import com.diego.esports.utils.Paths;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {

    private static Stage stagePrincipal;

    public static void setStage(Stage stage) {
        stagePrincipal = stage;
    }

    public static void cambiarEscena(String rutaFXML) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(rutaFXML));
            Parent root = loader.load();
            stagePrincipal.setScene(new Scene(root));
            stagePrincipal.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
