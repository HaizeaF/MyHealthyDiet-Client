/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers;

import businessLogic.ClientFactory;
import businessLogic.UserFactory;
import businessLogic.UserInterface;
import cryptography.Asymmetric;
import cryptography.HashMD5;
import static cryptography.HashMD5.hashText;
import exceptions.BusinessLogicException;
import exceptions.InvalidPasswordValueException;
import java.security.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.InternalServerErrorException;
import objects.ClientOBJ;
import objects.User;

/**
 *
 * @author Sendoa
 */
public class PasswordChangeVController {

    private Stage stage;
    private static final Logger LOGGER = Logger.getLogger("PasswordChangeVController.class");

    @FXML
    private PasswordField newPasswrdField;
    @FXML
    private PasswordField confNewPasswdField;
    @FXML
    private PasswordField passwrdField;
    @FXML
    private Label lblPasswrd;
    @FXML
    private Label lblNewPasswrd;
    @FXML
    private Label lblConfNewPasswrd;
    @FXML
    private ImageView imgNewPasswrd;
    @FXML
    private ImageView imgConfPassword;
    @FXML
    private ImageView imgPassword;
    @FXML
    private Button buttonConfirm;
    @FXML
    private Button buttonCancel;

    private ClientOBJ client;

    public void setClient(ClientOBJ client) {
        this.client = client;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initStage(Parent root) {
        Scene scene = new Scene(root);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setTitle("Password Change");
        stage.setResizable(false);

        stage.setOnCloseRequest(this::handleExitAction);

        // PASSWORD FIELDS EVENTS //
        passwrdField.setOnKeyTyped(this::handleKeyPasswdLength);
        confNewPasswdField.setOnKeyTyped(this::handleKeyPasswdLength);
        newPasswrdField.setOnKeyTyped(this::handleKeyPasswdLength);
        passwrdField.setOnKeyReleased(this::handleKeyPassword);
        confNewPasswdField.setOnKeyReleased(this::handleKeyPassword);
        newPasswrdField.setOnKeyReleased(this::handleKeyPassword);

        // BUTTON EVENTS //
        buttonCancel.setOnAction(this::handleCancel);
        buttonConfirm.setOnAction(this::handleConfirm);

        stage.show();
    }

    private void handleExitAction(WindowEvent event) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit? This will close the app.");
        a.showAndWait();
        try {
            if (a.getResult().equals(ButtonType.CANCEL)) {
                event.consume();
            } else {
                stage.close();
            }
        } catch (Exception e) {
            String msg = "Error closing the app: " + e.getMessage();
            Alert alert = new Alert(Alert.AlertType.ERROR, msg);
            alert.show();
            LOGGER.log(Level.SEVERE, msg);
        }
    }

    private void handleKeyPasswdLength(KeyEvent event) {
        if (((PasswordField) event.getSource()).getText().length() >= 25) {
            event.consume();
            ((PasswordField) event.getSource()).setText(((PasswordField) event.getSource()).getText().substring(0, 25));
        }
    }

