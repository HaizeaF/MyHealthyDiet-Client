/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myhealthydiet;

import businessLogic.ClientFactory;
import exceptions.BusinessLogicException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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

        ClientOBJ client = null;
        try {
            client = ClientFactory.getModel().findClientById(new GenericType<ClientOBJ>() {}, "6");
        } catch (BusinessLogicException ex) {
            Logger.getLogger(ModifyTest.class.getName()).log(Level.SEVERE, null, ex);
        }
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
