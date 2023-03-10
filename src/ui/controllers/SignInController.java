/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers;

import businessLogic.ClientFactory;
import businessLogic.UserFactory;
import businessLogic.UserInterface;
import exceptions.InvalidPasswordValueException;
import exceptions.InvalidUserValueException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import objects.User;
import files.AsymmetricClient;
import cryptography.HashMD5;
import exceptions.BusinessLogicException;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javax.ws.rs.core.GenericType;
import objects.ClientOBJ;

/**
 *
 * @author Sendoa
 */
public class SignInController {

    private Stage stage;
    private static final Logger LOGGER = Logger.getLogger("SignInVController.class");
    @FXML
    private TextField textFieldUsername;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField textFieldPassword;
    @FXML
    private Button buttonSignIn;
    @FXML
    private Button buttonSignUp;
    @FXML
    private Button buttonForgotPassword;
    @FXML
    private ToggleButton buttonShowHide;
    @FXML
    private ImageView imageViewButton;
    @FXML
    private Label labelInvalidUser;
    @FXML
    private Label labelInvalidPassword;
    @FXML
    private ImageView userIcon;
    @FXML
    private ImageView passwordIcon;

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

        // USERNAME TEXT FIELD //
        // Comprobar si el texto cambia
        textFieldUsername.setOnKeyTyped(this::textChanged);
        textFieldUsername.setOnKeyReleased(this::textErrorHandlerUsername);

        // PASSWORD FIELD //
        // Comprobar si el texto cambia
        passwordField.setOnKeyReleased(this::handleKeyReleasedPasswd);
        passwordField.setOnKeyTyped(this::textChanged);

        // PASSWORD TEXT FIELD //
        // Comprobar si el texto cambia
        textFieldPassword.setOnKeyTyped(this::textChanged);
        textFieldPassword.setOnKeyReleased(this::handleKeyReleasedPasswd);

        // BUTTONS //
        // Comprueba si los botones son pulsados
        buttonForgotPassword.setOnAction(this::forgotPassword);
        buttonShowHide.setOnAction(this::handleShowHide);
        buttonSignUp.setOnAction(this::handleSignUp);
        buttonSignIn.setOnAction(this::handleSignIn);
        // Comprueba cuando el boton "x" para cerrar la ventana es pulsado
        stage.setOnCloseRequest(this::handleExitAction);

