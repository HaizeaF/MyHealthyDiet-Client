/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers;

import businessLogic.IngredientFactory;
import businessLogic.IngredientInterface;
import cellFactories.FloatStringFormatterIngredient;
import exceptions.BusinessLogicException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
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
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javax.ws.rs.core.GenericType;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import objects.FoodTypeEnum;
import objects.Ingredient;
import objects.Plate;

/**
 * Controller of the ingredient management window.
 *
 * @author Mikel
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
    private MenuItem menuItemDelete;

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

    private ObservableList<Ingredient> ingredientsData = null;

    private ObservableList<Ingredient> ingredientsDataFiltered = FXCollections.observableArrayList();

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setData(ObservableList<Ingredient> ingredientsData) {
        this.ingredientsData = ingredientsData;
    }

    /*  @FXML
    private Label labelMessage;
    /**
     * This method initialize the stage of the application showing a greeting to the user who accessed to this.
     * @param root path of the window
     */
    public void initStage(Parent root) {
        try {
            buttonSearch.setDefaultButton(true);
            NumberFormat nf = NumberFormat.getNumberInstance(Locale.getDefault());
            DecimalFormat df = (DecimalFormat) nf;
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

            // Ventana de informacion de ayuda.
            buttonHelp.setOnAction(this::handleHelpWindowAction);

            // Crear un ingrediente
            buttonInsertRow.setOnAction(this::handleCreateAction);

            // Search button
            buttonSearch.setOnAction(this::handleSearchAction);

            // LOG OUT BUTTON //
            buttonLogout.setOnAction(this::handleLogOutAction);

            // Report button
            buttonReport.setOnAction(this::handleButtonReportAction);

            // Window navigation
            buttonClients.setOnAction(this::handleClientsWindowNavigation);
            buttonDiets.setOnAction(this::handleDietssWindowNavigation);
            buttonTips.setOnAction(this::handleTipsWindowNavigation);
            buttonPlates.setOnAction(this::handlePlatesWindowNavigation);
            buttonIngredients.setOnAction(this::handleIngredientsWindowNavigation);

            //tableIngredient.getSelectionModel().selectedItemProperty().addListener(this::handleTableSelectionChanged);
            // Cargar tabla de ingredientes
            columnIngredientName.setCellValueFactory(
                    new PropertyValueFactory<>("ingredientName"));
            columnIngredientName.setCellFactory(TextFieldTableCell.<Ingredient>forTableColumn());
            columnIngredientName.setOnEditCommit(
                    (CellEditEvent<Ingredient, String> t) -> {
                        try {
                            if (t.getNewValue().length() <= 50) {
                                ((Ingredient) t.getTableView().getItems().get(
                                        t.getTablePosition().getRow())).setIngredientName(t.getNewValue());
                            } else {
                                ((Ingredient) t.getTableView().getItems().get(
                                        t.getTablePosition().getRow())).setIngredientName(t.getOldValue());
                                Alert alert = new Alert(AlertType.ERROR, "Maximum characters (50) for the name of the ingredient exceeded.");
                                alert.show();
                            }
                            ingredientModel.edit_XML((Ingredient) t.getTableView().getSelectionModel().getSelectedItem());
                            tableIngredient.refresh();
                        } catch (BusinessLogicException ex) {
                            //If there is an error in the business class, shows an alert.
                            String msg = "Window can not be loaded:\n" + ex.getMessage();
                            Alert alert = new Alert(AlertType.ERROR, msg);
                            alert.show();
                            LOGGER.log(Level.SEVERE, "IngredientControlVController: Error at setOnEditCommit in tableColumnLipids, {0}", ex.getMessage());
                        }
                    });

            columnFoodType.setCellValueFactory(
                    new PropertyValueFactory<>("foodType"));
            columnFoodType.setCellFactory(ComboBoxTableCell.<Ingredient, FoodTypeEnum>forTableColumn(FoodTypeEnum.values()));
            columnFoodType.setOnEditCommit(
                    (CellEditEvent<Ingredient, FoodTypeEnum> t) -> {
                        try {
                            ((Ingredient) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())).setFoodType(t.getNewValue());
                            ingredientModel.edit_XML((Ingredient) t.getTableView().getSelectionModel().getSelectedItem());
                        } catch (BusinessLogicException ex) {
                            //If there is an error in the business class, shows an alert.
                            String msg = "Window can not be loaded:\n" + ex.getMessage();
                            Alert alert = new Alert(AlertType.ERROR, msg);
                            alert.show();
                            LOGGER.log(Level.SEVERE, "IngredientControlVController: Error at setOnEditCommit in tableColumnLipids, {0}", ex.getMessage());
                        }
                    });

            columnIsInSeason.setCellFactory(
                    CheckBoxTableCell.<Ingredient>forTableColumn(columnIsInSeason));
            columnIsInSeason.setCellValueFactory(
                    (CellDataFeatures<Ingredient, Boolean> param) -> param.getValue().getIsInSeasonProperty());

            columnWaterIndex.setCellValueFactory(
                    new PropertyValueFactory<>("waterIndex"));
            columnWaterIndex.setCellFactory(TextFieldTableCell.<Ingredient, Float>forTableColumn(new FloatStringFormatterIngredient()));
            columnWaterIndex.setOnEditCommit(
                    (CellEditEvent<Ingredient, Float> t) -> {
                        try {
                            if (t.getNewValue() <= 100 && t.getNewValue() >= 0) {
                                ((Ingredient) t.getTableView().getItems().get(
                                        t.getTablePosition().getRow())).setWaterIndex(t.getNewValue());
                            } else {
                                if (t.getNewValue() > 100) {
                                    Alert alert = new Alert(AlertType.ERROR, "Maximum number is 100, you cannot exceed it.");
                                    alert.show();
                                } else if (t.getNewValue() < 0) {
                                    Alert alert = new Alert(AlertType.ERROR, "Minimum  number is 0, you cannot put less than the minimum.");
                                    alert.show();
                                }
                            }
                            ingredientModel.edit_XML((Ingredient) t.getTableView().getSelectionModel().getSelectedItem());
                            tableIngredient.refresh();
                        } catch (NullPointerException ex) {
                            String msg = "This value is not compatible.";
                            Alert alert = new Alert(AlertType.ERROR, msg);
                            alert.show();
                            LOGGER.log(Level.SEVERE, msg);
                        } catch (BusinessLogicException ex) {
                            //If there is an error in the business class, shows an alert.
                            String msg = "Window can not be loaded:\n" + ex.getMessage();
                            Alert alert = new Alert(AlertType.ERROR, msg);
                            alert.show();
                            LOGGER.log(Level.SEVERE, "IngredientControlVController: Error at setOnEditCommit in tableColumnLipids, {0}", ex.getMessage());
                        }
                    }
            );

            if (ingredientsData == null) {
                ingredientsData = FXCollections.observableArrayList();
                List<Ingredient> ingredients = ingredientModel.findAll_XML(new GenericType<List<Ingredient>>() {
                });
                //Set handler to update ObservableList properties. Applicable if cell is edited

                for (int i = 0; i < ingredients.size(); i++) {
                    ingredientsData.add(ingredients.get(i));
                }
                ingredients.forEach(
                        ingredient -> ingredient.getIsInSeasonProperty().addListener((observable, oldValue, newValue) -> {
                            try {
                                ingredientModel.edit_XML(ingredient);
                            } catch (BusinessLogicException ex) {
                                //If there is an error in the business class, shows an alert.
                                String msg = "Window can not be loaded:\n" + ex.getMessage();
                                Alert alert = new Alert(AlertType.ERROR, msg);
                                alert.show();
                                LOGGER.log(Level.SEVERE, "IngredientControlVController: Error at setOnEditCommit in tableColumnLipids, {0}", ex.getMessage());
                            }
                        })
                );
            }
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
     *
     * @param event an ActionEvent.ACTION event type for when the button is
     * pressed
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
     * This method delete an ingredient from the table.
     *
     * @param event an ActionEvent.ACTION event type for when the button is
     * pressed
     */
    private void handleDeleteAction(ActionEvent event) {
        Alert a = new Alert(AlertType.CONFIRMATION, "Are you sure you want to delete this Ingredient?");
        a.showAndWait();
        try {
            if (a.getResult().equals(ButtonType.CANCEL)) {
                event.consume();
            } else {
                Integer id = ((Ingredient) tableIngredient.getSelectionModel().getSelectedItem()).getIngredient_id();
                IngredientFactory.getModel().remove(id);

                tableIngredient.getItems().remove(tableIngredient.getSelectionModel().getSelectedItem());
                tableIngredient.refresh();
            }
        } catch (Exception e) {
            String msg = "Error deleting an ingredient";
            Alert alert = new Alert(AlertType.ERROR, msg);
            alert.show();
            LOGGER.log(Level.SEVERE, msg);
        }
    }

    /**
     * This method create an ingredient from the table.
     *
     * @param e an ActionEvent.ACTION event type for when the button is pressed
     */
    private void handleCreateAction(ActionEvent e) {
        try {
            Float num = Float.parseFloat("20.5");
            Ingredient newIngredient = new Ingredient("aa", FoodTypeEnum.NUT, false, num);
            IngredientFactory.getModel().create_XML(newIngredient);
            ingredientsData = FXCollections.observableArrayList(IngredientFactory.getModel().findAll_XML(new GenericType<List<Ingredient>>() {
            }));
            tableIngredient.setItems(ingredientsData);
            tableIngredient.refresh();
        } catch (Exception ex) {
            String msg = "Error creating an ingredient";
            Alert alert = new Alert(AlertType.ERROR, msg);
            alert.show();
            LOGGER.log(Level.SEVERE, msg);
        }
    }

    /**
     * This method shows a report that loads all the ingredients.
     *
     * @param event an ActionEvent.ACTION event type for when the button is
     * pressed
     */
    private void handleButtonReportAction(ActionEvent event) {
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport("src/ui/reports/IngredientReport.jrxml");
            JRBeanCollectionDataSource dataItems = new JRBeanCollectionDataSource((Collection<Ingredient>) this.tableIngredient.getItems());
            Map<String, Object> parameters = new HashMap<>();
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataItems);
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setVisible(true);
        } catch (JRException ex) {
            String msg = ("Error trying to open report:\n" + ex.getMessage());
            Alert alert = new Alert(AlertType.ERROR, msg);
            alert.show();
            LOGGER.log(Level.SEVERE, "IngredientControlVController: Error opening report, {0}", ex.getMessage());
        }
    }

    /**
     * This method makes a search by name in the ingredients table.
     *
     * @param action an ActionEvent.ACTION event type for when the button is
     * pressed
     */
    private void handleSearchAction(ActionEvent action) {
        LOGGER.info("Searhing for clients");
        if (!(texfieldSearchbar.getText()).isEmpty()) {
            try {
                ingredientsData = FXCollections.observableArrayList(IngredientFactory.getModel().findIngredientsByName_XML(new GenericType<List<Ingredient>>() {
                }, texfieldSearchbar.getText()));
                tableIngredient.setItems(ingredientsData);
                tableIngredient.refresh();
            } catch (BusinessLogicException ex) {
                //If there is an error in the business class, shows an alert.
                String msg = "Window can not be loaded:\n" + ex.getMessage();
                Alert alert = new Alert(AlertType.ERROR, msg);
                alert.show();
                LOGGER.log(Level.SEVERE, "IngredientControlVController: Error at setOnEditCommit in tableColumnLipids, {0}", ex.getMessage());
            }
        } else {
            try {
                ingredientsData = FXCollections.observableArrayList(IngredientFactory.getModel().findAll_XML(new GenericType<List<Ingredient>>() {
                }));
                tableIngredient.setItems(ingredientsData);
                tableIngredient.refresh();
            } catch (BusinessLogicException ex) {
                //If there is an error in the business class, shows an alert.
                String msg = "Window can not be loaded:\n" + ex.getMessage();
                Alert alert = new Alert(AlertType.ERROR, msg);
                alert.show();
                LOGGER.log(Level.SEVERE, "IngredientControlVController: Error at setOnEditCommit in tableColumnLipids, {0}", ex.getMessage());
            }
        }
    }

    /**
     * This method makes a search by foodType in the ingredients table.
     *
     * @param event an ActionEvent.ACTION event type for when the button is
     * pressed
     */
    private void handleFilterAction(Event event) {
        try {
            MenuItem newMenuItem = ((MenuItem) event.getSource());
            String foodTypeSelected = newMenuItem.getText();
            menuItemVegetable.getText();
            ingredientsData = FXCollections.observableArrayList(IngredientFactory.getModel().findAll_XML(new GenericType<List<Ingredient>>() {
            }));
            ingredientsDataFiltered.clear();
            for (int i = 0; i < ingredientsData.size(); i++) {
                if (ingredientsData.get(i).getFoodType().toString().equalsIgnoreCase(foodTypeSelected)) {
                    ingredientsDataFiltered.add(ingredientsData.get(i));
                }
            }
            tableIngredient.setItems(ingredientsDataFiltered);
            tableIngredient.refresh();
        } catch (BusinessLogicException ex) {
            //If there is an error in the business class, shows an alert.
            String msg = "Window can not be loaded:\n" + ex.getMessage();
            Alert alert = new Alert(AlertType.ERROR, msg);
            alert.show();
            LOGGER.log(Level.SEVERE, "IngredientControlVController: Error at setOnEditCommit in tableColumnLipids, {0}", ex.getMessage());
        }
    }

    /**
     * This method opens an html window that shows information to help users to
     * navigate and use the window.
     *
     * @param event an ActionEvent.ACTION event type for when the button is
     * pressed
     */
    private void handleHelpWindowAction(Event event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ui/views/helpIngredient.fxml"));
            Parent root = (Parent) loader.load();
            IngredientHelpController controller = ((IngredientHelpController) loader.getController());
            controller.initAndShowStage(root);
        } catch (IOException ex) {
            Logger.getLogger(IngredientControlVController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method sends an observable List to another window
     *
     * @param ingredients ObservableList of all the ingredients of the window.
     */
    private void cargarTablaIngredientesUnPlato(ObservableList<Ingredient> ingredients) {
        if (ingredients != null) {
            tableIngredient.setItems(ingredients);
        }
    }

    /**
     * Navigation with Client window
     *
     * @param event an ActionEvent.ACTION event type for when the button is
     * pressed
     */
    @FXML
    private void handleClientsWindowNavigation(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ui/views/ClientAdminWindow.fxml"));
            Parent root = (Parent) loader.load();

            ClientControlWindow controller = ((ClientControlWindow) loader.getController());

            controller.setStage(stage);
            stage.close();
            controller.initStage(root);
        } catch (IOException | IllegalStateException ex) {
            //If theres is an error trying to change view, an alert will show.
            String msg = ("Failed trying to open Clients window.");
            Alert alert = new Alert(AlertType.ERROR, msg);
            alert.show();
            LOGGER.log(Level.SEVERE, "ClientControlWindow: Error trying to open Clients window, {0}", ex.getMessage());
        }
    }

    /**
     * Navigation with Diet window
     *
     * @param event an ActionEvent.ACTION event type for when the button is
     * pressed
     */
    @FXML
    private void handleDietssWindowNavigation(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ui/views/DietControlWindow.fxml"));
            Parent root = (Parent) loader.load();

            DietsControlVController controller = ((DietsControlVController) loader.getController());

            controller.setStage(stage);
            stage.close();
            controller.initStage(root);
        } catch (IOException | IllegalStateException ex) {
            //If theres is an error trying to change view, an alert will show.
            String msg = ("Failed trying to open Diets window.");
            Alert alert = new Alert(AlertType.ERROR, msg);
            alert.show();
            LOGGER.log(Level.SEVERE, "DietsControlVController: Error trying to open Dietss window, {0}", ex.getMessage());
        }
    }

    /**
     * Navigation with Plates window
     *
     * @param event an ActionEvent.ACTION event type for when the button is
     * pressed
     */
    @FXML
    private void handlePlatesWindowNavigation(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ui/views/PlateControlView.fxml"));
            Parent root = (Parent) loader.load();

            PlateControlVController controller = ((PlateControlVController) loader.getController());

            controller.setStage(stage);
            stage.close();
            controller.initStage(root);
        } catch (IOException | IllegalStateException ex) {
            //If theres is an error trying to change view, an alert will show.
            String msg = ("Failed trying to open Plates window.");
            Alert alert = new Alert(AlertType.ERROR, msg);
            alert.show();
            LOGGER.log(Level.SEVERE, "PlateControlVController: Error trying to open Plates window, {0}", ex.getMessage());
        }
    }

    /**
     * Navigation with Tips window
     *
     * @param event an ActionEvent.ACTION event type for when the button is
     * pressed
     */
    @FXML
    private void handleTipsWindowNavigation(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ui/views/TipsControlWindow.fxml"));
            Parent root = (Parent) loader.load();

            TipsControlVController controller = ((TipsControlVController) loader.getController());

            controller.setStage(stage);
            stage.close();
            controller.initStage(root);
        } catch (IOException | IllegalStateException ex) {
            //If theres is an error trying to change view, an alert will show.
            Alert alert = new Alert(AlertType.ERROR, ex.getMessage());
            alert.show();
            LOGGER.log(Level.SEVERE, "TipsControlVController: Error trying to open Tips window, {0}", ex.getMessage());
        }
    }

    /**
     * Navigation with Ingredient window
     *
     * @param event an ActionEvent.ACTION event type for when the button is
     * pressed
     */
    @FXML
    private void handleIngredientsWindowNavigation(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ui/views/IngredientControlWindow.fxml"));
            Parent root = (Parent) loader.load();

            IngredientControlVController controller = ((IngredientControlVController) loader.getController());

            controller.setStage(stage);
            stage.close();
            controller.initStage(root);
        } catch (IOException | IllegalStateException ex) {
            //If theres is an error trying to change view, an alert will show.
            String msg = ("Failed trying to open Ingredients window.");
            Alert alert = new Alert(AlertType.ERROR, msg);
            alert.show();
            LOGGER.log(Level.SEVERE, "IngredientControlVController: Error trying to open Ingredients window, {0}", ex.getMessage());
        }
    }

    /**
     * Navigation with Sign In window
     *
     * @param event an ActionEvent.ACTION event type for when the button is
     * pressed
     */
    @FXML
    public void handleLogOutAction(ActionEvent event) {
        try {
            Alert a = new Alert(AlertType.CONFIRMATION, "Are you sure you want to exit? This will close the app.");
            a.showAndWait();
            if (a.getResult().equals(ButtonType.CANCEL)) {
                event.consume();
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ui/views/SignInView.fxml"));
                Parent root = (Parent) loader.load();

                SignInController controller = ((SignInController) loader.getController());

                controller.setStage(stage);

                controller.initStage(root);
            }
        } catch (IOException ex) {
            Alert alert = new Alert(AlertType.ERROR, ex.getMessage());
            alert.show();
            LOGGER.log(Level.SEVERE, ex.getMessage());
        }
    }
}
