/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers;

import businessLogic.IngredientFactory;
import businessLogic.IngredientInterface;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.ws.rs.core.GenericType;
import objects.Ingredient;

/**
 *
 * @author User
 */
public class IngredientControlVController {
    
    private Stage stage;
    private static final Logger LOGGER = Logger.getLogger("IngredientControlVController");
    
    @FXML
    private Pane paneIngredientWindow;
    
    @FXML
    private Pane paneIngredientMenu;
    
    @FXML
    private Label labelTitle;
    
    @FXML
    private Button buttonTips;
    
    @FXML
    private Button buttonPlates;
    
    @FXML
    private Button buttonClients;
    
    @FXML
    private Button buttonDiets;
    
    @FXML
    private Button buttonIngredients;
    
    @FXML
    private Button buttonLogout;
    
    @FXML
    private TableView tableIngredient;
    
    @FXML
    private TableColumn columnIngredientName;
    
    @FXML
    private TableColumn columnFoodType;
    
    @FXML
    private TableColumn columnIsInSeason;
    
    @FXML
    private TableColumn columnWaterIndex;
    
    @FXML
    private TextField textfieldSearchbar;
    
    @FXML
    private Button buttonSearch;
    
    @FXML
    private MenuButton buttonFilters;
    
    @FXML
    private Pane paneButtons;
    
    @FXML
    private Button buttonInsertRow;
    
    @FXML
    private Button buttonHelp;
    
    @FXML
    private Button buttonReport;
    
    @FXML
    private ContextMenu contextMenu;
    
    private ObservableList<Ingredient> ingredientsData;
    
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

            // Cargar tabla de ingredientes
            
            columnIngredientName.setCellValueFactory(
                    new PropertyValueFactory<>("ingredientName"));
            columnFoodType.setCellValueFactory(
                    new PropertyValueFactory<>("foodType"));
            columnIsInSeason.setCellValueFactory(
                    new PropertyValueFactory<>("isInSeason"));
            columnWaterIndex.setCellValueFactory(
                    new PropertyValueFactory<>("waterIndex"));
            IngredientInterface client = IngredientFactory.getModel();
            List<Ingredient> ingredients = client.findAll_XML(new GenericType<List<Ingredient>>() {});
            
            ingredientsData=FXCollections.observableArrayList(ingredients);
            tableIngredient.setItems(ingredientsData);
            tableIngredient.setEditable(true);
            
            contextMenu.getItems().get(0).setOnAction(this::handleDeleteAction);
            
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
    
    private void handleDeleteAction(ActionEvent event) {
        Alert a = new Alert(AlertType.CONFIRMATION, "Are you sure you want to delete this Ingredient?");
        a.showAndWait();
        try{
            if (a.getResult().equals(ButtonType.CANCEL)) {
                event.consume();
            } else {
                Integer id = ((Ingredient)tableIngredient.getSelectionModel().getSelectedItem()).getIngredient_id();
                IngredientFactory.getModel().remove(id);
                
                tableIngredient.getItems().remove(tableIngredient.getSelectionModel().getSelectedItem());
                tableIngredient.refresh();
            }
        }catch (Exception e){
            String msg = "Error closing the app: " + e.getMessage();
            Alert alert = new Alert(AlertType.ERROR, msg);
            alert.show();
            LOGGER.log(Level.SEVERE, msg);
        }
            
    }
}