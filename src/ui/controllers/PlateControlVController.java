package ui.controllers;

import businessLogic.IngredientFactory;
import businessLogic.PlateFactory;
import businessLogic.PlateInterface;
import cellFactories.FloatStringFormatter;
import cellFactories.ImageButtonCell;
import cellFactories.IngredientsButtonCell;
import exceptions.BusinessLogicException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
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

    private List<Plate> filteredPlates;

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

        // The window has the title "Plates Management".
        stage.setTitle("Plate Management");
        // The window is not resizable.
        stage.setResizable(false);
        stage.setOnCloseRequest(this::handleExitAction);

        plateModel = PlateFactory.getModel();

        tableViewPlates.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        // The table is editable.
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
        tableColumnVegetarian.setCellValueFactory((CellDataFeatures<Plate, Boolean> param) -> param.getValue().getVegetarianProperty());
        tableColumnIngredients.setCellValueFactory(new PropertyValueFactory<>("ingredients"));
        tableColumnImage.setCellValueFactory(new PropertyValueFactory<>("plateImg"));

        // The cells of tableColumnName, tableColumnCalories, tableColumnCarbohydrates, tableColumnLipids and tableColumnProteins are TextField and will have the value formatted based on location.
        tableColumnName.setCellFactory(TextFieldTableCell.<Plate>forTableColumn());
        // Checks that the text entered does not exceed 50 characters. In case it is higher it shows a pop-up window with the text: "Maximum characters (50) for the name of the plate exceeded." In case it is not higher it sends a request to the server to update the plate with the entered data. If there is an error with the server, a popup window is shown informing about it.
        tableColumnName.setOnEditCommit((CellEditEvent<Plate, String> t) -> {
                    try {
                        if (t.getNewValue().length() <= 50) {
                            ((Plate) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())).setPlateName(t.getNewValue());
                            plateModel.edit_XML((Plate) t.getTableView().getSelectionModel().getSelectedItem());
                        } else {
                            tableViewPlates.setItems(platesData);
                            tableViewPlates.refresh();
                            Alert alert = new Alert(AlertType.ERROR, "Maximum characters (50) for the plate of the ingredient exceeded.");
                            alert.show();
                        }
                    } catch (BusinessLogicException ex) {
                        LOGGER.log(Level.SEVERE, ex.getMessage());
                        Alert alert = new Alert(AlertType.ERROR, ex.getMessage());
                        alert.show();
                    }
                });

        tableColumnCalories.setCellFactory(TextFieldTableCell.<Plate, Float>forTableColumn(new FloatStringFormatter()));
        // Check that the number entered is not greater than 9999. In case it is higher it displays a pop-up window with the text: "Only number with a maximum of 4 digits allowed." In case it is not higher it sends a request to the server to update the plate with the entered data. It is also updated in the table. If there is any error with the server a popup window is shown informing about it.
        tableColumnCalories.setOnEditCommit(
                (CellEditEvent<Plate, Float> t) -> {
                    try {
                        if (t.getNewValue() <= 9999) {
                            ((Plate) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())).setCalories(t.getNewValue());
                            plateModel.edit_XML((Plate) t.getTableView().getSelectionModel().getSelectedItem());
                        } else {
                            tableViewPlates.setItems(platesData);
                            tableViewPlates.refresh();
                            Alert alert = new Alert(AlertType.ERROR, "Only number with a maximum of 4 digits allowed.");
                            alert.show();
                        }
                    } catch (BusinessLogicException ex) {
                        LOGGER.log(Level.SEVERE, ex.getMessage());
                        Alert alert = new Alert(AlertType.ERROR, ex.getMessage());
                        alert.show();
                    }
                });

        tableColumnCarbohydrates.setCellFactory(TextFieldTableCell.<Plate, Float>forTableColumn(new FloatStringFormatter()));
        // Check that the number entered is not greater than 9999. In case it is higher it displays a pop-up window with the text: "Only number with a maximum of 4 digits allowed." In case it is not higher it sends a request to the server to update the plate with the entered data. It is also updated in the table. If there is any error with the server a popup window is shown informing about it.
        tableColumnCarbohydrates.setOnEditCommit(
                (CellEditEvent<Plate, Float> t) -> {
                    try {
                        if (t.getNewValue() <= 9999) {
                            ((Plate) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())).setCarbohydrates(t.getNewValue());
                            plateModel.edit_XML((Plate) t.getTableView().getSelectionModel().getSelectedItem());
                        } else {
                            tableViewPlates.setItems(platesData);
                            tableViewPlates.refresh();
                            Alert alert = new Alert(AlertType.ERROR, "Only number with a maximum of 4 digits allowed.");
                            alert.show();
                        }
                    } catch (BusinessLogicException ex) {
                        LOGGER.log(Level.SEVERE, ex.getMessage());
                        Alert alert = new Alert(AlertType.ERROR, ex.getMessage());
                        alert.show();
                    }
                });

        tableColumnLipids.setCellFactory(TextFieldTableCell.<Plate, Float>forTableColumn(new FloatStringFormatter()));
        // Check that the number entered is not greater than 9999. In case it is higher it displays a pop-up window with the text: "Only number with a maximum of 4 digits allowed." In case it is not higher it sends a request to the server to update the plate with the entered data. It is also updated in the table. If there is any error with the server a popup window is shown informing about it.
        tableColumnLipids.setOnEditCommit(
                (CellEditEvent<Plate, Float> t) -> {
                    try {
                        if (t.getNewValue() <= 9999) {
                            ((Plate) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())).setLipids(t.getNewValue());
                            plateModel.edit_XML((Plate) t.getTableView().getSelectionModel().getSelectedItem());
                        } else {
                            tableViewPlates.setItems(platesData);
                            tableViewPlates.refresh();
                            Alert alert = new Alert(AlertType.ERROR, "Only number with a maximum of 4 digits allowed.");
                            alert.show();
                        }
                    } catch (BusinessLogicException ex) {
                        LOGGER.log(Level.SEVERE, ex.getMessage());
                        Alert alert = new Alert(AlertType.ERROR, ex.getMessage());
                        alert.show();
                    }
                });

        tableColumnProteins.setCellFactory(TextFieldTableCell.<Plate, Float>forTableColumn(new FloatStringFormatter()));
        // Check that the number entered is not greater than 9999. In case it is higher it displays a pop-up window with the text: "Only number with a maximum of 4 digits allowed." In case it is not higher it sends a request to the server to update the plate with the entered data. It is also updated in the table. If there is any error with the server a popup window is shown informing about it.
        tableColumnProteins.setOnEditCommit(
                (CellEditEvent<Plate, Float> t) -> {
                    try {
                        if (t.getNewValue() <= 9999) {
                            ((Plate) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())).setProteins(t.getNewValue());
                            plateModel.edit_XML((Plate) t.getTableView().getSelectionModel().getSelectedItem());
                        } else {
                            tableViewPlates.setItems(platesData);
                            tableViewPlates.refresh();
                            Alert alert = new Alert(AlertType.ERROR, "Only number with a maximum of 4 digits allowed.");
                            alert.show();
                        }
                    } catch (BusinessLogicException ex) {
                        LOGGER.log(Level.SEVERE, ex.getMessage());
                        Alert alert = new Alert(AlertType.ERROR, ex.getMessage());
                        alert.show();
                    }
                });

        // The cells of tableColumnMealType are ComboBoxes.
        tableColumnMealType.setCellFactory(ComboBoxTableCell.<Plate, MealEnum>forTableColumn(MealEnum.values()));
        // Sends a request to the server to update the plate with the entered data. It is also updated in the table. If there is any error with the server a popup window is shown informing about it.
        tableColumnMealType.setOnEditCommit(
                (CellEditEvent<Plate, MealEnum> t) -> {
                    try {
                        ((Plate) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())).setMealType(t.getNewValue());
                        plateModel.edit_XML((Plate) t.getTableView().getSelectionModel().getSelectedItem());
                    } catch (BusinessLogicException ex) {
                        LOGGER.log(Level.SEVERE, ex.getMessage());
                        Alert alert = new Alert(AlertType.ERROR, ex.getMessage());
                        alert.show();
                    }
                });

        // The tableColumnVegetarian cells are a CheckBox.
        tableColumnVegetarian.setCellFactory(CheckBoxTableCell.<Plate>forTableColumn(tableColumnVegetarian));
        tableColumnVegetarian.setCellValueFactory(
                (CellDataFeatures<Plate, Boolean> param) -> param.getValue().getVegetarianProperty());

        // The cells of tableColumnImage and tableColumnIngredients are a Button.
        tableColumnIngredients.setCellFactory(cellIngredientsFactory);
        tableColumnIngredients.setMinWidth(20);

        tableColumnImage.setCellFactory(cellImageFactory);

        // The table is loaded with the plates data.
        if (platesData == null) {
            loadData();
        }
        tableViewPlates.setItems(platesData);

        textFieldSearchBar.setOnKeyPressed((KeyEvent event) -> handleSearchBar(event));
        buttonSearch.setDefaultButton(true);
        buttonSearch.setOnAction(this::handleButtonSearch);
        // A context menu will open with the "Delete row" option.
        contextMenu.getItems().get(0).setOnAction(this::handleDeleteAction);

        stage.show();
        LOGGER.info("PlateControlVController window initialized.");
    }

    // A pop-up window appears asking: "Are you sure you want to close the app? If you click "Yes", the program closes. If you click "No" the pop-up window disappears.
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

    // Adds a new editable line to the table, to create a new plate. Send to the server a request to create a new empty plate and add it to the table.
    @FXML
    private void handleAddRow(ActionEvent event) {
        try {
            Plate plate = new Plate();
            plateModel.create_XML(plate);
            loadData();
            tableViewPlates.setItems(platesData);
            tableViewPlates.refresh();
        } catch (BusinessLogicException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            Alert alert = new Alert(AlertType.ERROR, ex.getMessage());
            alert.show();
        }
    }

    // A pop-up window is displayed saying "Are you sure you want to delete this plate?" With two options (YES or NO). In case of saying yes, a request is sent to the server to delete the plate, it is deleted in the table and the pop-up window closes. If you say no, the pop-up window closes.
    private void handleDeleteAction(ActionEvent event) {
        Alert a = new Alert(AlertType.CONFIRMATION, "Are you sure you want to delete this plate?");
        a.showAndWait();
        try {
            if (a.getResult().equals(ButtonType.CANCEL)) {
                event.consume();
            } else {
                Integer id = ((Plate) tableViewPlates.getSelectionModel().getSelectedItem()).getPlate_id();
                plateModel.remove(id);
                loadData();
                tableViewPlates.getItems().remove(tableViewPlates.getSelectionModel().getSelectedItem());
                tableViewPlates.refresh();
            }
        } catch (NullPointerException ex) {
            String msg = "You must select the plate you want to delete.";
            Alert alert = new Alert(AlertType.ERROR, msg);
            alert.show();
            LOGGER.log(Level.SEVERE, msg);
        } catch (BusinessLogicException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            Alert alert = new Alert(AlertType.ERROR, ex.getMessage());
            alert.show();
        }
    }

    // Opens DietsControlWindow and closes this.
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
            LOGGER.log(Level.SEVERE, ex.getMessage());
            Alert alert = new Alert(AlertType.ERROR, ex.getMessage());
            alert.show();
        }
    }

    // Opens IngridientsControlWindow and closes this.
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
            LOGGER.log(Level.SEVERE, ex.getMessage());
            Alert alert = new Alert(AlertType.ERROR, ex.getMessage());
            alert.show();
        }
    }

    // Opens TipsControlWindow and closes this.
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
            LOGGER.log(Level.SEVERE, ex.getMessage());
            Alert alert = new Alert(AlertType.ERROR, ex.getMessage());
            alert.show();
        }
    }

    // Opens ClientsControlWindow and closes this.
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
            LOGGER.log(Level.SEVERE, ex.getMessage());
            Alert alert = new Alert(AlertType.ERROR, ex.getMessage());
            alert.show();
        }
    }

    // A pop-up window is displayed asking: "Are you sure you want to log out?". If you click "Yes" the pop-up window disappears, the SignIn window opens and the current one closes. If you click "No" the pop-up window disappears.
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
            LOGGER.log(Level.SEVERE, ex.getMessage());
            Alert alert = new Alert(AlertType.ERROR, ex.getMessage());
            alert.show();
        }
    }

    // Check that there are plates whose name matches the characters entered. If they do not exist, a pop-up window is displayed with the text: "There are no plates with that name". If they do exist, the table is reloaded with the plates whose names match the characters entered.
    private void handleButtonSearch(ActionEvent event) {
        try {
            if (!textFieldSearchBar.getText().isEmpty()) {
                List<Plate> namedPlates = PlateFactory.getModel().findPlatesByName_XML(new GenericType<List<Plate>>() {
                }, textFieldSearchBar.getText());
                List<Plate> filter = new ArrayList<>(filteredPlates);
                if (!namedPlates.isEmpty()) {
                    filter = filter.stream()
                            .filter(plate -> namedPlates.stream().anyMatch(namePlate -> namePlate.getPlateName().equals(plate.getPlateName())))
                            .collect(Collectors.toList());
                } else {
                    Alert alert = new Alert(AlertType.INFORMATION, "There are no plates with that name.");
                    alert.show();
                }
                tableViewPlates.setItems(FXCollections.observableList(filter));
                tableViewPlates.refresh();
            } else {
                tableViewPlates.setItems(FXCollections.observableList(filteredPlates));
                tableViewPlates.refresh();
            }
        } catch (BusinessLogicException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            Alert alert = new Alert(AlertType.ERROR, ex.getMessage());
            alert.show();
        }
    }

    // Lose focus of textFieldSearchBar.
    private void handleSearchBar(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ESCAPE) {
            buttonSearch.requestFocus();
        }
    }

    @FXML
    private void handleButtonFilter(ActionEvent event) {
        // Open a fileChooser to choose an image.
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Filters");
        alert.setHeaderText("Select your preferences");

        GridPane grid = new GridPane();

        // CheckBox with the text: "Vegetarian".
        CheckBox checkBox = new CheckBox();
        checkBox.setText("Vegetarian");
        checkBox.setPadding(new Insets(0, 0, 10, 0));

        grid.add(checkBox, 0, 0);

        // Label with the text: "Meal type".
        Label labelMeal = new Label("Meal Type");
        grid.add(labelMeal, 0, 1);

        // ComboBox with the following elements: BREAKFAST, LUNCH, DINNER
        ComboBox<MealEnum> comboBoxMeal = new ComboBox<>();
        comboBoxMeal.getItems().addAll(MealEnum.values());
        comboBoxMeal.setPromptText("Select meal of the day");

        grid.add(comboBoxMeal, 0, 2);

        Label labelFood = new Label("Food Type");
        labelFood.setPadding(new Insets(10, 0, 0, 0));
        grid.add(labelFood, 0, 3);

        // ComboBox with an element for each FoodTypeEnum
        ComboBox<FoodTypeEnum> comboBoxIngredients = new ComboBox<>();
        comboBoxIngredients.getItems().addAll(FoodTypeEnum.values());
        comboBoxIngredients.setPromptText("Select the food type");

        grid.add(comboBoxIngredients, 0, 4);

        alert.getDialogPane().setContent(grid);

        alert.showAndWait().ifPresent(response -> {
            // If you click Cancel the popup window disappears. If you click Accept the pop-up window disappears and checks for plates matching the filters.
            if (response == ButtonType.OK) {
                try {
                    textFieldSearchBar.setText("");
                    ObservableList<Ingredient> ingredients = FXCollections.observableArrayList(IngredientFactory.getModel().findAll_XML(new GenericType<List<Ingredient>>() {
                    }));
                    filteredPlates = new ArrayList<>(platesData);
                    HashMap<Integer, Ingredient> ingredientMap = new HashMap<>();
                    ingredients.forEach((ingredient) -> {
                        ingredientMap.put(ingredient.getIngredient_id(), ingredient);
                    });
                    
                    if (!comboBoxMeal.getSelectionModel().isEmpty()) {
                        try {
                            filteredPlates = plateModel.findPlatesByMealType_XML(new GenericType<List<Plate>>() {
                            }, comboBoxMeal.getValue().toString());
                        } catch (BusinessLogicException ex) {
                            LOGGER.log(Level.SEVERE, ex.getMessage());
                            Alert alert2 = new Alert(AlertType.ERROR, ex.getMessage());
                            alert2.show();
                        }
                    }
                    if (checkBox.isSelected()) {
                        filteredPlates = filteredPlates.stream().filter((Plate plate) -> plate.getIsVegetarian()).collect(Collectors.toList());
                    }
                    if (!comboBoxIngredients.getSelectionModel().isEmpty() && !ingredients.isEmpty()) {
                        final List<Ingredient> platesDataIngredients = ingredients.stream().filter((Ingredient ingredient) -> ingredient.getFoodType() == comboBoxIngredients.getValue()).collect(Collectors.toList());
                        filteredPlates.removeIf(p -> !p.getIngredients().stream().anyMatch(i -> platesDataIngredients.contains(ingredientMap.get(i.getIngredient_id()))));
                    } else if (!comboBoxIngredients.getSelectionModel().isEmpty() && ingredients.isEmpty()) {
                        filteredPlates.clear();
                    }
                    // If they do not exist, a pop-up window is displayed with the text: "There are no plates with those parameters".
                    if (filteredPlates.isEmpty()) {
                        loadData();
                        String msg = "There are no matching plates with the filters.";
                        Alert alert2 = new Alert(AlertType.INFORMATION, msg);
                        alert2.show();
                        LOGGER.log(Level.SEVERE, msg);
                    } else {
                        // If they do exist, the table is reloaded with the plates whose data match the chosen parameters.
                        tableViewPlates.setItems(FXCollections.observableList(filteredPlates));
                        tableViewPlates.refresh();
                    }
                } catch (BusinessLogicException ex) {
                    Logger.getLogger(PlateControlVController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (alert.getResult().equals(ButtonType.CANCEL)) {
                event.consume();
            }
        });
    }

    // It will display a help window.
    @FXML
    private void handleButtonHelp(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ui/views/PlateControlHelpView.fxml"));
            Parent root = (Parent) loader.load();

            PlateControlHelpController controller = ((PlateControlHelpController) loader.getController());

            controller.initAndShowStage(root);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            Alert alert = new Alert(AlertType.ERROR, ex.getMessage());
            alert.show();
        }
    }

    // Checks if there is data in the table. If there is data, it generates a report and displays it. If there is no data, it displays a pop-up window with the text: "No data available for report".
    @FXML
    private void handleButtonReport(ActionEvent event) {
        try {
            if (!tableViewPlates.getItems().isEmpty()) {
                JasperReport jasperReport = JasperCompileManager.compileReport("src/ui/reports/PlatesReport.jrxml");
                JRBeanCollectionDataSource dataItems = new JRBeanCollectionDataSource((Collection<Plate>) tableViewPlates.getItems());
                Map<String, Object> parameters = new HashMap<>();
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataItems);
                JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
                jasperViewer.setVisible(true);
            } else {
                Alert alert = new Alert(AlertType.WARNING, "No data available for report.");
                alert.show();
            }
        } catch (JRException ex) {
            String msg = "Error opening the report:" + ex.getMessage();
            LOGGER.log(Level.SEVERE, msg);
            Alert alert = new Alert(AlertType.ERROR, msg);
            alert.show();
        }
    }

    private void loadData() {
        try {
            platesData = FXCollections.observableArrayList(plateModel.findAll_XML(new GenericType<List<Plate>>() {
            }));
            platesData.forEach(
                    plate -> plate.getVegetarianProperty().addListener((observable, oldValue, newValue) -> {
                        try {
                            plateModel.edit_XML(plate);
                        } catch (BusinessLogicException ex) {
                            LOGGER.log(Level.SEVERE, ex.getMessage());
                            Alert alert = new Alert(AlertType.ERROR, ex.getMessage());
                            alert.show();
                        }
                    })
            );
            // Se carga una lista para filtrar los platos
            filteredPlates = new ArrayList<>(platesData);
        } catch (BusinessLogicException ex) {
            String msg = "An error occurred while connecting to the server. Try again later.";
            LOGGER.log(Level.SEVERE, msg);
            Alert alert = new Alert(AlertType.ERROR, msg);
            alert.show();
        }
    }
}
