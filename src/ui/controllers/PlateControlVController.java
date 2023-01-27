package ui.controllers;

import businessLogic.IngredientFactory;
import businessLogic.PlateFactory;
import businessLogic.PlateInterface;
import cellFactories.CellTest;
import cellFactories.ImageButtonCell;
import cellFactories.IngredientsButtonCell;
import java.awt.Checkbox;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.ws.rs.core.GenericType;
import objects.Plate;
import objects.Ingredient;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import javafx.util.converter.FloatStringConverter;
import objects.FoodTypeEnum;
import objects.MealEnum;

/**
 *
 * @author haize
 */
public class PlateControlVController {

    private Stage stage;
    private static final Logger LOGGER = Logger.getLogger("PlateControlVController.class");

    @FXML
    private Button buttonSearch;
    @FXML
    private TextField textFieldSearchBar;
    @FXML
    private ContextMenu contextMenu;
    @FXML
    private TableView tableViewPlates;
    @FXML
    private TableColumn<Plate, String> tableColumnName;
    @FXML
    private TableColumn<Plate, MealEnum> tableColumnMealType;
    @FXML
    private TableColumn<Plate, Float> tableColumnCalories;
    @FXML
    private TableColumn<Plate, Float> tableColumnCarbohydrates;
    @FXML
    private TableColumn<Plate, Float> tableColumnLipids;
    @FXML
    private TableColumn<Plate, Float> tableColumnProteins;
    @FXML
    private TableColumn<Plate, Boolean> tableColumnVegetarian;
    @FXML
    private TableColumn<Plate, List<Ingredient>> tableColumnIngredients;
    @FXML
    private TableColumn<Plate, byte[]> tableColumnImage;

    private ObservableList<Plate> platesData = null;

    private PlateInterface plateModel;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setData(ObservableList<Plate> platesData) {
        this.platesData = platesData;
    }

    /**
     * Method that initialises the window.
     *
     * @param root path of the window
     */
    public void initStage(Parent root) {
        Scene scene = new Scene(root);

        stage.setScene(scene);

        // La ventana tiene el título “Plates Management”.
        stage.setTitle("Plate Management");
        // La ventana no es redimensionable.
        stage.setResizable(false);
        stage.setOnCloseRequest(this::handleExitAction);

        plateModel = PlateFactory.getModel();

        tableViewPlates.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        // La tabla es editable.
        tableViewPlates.setEditable(true);

        Callback<TableColumn<Plate, byte[]>, TableCell<Plate, byte[]>> cellImageFactory
                = (TableColumn<Plate, byte[]> p) -> new ImageButtonCell(stage);
        Callback<TableColumn<Plate, List<Ingredient>>, TableCell<Plate, List<Ingredient>>> cellIngredientsFactory
                = (TableColumn<Plate, List<Ingredient>> p) -> new IngredientsButtonCell(stage);

        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("plateName"));
        tableColumnMealType.setCellValueFactory(new PropertyValueFactory<>("mealType"));
        tableColumnCalories.setCellValueFactory(new PropertyValueFactory<>("calories"));
        tableColumnCarbohydrates.setCellValueFactory(new PropertyValueFactory<>("carbohydrates"));
        tableColumnLipids.setCellValueFactory(new PropertyValueFactory<>("lipids"));
        tableColumnProteins.setCellValueFactory(new PropertyValueFactory<>("proteins"));
        tableColumnVegetarian.setCellValueFactory((CellDataFeatures<Plate, Boolean> param) -> param.getValue().vegetarianProperty());
        tableColumnIngredients.setCellValueFactory(new PropertyValueFactory<>("ingredients"));
        tableColumnImage.setCellValueFactory(new PropertyValueFactory<>("plateImg"));

