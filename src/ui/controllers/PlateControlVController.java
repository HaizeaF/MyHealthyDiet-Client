package ui.controllers;

import businessLogic.PlateFactory;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.ws.rs.core.GenericType;
import objects.Plate;

/**
 *
 * @author haize
 */
public class PlateControlVController {
    private Stage stage;
    private static final Logger LOGGER = Logger.getLogger("PlateControlVController.class");
    @FXML
    private Button buttonTips;
    @FXML
    private Button buttonDiets;
    @FXML
    private Button buttonIngredients;
    @FXML
    private Button buttonLogout;
    @FXML
    private Button buttonClients;
    @FXML
    private Button buttonInsertRow;
    @FXML
    private Button buttonFilter;
    @FXML
    private Button buttonSearch;
    @FXML
    private Button buttonReport;
    @FXML
    private Button buttonHelp;
    @FXML
    private TextField textFieldSearchBar;
    @FXML
    private TableView tableViewPlates;
    @FXML
    private TableColumn tableColumnName;
    @FXML
    private TableColumn tableColumnMealType;
    @FXML
    private TableColumn tableColumnCalories;
    @FXML
    private TableColumn tableColumnCarbohydrates;
    @FXML
    private TableColumn tableColumnLipids;
    @FXML
    private TableColumn tableColumnProteins;
    @FXML
    private TableColumn tableColumnVegetarian;
    @FXML
    private TableColumn tableColumnIngredients;
    @FXML
    private TableColumn tableColumnImage;

    private ObservableList<Plate> platesData;
    
    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    /**
     * Method that initialises the window. 
     * @param root path of the window
     */
    public void initStage(Parent root) {
        Scene scene = new Scene(root);

        stage.setScene(scene);

        stage.setTitle("Plate Management");
        stage.setResizable(false);
        stage.setOnCloseRequest(this::handleExitAction);
        tableViewPlates.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("plateName"));
        tableColumnMealType.setCellValueFactory(new PropertyValueFactory<>("mealType"));
        tableColumnCalories.setCellValueFactory(new PropertyValueFactory<>("calories"));
        tableColumnCarbohydrates.setCellValueFactory(new PropertyValueFactory<>("carbohydrates"));
        tableColumnLipids.setCellValueFactory(new PropertyValueFactory<>("lipids"));
        tableColumnProteins.setCellValueFactory(new PropertyValueFactory<>("proteins"));
        tableColumnVegetarian.setCellValueFactory(new PropertyValueFactory<>("isVegetarian"));
        tableColumnIngredients.setCellValueFactory(new PropertyValueFactory<>("ingredients"));
        tableColumnImage.setCellValueFactory(new PropertyValueFactory<>("plateImg"));
        
        platesData = FXCollections.observableArrayList(PlateFactory.getModel().findAll_XML(new GenericType<List<Plate>>() {}));
        tableViewPlates.setItems(platesData);
        
        stage.show();
        LOGGER.info("PlateControlVController window initialized");
    }
    
    private void handleExitAction(WindowEvent event) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit? This will close the app.");
        a.showAndWait();
        try {
            if (a.getResult().equals(ButtonType.CANCEL)) {
                event.consume();
            } else {
                Platform.exit();
            }
        } catch (Exception e) {
            String msg = "Error closing the app: " + e.getMessage();
            Alert alert = new Alert(Alert.AlertType.ERROR, msg);
            alert.show();
            LOGGER.log(Level.SEVERE, msg);
        }
    }
}
