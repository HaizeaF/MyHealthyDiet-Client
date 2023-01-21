/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers;

import businessLogic.TipFactory;
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
import objects.Tip;

/**
 *
 * @author Sendoa
 */
public class TipControlVController {

    private Stage stage;
    private static final Logger LOGGER = Logger.getLogger("TipControlVController.class");
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
    private MenuItem itemApp;
    @FXML
    private MenuItem itemNutrition;
    @FXML
    private TableView tableTips;
    @FXML
    private TableColumn columnTipId;
    @FXML
    private TableColumn columnText;
    @FXML
    private TableColumn columnType;

    private ObservableList<Tip> tipsData;

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

        tableTips.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        columnTipId.setCellValueFactory(new PropertyValueFactory("tip_id"));
        columnText.setCellValueFactory(new PropertyValueFactory("tipText"));
        columnType.setCellValueFactory(new PropertyValueFactory("type"));
        
        tipsData = FXCollections.observableArrayList(TipFactory.getModel().findAll(new GenericType<List<Tip>>() {}));
        tableTips.setItems(tipsData);

        stage.show();
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
}