    private void handleKeyPassword(KeyEvent event) {
        try {
            if (event == null) {
                if (passwrdField.getText().isEmpty() || newPasswrdField.getText().isEmpty() || confNewPasswdField.getText().isEmpty()) {
                    throw new InvalidPasswordValueException("Enter a password");
                }
            } else {
                if (((PasswordField) event.getSource()).getText().isEmpty()) {
                    throw new InvalidPasswordValueException("Enter a password");
                }
            }

            // Si el campo no está vacío comprobar que la contraseña tiene al menos 8 caracteres y que no hay espacios.
            // En caso de que no tenga 8 caracteres o contenga espacios en blanco cambiar el color de la imagen y el textfield a rojo.
            if (event == null) {
                if (passwrdField.getText().contains(" ") || newPasswrdField.getText().contains(" ") || confNewPasswdField.getText().contains(" ") || passwrdField.getText().length() < 8 || newPasswrdField.getText().length() < 8 || confNewPasswdField.getText().length() < 8) {
                    throw new InvalidPasswordValueException("Password must be at least 8 characters long \nand must not contain blank spaces");
                }
            } else {
                if (((PasswordField) event.getSource()).getText().contains(" ") || ((PasswordField) event.getSource()).getText().length() < 8) {
                    throw new InvalidPasswordValueException("Password must be at least 8 characters long \nand must not contain blank spaces");
                }
                ((PasswordField) event.getSource()).setStyle("-fx-border-color: #ABB2B9;");
                if (((PasswordField) event.getSource()).equals(passwrdField)) {
                    lblPasswrd.setText("");
                    imgPassword.setImage(new Image(getClass().getResourceAsStream("/ui/resources/icon_password.png")));
                } else if (((PasswordField) event.getSource()).equals(newPasswrdField)) {
                    lblNewPasswrd.setText("");
                    imgNewPasswrd.setImage(new Image(getClass().getResourceAsStream("/ui/resources/icon_password.png")));
                } else {
                    lblConfNewPasswrd.setText("");
                    imgConfPassword.setImage(new Image(getClass().getResourceAsStream("/ui/resources/icon_password.png")));
                }
            }
        } catch (InvalidPasswordValueException ex) {
            if (event != null) {
                ((PasswordField) event.getSource()).setStyle("-fx-border-color: red;");
                if (((PasswordField) event.getSource()).equals(passwrdField)) {
                    lblPasswrd.setText(ex.getMessage());
                    imgPassword.setImage(new Image(getClass().getResourceAsStream("/ui/resources/icon_password_incorrect.png")));
                } else if (((PasswordField) event.getSource()).equals(newPasswrdField)) {
                    lblNewPasswrd.setText(ex.getMessage());
                    imgNewPasswrd.setImage(new Image(getClass().getResourceAsStream("/ui/resources/icon_password_incorrect.png")));
                } else {
                    lblConfNewPasswrd.setText(ex.getMessage());
                    imgConfPassword.setImage(new Image(getClass().getResourceAsStream("/ui/resources/icon_password_incorrect.png")));
                }
            } else {
                passwrdField.setStyle("-fx-border-color: red;");
                lblPasswrd.setText(ex.getMessage());
                imgPassword.setImage(new Image(getClass().getResourceAsStream("/ui/resources/icon_password_incorrect.png")));
                newPasswrdField.setStyle("-fx-border-color: red;");
                lblNewPasswrd.setText(ex.getMessage());
                imgNewPasswrd.setImage(new Image(getClass().getResourceAsStream("/ui/resources/icon_password_incorrect.png")));
                confNewPasswdField.setStyle("-fx-border-color: red;");
                lblConfNewPasswrd.setText(ex.getMessage());
                imgConfPassword.setImage(new Image(getClass().getResourceAsStream("/ui/resources/icon_password_incorrect.png")));
            }

            LOGGER.info(ex.getMessage());
        }

    }

    private void handleConfirm(ActionEvent event) {
        handleKeyPassword(null);
        try {
            byte[] passwordBytes = new Asymmetric().cipher(passwrdField.getText());
            ClientOBJ client = UserFactory.getModel().logIn(ClientOBJ.class, this.client.getLogin(), HashMD5.hexadecimal(passwordBytes));
            if (!newPasswrdField.getText().equals(confNewPasswdField.getText())) {
                lblConfNewPasswrd.setText("Password doesnt match");
                confNewPasswdField.setStyle("-fx-border-color: red;");
                confNewPasswdField.setText("");
                imgConfPassword.setImage(new Image(getClass().getResourceAsStream("/ui/resources/icon_password_incorrect.png")));
            }
            if (lblPasswrd.getText().isEmpty() && lblNewPasswrd.getText().isEmpty() && lblConfNewPasswrd.getText().isEmpty()) {
                byte[] newPasswordBytes = new Asymmetric().cipher(newPasswrdField.getText());
                client.setPassword(HashMD5.hexadecimal(newPasswordBytes));
                client.setLastPasswordChange(new Date(System.currentTimeMillis()));
                ClientFactory.getModel().editPassword(client);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Password changed succesfully", ButtonType.OK);
                alert.showAndWait();
                stage.close();
            }
        } catch (BusinessLogicException ex) {
            lblPasswrd.setText("Password doesnt match");
            passwrdField.setStyle("-fx-border-color: red;");
            imgPassword.setImage(new Image(getClass().getResourceAsStream("/ui/resources/icon_password_incorrect.png")));
        }
    }

    private void handleCancel(ActionEvent event) {
        stage.close();
    }
}
