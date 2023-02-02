/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers;

import businessLogic.ClientFactory;
import exceptions.BusinessLogicException;
import objects.GenreEnum;
import exceptions.InvalidUserValueException;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import objects.GoalEnum;
import objects.ClientOBJ;
import objects.PrivilegeEnum;
import objects.StatusEnum;

/**
 *
 * @author Sendoa
 */
public class UserModifyVController {
    
    private Stage stage;
    private static final Logger LOGGER = Logger.getLogger("UserModifyVController.class");
    @FXML
    private Button buttonOverview;
    @FXML
    private Button buttonPlates;
    @FXML
    private Button buttonDiets;
    @FXML
    private Button buttonConfirm;
    @FXML
    private Button buttonCancel;
    @FXML
    private Button buttonHelp;
    @FXML
    private Button buttonChangePasswrd;
    @FXML
    private MenuButton buttonUser;
    @FXML
    private TextField userTextField;
    @FXML
    private TextField fullNameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField heightTextField;
    @FXML
    private TextField ageTextField;
    @FXML
    private ComboBox genreComboBox;
    @FXML
    private ComboBox goalComboBox;
    @FXML
    private Label lblUsername;
    @FXML
    private Label lblFullName;
    @FXML
    private Label lblEmail;
    @FXML
    private Label lblHeight;
    @FXML
    private Label lblAge;
    @FXML
    private Label lblGenre;
    @FXML
    private Label lblGoal;
    @FXML
    private ImageView userImg;
    @FXML
    private ImageView nameImg;
    @FXML
    private ImageView emailImg;
    @FXML
    private ImageView heightImg;
    @FXML
    private ImageView ageImg;
    @FXML
    private MenuItem menuItemModify;
    @FXML
    private MenuItem menuItemLogOut;
    
    private ClientOBJ client;
    
    public Stage getStage() {
        return stage;
    }
    
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    public void setClient(ClientOBJ client){
        this.client = client;
    }
    
    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        
        stage.setTitle("UserModify");
        stage.setResizable(false);

        // USERNAME TEXT FIELD //
        userTextField.setOnKeyTyped(this::textChanged);
        userTextField.setOnKeyReleased(this::usernameValid);
        userTextField.setText(client.getLogin());

        // FULL NAME TEXT FIELD //
        fullNameTextField.setOnKeyTyped(this::textChanged);
        fullNameTextField.setText(client.getFullName());

        // EMAIL TEXT FIELD //
        emailTextField.setOnKeyTyped(this::textChanged);
        emailTextField.setText(client.getEmail());

