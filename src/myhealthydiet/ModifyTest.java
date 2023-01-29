/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myhealthydiet;

import businessLogic.ClientFactory;
import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.ws.rs.core.GenericType;
import objects.ClientOBJ;
import ui.controllers.UserModifyVController;

/**
 *
 * @author Sendoa
 */
public class ModifyTest extends Application {
    
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ui/views/UserModify.fxml"));
        Parent root = (Parent) loader.load();

        UserModifyVController controller = ((UserModifyVController) loader.getController());

        controller.setStage(stage);

        ClientOBJ client = ClientFactory.getModel().findClientById(ClientOBJ.class, "6");
        controller.setClient(client);
        
        controller.initStage(root);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}