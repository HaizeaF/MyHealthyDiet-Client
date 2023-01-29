/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers;

import businessLogic.IngredientFactory;
import businessLogic.IngredientInterface;
import cellFactories.FloatEditingCellIngredient;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javafx.util.converter.FloatStringConverter;
import javax.ws.rs.core.GenericType;
import objects.FoodTypeEnum;
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
    private TableColumn<Ingredient, String> columnIngredientName;
    
    @FXML
    private TableColumn<Ingredient, FoodTypeEnum> columnFoodType;
    
    @FXML
    private TableColumn<Ingredient, Boolean> columnIsInSeason;
    
    @FXML
    private TableColumn<Ingredient, Float> columnWaterIndex;
    
    @FXML
    private TextField texfieldSearchbar;
    
    @FXML
    private Button buttonSearch;
    
    @FXML
    private MenuButton menuButtonFilters;
    
    @FXML
    private MenuItem menuItemVegetable;
    
    @FXML
    private MenuItem menuItemFruit;
    
    @FXML
    private MenuItem menuItemNut;
    
    @FXML
    private MenuItem menuItemGrain;
    
    @FXML
    private MenuItem menuItemBean;
    
    @FXML
    private MenuItem menuItemMeat;
    
    @FXML
    private MenuItem menuItemPorultry;
    
    @FXML
    private MenuItem menuItemFish;
    
    @FXML
    private MenuItem menuItemSeafood;
    
    @FXML
    private MenuItem menuItemDairy;
    
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
    
    private CheckBox cuadro;
    
    private ObservableList<Ingredient> ingredientsData = FXCollections.observableArrayList();
    
    private ObservableList<Ingredient> ingredientsDataFiltered = FXCollections.observableArrayList();
    
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
            Locale locale = Locale.getDefault();
            String lang = locale.getDisplayLanguage();
            String country = locale.getDisplayCountry();
            NumberFormat.getPercentInstance(Locale.getDefault());
            IngredientInterface ingredientModel = IngredientFactory.getModel();
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
            
            // Filtro a buscar por FoodTypeEnum
            menuItemVegetable.setOnAction(this::handleFilterAction);
            menuItemSeafood.setOnAction(this::handleFilterAction);
            menuItemPorultry.setOnAction(this::handleFilterAction);
            menuItemNut.setOnAction(this::handleFilterAction);
            menuItemMeat.setOnAction(this::handleFilterAction);
            menuItemGrain.setOnAction(this::handleFilterAction);
            menuItemFruit.setOnAction(this::handleFilterAction);
            menuItemFish.setOnAction(this::handleFilterAction);
            menuItemDairy.setOnAction(this::handleFilterAction);
            menuItemBean.setOnAction(this::handleFilterAction);
            
            // 
            buttonHelp.setOnAction(this::handleHelpWindowAction);
            
            // Crear un ingrediente
            buttonInsertRow.setOnAction(this::handleCreateAction);
            
            buttonSearch.setOnAction(this::handleSearchAction);
            
            //tableIngredient.getSelectionModel().selectedItemProperty().addListener(this::handleTableSelectionChanged);
            
            // Cargar tabla de ingredientes
            columnIngredientName.setCellValueFactory(
                    new PropertyValueFactory<>("ingredientName"));
            columnIngredientName.setCellFactory(TextFieldTableCell.<Ingredient>forTableColumn());
            columnIngredientName.setOnEditCommit(
                    (CellEditEvent<Ingredient, String> t) -> {
                        if(t.getNewValue().length()<=50){
                            ((Ingredient) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())
                                    ).setIngredientName(t.getNewValue());
                        } else {
                            ((Ingredient) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())
                                    ).setIngredientName(t.getOldValue());
                            Alert alert = new Alert(AlertType.ERROR, "Maximum characters (50) for the name of the ingredient exceeded.");
                            alert.show();
                        }
                    ingredientModel.edit_XML((Ingredient) t.getTableView().getSelectionModel().getSelectedItem());
                    tableIngredient.refresh();
            });
            
            
            columnFoodType.setCellValueFactory(
                    new PropertyValueFactory<>("foodType"));
            columnFoodType.setCellFactory(ComboBoxTableCell.<Ingredient, FoodTypeEnum>forTableColumn(FoodTypeEnum.values()));
            columnFoodType.setOnEditCommit(
                (CellEditEvent<Ingredient, FoodTypeEnum> t) -> {
                    ((Ingredient) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setFoodType(t.getNewValue());
                ingredientModel.edit_XML((Ingredient) t.getTableView().getSelectionModel().getSelectedItem());
                });
            
            columnIsInSeason.setCellFactory(
                CheckBoxTableCell.<Ingredient>forTableColumn(columnIsInSeason));
            columnIsInSeason.setCellValueFactory(
                    (CellDataFeatures<Ingredient, Boolean> param) ->  param.getValue().getIsInSeasonProperty());
            
            columnWaterIndex.setCellValueFactory(
                    new PropertyValueFactory<>("waterIndex"));
            Callback<TableColumn<Ingredient, Float>, TableCell<Ingredient, Float>> cellFloatFactory
                = (TableColumn<Ingredient, Float> p) -> new FloatEditingCellIngredient();
            columnWaterIndex.setCellFactory(cellFloatFactory);
            columnWaterIndex.setOnEditCommit(
                (CellEditEvent<Ingredient, Float> t) -> {
                    if(t.getNewValue()<=100 && t.getNewValue()>=0){
                        ((Ingredient) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())).setWaterIndex(t.getNewValue());
                    }else{
                        ((Ingredient) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())).setWaterIndex(t.getNewValue());
                        if(t.getNewValue()>100){
                            Alert alert = new Alert(AlertType.ERROR, "Maximum number is 100, you cannot exceed it.");
                            alert.show();
                        }else{
                            Alert alert = new Alert(AlertType.ERROR, "Minimum  number is 0, you cannot put less than the minimum.");
                            alert.show();
                        }
                    }
                ingredientModel.edit_XML((Ingredient) t.getTableView().getSelectionModel().getSelectedItem());
                tableIngredient.refresh();
            });
            
            List<Ingredient> ingredients = ingredientModel.findAll_XML(new GenericType<List<Ingredient>>() {});
            //Set handler to update ObservableList properties. Applicable if cell is edited

            for(int i=0;i<ingredients.size();i++){
                ingredientsData.add(ingredients.get(i));
            }
            ingredients.forEach(
                ingredient -> ingredient.getIsInSeasonProperty().addListener((observable, oldValue, newValue) -> {
                    ingredientModel.edit_XML(ingredient);
                })
            );
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
    
    private void handleCreateAction(ActionEvent e){
        try{
            Ingredient newIngredient = new Ingredient();
            IngredientFactory.getModel().create_XML(newIngredient);
            ingredientsData = FXCollections.observableArrayList(IngredientFactory.getModel().findAll_XML(new GenericType<List<Ingredient>> () {}));
            tableIngredient.setItems(ingredientsData);
            tableIngredient.refresh();
        }catch (Exception ex){
            String msg = "Error closing the app: " + ex.getMessage();
            Alert alert = new Alert(AlertType.ERROR, msg);
            alert.show();
            LOGGER.log(Level.SEVERE, msg); 
        }
    }
    
    public void handleSearchAction(ActionEvent action) {
        LOGGER.info("Searhing for clients");
        if (!(texfieldSearchbar.getText()).isEmpty()) {
            ingredientsData = FXCollections.observableArrayList(IngredientFactory.getModel().findIngredientsByName_XML(new GenericType<List<Ingredient>>() {
            }, texfieldSearchbar.getText()));
            tableIngredient.setItems(ingredientsData);
            tableIngredient.refresh();
        } else {
            ingredientsData = FXCollections.observableArrayList(IngredientFactory.getModel().findAll_XML(new GenericType<List<Ingredient>>() {
            }));
            tableIngredient.setItems(ingredientsData);
            tableIngredient.refresh();
        }
    }
    
    public void handleFilterAction(Event event){
        MenuItem newMenuItem = ((MenuItem)event.getSource());
        String foodTypeSelected = newMenuItem.getText();
        menuItemVegetable.getText();
        ingredientsData = FXCollections.observableArrayList(IngredientFactory.getModel().findAll_XML(new GenericType<List<Ingredient>>() {
        }));
        ingredientsDataFiltered.clear();
        for(int i = 0; i<ingredientsData.size(); i++){
            if(ingredientsData.get(i).getFoodType().toString().equalsIgnoreCase(foodTypeSelected)){
                ingredientsDataFiltered.add(ingredientsData.get(i));
            }
        }
        tableIngredient.setItems(ingredientsDataFiltered);
        tableIngredient.refresh();
    }
    
    public void handleHelpWindowAction(Event event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ui/views/helpIngredient.fxml"));
            Parent root = (Parent) loader.load();
            IngredientHelpController controller = ((IngredientHelpController) loader.getController());
            controller.initAndShowStage(root);
        } catch (IOException ex) {
            Logger.getLogger(IngredientControlVController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void cargarTablaIngredientesUnPlato(ObservableList<Ingredient> ingredients){
        if(ingredients!=null)
            tableIngredient.setItems(ingredients);
    }
}