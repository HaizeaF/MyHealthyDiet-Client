/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers;

import businessLogic.ClientFactory;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.converter.DateStringConverter;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;
import javax.ws.rs.core.GenericType;
import objects.ClientOBJ;
import objects.GenreEnum;
import objects.GoalEnum;
import objects.StatusEnum;

/**
 *
 * @author Sendoa
 */
public class ClientControlWindow {

    private Stage stage;
    private static final Logger LOGGER = Logger.getLogger("ClientControlWindow.class");
    @FXML
    private Button buttonTips;
    @FXML
    private Button buttonPlates;
    @FXML
    private Button buttonDiets;
    @FXML
    private Button buttonIngredients;
    @FXML
    private Button buttonLogout;
    @FXML
    private Button buttonClients;
    @FXML
    private Button buttonReport;
    @FXML
    private Button buttonInsert;
    @FXML
    private Button buttonSearch;
    @FXML
    private Button buttonHelp;
    @FXML
    private MenuButton menuButtonFilters;
    @FXML
    private MenuItem itemEnabled;
    @FXML
    private MenuItem itemDisabled;
    @FXML
    private TableView tableClients;
    @FXML
    private TableColumn<ClientOBJ, String> columnEmail;
    @FXML
    private TableColumn<ClientOBJ, String> columnName;
    @FXML
    private TableColumn columnPasswordChange;
    @FXML
    private TableColumn<ClientOBJ, String> columnLogin;
    @FXML
    private TableColumn columnPasswrd;
    @FXML
    private TableColumn<ClientOBJ, StatusEnum> columnStatus;
    @FXML
    private TableColumn<ClientOBJ, Integer> columnAge;
    @FXML
    private TableColumn<ClientOBJ, GenreEnum> columnGenre;
    @FXML
    private TableColumn<ClientOBJ, GoalEnum> columnGoal;
    @FXML
    private TableColumn<ClientOBJ, Float> columnHeight;
    @FXML
    private ContextMenu menuTable;

    private ObservableList<ClientOBJ> clientsData;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initStage(Parent root) {
        Scene scene = new Scene(root);

        stage.setScene(scene);

        stage.setTitle("SignIn");
        stage.setResizable(false);

        // Confirmar el cierre de la aplicación
        stage.setOnCloseRequest(this::handleExitAction);

        // LOAD TABLE ITEMS //
        tableClients.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        columnAge.setCellValueFactory(new PropertyValueFactory("age"));
        columnEmail.setCellValueFactory(new PropertyValueFactory("email"));
        columnGenre.setCellValueFactory(new PropertyValueFactory("genre"));
        columnGoal.setCellValueFactory(new PropertyValueFactory("goal"));
        columnHeight.setCellValueFactory(new PropertyValueFactory("height"));
        columnLogin.setCellValueFactory(new PropertyValueFactory("login"));
        columnName.setCellValueFactory(new PropertyValueFactory("fullName"));
        columnPasswordChange.setCellValueFactory(new PropertyValueFactory("lastPasswordChange"));
        columnPasswrd.setCellValueFactory(new PropertyValueFactory("password"));
        columnStatus.setCellValueFactory(new PropertyValueFactory("status"));

        clientsData = FXCollections.observableArrayList(ClientFactory.getModel().findAll(new GenericType<List<ClientOBJ>>() {
        }));
        tableClients.setItems(clientsData);
        tableClients.setEditable(true);

        // EDITABLE ITEMS //
        columnAge.setCellFactory(TextFieldTableCell.<ClientOBJ, Integer>forTableColumn(new IntegerStringConverter()));
        columnAge.setOnEditCommit((CellEditEvent<ClientOBJ, Integer> t) -> {
            ((ClientOBJ) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())).setAge(t.getNewValue());
            ClientFactory.getModel().edit((ClientOBJ) t.getTableView().getSelectionModel().getSelectedItem());
        });

        columnEmail.setCellFactory(TextFieldTableCell.<ClientOBJ>forTableColumn());
        columnEmail.setOnEditCommit((CellEditEvent<ClientOBJ, String> t) -> {
                    ((ClientOBJ) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setEmail(t.getNewValue());
                    ClientFactory.getModel().edit((ClientOBJ) t.getTableView().getSelectionModel().getSelectedItem());
                });

        columnGenre.setCellFactory(ComboBoxTableCell.<ClientOBJ, GenreEnum>forTableColumn(GenreEnum.values()));
        columnGenre.setOnEditCommit((CellEditEvent<ClientOBJ, GenreEnum> t) -> {
            ((ClientOBJ) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())).setGenre(t.getNewValue());
            ClientFactory.getModel().edit((ClientOBJ) t.getTableView().getSelectionModel().getSelectedItem());
        });

