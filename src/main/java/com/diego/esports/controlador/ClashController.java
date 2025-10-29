package com.diego.esports.controlador;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.collections.FXCollections;

import java.util.Objects;

public class ClashController {

    @FXML private Tab tabOverview;
    @FXML private Tab tabLadders;
    @FXML private Tab tabTournaments;
    @FXML private Tab tabLive;
    @FXML private Tab tabStats;
    @FXML private TableView<String> leaderboardTable;
    @FXML private VBox leftColumn;
    @FXML private VBox rightColumn;
    @FXML private StackPane contentStack;
    @FXML private ImageView bannerImage;
    @FXML private ImageView logoImage;
    @FXML private TabPane tabPane;
    @FXML private FlowPane cardsFlow;
    @FXML private TextField searchField;
    @FXML private ChoiceBox<String> regionChoice;
    @FXML private ListView<String> topPlayersList;
    @FXML private ListView<String> upcomingTournaments;

    @FXML
    private void initialize() {
        regionChoice.setItems(FXCollections.observableArrayList("Global", "NA", "EU", "LATAM"));
        regionChoice.setValue("Global");
        // ejemplo: setear imágenes (poner recursos en /resources/images/)
//        bannerImage.setImage(new Image(getClass().getResourceAsStream("com/diego/esports/assets/clash.jpg")));
//        logoImage.setImage(new Image(getClass().getResourceAsStream("com/diego/esports/assets/clash.jpg")));

        // regiones ejemplo
        regionChoice.setItems(FXCollections.observableArrayList("Global", "NA", "EU", "LATAM"));
        regionChoice.setValue("Global");

        // cargar cards demo
        loadDemoCards();

        // listeners
        searchField.textProperty().addListener((obs, oldV, newV) -> filterCards(newV));
    }

    private void loadDemoCards() {
        // fill top players (demo)
        topPlayersList.setItems(FXCollections.observableArrayList("Player1", "Player2", "Player3"));

        // upcoming tournaments (demo)
        upcomingTournaments.setItems(FXCollections.observableArrayList("Weekly Cup - Sat", "Clan Clash - Sun"));

        // create 4 demo cards
        for (int i=1; i<=6; i++) {
            VBox card = createDemoCard(i);
            cardsFlow.getChildren().add(card);
        }
    }

    private VBox createDemoCard(int i) {
        VBox card = new VBox(8);
        card.getStyleClass().add("card");
//        ImageView thumb = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/diego/esports/assets/cr_card_" + (i % 3 + 1) + ".jpg"))));
//        thumb.setFitHeight(120);
//        thumb.setPreserveRatio(true);

        Label title = new Label("CR Cup #" + i);
        title.getStyleClass().add("card-title");

        Label sub = new Label("1v1 • $200 prize");
        sub.getStyleClass().add("card-sub");

        HBox actions = new HBox(8);
        Button details = new Button("Detalles");
        details.getStyleClass().add("button-outline");
        Button join = new Button("Unirse");
        join.getStyleClass().add("button-primary");

        actions.getChildren().addAll(details, join);
//        card.getChildren().addAll(thumb, title, sub, actions);
        return card;
    }

    private void filterCards(String q) {
        // TODO: filtro real contra datos; por ahora sólo log
        System.out.println("Filter: " + q);
    }
}
