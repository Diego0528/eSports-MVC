package com.diego.esports.controlador;

import com.diego.esports.modelo.entidades.Jugador;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

public class JugadoresController {

    @FXML private TableView<Jugador> playersTable;
    @FXML private TableColumn<Jugador, Integer> colId;
    @FXML private TableColumn<Jugador, String> colNombre;
    @FXML private TableColumn<Jugador, String> colNickname;
    @FXML private TableColumn<Jugador, String> colEquipo;
    @FXML private TableColumn<Jugador, String> colPais;
    @FXML private TableColumn<Jugador, Void> colAcciones;
    @FXML private TextField searchField;

    @FXML
    public void initialize() {
        // Simulación de datos (puedes conectar esto con tu modelo o DB)
        ObservableList<Jugador> jugadores = FXCollections.observableArrayList(
                new Jugador(1, "Diego López", "DLopez", "Raptors", "México"),
                new Jugador(2, "Ana Torres", "Shadow", "Phoenix", "Chile"),
                new Jugador(3, "Carlos Ruiz", "StormX", "Dragons", "Argentina")
        );

        colId.setCellValueFactory(cell -> cell.getValue().idProperty().asObject());
        colNombre.setCellValueFactory(cell -> cell.getValue().nombreProperty());
        colNickname.setCellValueFactory(cell -> cell.getValue().nicknameProperty());
        colEquipo.setCellValueFactory(cell -> cell.getValue().equipoProperty());
        colPais.setCellValueFactory(cell -> cell.getValue().paisProperty());

        addActionButtons();
        playersTable.setItems(jugadores);
    }

    private void addActionButtons() {
        colAcciones.setCellFactory(new Callback<TableColumn<Jugador, Void>, TableCell<Jugador, Void>>() {
            @Override
            public TableCell<Jugador, Void> call(final TableColumn<Jugador, Void> param) {
                return new TableCell<>() {
                    private final Button editBtn = new Button("✏️");
                    private final Button deleteBtn = new Button("🗑️");

                    {
                        editBtn.getStyleClass().add("action-edit");
                        deleteBtn.getStyleClass().add("action-delete");

                        editBtn.setOnAction(e -> {
                            Jugador player = getTableView().getItems().get(getIndex());
                            System.out.println("Editar jugador: " + player.getNombre());
                        });
                        deleteBtn.setOnAction(e -> {
                            Jugador player = getTableView().getItems().get(getIndex());
                            System.out.println("Eliminar jugador: " + player.getNombre());
                        });
                    }

                    private final HBox pane = new HBox(10, editBtn, deleteBtn);

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        setGraphic(empty ? null : pane);
                    }
                };
            }
        });
    }
}