        // Las celdas de tableColumnName, tableColumnCalories, tableColumnCarbohydrates, tableColumnLipids y tableColumnProteins son TextField.
        tableColumnName.setCellFactory(TextFieldTableCell.<Plate>forTableColumn());
        tableColumnName.setOnEditCommit(
                (CellEditEvent<Plate, String> t) -> {
                    ((Plate) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setPlateName(t.getNewValue());
                    plateModel.edit_XML((Plate) t.getTableView().getSelectionModel().getSelectedItem());
                });

        tableColumnCalories.setCellFactory(TextFieldTableCell.<Plate, Float>forTableColumn(new FloatStringConverter()));
        tableColumnCalories.setOnEditCommit(
                (CellEditEvent<Plate, Float> t) -> {
                    ((Plate) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setCalories(t.getNewValue());
                    plateModel.edit_XML((Plate) t.getTableView().getSelectionModel().getSelectedItem());
                });

        tableColumnCarbohydrates.setCellFactory(TextFieldTableCell.<Plate, Float>forTableColumn(new FloatStringConverter()));
        tableColumnCarbohydrates.setOnEditCommit(
                (CellEditEvent<Plate, Float> t) -> {
                    ((Plate) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setCarbohydrates(t.getNewValue());
                    plateModel.edit_XML((Plate) t.getTableView().getSelectionModel().getSelectedItem());
                });

        tableColumnLipids.setCellFactory(TextFieldTableCell.<Plate, Float>forTableColumn(new FloatStringConverter()));
        tableColumnLipids.setOnEditCommit(
                (CellEditEvent<Plate, Float> t) -> {
                    ((Plate) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setLipids(t.getNewValue());
                    plateModel.edit_XML((Plate) t.getTableView().getSelectionModel().getSelectedItem());
                });

        tableColumnProteins.setCellFactory(TextFieldTableCell.<Plate, Float>forTableColumn(new FloatStringConverter()));
        tableColumnProteins.setOnEditCommit(
                (CellEditEvent<Plate, Float> t) -> {
                    ((Plate) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setProteins(t.getNewValue());
                    plateModel.edit_XML((Plate) t.getTableView().getSelectionModel().getSelectedItem());
                });

        // Las celdas de tableColumnMealType son ComboBox.
        tableColumnMealType.setCellFactory(ComboBoxTableCell.<Plate, MealEnum>forTableColumn(MealEnum.values()));
        tableColumnMealType.setOnEditCommit(
                (CellEditEvent<Plate, MealEnum> t) -> {
                    ((Plate) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setMealType(t.getNewValue());
                    plateModel.edit_XML((Plate) t.getTableView().getSelectionModel().getSelectedItem());
                });

        // Las celdas de tableColumnVegetarian son una CheckBox.
        tableColumnVegetarian.setCellFactory(CheckBoxTableCell.<Plate>forTableColumn(tableColumnVegetarian));

        // Las celdas de ingredients son una lista.
        tableColumnIngredients.setCellFactory(cellIngredientsFactory);

        // Las celdas de tableColumnImage son Button.
        tableColumnImage.setCellFactory(cellImageFactory);

        // La tabla se carga con los datos de los platos.
        if (platesData == null) {
            platesData = FXCollections.observableArrayList(plateModel.findAll_XML(new GenericType<List<Plate>>() {
            }));
            platesData.forEach(
                    plate -> plate.vegetarianProperty().addListener((observable, oldValue, newValue) -> {
                        plateModel.edit_XML(plate);
                    })
            );
        }
        tableViewPlates.setItems(platesData);
        
        buttonSearch.setDefaultButton(true);
        buttonSearch.setOnAction(this::handleButtonSearch);
        contextMenu.getItems().get(0).setOnAction(this::handleDeleteAction);

        stage.show();
        LOGGER.info("PlateControlVController window initialized");
    }

    // Se muestra una ventana emergente preguntando: “Are you sure you want to close the app?”.
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

    // Añade una nueva línea editable a la tabla, para crear un nuevo plate. Envia al servidor una petición para crear un nuevo Plate vacio y se añade a la tabla.
    @FXML
    private void handleAddRow(ActionEvent event) {
        Plate plate = new Plate();
        plateModel.create_XML(plate);
        platesData = FXCollections.observableArrayList(plateModel.findAll_XML(new GenericType<List<Plate>>() {
        }));
        tableViewPlates.setItems(platesData);
        tableViewPlates.refresh();
    }

    private void handleDeleteAction(ActionEvent event) {
        Alert a = new Alert(AlertType.CONFIRMATION, "Are you sure you want to delete this plate?");
        a.showAndWait();
        try {
            if (a.getResult().equals(ButtonType.CANCEL)) {
                event.consume();
            } else {
                Integer id = ((Plate) tableViewPlates.getSelectionModel().getSelectedItem()).getPlate_id();
                plateModel.remove(id);

                tableViewPlates.getItems().remove(tableViewPlates.getSelectionModel().getSelectedItem());
                tableViewPlates.refresh();
            }
        } catch (Exception e) {
            String msg = "Error closing the app: " + e.getMessage();
            Alert alert = new Alert(AlertType.ERROR, msg);
            alert.show();
            LOGGER.log(Level.SEVERE, msg);
        }
    }

    @FXML
    private void handleButtonDiets(ActionEvent event) {
        try {
            stage.close();
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ui/views/DietControlWindow.fxml"));
            Parent root = (Parent) loader.load();

            PlateControlVController controller = ((PlateControlVController) loader.getController());

            controller.setStage(new Stage());

            controller.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(PlateControlVController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleButtonIngredients(ActionEvent event) {
        try {
            stage.close();
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ui/views/IngredientControlWindow.fxml"));
            Parent root = (Parent) loader.load();

            PlateControlVController controller = ((PlateControlVController) loader.getController());

            controller.setStage(stage);

            controller.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(PlateControlVController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleButtonTips(ActionEvent event) {
        try {
            stage.close();
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ui/views/TipsControlWindow.fxml"));
            Parent root = (Parent) loader.load();

            PlateControlVController controller = ((PlateControlVController) loader.getController());

            controller.setStage(stage);

            controller.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(PlateControlVController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleButtonClients(ActionEvent event) {
        try {
            stage.close();
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ui/views/ClientsControlWindow.fxml"));
            Parent root = (Parent) loader.load();

            PlateControlVController controller = ((PlateControlVController) loader.getController());

            controller.setStage(stage);

            controller.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(PlateControlVController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleButtonLogOut(ActionEvent event) {
        try {
            stage.close();
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ui/views/ClientsControlWindow.fxml"));
            Parent root = (Parent) loader.load();

            PlateControlVController controller = ((PlateControlVController) loader.getController());

            controller.setStage(stage);

            controller.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(PlateControlVController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void handleButtonSearch(ActionEvent event) {
        platesData = FXCollections.observableArrayList(PlateFactory.getModel().findPlatesByName_XML(new GenericType<List<Plate>>() {
        }, textFieldSearchBar.getText()));
        if (platesData == null ) {

        } else {
            tableViewPlates.setItems(platesData);
            tableViewPlates.refresh();
        }

    }

    @FXML
    private void handleButtonFilter(ActionEvent event) {

        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Filters");
            alert.setHeaderText("Please select an option");

            GridPane grid = new GridPane();

            ComboBox<String> comboBox = new ComboBox<>();
            comboBox.getItems().addAll("BREAKFAST", "LUNCH", "DINNER");
            comboBox.setPromptText("Select an option");

            grid.add(comboBox, 0, 0);

            CheckBox checkBox = new CheckBox();
            checkBox.setText("Is vegetarian");

            grid.add(checkBox, 0, 1);

            ComboBox<String> comboBoxIngredients = new ComboBox<>();
            comboBoxIngredients.getItems().addAll("VEGETABLE", "FRUIT", "NUT", "GRAIN", "BEAN", "MEAT", "POULTRY", "FISH", "SEAFOOD", "DAIRY");
            comboBoxIngredients.setPromptText("Select an option");

            grid.add(comboBoxIngredients, 0, 2);

            alert.getDialogPane().setContent(grid);

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    ObservableList<Ingredient> ingredients;
                    Boolean containsIngredient;

                    String selectedMeal = comboBox.getValue();
                    platesData = FXCollections.observableArrayList(plateModel.findPlatesByMealType_XML(new GenericType<List<Plate>>() {
                    }, selectedMeal));
                    if (checkBox.isSelected()) {
                        platesData = platesData.filtered((Plate plate) -> plate.getIsVegetarian());
                    }
                    String selectedFood = comboBoxIngredients.getValue();
                    ingredients = FXCollections.observableArrayList(IngredientFactory.getModel().findAll_XML(new GenericType<List<Ingredient>>() {
                    }));
                    final ObservableList<Ingredient> platesDataIngredients = FXCollections.observableList(ingredients.stream().filter((Ingredient ingredient) -> ingredient.getFoodType() == (FoodTypeEnum.valueOf(selectedFood))).collect(Collectors.toList()));
                    for (Plate plate : platesData) {
                        containsIngredient = false;
                        for (Ingredient ingredient : platesDataIngredients) {
                            if (plate.getIngredients() != null) {
                                for (int i = 0; i < plate.getIngredients().size(); i++) {
                                    if (plate.getIngredients().get(i).getIngredient_id().equals(ingredient.getIngredient_id())) {
                                        containsIngredient = true;
                                    }
                                }
                            }
                        }
                        if (!containsIngredient) {
                            platesData.remove(plate);
                        }
                    }
                    tableViewPlates.setItems(platesData);
                    tableViewPlates.refresh();
                }
                if (alert.getResult().equals(ButtonType.CANCEL)) {
                    event.consume();
                }
            });
        } catch (Exception ex) {
            Alert alert = new Alert(AlertType.ERROR, ex.getMessage());
            alert.show();
            LOGGER.log(Level.SEVERE, ex.getMessage());
        }
    }
}
