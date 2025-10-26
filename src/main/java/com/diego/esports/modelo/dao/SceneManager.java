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

//    public static void cambiarEscena(int rol) {
//        try {
//            if (rol == 1) {
//                FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(Paths.JugadorView));
//                Parent root = loader.load();
//                stagePrincipal.setScene(new Scene(root));
//                stagePrincipal.show();
//            }else if(rol == 2){
//                FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(Paths.OrganizadorView));
//                Parent root = loader.load();
//                stagePrincipal.setScene(new Scene(root));
//            }else if(rol == 3){
//                FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(Paths.AdminView));
//                Parent root = loader.load();
//                stagePrincipal.setScene(new Scene(root));
//            }
//            else{
//                System.out.println("Rol desconocido");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
 //   }
}
