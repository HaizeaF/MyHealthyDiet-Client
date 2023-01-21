/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers;

import exceptions.InvalidUserValueException;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

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

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initStage(Parent root) {
        Scene scene = new Scene(root);

        stage.setScene(scene);

        stage.setTitle("UserModify");
        stage.setResizable(false);

        // USERNAME TEXT FIELD //
        userTextField.setOnKeyTyped(this::textChanged);
        userTextField.setOnKeyReleased(this::usernameValid);

        // FULL NAME TEXT FIELD //
        fullNameTextField.setOnKeyTyped(this::textChanged);

        // EMAIL TEXT FIELD //
        emailTextField.setOnKeyTyped(this::textChanged);

        // HEIGHT TEXT FIELD //
        heightTextField.setOnKeyTyped(this::numberChanged);
        heightTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                if (!newValue.matches("\\d*")) {
                    heightTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

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

        // GENRE COMBO BOX //
        // GOAL COMBO BOX //
        // BUTTON CONFIRM //
        buttonConfirm.setDisable(true);
        buttonConfirm.setOnAction(this::confirmChanges);

        // BUTTON CANCEL //
        buttonCancel.setDisable(true);
        buttonCancel.setOnAction(this::cancelChanges);

        // BUTTON CHANGE PASSWORD //
        buttonChangePasswrd.setOnAction(this::changePassword);

        // MENU BUTTONS //
        stage.show();
        LOGGER.info("Modify window initialiced");
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
        if (((TextField) event.getSource()).getText().length() >= 3) {
            event.consume();
            ((TextField) event.getSource()).setText(((TextField) event.getSource()).getText().substring(0, 3));
        }
    }

    private void confirmChanges(ActionEvent event) {

    }

    private void cancelChanges(ActionEvent event) {

    }

    private void changePassword(ActionEvent event) {

    }
}
