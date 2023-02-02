package ui.controllers;

import businessLogic.DietFactory;
import cellFactories.ButtonImgListCell;
import cellFactories.FloatStringFormatter;
import cellFactories.PlatesCell;
import cellFactories.TipsCell;
import exceptions.BusinessLogicException;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
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
import objects.Diet;
import objects.GoalEnum;
import objects.Plate;
import objects.Tip;

/**
 * Controller class for diet management view. It contains event handlers and
 * initialization code for the view defined in DietControlWindow.fmxl file.
 *
 * @author JulenB
 */
public class DietsControlVController {

    private Stage stage;
    /**
     * Log that gives more info when app is running.
     */
    private static final Logger LOGGER = Logger.getLogger("DietsControlVController.class");

    /**
     * Sends you to the plates view.
     */
    @FXML
    private Button buttonPlates;
    /**
     * Sends you to the diets view.
     */
    @FXML
    private Button buttonDiets;
    /**
     * Sends you to the ingredients view.
     */
    @FXML
    private Button buttonIngredients;
    /**
     * Sends you to the tips view.
     */
    @FXML
    private Button buttonTips;
    /**
     * Sends you to the clients view.
     */
    @FXML
    private Button buttonClients;
    /**
     * Logout from app.
     */
    @FXML
    private Button buttonLogout;
    /**
     * Help view showing button.
     */
    @FXML
    private Button buttonHelp;
    /**
     * Textfield that filters diets by its name.
     */
    @FXML
    private TextField texfieldSearchbar;
    /**
     * Search diets button.
     */
    @FXML
    private Button buttonSearch;
    /**
     * Button that show all filters for diets.
     */
    @FXML
    private Button buttonFilters;
    /**
     * Button that creates a diet.
     */
    @FXML
    private Button buttonInsertRow;
    @FXML
    /**
     * Button that shows view report.
     */
    private Button buttonReport;
    /**
     * ContextMenu that pop up a menu.
     */
    @FXML
    private ContextMenu contextMenu;
    /**
     * TableView that shows diets data.
     */
    @FXML
    private TableView tableViewDiets;
    /**
     * Diets id data table column.
     */
    @FXML
    private TableColumn tableColumnId;
    /**
     * Diets name data table column.
     */
    @FXML
    private TableColumn<Diet, String> tableColumnDietName;
    /**
     * Diets description data table column.
     */
    @FXML
    private TableColumn<Diet, String> tableColumnDescription;
    /**
     * Diets calories data table column.
     */
    @FXML
    private TableColumn<Diet, Float> tableColumnCalories;
    /**
     * Diets proteins data table column.
     */
    @FXML
    private TableColumn<Diet, Float> tableColumnProteins;
    /**
     * Diets lipids data table column.
     */
    @FXML
    private TableColumn<Diet, Float> tableColumnLipids;
    /**
     * Diets carbohydrates data table column.
     */
    @FXML
    private TableColumn<Diet, Float> tableColumnCarbohydrates;
    /**
     * Diets type data table column.
     */
    @FXML
    private TableColumn<Diet, GoalEnum> tableColumnType;
    /**
     * Diets plates button table column.
     */
    @FXML
    private TableColumn<Diet, List<Plate>> tableColumnPlates;
    /**
     * Diets tips button table column.
     */
    @FXML
    private TableColumn<Diet, List<Tip>> tableColumnTips;
    /**
     * Diets image button table column.
     */
    @FXML
    private TableColumn<Diet, byte[]> tableColumnImage;
    /**
     * ObservableList that saves diets data.
     */
    private ObservableList<Diet> dietsData;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Method that initialises the window. A pop-up window is displayed asking:
     * "Are you sure you want to close the app?". If you hit Yes the pop-up
     * window disappears, the SignIn window opens and the current one closes.
     * Conversely, if you click No the pop-up window disappears.
     *
     * @param root path of the window
     */
    public void initStage(Parent root) {
        try {
            Scene scene = new Scene(root);
            //Set stage properties
            stage.setScene(scene);
            stage.setTitle("Diet Management");
            stage.setResizable(false);
            tableViewDiets.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            buttonSearch.setDefaultButton(true);

            //TABLE SET UP
            //Create an observable list for diets table.
            dietsData = FXCollections.observableArrayList(DietFactory.getModel().findAllDiets_XML(new GenericType<List<Diet>>() {
            }));
            //Set data from dietsData into tableView.
            tableViewDiets.setItems(dietsData);
            
            Callback<TableColumn<Diet, List<Plate>>, TableCell<Diet, List<Plate>>> cellPlatesFactory
                    = (TableColumn<Diet, List<Plate>> p) -> new PlatesCell(stage, tableViewDiets);
            Callback<TableColumn<Diet, List<Tip>>, TableCell<Diet, List<Tip>>> cellTipsFactory
                    = (TableColumn<Diet, List<Tip>> p) -> new TipsCell(stage);
            Callback<TableColumn<Diet, byte[]>, TableCell<Diet, byte[]>> buttonImgListCell
                    = (TableColumn<Diet, byte[]> p) -> new ButtonImgListCell(stage);

            //Set factories for cell values in diets table columns.
            tableColumnId.setCellValueFactory(new PropertyValueFactory<>("diet_id"));
            tableColumnDietName.setCellValueFactory(new PropertyValueFactory<>("dietName"));
            tableColumnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
            tableColumnCalories.setCellValueFactory(new PropertyValueFactory<>("calories"));
            tableColumnProteins.setCellValueFactory(new PropertyValueFactory<>("proteins"));
            tableColumnLipids.setCellValueFactory(new PropertyValueFactory<>("lipids"));
            tableColumnCarbohydrates.setCellValueFactory(new PropertyValueFactory<>("carbohydrates"));
            tableColumnType.setCellValueFactory(new PropertyValueFactory<>("type"));
            tableColumnPlates.setCellValueFactory(new PropertyValueFactory<>("plates"));
            tableColumnTips.setCellValueFactory(new PropertyValueFactory<>("tips"));
            tableColumnImage.setCellValueFactory(new PropertyValueFactory<>("dietImg"));

            //Set cell factories in diets table columns.
            tableColumnDietName.setCellFactory(TextFieldTableCell.<Diet>forTableColumn());
            /**
             * Checks that the text entered does not exceed 50 characters. In
             * case it is higher it shows a pop-up window with the text:
             * "Maximum characters (50) for the name of the diet exceeded." In
             * case it is not higher it sends a request to the server to update
             * the diet with the entered data. If there is any error with the
             * server a popup window is shown informing about it.
             *
             */
            tableColumnDietName.setOnEditCommit(
                    (CellEditEvent<Diet, String> t) -> {
                        Diet selectedDiet = (Diet) tableViewDiets.getSelectionModel().getSelectedItem();
                        String name = selectedDiet.getDietName();
                        try {
                            if (t.getNewValue().length() <= 50) {
                                ((Diet) t.getTableView().getItems().get(
                                        t.getTablePosition().getRow())).setDietName(t.getNewValue());
                                DietFactory.getModel().edit_XML((Diet) t.getTableView().getSelectionModel().getSelectedItem());
                            } else {
                                showErrorAlert("Maximum characters (50) for the name of the diet exceeded.");
                                dietsData = FXCollections.observableArrayList(DietFactory.getModel().findAllDiets_XML(new GenericType<List<Diet>>() {
                                }));
                                tableViewDiets.setItems(dietsData);
                            }
                        } catch (BusinessLogicException ex) {
                            ((Diet) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())).setDietName(name);
                            tableViewDiets.refresh();
                            //If there is an error in the business class, shows an alert.
                            showErrorAlert("Window can not be loaded:\n" + ex.getMessage());
                            LOGGER.log(Level.SEVERE, "DietsControlVController: Error at setOnEditCommit in tableColumnDietName, {0}", ex.getMessage());

                        }
                    });

            tableColumnDescription.setCellFactory(TextFieldTableCell.<Diet>forTableColumn());
            /**
             * Checks that the text entered does not exceed 50 characters. In
             * case it is higher it shows a pop-up window with the text:
             * "Maximum characters (50) for the name of the diet exceeded." In
             * case it is not higher it sends a request to the server to update
             * the diet with the entered data. If there is any error with the
             * server a popup window is shown informing about it.
             *
             */
            tableColumnDescription.setOnEditCommit((CellEditEvent<Diet, String> t) -> {
                Diet selectedDiet = (Diet) tableViewDiets.getSelectionModel().getSelectedItem();
                String description = selectedDiet.getDescription();
                try {
                    if (t.getNewValue().length() <= 50) {

                        ((Diet) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())).setDescription(t.getNewValue());
                        DietFactory.getModel().edit_XML((Diet) t.getTableView().getSelectionModel().getSelectedItem());
                    } else {
                        showErrorAlert("Maximum characters (50) for the description of the diet exceeded.");
                        dietsData = FXCollections.observableArrayList(DietFactory.getModel().findAllDiets_XML(new GenericType<List<Diet>>() {
                        }));
                        tableViewDiets.setItems(dietsData);
                    }
                } catch (BusinessLogicException ex) {
                    ((Diet) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setDescription(description);
                    tableViewDiets.refresh();
                    //If there is an error in the business class, shows an alert.
                    showErrorAlert("Window can not be loaded:\n" + ex.getMessage());
                    LOGGER.log(Level.SEVERE, "DietsControlVController: Error at setOnEditCommit in tableColumnDescription, {0}", ex.getMessage());
                }
            });

            tableColumnCalories.setCellFactory(TextFieldTableCell.<Diet, Float>forTableColumn(new FloatStringFormatter()));
            /**
             * Check that the number entered is not more than 4 digits: . In
             * case it is higher it displays a pop-up window with the text:
             * "Only number with a maximum of 4 digits allowed." In case it is
             * not higher it sends a request to the server to update the diet
             * with the entered data. If there is any error with the server a
             * popup window is shown informing about it.
             *
             */
            tableColumnCalories.setOnEditCommit((CellEditEvent<Diet, Float> t) -> {
                Diet selectedDiet = (Diet) tableViewDiets.getSelectionModel().getSelectedItem();
                Float calories = selectedDiet.getCalories();
                try {
                    if (t.getNewValue() <= 9999) {
                        ((Diet) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())).setCalories(t.getNewValue());
                        DietFactory.getModel().edit_XML((Diet) t.getTableView().getSelectionModel().getSelectedItem());
                    } else {
                        showErrorAlert("Only number with a maximum of 4 digits allowed.");
                        dietsData = FXCollections.observableArrayList(DietFactory.getModel().findAllDiets_XML(new GenericType<List<Diet>>() {
                        }));
                        tableViewDiets.setItems(dietsData);
                    }
                } catch (BusinessLogicException ex) {
                    ((Diet) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setCalories(calories);
                    tableViewDiets.refresh();
                    //If there is an error in the business class, shows an alert.
                    showErrorAlert("Window can not be loaded:\n" + ex.getMessage());
                    LOGGER.log(Level.SEVERE, "DietsControlVController: Error at setOnEditCommit in tableColumnCalories, {0}", ex.getMessage());
                } catch (NullPointerException ex) {
                    ((Diet) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setCalories(t.getOldValue());
                    tableViewDiets.refresh();
                    //If is set a String value it, shows an alert.
                    showErrorAlert("This field only admits numbers.");
                    LOGGER.log(Level.SEVERE, "DietsControlVController: Error at setOnEditCommit in tableColumnCalories, {0}", ex.getMessage());
                }
            });

            tableColumnProteins.setCellFactory(TextFieldTableCell.<Diet, Float>forTableColumn(new FloatStringFormatter()));
            /**
             * Check that the number entered is not more than 4 digits: . In
             * case it is higher it displays a pop-up window with the text:
             * "Only number with a maximum of 4 digits allowed." In case it is
             * not higher it sends a request to the server to update the diet
             * with the entered data. If there is any error with the server a
             * popup window is shown informing about it.
             *
             */
            tableColumnProteins.setOnEditCommit((CellEditEvent<Diet, Float> t) -> {
                Diet selectedDiet = (Diet) tableViewDiets.getSelectionModel().getSelectedItem();
                Float proteins = selectedDiet.getProteins();
                try {
                    if (t.getNewValue() <= 9999) {
                        ((Diet) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())).setProteins(t.getNewValue());
                        DietFactory.getModel().edit_XML((Diet) t.getTableView().getSelectionModel().getSelectedItem());
                    } else {
                        showErrorAlert("Only number with a maximum of 4 digits allowed.");
                        dietsData = FXCollections.observableArrayList(DietFactory.getModel().findAllDiets_XML(new GenericType<List<Diet>>() {
                        }));
                        tableViewDiets.setItems(dietsData);
                    }
                } catch (BusinessLogicException ex) {
                    ((Diet) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setProteins(proteins);
                    tableViewDiets.refresh();
                    //If there is an error in the business class, shows an alert.
                    showErrorAlert("Window can not be loaded:\n" + ex.getMessage());
                    LOGGER.log(Level.SEVERE, "DietsControlVController: Error at setOnEditCommit in tableColumnProteins, {0}", ex.getMessage());
                } catch (NullPointerException ex) {
                    ((Diet) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setCalories(t.getOldValue());
                    tableViewDiets.refresh();
                    //If is set a String value it, shows an alert.
                    showErrorAlert("This field only admits numbers.");
                    LOGGER.log(Level.SEVERE, "DietsControlVController: Error at setOnEditCommit in tableColumnProteins, {0}", ex.getMessage());
                }
            });

            tableColumnLipids.setCellFactory(TextFieldTableCell.<Diet, Float>forTableColumn(new FloatStringFormatter()));
            /**
             * Check that the number entered is not more than 4 digits: . In
             * case it is higher it displays a pop-up window with the text:
             * "Only number with a maximum of 4 digits allowed." In case it is
             * not higher it sends a request to the server to update the diet
             * with the entered data. If there is any error with the server a
             * popup window is shown informing about it.
             *
             */
            tableColumnLipids.setOnEditCommit((CellEditEvent<Diet, Float> t) -> {
                Diet selectedDiet = (Diet) tableViewDiets.getSelectionModel().getSelectedItem();
                Float lipids = selectedDiet.getLipids();
                try {
                    if (t.getNewValue() <= 9999) {
                        ((Diet) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())).setLipids(t.getNewValue());
                        DietFactory.getModel().edit_XML((Diet) t.getTableView().getSelectionModel().getSelectedItem());
                    } else {
                        showErrorAlert("Only number with a maximum of 4 digits allowed.");
                        dietsData = FXCollections.observableArrayList(DietFactory.getModel().findAllDiets_XML(new GenericType<List<Diet>>() {
                        }));
                        tableViewDiets.setItems(dietsData);
                    }
                } catch (BusinessLogicException ex) {
                    ((Diet) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setLipids(lipids);
                    tableViewDiets.refresh();
                    //If there is an error in the business class, shows an alert.
                    showErrorAlert("Window can not be loaded:\n" + ex.getMessage());
                    LOGGER.log(Level.SEVERE, "DietsControlVController: Error at setOnEditCommit in tableColumnLipids, {0}", ex.getMessage());
                } catch (NullPointerException ex) {
                    ((Diet) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setCalories(t.getOldValue());
                    tableViewDiets.refresh();
                    //If is set a String value it, shows an alert.
                    showErrorAlert("This field only admits numbers.");
                    LOGGER.log(Level.SEVERE, "DietsControlVController: Error at setOnEditCommit in tableColumnLipids, {0}", ex.getMessage());
                }
            });

            tableColumnCarbohydrates.setCellFactory(TextFieldTableCell.<Diet, Float>forTableColumn(new FloatStringFormatter()));
            /**
             * Check that the number entered is not more than 4 digits: . In
             * case it is higher it displays a pop-up window with the text:
             * "Only number with a maximum of 4 digits allowed." In case it is
             * not higher it sends a request to the server to update the diet
             * with the entered data. If there is any error with the server a
             * popup window is shown informing about it.
             *
             */
            tableColumnCarbohydrates.setOnEditCommit((CellEditEvent<Diet, Float> t) -> {
                Diet selectedDiet = (Diet) tableViewDiets.getSelectionModel().getSelectedItem();
                Float carbohydrates = selectedDiet.getCarbohydrates();
                try {
                    if (t.getNewValue() <= 9999) {
                        ((Diet) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())).setCarbohydrates(t.getNewValue());
                        DietFactory.getModel().edit_XML((Diet) t.getTableView().getSelectionModel().getSelectedItem());
                    } else {
                        showErrorAlert("Only number with a maximum of 4 digits allowed.");
                        dietsData = FXCollections.observableArrayList(DietFactory.getModel().findAllDiets_XML(new GenericType<List<Diet>>() {
                        }));
                        tableViewDiets.setItems(dietsData);
                    }
                } catch (BusinessLogicException ex) {
                    ((Diet) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setCarbohydrates(carbohydrates);
                    tableViewDiets.refresh();
                    //If there is an error in the business class, shows an alert.
                    showErrorAlert("Window can not be loaded:\n" + ex.getMessage());
                    LOGGER.log(Level.SEVERE, "DietsControlVController: Error at setOnEditCommit in tableColumnCarbohydrates, {0}", ex.getMessage());
                } catch (NullPointerException ex) {
                    ((Diet) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setCalories(t.getOldValue());
                    tableViewDiets.refresh();
                    //If is set a String value it, shows an alert.
                    showErrorAlert("This field only admits numbers.");
                    LOGGER.log(Level.SEVERE, "DietsControlVController: Error at setOnEditCommit in tableColumnCarbohydrates, {0}", ex.getMessage());
                }
            });

            tableColumnType.setCellFactory(ComboBoxTableCell.<Diet, GoalEnum>forTableColumn(GoalEnum.values()));
            tableColumnType.setOnEditCommit(
                    //If the image is valid, it will save it, send a update message to business logic and updates it.
                    (CellEditEvent<Diet, GoalEnum> t) -> {
                        Diet selectedDiet = (Diet) tableViewDiets.getSelectionModel().getSelectedItem();
                        ComboBox<String> comboBox = new ComboBox<>();
                        comboBox.getItems().addAll("MAINTAIN", "INCREASE", "DECREASE");
                        comboBox.setValue(selectedDiet.getType().toString());
                        try {
                            ((Diet) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())).setType(t.getNewValue());
                            DietFactory.getModel().edit_XML((Diet) t.getTableView().getSelectionModel().getSelectedItem());
                        } catch (BusinessLogicException ex) {
                            GoalEnum ge = GoalEnum.DECREASE;
                            if(comboBox.getValue().equals("MAINTAIN")){
                                ge = GoalEnum.MAINTAIN;
                            }else if(comboBox.getValue().equals("INCREASE")){
                                ge = GoalEnum.INCREASE;
                            }
                            ((Diet) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())).setType(ge);
                            tableViewDiets.refresh();
                            //If there is an error in the business class, shows an alert.
                            showErrorAlert("Window can not be loaded:\n" + ex.getMessage());
                            LOGGER.log(Level.SEVERE, "DietsControlVController: Error at setOnEditCommit in tableColumnType, {0}", ex.getMessage());
                        }
                    });

            /**
             * Check for existing plates, then if you click on the existing
             * button in the cell, it will take you to the dishes window and
             * automatically list those dishes.
             */
            tableColumnPlates.setCellFactory(cellPlatesFactory);

            /**
             * Check for existing tips, then if you click on the existing button
             * in the cell, it will take you to the dishes window and
             * automatically list those dishes.
             */
            tableColumnTips.setCellFactory(cellTipsFactory);

            tableColumnImage.setCellFactory(buttonImgListCell);
            /**
             * In case it is not higher it sends a request to the server to
             * update the diet with the entered data. If there is any error with
             * the server a popup window is shown informing about it.
             */
            tableColumnImage.setOnEditCommit((CellEditEvent<Diet, byte[]> t) -> {
                try {
                    ((Diet) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setDietImg(t.getNewValue());
                    DietFactory.getModel().edit_XML((Diet) t.getTableView().getSelectionModel().getSelectedItem());
                } catch (BusinessLogicException ex) {
                    showErrorAlert("Window can not be loaded:\n" + ex.getMessage());
                    LOGGER.log(Level.SEVERE, "DietsControlVController: Error at setOnEditCommit in tableColumnImage, {0}", ex.getMessage());
                }
            });

            //ACTIONS FOR CONTROLS
            stage.setOnCloseRequest(this::handleExitAction);

            buttonLogout.setOnAction(this::handleButtonLogOutAction);

            buttonFilters.setOnAction(this::handleButtonFiltersAction);

            buttonSearch.setOnAction(this::handleButtonSearchAction);

            buttonSearch.setOnKeyTyped(this::handleButtonSearchEnterAction);

            contextMenu.getItems().get(0).setOnAction(this::handleDeleteAction);

            buttonInsertRow.setOnAction(this::handleCreateAction);

            buttonHelp.setOnAction(this::handleButtonHelpAction);

            buttonReport.setOnAction(this::handleButtonReportAction);

            buttonPlates.setOnAction(this::handleButtonPlatesAction);

            buttonDiets.setOnAction(this::handleButtonDietsAction);

            buttonIngredients.setOnAction(this::handleButtonIngredientsAction);

            buttonTips.setOnAction(this::handleButtonTipsAction);

            buttonClients.setOnAction(this::handleButtonClientsAction);
            //Show window.
            stage.show();
            LOGGER.log(Level.INFO, "DietsControlVController: Window initialized");
        } catch (BusinessLogicException ex) {
            //If any error occurs initializing the window, show an alert.
            showErrorAlert("Window can not be loaded, " + ex.getMessage());
            LOGGER.log(Level.SEVERE, "DietsControlVController: Error initializing window, {0}", ex.getMessage());
        }

    }

    //METHODS
    /**
     *
     * @param event
     */
    private void handleExitAction(WindowEvent event) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit? This will close the app.");
        a.showAndWait();
        try {
            if (a.getResult().equals(ButtonType.CANCEL)) {
                event.consume();
            } else {
                Platform.exit();
                LOGGER.log(Level.INFO, "DietsControlVController: Window exited");
            }
        } catch (Exception ex) {
            //If theres is an error exiting the app, an alert will show.
            showErrorAlert("Error trying to exit app:\n" + ex.getMessage());
            LOGGER.log(Level.SEVERE, "DietsControlVController: Error trying to exit app, {0}", ex.getMessage());
        }
    }

    /**
     * It will display a help window.
     *
     * @param event The ActionEvent object for the event.
     */
    private void handleButtonHelpAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/views/DietHelpFXMLControlVController.fxml"));
            Parent root = (Parent) loader.load();
            DietHelpControllerController dietHelpControllerController = ((DietHelpControllerController) loader.getController());
            //Initializes and shows help stage
            dietHelpControllerController.initAndShowStage(root);
        } catch (IOException ex) {
            //If theres is an error trying to open the help view, an alert will show. 
            showErrorAlert("Error trying to open help view:\n" + ex.getMessage());
            LOGGER.log(Level.SEVERE, "DietsControlVController: Error trying to open help view, {0}", ex.getMessage());
        }
    }

    /**
     * Logout form app. A pop-up window is displayed asking: "Are you sure you
     * want to log out?". If you hit Yes the pop-up window disappears, the
     * SignIn window opens and the current one closes. Conversely, if you click
     * No the pop-up window disappears.
     *
     * @param event The ActionEvent object for the event.
     */
    @FXML
    private void handleButtonLogOutAction(ActionEvent event) {
        try {
            Alert a = new Alert(AlertType.CONFIRMATION, "Are you sure you want to Log Out?");
            a.showAndWait();
            if (a.getResult().equals(ButtonType.OK)) {
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/SignInView.fxml"));
                Parent root = (Parent) loader.load();

//                SignInVController controller = ((SignInVController) loader.getController());
//                controller.setStage(new Stage());
                stage.close();
                LOGGER.info("DietsControlVController: Diet Management window closed");
//                controller.initStage(root);
            }
            if (a.getResult().equals(ButtonType.CANCEL)) {
                event.consume();
            }
        } catch (IOException | IllegalStateException ex) {
            //If theres is an error trying to log out the app, an alert will show.
            showErrorAlert("Error trying to log out:\n" + ex.getMessage());
            LOGGER.log(Level.SEVERE, "DietsControlVController: Error trying to log out, {0}", ex.getMessage());
        }
    }

    /**
     * When click buttonFilters a menu will pop up. A popup window with a grid
     * containing the following node is displayed: A combobox with the following
     * options: "Maintain". "Increase". "Decrease". If you click Cancel the
     * popup window disappears. If you click Accept the popup window disappears
     * and the diets that match the filters will appear in the tableViewDiets.
     * In case it does not find one/s it will show a popup saying "No diets
     * exists with that type of goal".
     *
     * In case they do exist, it will show all those that match the applied
     * filter.
     *
     * @param event The ActionEvent object for the event.
     */
    @FXML
    private void handleButtonFiltersAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Filters");
        alert.setHeaderText("Please select an option");

        GridPane grid = new GridPane();

        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("MAINTAIN", "INCREASE", "DECREASE");
        comboBox.setPromptText("Select an option");

        grid.add(comboBox, 0, 0);

        alert.getDialogPane().setContent(grid);

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    String selectedOption = comboBox.getValue();
                    dietsData = FXCollections.observableArrayList(DietFactory.getModel().findAllDietsByGoal_XML(new GenericType<List<Diet>>() {
                    }, selectedOption));
                    if (dietsData.isEmpty()) {
                        showErrorAlert("There aren't any diets with that filter.");
                        LOGGER.log(Level.INFO, "DietsControlVController: No diets for {0}", selectedOption);
                    } else {
                        tableViewDiets.setItems(dietsData);
                        tableViewDiets.refresh();
                    }
                } catch (BusinessLogicException ex) {
                    //If there is an error in the business class, shows an alert.
                    showErrorAlert("Error loading data:\n" + ex.getMessage());
                    LOGGER.log(Level.SEVERE, "DietsControlVController: Error finding diets by goal, {0}", ex.getMessage());
                }
            }
            if (alert.getResult().equals(ButtonType.CANCEL)) {
                event.consume();
            }
        });
    }

    /**
     * This method search diets by its name. It will search and show in the
     * table the Diet with the the name specifying if it exists, in case it is
     * not found it will show an alert saying "no diets exists with the
     * specified name".
     *
     *
     * @param event The ActionEvent object for the event.
     */
    @FXML
    private void handleButtonSearchAction(ActionEvent event) {
        try {
            if (texfieldSearchbar.getText().equals("")) {
                dietsData = FXCollections.observableArrayList(DietFactory.getModel().findAllDiets_XML(new GenericType<List<Diet>>() {
                }));
            } else {
                dietsData = FXCollections.observableArrayList(DietFactory.getModel().findAllDietsByName_XML(new GenericType<List<Diet>>() {
                }, texfieldSearchbar.getText()));
            }
            tableViewDiets.setItems(dietsData);
            tableViewDiets.refresh();
        } catch (BusinessLogicException ex) {
            //If there is an error in the business class, shows an alert.
            showErrorAlert("Error loading data:\n" + ex.getMessage());
            LOGGER.log(Level.SEVERE, "DietsControlVController: Error finding diets or diets by name, {0}", ex.getMessage());
        }

    }

    /**
     * This method search for diets when buttonSearch is clicked.
     *
     * @param event The ActionEvent object for the event.
     */
    @FXML
    private void handleButtonSearchEnterAction(KeyEvent event) {
        try {
            if (event.getSource() == KeyCode.ENTER) {
                if (texfieldSearchbar.getText().equals("")) {
                    dietsData = FXCollections.observableArrayList(DietFactory.getModel().findAllDiets_XML(new GenericType<List<Diet>>() {
                    }));
                } else {
                    dietsData = FXCollections.observableArrayList(DietFactory.getModel().findAllDietsByName_XML(new GenericType<List<Diet>>() {
                    }, texfieldSearchbar.getText()));
                }
                tableViewDiets.setItems(dietsData);
                tableViewDiets.refresh();
            }
        } catch (BusinessLogicException ex) {
            //If there is an error in the business class, shows an alert.
            showErrorAlert("Error loading data:\n" + ex.getMessage());
            LOGGER.log(Level.SEVERE, "DietsControlVController: Error finding diets or diets by name, {0}", ex.getMessage());
        }
    }

    /**
     * When delete is clicked.A pop-up window is displayed saying "Are you sure
     * you want to delete this diet?" With two options (YES or NO). In case you
     * say yes, the selected diet is deleted and the pop-up window closes. In
     * case you say no, the pop-up window closes.
     *
     *
     * @param event The ActionEvent object for the event.
     */
    @FXML
    private void handleDeleteAction(ActionEvent event) {
        try {
            Diet selectedItem = (Diet) tableViewDiets.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to delete this diet?");
            alert.showAndWait();
            if (alert.getResult().equals(ButtonType.OK)) {
                DietFactory.getModel().remove(selectedItem.getDiet_id());
                tableViewDiets.getItems().remove(selectedItem);
                tableViewDiets.refresh();
            }
            if (alert.getResult().equals(ButtonType.CANCEL)) {
                event.consume();
            }
        } catch (BusinessLogicException ex) {
            //If there is an error in the business class, shows an alert.
            showErrorAlert("Error deleting the diet:\n" + ex.getMessage());
            LOGGER.log(Level.SEVERE, "DietsControlVController: Error removing a diet, {0}", ex.getMessage());
        } catch (NullPointerException ex) {
            //If there is an error selecting a diet, shows an alert.
            showErrorAlert("You must select the diet you want to delete.");
            LOGGER.log(Level.SEVERE, "DietsControlVController: Error removing a diet, {0}", ex.getMessage());
        }
    }

    /**
     * When insertRowButton is clicked, sends create message to the business
     * class and creates a diet and shows in table view. Adds a new editable
     * line to the table, to create a new diet. It will send to the server a
     * request to create a new Diet.
     *
     * @param event The ActionEvent object for the event.
     */
    @FXML
    private void handleCreateAction(ActionEvent event) {
        try {
            Diet diet = (Diet) new Diet();
            diet.setType(GoalEnum.INCREASE);
            DietFactory.getModel().create_XML(diet);
            dietsData = FXCollections.observableArrayList(DietFactory.getModel().findAllDiets_XML(new GenericType<List<Diet>>() {
            }));
            tableViewDiets.setItems(dietsData);
            tableViewDiets.refresh();
        } catch (BusinessLogicException ex) {
            //If there is an error in the business class, shows an alert.
            showErrorAlert("Error creating a diet:\n" + ex.getMessage());
            LOGGER.log(Level.SEVERE, "DietsControlVController: Error creating a diet, {0}", ex.getMessage());
        }
    }

    /**
     * Action event handler for button report. It shows a JFrame containing a
     * report of diets. Checks if there is data in the table. If there is data,
     * it generates a report and displays it. If there is no data, it displays a
     * pop-up window with the text "No data available for report".
     *
     *
     * @param event The ActionEvent object for the event.
     */
    private void handleButtonReportAction(ActionEvent event) {
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport("src/ui/reports/DietReport.jrxml");
            JRBeanCollectionDataSource dataItems = new JRBeanCollectionDataSource((Collection<Diet>) this.tableViewDiets.getItems());
            if (dataItems.getData().size() < 1) {
                showErrorAlert("No data available for report");
            } else {
                Map<String, Object> parameters = new HashMap<>();
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataItems);
                JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
                jasperViewer.setVisible(true);
            }

        } catch (JRException ex) {
            showErrorAlert("Error trying to open report:\n" + ex.getMessage());
            LOGGER.log(Level.SEVERE, "DietsControlVController: Error opening report, {0}", ex.getMessage());
        }
    }

    /**
     * Displays plates window and close this one. Open the PlateControlWindow
     * window and close it.
     *
     * @param event The ActionEvent object for the event.
     */
    @FXML
    private void handleButtonPlatesAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ui/views/PlateControlView.fxml"));
            Parent root = (Parent) loader.load();

            PlateControlVController controller = ((PlateControlVController) loader.getController());

            controller.setStage(stage);
            stage.close();
            controller.initStage(root);
        } catch (IOException | IllegalStateException ex) {
            //If theres is an error trying to change view, an alert will show.
            showErrorAlert("Failed trying to open Plates window.");
            LOGGER.log(Level.SEVERE, "DietsControlVController: Error trying to open Plate window, {0}", ex.getMessage());
        }
    }

    /**
     * Displays diets window and close this one. Open the DietControlWindow
     * window and close it.
     *
     * @param event The ActionEvent object for the event.
     */
    @FXML
    private void handleButtonDietsAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ui/views/DietControlWindow.fxml"));
            Parent root = (Parent) loader.load();

            DietsControlVController controller = ((DietsControlVController) loader.getController());

            controller.setStage(stage);
            stage.close();
            controller.initStage(root);
        } catch (IOException | IllegalStateException ex) {
            //If theres is an error trying to change view, an alert will show.
            showErrorAlert("Failed trying to open Diets window.");
            LOGGER.log(Level.SEVERE, "DietsControlVController: Error trying to open Diets window, {0}", ex.getMessage());
        }
    }

    /**
     * Displays ingredients window and close this one. Open the
     * IngredientsControlWindow window and close it.
     *
     * @param event The ActionEvent object for the event.
     */
    @FXML
    private void handleButtonIngredientsAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ui/views/IngredientControlWindow.fxml"));
            Parent root = (Parent) loader.load();

            IngredientControlVController controller = ((IngredientControlVController) loader.getController());

            controller.setStage(stage);
            stage.close();
            controller.initStage(root);
        } catch (IOException | IllegalStateException ex) {
            //If theres is an error trying to change view, an alert will show.
            showErrorAlert("Failed trying to open Ingredients window.");
            LOGGER.log(Level.SEVERE, "DietsControlVController: Error trying to open Plates window, {0}", ex.getMessage());
        }
    }

    /**
     * Displays tips window and close this one. Open the TipControlWindow window
     * and close it.
     *
     * @param event The ActionEvent object for the event.
     */
    @FXML
    private void handleButtonTipsAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ui/views/TipsControlWindow.fxml"));
            Parent root = (Parent) loader.load();

            DietsControlVController controller = ((DietsControlVController) loader.getController());

            controller.setStage(stage);
            stage.close();
            controller.initStage(root);
        } catch (IOException | IllegalStateException ex) {
            //If theres is an error trying to change view, an alert will show.
            showErrorAlert("Failed trying to open Tips window.");
            LOGGER.log(Level.SEVERE, "DietsControlVController: Error trying to open Tips window, {0}", ex.getMessage());
        }
    }

    /**
     * Displays clients window and close this one. Open the ClientControlWindow
     * window and close it.
     *
     * @param event The ActionEvent object for the event.
     */
    @FXML
    private void handleButtonClientsAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ui/views/ClientAdminWindow.fxml"));
            Parent root = (Parent) loader.load();

            ClientControlWindow controller = ((ClientControlWindow) loader.getController());

            controller.setStage(stage);
            stage.close();
            controller.initStage(root);
        } catch (IOException | IllegalStateException ex) {
            //If theres is an error trying to change view, an alert will show.
            showErrorAlert("Failed trying to open Clients window.");
            LOGGER.log(Level.SEVERE, "DietsControlVController: Error trying to open Clients window, {0}", ex.getMessage());
        }
    }

    /**
     * This method makes an alert to be used by other methods.
     *
     * @param errorMsg Message that will be shown in the alert.
     */
    protected void showErrorAlert(String errorMsg) {
        Alert alert = new Alert(Alert.AlertType.ERROR, errorMsg, ButtonType.OK);
        alert.showAndWait();

    }
}
