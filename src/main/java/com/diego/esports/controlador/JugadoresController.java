package com.diego.esports.controlador;

import com.diego.esports.datos.impDatos;
import com.diego.esports.modelo.entidades.Jugador;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;

public class JugadoresController {

    public Button addPlayerButton;
    @FXML private TableView<Jugador> tblJugadores;
    @FXML private TableColumn<Jugador, Integer> colId;
    @FXML private TableColumn<Jugador, String> colNombre;
    @FXML private TableColumn<Jugador, String> colNickname;
    @FXML private TableColumn<Jugador, String> colEquipo;
    @FXML private TableColumn<Jugador, Void> colAcciones;
    @FXML private TextField searchField;

    private final impDatos datos = new impDatos();

    @FXML
    public void initialize() {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colNickname.setCellValueFactory(new PropertyValueFactory<>("nickname"));
        colEquipo.setCellValueFactory(new PropertyValueFactory<>("equipo"));
        actualizarTabla();

        addActionButtons();
    }
    private void actualizarTabla() {
        List<Jugador> jugador = datos.listarJugadores();
        tblJugadores.getItems().clear();
        tblJugadores.getItems().addAll(jugador);
        tblJugadores.refresh();

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
