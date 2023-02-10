/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers;

import businessLogic.ClientFactory;
import cellFactories.FloatStringFormatter;
import files.AsymmetricClient;
import cryptography.HashMD5;
import exceptions.BusinessLogicException;
import java.util.Collection;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.ConnectException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
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
import javafx.scene.web.WebEngine;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javafx.util.converter.DateStringConverter;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;
import javax.ws.rs.core.GenericType;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import objects.ClientOBJ;
import objects.GenreEnum;
import objects.GoalEnum;
import objects.StatusEnum;

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
    private Button buttonInsert;
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
    private MenuItem itemAll;
    @FXML
    private TableView tableClients;
    @FXML
    private TableColumn<ClientOBJ, String> columnEmail;
    @FXML
    private TableColumn<ClientOBJ, String> columnName;
    @FXML
    private TableColumn columnPasswordChange;
    @FXML
    private TableColumn<ClientOBJ, String> columnLogin;
    @FXML
    private TableColumn<ClientOBJ, StatusEnum> columnStatus;
    @FXML
    private TableColumn<ClientOBJ, String> columnAge;
    @FXML
    private TableColumn<ClientOBJ, GenreEnum> columnGenre;
    @FXML
    private TableColumn<ClientOBJ, GoalEnum> columnGoal;
    @FXML
    private TableColumn<ClientOBJ, Float> columnHeight;
    @FXML
    private ContextMenu menuTable;
    @FXML
    private TextField texfieldSearchbar;

    private ObservableList<ClientOBJ> clientsData;

    private static final String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";

    private static final Pattern PATTERN = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initStage(Parent root) {
        Scene scene = new Scene(root);

        stage.setScene(scene);

        stage.setTitle("SignIn");
        stage.setResizable(false);

        // Confirmar el cierre de la aplicación
        stage.setOnCloseRequest(this::handleExitAction);
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.F1) {
                    // Código que se ejecutará cuando se pulse la tecla F1
                    handleHelpAction(null);
                }
            }
        });

        // LOAD TABLE ITEMS //
        tableClients.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        columnAge.setCellValueFactory(new PropertyValueFactory("age"));
        columnEmail.setCellValueFactory(new PropertyValueFactory("email"));
        columnGenre.setCellValueFactory(new PropertyValueFactory("genre"));
        columnGoal.setCellValueFactory(new PropertyValueFactory("goal"));
        columnHeight.setCellValueFactory(new PropertyValueFactory("height"));
        columnLogin.setCellValueFactory(new PropertyValueFactory("login"));
        columnName.setCellValueFactory(new PropertyValueFactory("fullName"));
        columnPasswordChange.setCellValueFactory(new PropertyValueFactory("lastPasswordChange"));
        columnStatus.setCellValueFactory(new PropertyValueFactory("status"));

        stage.show();

        try {
            // load all the clients and place them in the table
            clientsData = FXCollections.observableArrayList(ClientFactory.getModel().findAll(new GenericType<List<ClientOBJ>>() {
            }));
        } catch (BusinessLogicException ex) {
            Alert alert = new Alert(AlertType.ERROR, "Data could not be loaded. BackEnd error: try again later or contact your help desk.");
            alert.show();
            LOGGER.log(Level.SEVERE, ex.getMessage());
            tableClients.refresh();
        }
        tableClients.setItems(clientsData);
        tableClients.setEditable(true);

        buttonSearch.setOnAction(this::handleSearchAction);
        itemEnabled.setOnAction(this::handleFilterEnabled);
        itemDisabled.setOnAction(this::handleFilterDisabled);
        itemAll.setOnAction(this::handleFilterAll);

        // EDITABLE ITEMS //
        /**
         * Checks if it can be converted to integer, if it does it updates the
         * client with the new age value if not it shows an alert indicating the
         * error and puts back the previous value
         */
        columnAge.setCellFactory(TextFieldTableCell.<ClientOBJ>forTableColumn());
        columnAge.setOnEditCommit((CellEditEvent<ClientOBJ, String> t) -> {
            try {
                Integer age = Integer.parseInt(t.getNewValue());
                if (age <= 0) {
                    throw new BusinessLogicException("Value cannot be negative");
                }
                ClientFactory.getModel().edit((ClientOBJ) t.getTableView().getSelectionModel().getSelectedItem());
                ((ClientOBJ) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setAge(t.getNewValue());
                LOGGER.log(Level.INFO, "Client updated correctly");
            } catch (NumberFormatException | BusinessLogicException ex) {
                Alert alert = new Alert(AlertType.ERROR, ex.getMessage() + ". BackEnd error: try again later or contact your help desk.");
                alert.show();
                LOGGER.log(Level.SEVERE, ex.getMessage());
                ((ClientOBJ) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setAge(t.getOldValue());
                tableClients.refresh();
            }
        });

        /**
         * This checks the maximum characters and the format of the email, if
         * everything is correct it updates the client with the new email value,
         * if not it shows and alert specifying the error and puts back the
         * previous value
         */
        columnEmail.setCellFactory(TextFieldTableCell.<ClientOBJ>forTableColumn());
        columnEmail.setOnEditCommit((CellEditEvent<ClientOBJ, String> t) -> {
            if (t.getNewValue().length() >= 50 || validateEmail(t.getNewValue())) {
                try {
                    ClientFactory.getModel().edit((ClientOBJ) t.getTableView().getSelectionModel().getSelectedItem());
                    ((ClientOBJ) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setEmail(t.getNewValue());
                    LOGGER.log(Level.INFO, "Client updated correctly");
                } catch (BusinessLogicException ex) {
                    Alert alert = new Alert(AlertType.ERROR, ex.getMessage() + ". BackEnd error: try again later or contact your help desk.");
                    alert.show();
                    LOGGER.log(Level.SEVERE, ex.getMessage());
                    ((ClientOBJ) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setEmail(t.getOldValue());
                    tableClients.refresh();
                }
            } else {
                Alert alert = new Alert(AlertType.ERROR, "The email must have a correct pattern and less than 50 characters.");
                alert.show();
                LOGGER.log(Level.SEVERE, "The email must have a correct pattern and less than 50 characters.");
                ((ClientOBJ) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setEmail(t.getOldValue());
                tableClients.refresh();
            }
        });

        /**
         * Updates the client genre
         */
        columnGenre.setCellFactory(ComboBoxTableCell.<ClientOBJ, GenreEnum>forTableColumn(GenreEnum.values()));
        columnGenre.setOnEditCommit((CellEditEvent<ClientOBJ, GenreEnum> t) -> {
            try {
                ClientFactory.getModel().edit((ClientOBJ) t.getTableView().getSelectionModel().getSelectedItem());
                ((ClientOBJ) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setGenre(t.getNewValue());
                LOGGER.log(Level.INFO, "Client updated correctly");
            } catch (BusinessLogicException ex) {
                Alert alert = new Alert(AlertType.ERROR, ex.getMessage() + ". BackEnd error: try again later or contact your help desk.");
                alert.show();
                LOGGER.log(Level.SEVERE, ex.getMessage());
                ((ClientOBJ) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setGenre(t.getOldValue());
                tableClients.refresh();
            }
        });

        /**
         * Updates the client goal
         */
        columnGoal.setCellFactory(ComboBoxTableCell.<ClientOBJ, GoalEnum>forTableColumn(GoalEnum.values()));
        columnGoal.setOnEditCommit((CellEditEvent<ClientOBJ, GoalEnum> t) -> {
            try {
                ClientFactory.getModel().edit((ClientOBJ) t.getTableView().getSelectionModel().getSelectedItem());
                ((ClientOBJ) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setGoal(t.getNewValue());
                LOGGER.log(Level.INFO, "Client updated correctly");
            } catch (BusinessLogicException ex) {
                Alert alert = new Alert(AlertType.ERROR, ex.getMessage() + ". BackEnd error: try again later or contact your help desk.");
                alert.show();
                LOGGER.log(Level.SEVERE, ex.getMessage());
                ((ClientOBJ) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setGoal(t.getOldValue());
                tableClients.refresh();
            }
        });

        /**
         * Checks if entered value can be converted to Float, if everything goes
         * correctly it updates the value, if not it puts back the previous
         * value
         */
        columnHeight.setCellFactory(TextFieldTableCell.<ClientOBJ, Float>forTableColumn(new FloatStringFormatter()));
        columnHeight.setOnEditCommit((CellEditEvent<ClientOBJ, Float> t) -> {
            try {
                if (t.getNewValue() <= 0) {
                    throw new BusinessLogicException("Value cannot be negative");
                }
                ClientFactory.getModel().edit((ClientOBJ) t.getTableView().getSelectionModel().getSelectedItem());
                ((ClientOBJ) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setHeight(t.getNewValue());
                LOGGER.log(Level.INFO, "Client updated correctly");
            } catch (BusinessLogicException | NullPointerException ex) {
                Alert alert = new Alert(AlertType.ERROR, ex.getMessage() + ". BackEnd error: try again later or contact your help desk.");
                alert.show();
                LOGGER.log(Level.SEVERE, ex.getMessage());
                ((ClientOBJ) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setHeight(t.getOldValue());
                tableClients.refresh();
            }
        });

        /**
         * Checks if the Login exists, if it does, it shows an alert indicating
         * it, if it doesent it checks wether or not the length is correct, if
         * it is, the client login gets updated
         */
        columnLogin.setCellFactory(TextFieldTableCell.<ClientOBJ>forTableColumn());
        columnLogin.setOnEditCommit((CellEditEvent<ClientOBJ, String> t) -> {
            try {
                ClientOBJ cliente = ClientFactory.getModel().findClientByLogin(new GenericType<ClientOBJ>() {
                }, t.getNewValue());
                Alert alert = new Alert(AlertType.ERROR, "The entered login already exists");
                alert.show();
                LOGGER.log(Level.SEVERE, "The entered login already exists");
                ((ClientOBJ) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setLogin(t.getOldValue());
                tableClients.refresh();
            } catch (Exception ex) {
                if (t.getNewValue().length() >= 25) {
                    Alert alert = new Alert(AlertType.ERROR, "The login must be less than 25 characters.");
                    alert.show();
                    LOGGER.log(Level.SEVERE, "The login must be less than 25 characters.");
                    ((ClientOBJ) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setLogin(t.getOldValue());
                    tableClients.refresh();
                } else {
                    try {
                        ClientFactory.getModel().edit((ClientOBJ) t.getTableView().getSelectionModel().getSelectedItem());
                        ((ClientOBJ) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())).setLogin(t.getNewValue());
                        LOGGER.log(Level.INFO, "Client updated correctly");
                    } catch (BusinessLogicException ex1) {
                        Alert alert = new Alert(AlertType.ERROR, ex1.getMessage() + ". BackEnd error: try again later or contact your help desk.");
                        alert.show();
                        LOGGER.log(Level.SEVERE, ex.getMessage());
                        ((ClientOBJ) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())).setLogin(t.getOldValue());
                        tableClients.refresh();
                    }
                }
            }
        }
        );

        /**
         * Checks if the length is ok, if it is it updates the name
         */
        columnName.setCellFactory(TextFieldTableCell.<ClientOBJ>forTableColumn());
        columnName.setOnEditCommit(
                (CellEditEvent<ClientOBJ, String> t) -> {
                    if (t.getNewValue().length() >= 25) {
                        Alert alert = new Alert(AlertType.ERROR, "The name must be less than 25 characters.");
                        alert.show();
                        LOGGER.log(Level.SEVERE, "The name must be less than 25 characters.");
                        ((ClientOBJ) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())).setLogin(t.getOldValue());
                        tableClients.refresh();
                    } else {
                        try {
                            ClientFactory.getModel().edit((ClientOBJ) t.getTableView().getSelectionModel().getSelectedItem());
                            ((ClientOBJ) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())).setFullName(t.getNewValue());
                            LOGGER.log(Level.INFO, "Client updated correctly");
                        } catch (BusinessLogicException ex) {
                            Alert alert = new Alert(AlertType.ERROR, ex.getMessage() + ". BackEnd error: try again later or contact your help desk.");
                            alert.show();
                            LOGGER.log(Level.SEVERE, ex.getMessage());
                            ((ClientOBJ) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())).setFullName(t.getOldValue());
                            tableClients.refresh();
                        }
                    }
                }
        );

        /**
         * Updates the client status
         */
        columnStatus.setCellFactory(ComboBoxTableCell.<ClientOBJ, StatusEnum>forTableColumn(StatusEnum.values()));
        columnStatus.setOnEditCommit(
                (CellEditEvent<ClientOBJ, StatusEnum> t) -> {
                    try {
                        ClientFactory.getModel().edit((ClientOBJ) t.getTableView().getSelectionModel().getSelectedItem());
                        ((ClientOBJ) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())).setStatus(t.getNewValue());
                        LOGGER.log(Level.INFO, "Client updated correctly");
                    } catch (BusinessLogicException ex) {
                        Alert alert = new Alert(AlertType.ERROR, ex.getMessage() + ". BackEnd error: try again later or contact your help desk.");
                        alert.show();
                        LOGGER.log(Level.SEVERE, ex.getMessage());
                        ((ClientOBJ) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())).setStatus(t.getOldValue());
                        tableClients.refresh();
                    }
                }
        );

        // FORMAT CELLS //
        columnPasswordChange.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<ClientOBJ, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ClientOBJ, String> item
            ) {
                SimpleStringProperty property = new SimpleStringProperty();
                property.setValue(formatDate((item.getValue()).getLastPasswordChange()).toString());
                return property;
            }
        }
        );

        // DELETE ITEMS //
        menuTable.getItems()
                .get(0).setOnAction(this::handleDeleteAction);

        // INSERT ITEMS // 
        buttonInsert.setOnAction(
                this::handleInsertAction);

        // HELP BUTTON //
        buttonHelp.setOnAction(
                this::handleHelpAction);

        // REPORT BUTTON //
        buttonReport.setOnAction(
                this::handleButtonReportAction);

        // LOG OUT BUTTON //
        buttonLogout.setOnAction(this::handleLogOutAction);

        // MENU BUTTONS //
        buttonPlates.setOnAction(this::handleButtonPlatesAction);

        buttonDiets.setOnAction(this::handleButtonDietsAction);

        buttonIngredients.setOnAction(this::handleButtonIngredientsAction);

        buttonTips.setOnAction(this::handleButtonTipsAction);

        buttonClients.setOnAction(this::handleButtonClientsAction);

        LOGGER.info(
                "ClientController window initialized");
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
     * This method creates a new ClientOBJ with default values and places it in
     * the table
     *
     * @param action an ActionEvent.ACTION event type for when the button is
     * pressed
     */
    private void handleInsertAction(ActionEvent action) {
        try {
            ClientOBJ client = new ClientOBJ();
            byte[] passwordBytes = new AsymmetricClient().cipher("abcd*1234");
            client.setPassword(HashMD5.hexadecimal(passwordBytes));
            ClientFactory.getModel().create(client);
            clientsData = FXCollections.observableArrayList(ClientFactory.getModel().findAll(new GenericType<List<ClientOBJ>>() {
            }));
            tableClients.setItems(clientsData);
            tableClients.refresh();
            LOGGER.log(Level.INFO, "Client created correctly");
        } catch (BusinessLogicException ex) {
            Alert alert = new Alert(AlertType.ERROR, ex.getMessage() + ". BackEnd error: try again later or contact your help desk.");
            alert.show();
            LOGGER.log(Level.SEVERE, ex.getMessage());
            tableClients.refresh();
        }
    }

    /**
     * This method deletes the user that has been selected in the table
     *
     * @param action an ActionEvent.ACTION event type for when the button is
     * pressed
     */
    private void handleDeleteAction(ActionEvent action) {
        Alert a = new Alert(AlertType.CONFIRMATION, "Are you sure you want to delete this item?");
        a.showAndWait();
        try {
            if (a.getResult().equals(ButtonType.CANCEL)) {
                action.consume();
            } else {
                ClientFactory.getModel().remove(((ClientOBJ) tableClients.getSelectionModel().getSelectedItem()).getUser_id());
                tableClients.getItems().remove(tableClients.getSelectionModel().getSelectedItem());
                tableClients.refresh();
                LOGGER.log(Level.INFO, "Client deleted correctly");
            }
        } catch (BusinessLogicException e) {
            String msg = "Error deleting the Client: " + e.getMessage() + ". BackEnd error: try again later or contact your help desk.";
            Alert alert = new Alert(AlertType.ERROR, msg);
            alert.show();
            LOGGER.log(Level.SEVERE, msg);
        }
    }

    /**
     * This method either finds clients based on the text in the search bar or
     * it finds all the clients if the search bar is empty
     *
     * @param action an ActionEvent.ACTION event type for when the button is
     * pressed
     */
    private void handleSearchAction(ActionEvent action) {
        LOGGER.info("Searhing for clients");
        try {
            if (!texfieldSearchbar.getText().isEmpty()) {
                clientsData = FXCollections.observableArrayList(ClientFactory.getModel().findClientBySearch(new GenericType<List<ClientOBJ>>() {
                }, texfieldSearchbar.getText()));
                tableClients.setItems(clientsData);
                tableClients.refresh();
            } else {
                clientsData = FXCollections.observableArrayList(ClientFactory.getModel().findAll(new GenericType<List<ClientOBJ>>() {
                }));
                tableClients.setItems(clientsData);
                tableClients.refresh();
            }
        } catch (BusinessLogicException ex) {
            Alert alert = new Alert(AlertType.ERROR, ex.getMessage() + ". BackEnd error: try again later or contact your help desk.");
            alert.show();
            LOGGER.log(Level.SEVERE, ex.getMessage());
            tableClients.refresh();
        }
    }

    /**
     * This method searches and shows all the clients that are enabled
     *
     * @param action an ActionEvent.ACTION event type for when the button is
     * pressed
     */
    private void handleFilterEnabled(ActionEvent action) {
        try {
            LOGGER.info("Searhing for enabled clients");
            clientsData = FXCollections.observableArrayList(ClientFactory.getModel().findClientByStatus(new GenericType<List<ClientOBJ>>() {
            }, StatusEnum.ENABLED.toString()));
            tableClients.setItems(clientsData);
            tableClients.refresh();
        } catch (BusinessLogicException ex) {
            Alert alert = new Alert(AlertType.ERROR, ex.getMessage() + ". BackEnd error: try again later or contact your help desk.");
            alert.show();
            LOGGER.log(Level.SEVERE, ex.getMessage());
            tableClients.refresh();
        }
    }

    /**
     * This method searches and shows all the clients that are disabled
     *
     * @param action an ActionEvent.ACTION event type for when the button is
     * pressed
     */
    private void handleFilterDisabled(ActionEvent action) {
        try {
            LOGGER.info("Searhing for disabled clients");
            clientsData = FXCollections.observableArrayList(ClientFactory.getModel().findClientByStatus(new GenericType<List<ClientOBJ>>() {
            }, StatusEnum.DISABLED.toString()));
            tableClients.setItems(clientsData);
            tableClients.refresh();
        } catch (BusinessLogicException ex) {
            Alert alert = new Alert(AlertType.ERROR, ex.getMessage() + ". BackEnd error: try again later or contact your help desk.");
            alert.show();
            LOGGER.log(Level.SEVERE, ex.getMessage());
            tableClients.refresh();
        }
    }

    private void handleFilterAll(ActionEvent action) {
        try {
            LOGGER.info("Searhing for all the clients");
            clientsData = FXCollections.observableArrayList(ClientFactory.getModel().findAll(new GenericType<List<ClientOBJ>>() {
            }));
            tableClients.setItems(clientsData);
            tableClients.refresh();
        } catch (BusinessLogicException ex) {
            Alert alert = new Alert(AlertType.ERROR, ex.getMessage() + ". BackEnd error: try again later or contact your help desk.");
            alert.show();
            LOGGER.log(Level.SEVERE, ex.getMessage());
            tableClients.refresh();
        }
    }

    /**
     * This method creates a report based on the table data
     *
     * @param event an ActionEvent.ACTION event type for when the button is
     * pressed
     */
    private void handleButtonReportAction(ActionEvent event) {
        try {
            InputStream input = getClass().getResourceAsStream("/ui/reports/ClientsReport.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(input);
            input.close();
            JRBeanCollectionDataSource dataItems = new JRBeanCollectionDataSource((Collection<ClientOBJ>) this.tableClients.getItems());
            Map<String, Object> parameters = new HashMap<>();
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataItems);
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setVisible(true);
        } catch (JRException ex) {
            String msg = "Error trying to open report:\n" + ex.getMessage();
            Alert alert = new Alert(AlertType.ERROR, msg);
            alert.show();
            LOGGER.log(Level.SEVERE, "DietsControlVController: Error opening report, {0}", ex.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(ClientControlWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method opens the help window
     *
     * @param event
     */
    private void handleHelpAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/views/ClientControlHelp.fxml"));
            Parent root = (Parent) loader.load();
            ClientControlHelp clientControlHelp = ((ClientControlHelp) loader.getController());
            //Initializes and shows help stage
            clientControlHelp.initAndShowStage(root);
        } catch (IOException ex) {
            Logger.getLogger(ClientControlWindow.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Method to format a Date
     *
     * @param dateToFormat The date to be formatted
     * @return The date converted to local date
     */
    public LocalDate formatDate(Date dateToFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(sdf.format(dateToFormat), formatter);
        return localDate;
    }

    /**
     * Email validation method
     *
     * @param email the email to be chacked
     * @return boolean depending if the pattern is correct
     */
    public static boolean validateEmail(String email) {
        Matcher matcher = PATTERN.matcher(email);
        return matcher.matches();
    }

    /**
     * Opens log in windwow and closes this one
     *
     * @param event The ActionEvent object for the event.
     */
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
            Alert alert = new Alert(AlertType.ERROR, ex.getMessage());
            alert.show();
            LOGGER.log(Level.SEVERE, "ClientControlWindow: Error trying to open Plate window, {0}", ex.getMessage());
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
            Alert alert = new Alert(AlertType.ERROR, ex.getMessage());
            alert.show();
            LOGGER.log(Level.SEVERE, "ClientControlWindow: Error trying to open Diets window, {0}", ex.getMessage());
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
            Alert alert = new Alert(AlertType.ERROR, ex.getMessage());
            alert.show();
            LOGGER.log(Level.SEVERE, "ClientControlWindow: Error trying to open Plates window, {0}", ex.getMessage());
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

            TipsControlVController controller = ((TipsControlVController) loader.getController());

            controller.setStage(stage);
            stage.close();
            controller.initStage(root);
        } catch (IOException | IllegalStateException ex) {
            //If theres is an error trying to change view, an alert will show.
            Alert alert = new Alert(AlertType.ERROR, ex.getMessage());
            alert.show();
            LOGGER.log(Level.SEVERE, "ClientControlWindow: Error trying to open Tips window, {0}", ex.getMessage());
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
            stage.close();
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ui/views/ClientAdminWindow.fxml"));
            Parent root = (Parent) loader.load();

            ClientControlWindow controller = ((ClientControlWindow) loader.getController());

            controller.setStage(stage);

            controller.initStage(root);
        } catch (IOException | IllegalStateException ex) {
            //If theres is an error trying to change view, an alert will show.
            Alert alert = new Alert(AlertType.ERROR, ex.getMessage());
            alert.show();
            LOGGER.log(Level.SEVERE, "ClientControlWindow: Error trying to open Clients window, {0}", ex.getMessage());
        }
    }
}