        columnGoal.setCellFactory(ComboBoxTableCell.<ClientOBJ, GoalEnum>forTableColumn(GoalEnum.values()));
        columnGoal.setOnEditCommit((CellEditEvent<ClientOBJ, GoalEnum> t) -> {
            ((ClientOBJ) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())).setGoal(t.getNewValue());
            ClientFactory.getModel().edit((ClientOBJ) t.getTableView().getSelectionModel().getSelectedItem());
        });

        columnHeight.setCellFactory(TextFieldTableCell.<ClientOBJ, Float>forTableColumn(new FloatStringConverter()));
        columnHeight.setOnEditCommit((CellEditEvent<ClientOBJ, Float> t) -> {
                    ((ClientOBJ) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setHeight(t.getNewValue());
                    ClientFactory.getModel().edit((ClientOBJ) t.getTableView().getSelectionModel().getSelectedItem());
                });

        columnLogin.setCellFactory(TextFieldTableCell.<ClientOBJ>forTableColumn());
        columnLogin.setOnEditCommit((CellEditEvent<ClientOBJ, String> t) -> {
                    ((ClientOBJ) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setLogin(t.getNewValue());
                    ClientFactory.getModel().edit((ClientOBJ) t.getTableView().getSelectionModel().getSelectedItem());
                });

        columnName.setCellFactory(TextFieldTableCell.<ClientOBJ>forTableColumn());
        columnName.setOnEditCommit((CellEditEvent<ClientOBJ, String> t) -> {
                    ((ClientOBJ) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setFullName(t.getNewValue());
                    ClientFactory.getModel().edit((ClientOBJ) t.getTableView().getSelectionModel().getSelectedItem());
                });

        columnStatus.setCellFactory(ComboBoxTableCell.<ClientOBJ, StatusEnum>forTableColumn(StatusEnum.values()));
        columnStatus.setOnEditCommit((CellEditEvent<ClientOBJ, StatusEnum> t) -> {
            ((ClientOBJ) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())).setStatus(t.getNewValue());
            ClientFactory.getModel().edit((ClientOBJ) t.getTableView().getSelectionModel().getSelectedItem());
        });

        // DELETE ITEMS
        menuTable.getItems().get(0).setOnAction(this::handleDeleteAction);

        // INSERT ITEMS // 
        buttonInsert.setOnAction(this::handleInsertAction);

        stage.show();
        LOGGER.info("ClientController window initialized");
    }

    /**
     * This method close the application using an alert
     *
     * @param event an ActionEvent.ACTION event type for when the button is
     * pressed
     */
    private void handleExitAction(WindowEvent event) {
        Alert a = new Alert(AlertType.CONFIRMATION, "Are you sure you want to exit? This will close the app.");
        a.showAndWait();
        try {
            if (a.getResult().equals(ButtonType.CANCEL)) {
                event.consume();
            } else {
                Platform.exit();
            }
        } catch (Exception e) {
            String msg = "Error closing the app: " + e.getMessage();
            Alert alert = new Alert(AlertType.ERROR, msg);
            alert.show();
            LOGGER.log(Level.SEVERE, msg);
        }
    }

    public void handleInsertAction(ActionEvent action) {
        ClientOBJ client = (ClientOBJ) new ClientOBJ();
        ClientFactory.getModel().create(client);
        clientsData = FXCollections.observableArrayList(ClientFactory.getModel().findAll(new GenericType<List<ClientOBJ>>() {
        }));
        tableClients.setItems(clientsData);
        tableClients.refresh();
    }

    public void handleDeleteAction(ActionEvent action) {
        Alert a = new Alert(AlertType.CONFIRMATION, "Are you sure ypu want to delete this item?");
        a.showAndWait();
        try {
            if (a.getResult().equals(ButtonType.CANCEL)) {
                action.consume();
            } else {
                ClientFactory.getModel().remove(((ClientOBJ) tableClients.getSelectionModel().getSelectedItem()).getUser_id());
                tableClients.getItems().remove(tableClients.getSelectionModel().getSelectedItem());
                tableClients.refresh();
            }
        } catch (Exception e) {
            String msg = "Error deleting the Client: " + e.getMessage();
            Alert alert = new Alert(AlertType.ERROR, msg);
            alert.show();
            LOGGER.log(Level.SEVERE, msg);
        }
    }
}
