package com.diego.esports.controlador;

import com.diego.esports.modelo.dao.JugadoresCrud;
import com.diego.esports.modelo.dao.SceneManager;
import com.diego.esports.modelo.entidades.Jugador;
import com.diego.esports.utils.Paths;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class JugadoresController {

    private final JugadoresCrud datos = new JugadoresCrud();
    boolean tablaSeleccionado = false;

    public Button agregarJugadorbtn;
    @FXML private Button btnHome;
    @FXML private Button btnEliminarJugador;
    @FXML private TableView<Jugador> tblJugadores;
    @FXML private TableColumn<Jugador, Integer> colId;
    @FXML private TableColumn<Jugador, String> colNombre;
    @FXML private TableColumn<Jugador, String> colNickname;
    @FXML private TableColumn<Jugador, String> colEmail;
    @FXML private TableColumn<Jugador, String> colContrasena;
    @FXML private TableColumn<Jugador, String> colRol;
    @FXML private TableColumn<Jugador, String> colEstado;
    @FXML private TableColumn<Jugador, Void> colAcciones;
    @FXML private TextField searchField;
    @FXML private TextField contraseñaField;
    @FXML private TextField emailField;
    @FXML private ComboBox<String> estadoCBoc;
    @FXML private ComboBox<String> rolCBox;
    @FXML private TextField idField;
    @FXML private TextField nicknameField;
    @FXML private TextField nombreField;




    @FXML
    void irCasa(ActionEvent event) {
        SceneManager.cambiarEscena(Paths.AdminView);
    }

    @FXML
    void agregarJugador(ActionEvent event) {
        if(tablaSeleccionado) {
            editarJugador();
            limpiarCampos();
            tablaSeleccionado = false;
            actualizarTabla();
        }else {
            agregarJugador();
            actualizarTabla();
        }
    }

    @FXML
    void eliminarJugador(ActionEvent event) {
        eliminarJugador();
    }


    @FXML
    public void initialize() {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colNickname.setCellValueFactory(new PropertyValueFactory<>("nickname"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContrasena.setCellValueFactory(new PropertyValueFactory<>("contrasena"));
        colRol.setCellValueFactory(new PropertyValueFactory<>("rol"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        ObservableList<String> estados = FXCollections.observableArrayList("activo", "pendiente", "suspendido");
        estadoCBoc.setItems(estados);
        ObservableList<String> roles = FXCollections.observableArrayList("jugador","organizador","admin");
        rolCBox.setItems(roles);


        tblJugadores.setOnMouseClicked(mouseEvent -> {
            if(tblJugadores.getSelectionModel().getSelectedItem() != null){
                if(mouseEvent.getClickCount() == 2 ){
                    tablaSeleccionado = true;
                    actualizarMode();
                    cargarCampos();
                }
            }
        });

        actualizarTabla();

    }

    //Actualemte solo se ve la tabla de sql, hay que acgregar Creación, Actualizar y Eliminar usuarios
    private void actualizarTabla() {
        List<Jugador> jugador = datos.listarJugadores();
        tblJugadores.getItems().clear();
        tblJugadores.getItems().addAll(jugador);
        tblJugadores.refresh();
        actualizarMode();

    }

    private void cargarCampos() {
        Jugador jugador = tblJugadores.getSelectionModel().getSelectedItem();

        idField.setText(String.valueOf(jugador.getId()));
        nombreField.setText(jugador.getNombre());
        nicknameField.setText(jugador.getNickname());
        emailField.setText(jugador.getEmail());
        estadoCBoc.setValue(jugador.getEstado());
        rolCBox.setValue(jugador.getRol());
        contraseñaField.setText(jugador.getContrasena());
    }

    private void editarJugador() {
        String id = idField.getText();
        String nombre = nombreField.getText();
        String nickname = nicknameField.getText();
        String email = emailField.getText();
        String estado = estadoCBoc.getValue();
        String rol = rolCBox.getValue();
        String contrasena = contraseñaField.getText();
        datos.actualizarJugador(id, nombre, nickname, email, estado, rol, contrasena);
        actualizarTabla();
    }

    private void actualizarMode() {
        if(tablaSeleccionado) {
            agregarJugadorbtn.setText("Actualizar Jugador");
        }else {
            agregarJugadorbtn.setText("+ Agregar Jugador");
        }
    }

    private void limpiarCampos() {
        idField.clear();
        nombreField.clear();
        nicknameField.clear();
        emailField.clear();
        estadoCBoc.setValue(null);
        rolCBox.setValue(null);
    }

    private void eliminarJugador() {
        Jugador jugador = tblJugadores.getSelectionModel().getSelectedItem();
        datos.eliminarJugador(jugador.getId());
        actualizarTabla();

    }

    private void agregarJugador() {
        String nombre = nombreField.getText();
        String contrasena = contraseñaField.getText();
        String email = emailField.getText();
        String nickname = nicknameField.getText();
        datos.registrarUsuario(nombre, contrasena, email, nickname);

    }



}