        stage.show();
        LOGGER.info("SingIn window initialized");
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
        if (((TextField) event.getSource()).getText().length() >= 25) {
            event.consume();
            ((TextField) event.getSource()).setText(((TextField) event.getSource()).getText().substring(0, 25));
        }
    }

    private void forgotPassword(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Password Recovery");
        alert.setHeaderText("Insert your email:");

        GridPane grid = new GridPane();

        TextField textFieldEmail = new TextField();
        textFieldEmail.setPromptText("example@mail.com");

        grid.add(textFieldEmail, 0, 0);

        alert.getDialogPane().setContent(grid);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    ClientOBJ client = ClientFactory.getModel().findClientByEmail(new GenericType<ClientOBJ>() {
                    }, textFieldEmail.getText());
                    LOGGER.log(Level.INFO, "Client found");
                    ClientFactory.getModel().recoverPassword(client);
                } catch (BusinessLogicException ex) {
                    Alert alerta = new Alert(Alert.AlertType.ERROR, ex.getMessage());
                    alerta.show();
                    LOGGER.log(Level.SEVERE, ex.getMessage());
                }
            }
        });
    }

    /**
     * Open the Sign Up window and close the Sign in window.
     *
     * @param event an ActionEvent.ACTION event type for when the button is
     * pressed
     */
    private void handleSignUp(ActionEvent event) {
        try {
            stage.close();
            LOGGER.info("SignIn window closed");
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ui/views/SignUpView.fxml"));
            Parent root = (Parent) loader.load();

            SignUpController controller = ((SignUpController) loader.getController());

            controller.setStage(new Stage());

            controller.initStage(root);
            LOGGER.info("SignUp window opened");
        } catch (IOException ex) {
            Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Login method
     *
     * @param event an ActionEvent.ACTION event type for when the button is
     * pressed
     */
    private void handleSignIn(ActionEvent event) {
        LOGGER.info("Signin in user");
        buttonSignIn.requestFocus();
        // Comprueba que los campos est??n informados y que el usuario y la contrase??a son v??lidos 
        // (cumplen los requisitos especificados en sus propios eventos)
        // Si los datos se validan correctame/ (cumplen los requisitos especificados en sus propios eventos)
        // Si los datos se validan correctamente, se ejecuta el m??todo doSignIn().
        textErrorHandlerUsername(null);
        handleKeyReleasedPasswd(null);
        try {
            if (labelInvalidPassword.getText().equalsIgnoreCase("") && labelInvalidUser.getText().equalsIgnoreCase("")) {
                if (textFieldUsername.getText().equals("profe") && textFieldPassword.getText().equals("abcd*1234")){
                    stage.close();
                    LOGGER.info("Usuario encontrado");
                    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ui/views/ClientAdminWindow.fxml"));
                    Parent root = (Parent) loader.load();

                    ClientControlWindow controller = ((ClientControlWindow) loader.getController());

                    controller.setStage(stage);

                    controller.initStage(root);
                } else if (textFieldUsername.getText().equals("cliente") && textFieldPassword.getText().equals("abcd*1234")) {
                    ClientOBJ cliente = new ClientOBJ();
                    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ui/views/HomeView.fxml"));
                    Parent root = (Parent) loader.load();

                    HomeViewController controller = ((HomeViewController) loader.getController());

                    controller.setStage(stage);
                    controller.setClient(cliente);
                    controller.initStage(root);
                }
                UserInterface model = UserFactory.getModel();
                byte[] passwordBytes = new AsymmetricClient().cipher(textFieldPassword.getText());
                User user = model.logIn(ClientOBJ.class, textFieldUsername.getText(), HashMD5.hexadecimal(passwordBytes));
                if (user instanceof ClientOBJ) {
                    ClientOBJ client = (ClientOBJ) user;
                    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ui/views/HomeView.fxml"));
                    Parent root = (Parent) loader.load();

                    HomeViewController controller = ((HomeViewController) loader.getController());

                    controller.setStage(stage);
                    controller.setClient(client);
                    controller.initStage(root);
                    LOGGER.info("Cliente encontrado");
                } else {
                    stage.close();
                    LOGGER.info("Usuario encontrado");
                    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ui/views/ClientAdminWindow.fxml"));
                    Parent root = (Parent) loader.load();

                    ClientControlWindow controller = ((ClientControlWindow) loader.getController());

                    controller.setStage(stage);

                    controller.initStage(root);
                }
                
            }
        } catch (BusinessLogicException e) {
            
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.show();
            LOGGER.severe(e.getMessage());
        } catch (IOException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Window not found");
            alert.show();
            LOGGER.severe("Window not found");
        }
    }

    /**
     * Check what state (pressed/not pressed) the password is in.
     *
     * @param event an ActionEvent.ACTION event type for when the button is
     * pressed
     */
    private void handleShowHide(ActionEvent event) {
        if (buttonShowHide.isSelected()) {
            // Si est?? presionado se muestra un textField y a imagen de imageShowHide es hideIcon.
            imageViewButton.setImage(new Image(getClass().getResourceAsStream("/ui/resources/iconEye2.png")));
            passwordField.setVisible(false);
            textFieldPassword.setVisible(true);
        } else {
            // Si no est?? presionado se muestra un passwordField y la imagen de imageShowHide es showIcon.
            imageViewButton.setImage(new Image(getClass().getResourceAsStream("/ui/resources/iconEye.png")));
            passwordField.setVisible(true);
            textFieldPassword.setVisible(false);
        }
    }

    /**
     * Copy text from one field to another
     *
     * @param event an ActionEvent.ACTION event type for when the button is
     * pressed
     */
    private void handleKeyReleasedPasswd(KeyEvent event) {
        if (passwordField.isVisible()) {
            // Cuando se escribe un car??cter en el passwordField se copia en el textFieldPassword.
            textFieldPassword.setText(passwordField.getText());
        } else if (textFieldPassword.isVisible()) {
            // Cuando se escribe un car??cter en el textFieldPassword se copia en el passwordField.
            passwordField.setText(textFieldPassword.getText());
        }
        try {
            if (passwordField.getText().isEmpty() || textFieldPassword.getText().isEmpty()) {
                throw new InvalidPasswordValueException("Enter a password");
            }
            // Si el campo no est?? vac??o comprobar que la contrase??a tiene al menos 8 caracteres y que no hay espacios.
            // En caso de que no tenga 8 caracteres o contenga espacios en blanco cambiar el color de imagePassword y linePassword a rojo.
            if (passwordField.getText().contains(" ") || textFieldPassword.getText().contains(" ") || passwordField.getText().length() < 8 || textFieldPassword.getText().length() < 8) {
                throw new InvalidPasswordValueException("Password must be at least 8 characters long and must not contain blank spaces");
            }
            passwordIcon.setImage(new Image(getClass().getResourceAsStream("/ui/resources/icon_password.png")));
            passwordField.setStyle("-fx-border-color: #ABB2B9;");
            textFieldPassword.setStyle("-fx-border-color: #ABB2B9;");
            labelInvalidPassword.setText("");
        } catch (InvalidPasswordValueException ex) {
            passwordIcon.setImage(new Image(getClass().getResourceAsStream("/ui/resources/icon_password_incorrect.png")));
            passwordField.setStyle("-fx-border-color: red;");
            textFieldPassword.setStyle("-fx-border-color: red;");
            LOGGER.info(ex.getMessage());
            labelInvalidPassword.setText(ex.getMessage());
        }
    }

    private void textErrorHandlerUsername(KeyEvent event) {
        try {
            if (textFieldUsername.getText().isEmpty()) {
                throw new InvalidUserValueException("Enter a username");
            }
            // Si el campo no est?? vac??o comprobar que la contrase??a tiene al menos 8 caracteres y que no hay espacios.
            // En caso de que no tenga 8 caracteres o contenga espacios en blanco cambiar el color de imagePassword y linePassword a rojo.
            if (textFieldUsername.getText().contains(" ")) {
                throw new InvalidUserValueException("Username can't contain an empty space");
            }
            userIcon.setImage(new Image(getClass().getResourceAsStream("/ui/resources/ic_user.png")));
            textFieldUsername.setStyle("-fx-border-color: #ABB2B9;");
            labelInvalidUser.setText("");
        } catch (InvalidUserValueException ex) {
            userIcon.setImage(new Image(getClass().getResourceAsStream("/ui/resources/ic_user_incorrect.png")));
            textFieldUsername.setStyle("-fx-border-color: red;");
            LOGGER.info(ex.getMessage());
            labelInvalidUser.setText(ex.getMessage());
        }
    }

    /**
     * Check the change of focus
     *
     * @param observable Actual value
     * @param oldValue Old value
     * @param newValue New Value
     */
    private void focusedPropertyChanged(ObservableValue observable, Boolean oldValue, Boolean newValue) {
        if (oldValue) {
            if (!textFieldUsername.isFocused()) {
                try {
                    if (textFieldUsername.getText().isEmpty()) {
                        throw new InvalidUserValueException("Enter a username");
                    }
                    // Si el campo no est?? vac??o comprobar que la contrase??a tiene al menos 8 caracteres y que no hay espacios.
                    // En caso de que no tenga 8 caracteres o contenga espacios en blanco cambiar el color de imagePassword y linePassword a rojo.
                    if (textFieldUsername.getText().contains(" ")) {
                        throw new InvalidUserValueException("Username can't contain an empty space");
                    }
                    userIcon.setImage(new Image(getClass().getResourceAsStream("/ui/resources/ic_user.png")));
                    textFieldUsername.setStyle("-fx-border-color: #ABB2B9;");
                    labelInvalidUser.setText("");
                } catch (InvalidUserValueException ex) {
                    userIcon.setImage(new Image(getClass().getResourceAsStream("/ui/resources/ic_user_incorrect.png")));
                    textFieldUsername.setStyle("-fx-border-color: red;");
                    LOGGER.info(ex.getMessage());
                    labelInvalidUser.setText(ex.getMessage());
                }
            }
            if (!passwordField.isFocused() && !textFieldPassword.isFocused()) {
                try {
                    if (passwordField.getText().isEmpty() || textFieldPassword.getText().isEmpty()) {
                        throw new InvalidPasswordValueException("Enter a password");
                    }
                    // Si el campo no est?? vac??o comprobar que la contrase??a tiene al menos 8 caracteres y que no hay espacios.
                    // En caso de que no tenga 8 caracteres o contenga espacios en blanco cambiar el color de imagePassword y linePassword a rojo.
                    if (passwordField.getText().contains(" ") || textFieldPassword.getText().contains(" ") || passwordField.getText().length() < 8 || textFieldPassword.getText().length() < 8) {
                        throw new InvalidPasswordValueException("Password must be at least 8 characters long and must not contain blank spaces");
                    }
                    passwordIcon.setImage(new Image(getClass().getResourceAsStream("/ui/resources/icon_password.png")));
                    passwordField.setStyle("-fx-border-color: #ABB2B9;");
                    textFieldPassword.setStyle("-fx-border-color: #ABB2B9;");
                    labelInvalidPassword.setText("");
                } catch (InvalidPasswordValueException ex) {
                    passwordIcon.setImage(new Image(getClass().getResourceAsStream("/ui/resources/icon_password_incorrect.png")));
                    passwordField.setStyle("-fx-border-color: red;");
                    textFieldPassword.setStyle("-fx-border-color: red;");
                    LOGGER.info(ex.getMessage());
                    labelInvalidPassword.setText(ex.getMessage());
                }
            }
        }
    }
}
