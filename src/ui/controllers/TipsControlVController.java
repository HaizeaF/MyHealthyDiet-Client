/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

/**
 *
 * @author Sendoa
 */
public class TipsControlVController {

    private Stage stage;
    /**
     * Log that gives more info when app is running.
     */
    private static final Logger LOGGER = Logger.getLogger("TipsControlVController.class");

    /**
     * Sends you to the plates view.
     */
    @FXML
    private Button buttonPlates;
    /**
     * Sends you to the diets view.
     */
    @FXML
    private Button buttonDiets;
    /**
     * Sends you to the ingredients view.
     */
    @FXML
    private Button buttonIngredients;
    /**
     * Sends you to the tips view.
     */
    @FXML
    private Button buttonTips;
    /**
     * Sends you to the clients view.
     */
    @FXML
    private Button buttonClients;
    @FXML
    private Button buttonLogout;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        //Set stage properties
        stage.setScene(scene);
        stage.setTitle("Tip Management");
        stage.setResizable(false);

        buttonPlates.setOnAction(this::handleButtonPlatesAction);

        buttonDiets.setOnAction(this::handleButtonDietsAction);

        buttonIngredients.setOnAction(this::handleButtonIngredientsAction);

        buttonTips.setOnAction(this::handleButtonTipsAction);

        buttonClients.setOnAction(this::handleButtonClientsAction);

        buttonLogout.setOnAction(this::handleLogOutAction);

        stage.show();
    }

    /**
     * Opens log in windwow and closes this one
     *
     * @param event The ActionEvent object for the event.
     */
    public void handleLogOutAction(ActionEvent event) {
        try {
            Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit? This will close the app.");
            a.showAndWait();
            if (a.getResult().equals(ButtonType.CANCEL)) {
                event.consume();
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ui/views/SignInView.fxml"));
                Parent root = (Parent) loader.load();

                SignInController controller = ((SignInController) loader.getController());

                controller.setStage(stage);

                controller.initStage(root);
            }
        } catch (IOException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage());
            alert.show();
            LOGGER.log(Level.SEVERE, ex.getMessage());
        }
    }

    /**
     * Displays plates window and close this one. Open the PlateControlWindow
     * window and close it.
     *
     * @param event The ActionEvent object for the event.
     */
    @FXML
    private void handleButtonPlatesAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ui/views/PlateControlView.fxml"));
            Parent root = (Parent) loader.load();

            PlateControlVController controller = ((PlateControlVController) loader.getController());

            controller.setStage(stage);
            stage.close();
            controller.initStage(root);
        } catch (IOException | IllegalStateException ex) {
            //If theres is an error trying to change view, an alert will show.
            showErrorAlert("Failed trying to open Plates window.");
            LOGGER.log(Level.SEVERE, "TipsControlVController: Error trying to open Plate window, {0}", ex.getMessage());
        }
    }

    /**
     * Displays diets window and close this one. Open the DietControlWindow
     * window and close it.
     *
     * @param event The ActionEvent object for the event.
     */
    @FXML
    private void handleButtonDietsAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ui/views/DietControlWindow.fxml"));
            Parent root = (Parent) loader.load();

            DietsControlVController controller = ((DietsControlVController) loader.getController());

            controller.setStage(stage);
            stage.close();
            controller.initStage(root);
        } catch (IOException | IllegalStateException ex) {
            //If theres is an error trying to change view, an alert will show.
            showErrorAlert("Failed trying to open Diets window.");
            LOGGER.log(Level.SEVERE, "TipsControlVController: Error trying to open Diets window, {0}", ex.getMessage());
        }
    }

    /**
     * Displays ingredients window and close this one. Open the
     * IngredientsControlWindow window and close it.
     *
     * @param event The ActionEvent object for the event.
     */
    @FXML
    private void handleButtonIngredientsAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ui/views/IngredientControlWindow.fxml"));
            Parent root = (Parent) loader.load();

            IngredientControlVController controller = ((IngredientControlVController) loader.getController());

            controller.setStage(stage);
            stage.close();
            controller.initStage(root);
        } catch (IOException | IllegalStateException ex) {
            //If theres is an error trying to change view, an alert will show.
            showErrorAlert("Failed trying to open Ingredients window.");
            LOGGER.log(Level.SEVERE, "TipsControlVController: Error trying to open Plates window, {0}", ex.getMessage());
        }
    }

    /**
     * Displays tips window and close this one. Open the TipControlWindow window
     * and close it.
     *
     * @param event The ActionEvent object for the event.
     */
    @FXML
    private void handleButtonTipsAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ui/views/TipsControlWindow.fxml"));
            Parent root = (Parent) loader.load();

            TipsControlVController controller = ((TipsControlVController) loader.getController());

            controller.setStage(stage);
            stage.close();
            controller.initStage(root);
        } catch (IOException | IllegalStateException ex) {
            //If theres is an error trying to change view, an alert will show.
            showErrorAlert("Failed trying to open Tips window.");
            LOGGER.log(Level.SEVERE, "TipsControlVController: Error trying to open Tips window, {0}", ex.getMessage());
        }
    }

    /**
     * Displays clients window and close this one. Open the ClientControlWindow
     * window and close it.
     *
     * @param event The ActionEvent object for the event.
     */
    @FXML
    private void handleButtonClientsAction(ActionEvent event) {
        try {
            stage.close();
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ui/views/ClientAdminWindow.fxml"));
            Parent root = (Parent) loader.load();

            ClientControlWindow controller = ((ClientControlWindow) loader.getController());

            controller.setStage(stage);

            controller.initStage(root);
        } catch (IOException | IllegalStateException ex) {
            //If theres is an error trying to change view, an alert will show.
            showErrorAlert("Failed trying to open Clients window.");
            LOGGER.log(Level.SEVERE, "TipsControlVController: Error trying to open Clients window, {0}", ex.getMessage());
        }
    }

    /**
     * This method makes an alert to be used by other methods.
     *
     * @param errorMsg Message that will be shown in the alert.
     */
    protected void showErrorAlert(String errorMsg) {
        Alert alert = new Alert(Alert.AlertType.ERROR, errorMsg, ButtonType.OK);
        alert.showAndWait();

    }
}
