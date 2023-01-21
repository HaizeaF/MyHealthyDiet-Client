/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers;

import businessLogic.ClientFactory;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.ws.rs.core.GenericType;
import objects.Client;

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
    private Button buttonReport1;
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
    private TableColumn columnEmail;
    @FXML
    private TableColumn columnName;
    @FXML
    private TableColumn columnPasswordChange;
    @FXML
    private TableColumn columnLogin;
    @FXML
    private TableColumn columnPasswrd;
    @FXML
    private TableColumn columnStatus;
    @FXML
    private TableColumn columnAge;
    @FXML
    private TableColumn columnGenre;
    @FXML
    private TableColumn columnGoal;
    @FXML
    private TableColumn columnHeight;
    
    private ObservableList<Client> clientsData;

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
        
        clientsData = FXCollections.observableArrayList(ClientFactory.getModel().findAll(new GenericType<List<Client>>() {}));
        tableClients.setItems(clientsData);

        stage.show();
        LOGGER.info("ClientController window initialized");
    }

    /**
     * This method close the application using an alert
     * @param event an ActionEvent.ACTION event type for when the button is pressed
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
}
