/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author User
 */
public class IngredientControlVController {
    
    private Stage stage;
    private static final Logger LOGGER = Logger.getLogger("IngredientControlVController");
    
    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

  /*  @FXML
    private Label labelMessage;
    /**
     * This method initialize the stage of the application showing a greeting to the user who accessed to this.
     * @param root path of the window
     */
    
    public void initStage(Parent root) {
        try {
            // Crea una escena asociada al root
            Scene scene = new Scene(root);
            // Asocia una escena al escenario
            stage.setScene(scene);
            // La ventana tiene el título “Application”
            stage.setTitle("Ingredient Control Window");
            // La ventana no es redimensionable
            stage.setResizable(false);
            
            
            
            // Confirmar el cierre de la aplicación
            stage.setOnCloseRequest(this::handleExitAction);

            // Enseña la ventana principal
            stage.show();
            LOGGER.info("Ingredient Control Window initialized");
        } catch (Exception e) {
            String msg = "Error opening the window: " + e.getMessage();
            Alert alert = new Alert(AlertType.ERROR, msg);
            alert.show();
            LOGGER.log(Level.SEVERE, msg);
        }
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
    /**
     * This method closes the scenario and sends you to the SignIn via an alert by clicking a "LogOut" button.
     * @param event an ActionEvent.ACTION event type for when the button is pressed
     */
    @FXML
    private void handleButtonLogOutAction(ActionEvent event) {
        try {
            Alert a = new Alert(AlertType.CONFIRMATION, "Are you sure you want to Log Out?");
            a.showAndWait();
            if (a.getResult().equals(ButtonType.OK)) {
                stage.close();
                LOGGER.info("Application window closed");
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/SignInView.fxml"));
                Parent root = (Parent) loader.load();
                IngredientControlVController controller = ((IngredientControlVController) loader.getController());
                controller.setStage(new Stage());
                controller.initStage(root);
                LOGGER.info("SignIn window opened");
            }
        } catch (IOException ex) {
            Alert alert = new Alert(AlertType.ERROR, ex.getMessage());
            alert.show();
            LOGGER.log(Level.SEVERE, ex.getMessage());
        }
    }

}