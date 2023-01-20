/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers;

import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

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
    
    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    public void initStage(Parent root){
        Scene scene = new Scene(root);

        stage.setScene(scene);
        
        stage.setTitle("SignIn");
        stage.setResizable(false);
        
        stage.show();
    }
    
}