        // HEIGHT TEXT FIELD //
        heightTextField.setOnKeyTyped(this::numberChanged);
        heightTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                if (!newValue.matches("\\d*(\\.\\d*)?")) {
                    heightTextField.setText(oldValue);
               }
            }
        });
        heightTextField.setText(String.valueOf(client.getHeight()));

        // AGE TEXT FIELD //
        ageTextField.setOnKeyTyped(this::numberChanged);
        ageTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                if (!newValue.matches("\\d*")) {
                    ageTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        ageTextField.setText(String.valueOf(client.getAge()));

        // GENRE COMBO BOX //
        genreComboBox.setItems(FXCollections.observableArrayList(GenreEnum.values()));
        genreComboBox.setOnAction(this::handleComboBox);
        genreComboBox.setValue(client.getGenre());

        // GOAL COMBO BOX //
        goalComboBox.setItems(FXCollections.observableArrayList(GoalEnum.values()));
        goalComboBox.setOnAction(this::handleComboBox);
        goalComboBox.setValue(client.getGoal());

        // BUTTON CONFIRM //
        buttonConfirm.setDisable(true);
        buttonConfirm.setOnAction(this::confirmChanges);

        // BUTTON CANCEL //
        buttonCancel.setDisable(true);
        buttonCancel.setOnAction(this::cancelChanges);

        // BUTTON CHANGE PASSWORD //
        buttonChangePasswrd.setOnAction(this::changePassword);

        // MENU BUTTONS //
        stage.setOnCloseRequest(this::handleExitAction);
        menuItemLogOut.setOnAction(this::handleLogOutAction);
        menuItemModify.setOnAction(this::handleModifyAction);
        
        stage.show();
        
        LOGGER.info("Modify window initialiced");
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

    /**
     * It checks that the text entered is less than 25 characters. If it reaches
     * the maximum allowed, it does not allow more characters to be entered and
     * subtracts and displays the first 25 characters.
     *
     * @param event an ActionEvent.ACTION event type for when the button is
     * pressed
     */
    private void textChanged(KeyEvent event) {
        buttonConfirm.setDisable(false);
        buttonCancel.setDisable(false);
        if (!((TextField) event.getSource()).equals(emailTextField)) {
            if (((TextField) event.getSource()).getText().length() >= 25) {
                event.consume();
                ((TextField) event.getSource()).setText(((TextField) event.getSource()).getText().substring(0, 25));
            }
        } else {
            if (((TextField) event.getSource()).getText().length() >= 50) {
                event.consume();
                ((TextField) event.getSource()).setText(((TextField) event.getSource()).getText().substring(0, 50));
            }
        }
    }
    
    private void usernameValid(KeyEvent event) {
        try {
            if (userTextField.getText().isEmpty()) {
                throw new InvalidUserValueException("Enter a username");
            }
            // Si el campo no está vacío comprobar que la contraseña tiene al menos 8 caracteres y que no hay espacios.
            // En caso de que no tenga 8 caracteres o contenga espacios en blanco cambiar el color de userImg y userTextField a rojo.
            if (userTextField.getText().contains(" ")) {
                throw new InvalidUserValueException("Username can't contain an empty space");
            }
            userImg.setImage(new Image(getClass().getResourceAsStream("../resources/ic_user.png")));
            userTextField.setStyle("-fx-border-color: #ABB2B9;");
            lblUsername.setText("");
        } catch (InvalidUserValueException e) {
            userImg.setImage(new Image(getClass().getResourceAsStream("../resources/ic_user_incorrect.png")));
            userTextField.setStyle("-fx-border-color: red;");
            lblUsername.setText(e.getMessage());
        }
    }
    
    private void numberChanged(KeyEvent event) {
        buttonConfirm.setDisable(false);
        buttonCancel.setDisable(false);
        if (((TextField) event.getSource()).equals(heightTextField)) {
            if (heightTextField.getText().length() >= 4) {
                event.consume();
                ((TextField) event.getSource()).setText(((TextField) event.getSource()).getText().substring(0, 4));
            }
        } else {
            if (ageTextField.getText().length() >= 2) {
                event.consume();
                ((TextField) event.getSource()).setText(((TextField) event.getSource()).getText().substring(0, 2));
            }
        }
    }
    
    private void confirmChanges(ActionEvent event) {
        try {
            ClientOBJ cliente = new ClientOBJ(this.client.getUser_id(), userTextField.getText(), emailTextField.getText(), fullNameTextField.getText(), StatusEnum.ENABLED, PrivilegeEnum.USER, null, new Date(System.currentTimeMillis()), ageTextField.getText(), Float.parseFloat(heightTextField.getText()), (GenreEnum) genreComboBox.getSelectionModel().getSelectedItem(), (GoalEnum) goalComboBox.getSelectionModel().getSelectedItem());
            ClientFactory.getModel().edit(cliente);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Data updated succesfully");
            alert.show();
            LOGGER.info("Client updated");
            buttonCancel.setDisable(true);
            buttonConfirm.setDisable(true);
        } catch (BusinessLogicException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage());
            alert.show();
            LOGGER.log(Level.SEVERE, ex.getMessage());
        }
    }
    
    private void cancelChanges(ActionEvent event) {
        userTextField.setText(client.getLogin());
        fullNameTextField.setText(client.getFullName());
        emailTextField.setText(client.getEmail());
        heightTextField.setText(String.valueOf(client.getHeight()));
        ageTextField.setText(String.valueOf(client.getAge()));
        genreComboBox.setValue(client.getGenre());
        goalComboBox.setValue(client.getGoal());
        buttonConfirm.setDisable(true);
        buttonCancel.setDisable(true);
    }
    
    private void changePassword(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ui/views/PasswordChange.fxml"));
            Parent root = (Parent) loader.load();
            PasswordChangeVController controller = ((PasswordChangeVController) loader.getController());
            controller.setStage(new Stage());
            controller.setClient(client);
            controller.initStage(root);
        } catch (IOException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage());
            alert.show();
            LOGGER.info(ex.getMessage());
        }
    }
    
    private void handleComboBox(Event event){
        buttonConfirm.setDisable(false);
        buttonCancel.setDisable(false);
    }
    
    public void handleModifyAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ui/views/UserModify.fxml"));
            Parent root = (Parent) loader.load();
            
            UserModifyVController controller = ((UserModifyVController) loader.getController());
            
            controller.setStage(stage);
            controller.setClient(client);
            controller.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(HomeViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Opens log in windwow and closes this one
     *
     * @param event The ActionEvent object for the event.
     */
    public void handleLogOutAction(ActionEvent event) {
        try {
            Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit? This will close the app.");
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
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage());
            alert.show();
            LOGGER.log(Level.SEVERE, ex.getMessage());
        }
    }
}
