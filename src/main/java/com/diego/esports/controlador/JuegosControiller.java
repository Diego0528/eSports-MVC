package com.diego.esports.controlador;

import com.diego.esports.modelo.dao.SceneManager;
import com.diego.esports.modelo.entidades.Juego;
import com.diego.esports.utils.Paths;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class JuegosControiller {

    @FXML
    private FlowPane contenedorJuegos;

    @FXML
    public void initialize() {
        List<Juego> juegos = cargarJuegos();
        mostrarJuegos(juegos);
    }

    private List<Juego> cargarJuegos() {
        List<Juego> lista = new ArrayList<>();
        lista.add(new Juego("Valorant", "/com/diego/esports/assets/valorant.jpg"));
        lista.add(new Juego("Clash Royale", "/com/diego/esports/assets/clash.jpg"));
        lista.add(new Juego("League of Legends", "/com/diego/esports/assets/lol.jpg"));
        lista.add(new Juego("CS:GO", "/com/diego/esports/assets/csgo.jpg"));
        lista.add(new Juego("Dota 2", "/com/diego/esports/assets/dota.jpg"));
        return lista;
    }

    private void mostrarJuegos(List<Juego> juegos) {
        for (Juego juego : juegos) {
            VBox tarjeta = crearTarjetaJuego(juego);
            contenedorJuegos.getChildren().add(tarjeta);
        }
    }
    @FXML
    void irCasa(javafx.event.ActionEvent event) {
        SceneManager.cambiarEscena(com.diego.esports.utils.Paths.AdminView);
    }

    private VBox crearTarjetaJuego(Juego juego) {
        VBox tarjeta = new VBox(10);
        tarjeta.setAlignment(Pos.CENTER);
        tarjeta.setStyle("-fx-background-color: #1E1E1E; -fx-padding: 10; -fx-border-radius: 10; -fx-background-radius: 10;");

        ImageView imageView = new ImageView();
        try {
            imageView.setImage(new Image(getClass().getResourceAsStream(juego.getImagen())));
        } catch (Exception e) {
            System.out.println("No se pudo cargar imagen de " + juego.getNombre());
        }
        imageView.setFitWidth(180);
        imageView.setFitHeight(250);
        imageView.setPreserveRatio(true);

        Label nombre = new Label(juego.getNombre());
        nombre.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");

        tarjeta.getChildren().addAll(imageView, nombre);
        return tarjeta;

    }
}
