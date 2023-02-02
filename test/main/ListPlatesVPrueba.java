/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import ui.controllers.ListPlatesVController;

/**
 *
 * @author HaizeaF
 */
public class ListPlatesVPrueba extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ui/views/ListPlatesWindow.fxml"));
        Parent root = (Parent) loader.load();

        ListPlatesVController controller = ((ListPlatesVController) loader.getController());

        controller.setStage(stage);

        controller.initStage(root);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}