/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers;

import businessLogic.ClientFactory;
import cryptography.Asymmetric;
import cryptography.HashMD5;
import exceptions.BusinessLogicException;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import objects.ClientOBJ;
import objects.GenreEnum;
import objects.GoalEnum;
import objects.PrivilegeEnum;
import objects.StatusEnum;

/**
 *
 * @author Sendoa
 */
public class SignUpController {
    
    private Stage stage;
    private static final Logger LOGGER = Logger.getLogger("ClientControlWindow.class");
    @FXML
    private TextField textFieldUsername;
    @FXML
    private TextField textFieldPassword;
    @FXML
    private TextField textFieldName;
    @FXML
    private TextField textFieldConfirmPassword;
    @FXML
    private TextField textFieldEmail;
    @FXML
    private Button buttonSignUp;
    @FXML
    private Button buttonSignIn;
    @FXML
    private Label labelInvalidUser;
    @FXML
    private Label labelInvalidPassword;
    @FXML
    private Label labelInvalidConfirmPassword;
    @FXML
    private Label labelInvalidEmail;
    @FXML
    private Label labelInvalidName;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField passwordFieldConfirm;
    
    
    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    public void initStage(Parent root){
        Scene scene = new Scene(root);
        //Associate scene to primaryStage(Window
        stage.setScene(scene);
        //Set window properties
        stage.setTitle("SignUp");
        stage.setResizable(false);
        //Add Listeners
        //Writing, key typed
        textFieldUsername.setOnKeyTyped(this::textChanged);
        textFieldPassword.setOnKeyTyped(this::textChanged);
        passwordField.setOnKeyTyped(this::textChanged);
        //passwordField.setOnKeyReleased(this::textChangedPressed);
        //textFieldPassword.setOnKeyReleased(this::textChangedPressed);
        //passwordFieldConfirm.setOnKeyReleased(this::textChangedPressed);
        //textFieldConfirmPassword.setOnKeyReleased(this::textChangedPressed);
        textFieldEmail.setOnKeyTyped(this::textChangedEmail);
        textFieldName.setOnKeyTyped(this::textChangedName);
        //
        //Focus lost
        /*textFieldPassword.focusedProperty().addListener(this::focusedPropertyChangedPassword);
        passwordField.focusedProperty().addListener(this::focusedPropertyChangedPassword);
        textFieldEmail.focusedProperty().addListener(this::focusedPropertyChangedEmail);
        textFieldUsername.focusedProperty().addListener(this::focusedPropertyChanged);
        textFieldConfirmPassword.focusedProperty().addListener(this::focusedPropertyChangedPasswordConfirm);
        passwordFieldConfirm.focusedProperty().addListener(this::focusedPropertyChangedPasswordConfirm);*/

        /*textFieldPassword.focusedProperty().addListener(this::focusedPropertyChangedPassword);
        passwordField.focusedProperty().addListener(this::focusedPropertyChangedPassword);
        textFieldEmail.focusedProperty().addListener(this::focusedPropertyChangedEmail);
        textFieldUsername.focusedProperty().addListener(this::focusedPropertyChanged);
        textFieldConfirmPassword.focusedProperty().addListener(this::focusedPropertyChangedPasswordConfirm);
        passwordFieldConfirm.focusedProperty().addListener(this::focusedPropertyChangedPasswordConfirm);*/
        //
        //Button Actions
        buttonSignIn.setOnAction(this::signIn);
        //buttonShowHide1.setOnAction(this::showHide);
        //buttonShowHide2.setOnAction(this::showHideConfirm);
        buttonSignUp.setOnAction(this::signUp);
        //
        // CLOSE //
        stage.setOnCloseRequest(this::handleExitAction);
        //
        //Show primary window
        stage.show();
    }
    
    // Comprueba que los campos están informados y que el usuario, el email, el nombre completo y la contraseña son válidos (cumplen los requisitos especificados en sus propios eventos).
    // En caso de que un campo (o varios) esté vacío cambiar el color de su icono (imageUser, imageEmail, etc.) y la línea inferior (lineUser, lineEmail, etc.) a rojo. Cambiar los mensajes de campo invalido (labelInvalidUser, labelInvalidEmail, etc.) a “Enter [campo]”
    // (Cambiar campo por el dato a introducir en cuestión. 
    // Ej.: Enter an email)
    // Si los datos se validan correctamente, se ejecuta el método doSignUp() enviándole un user con los datos introducidos, y devuelve una excepción en caso de error o una respuesta OK si todo va bien.
    // Si no devuelve ninguna excepción abre la ventana SignIn y cierra la actual.
    // Si devuelve una excepción se muestra una ventana emergente que muestra el error.
    /**
     * Event that allows to control that the maximum number of carters in the
     * textfield is 25.
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

    /**
     * Event that allows to control that the maximum number of email addresses
     * is 35.
     *
     * @param event
     */
    private void textChangedEmail(KeyEvent event) {
        if (((TextField) event.getSource()).getText().length() >= 35) {
            event.consume();
            ((TextField) event.getSource()).setText(((TextField) event.getSource()).getText().substring(0, 35));
        }
    }

    /**
     * Event that allows to control that the maximum number of characters in the
     * name is 50.
     *
     * @param event an ActionEvent.ACTION event type for when the button is
     * pressed
     */
    private void textChangedName(KeyEvent event) {
        if (((TextField) event.getSource()).getText().length() >= 50) {
            event.consume();
            ((TextField) event.getSource()).setText(((TextField) event.getSource()).getText().substring(0, 50));
        }
    }

    /**
     * This method checks whether the username is valid or not.
     * @return boolean If it is valid returns true if not false.
     */
    /*private boolean usernameIsValid() {
        try {
            if (textFieldUsername.getText().contains(" ") || textFieldUsername.getText().isEmpty()) {
                throw new InvalidUserValueException("Username can't be empty nor contain an empty space.");
            }
            imageViewUsername.setImage(new Image(getClass().getResourceAsStream("/resources/iconUser.png")));
            lineUsername.setStroke(Color.GREY);
            labelInvalidUser.setText("");
            return true;
        } catch (InvalidUserValueException e) {
            imageViewUsername.setImage(new Image(getClass().getResourceAsStream("/resources/iconUserInconrrect.png")));
            lineUsername.setStroke(Color.RED);
            labelInvalidUser.setText(e.getMessage());
            return false;
        }
    }*/

    /**
     * This method checks whether the email is valid or not.
     * @return boolean If it is valid returns true if not false.
     */
    /*private boolean emailIsValid() {
        boolean matchOrNot = false;
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(textFieldEmail.getText());
        if (matcher.find()) {
            matchOrNot = true;
        }
        try {
            if (!matchOrNot) {
                throw new InvalidEmailValueException("Invalid format of email (*@*.*)");
            } else {
                imageViewEmail.setImage(new Image(getClass().getResourceAsStream("/resources/iconEmail.png")));
                lineEmail.setStroke(Color.GREY);
                labelInvalidEmail.setText("");
                return true;
            }
        } catch (InvalidEmailValueException e) {
            imageViewEmail.setImage(new Image(getClass().getResourceAsStream("/resources/iconEmailIncorrect.png")));
            lineEmail.setStroke(Color.RED);
            labelInvalidEmail.setText(e.getMessage());
            return false;
        }

    }*/

    /**
     * This method checks whether the name is empty or not.
     * @return boolean If it is valid returns true if not false.
     */
    /*private boolean nameIsEmptyOrNo() {
        try {
            if (textFieldName.getText().isEmpty()) {
                throw new InvalidUserValueException("Name is empty");
            }
            imageViewName.setImage(new Image(getClass().getResourceAsStream("/resources/iconFullName.png")));
            lineName.setStroke(Color.GREY);
            labelInvalidName.setText("");
            return true;
        } catch (InvalidUserValueException e) {
            imageViewName.setImage(new Image(getClass().getResourceAsStream("/resources/iconFullNameIncorrect.png")));
            lineName.setStroke(Color.RED);
            labelInvalidName.setText(e.getMessage());
            return false;
        }

    }*/

    /**
     * This method checks whether the password is valid or not.
     * @return boolean If it is valid returns true if not false.
     */
    /*private boolean passwordIsValid() {
        try {
            if (passwordField.getText().contains(" ") || passwordField.getText().length() < 8 || passwordField.getText().isEmpty()) {
                throw new InvalidPasswordValueException("Password can't be empty nor contain an empty space or his lenght is less than 8.");
            }
            imageViewPassword.setImage(new Image(getClass().getResourceAsStream("/resources/iconPassword.png")));
            linePassword.setStroke(Color.GREY);
            labelInvalidPassword.setText("");
            return true;
        } catch (InvalidPasswordValueException e) {
            imageViewPassword.setImage(new Image(getClass().getResourceAsStream("/resources/iconPasswordRedIncorrect.png")));
            linePassword.setStroke(Color.RED);
            labelInvalidPassword.setText(e.getMessage());
            return false;
        }

    }*/

    /**
     * This method checks whether the confirmPassword is valid or not.
     * @return boolean If it is valid returns true if not false.
     */
    /*private boolean ConfirmPasswordIsValid() {
        try {
            if (!passwordFieldConfirm.getText().toString().equalsIgnoreCase(passwordField.getText().toString())) {
                throw new InvalidConfirmPasswordValueException("These passwords didn’t match");
            }
            imageViewConfirmPassword.setImage(new Image(getClass().getResourceAsStream("/resources/iconPassword.png")));
            lineConfirmPassword.setStroke(Color.GREY);
            labelInvalidConfirmPassword.setText("");
            return true;
        } catch (InvalidConfirmPasswordValueException e) {
            imageViewConfirmPassword.setImage(new Image(getClass().getResourceAsStream("/resources/iconPasswordRedIncorrect.png")));
            lineConfirmPassword.setStroke(Color.RED);
            labelInvalidConfirmPassword.setText(e.getMessage());
            return false;
        }

    }*/

    /**
     * Check if the email is under focus and verify if it is valid or not, using
     * a pattern we will see if it corresponds to the correct structure.
     *
     * @param value Actual value
     * @param oldValue Old value
     * @param newValue New value
     */
    /*private void focusedPropertyChangedEmail(Observable value, Boolean oldValue, Boolean newValue) {
        if (!textFieldEmail.isFocused()) {
            boolean matchOrNot = false;
            Pattern pattern = Pattern.compile(emailPattern);
            Matcher matcher = pattern.matcher(textFieldEmail.getText());
            if (matcher.find()) {
                matchOrNot = true;
            }
            try {
                if (!matchOrNot) {
                    throw new InvalidEmailValueException("Invalid format of email (*@*.*)");
                } else {
                    imageViewEmail.setImage(new Image(getClass().getResourceAsStream("/resources/iconEmail.png")));
                    lineEmail.setStroke(Color.GREY);
                    labelInvalidEmail.setText("");
                }
            } catch (InvalidEmailValueException e) {
                imageViewEmail.setImage(new Image(getClass().getResourceAsStream("/resources/iconEmailIncorrect.png")));
                lineEmail.setStroke(Color.RED);
                labelInvalidEmail.setText(e.getMessage());
            }

        }
    }*/

    /**
     * Check if the username is in focus and validate if it is correct, does not
     * contain blanks and is not empty.
     *
     * @param value Actual value
     * @param oldValue Old value
     * @param newValue New value
     */
    /*private void focusedPropertyChanged(Observable value, Boolean oldValue, Boolean newValue) {
        if (oldValue) {
            if (!textFieldUsername.isFocused()) {
                try {
                    if (textFieldUsername.getText().contains(" ") || textFieldUsername.getText().isEmpty()) {
                        throw new InvalidUserValueException("Username can't be empty nor contain an empty space.");
                    }
                    imageViewUsername.setImage(new Image(getClass().getResourceAsStream("/resources/iconUser.png")));
                    lineUsername.setStroke(Color.GREY);
                    labelInvalidUser.setText("");
                } catch (InvalidUserValueException e) {
                    imageViewUsername.setImage(new Image(getClass().getResourceAsStream("/resources/iconUserInconrrect.png")));
                    lineUsername.setStroke(Color.RED);
                    labelInvalidUser.setText(e.getMessage());
                }
            }
        }
    }*/

    /**
     * Check if the password confirmation is under focus and validate if it is
     * correct, that it is the same as the password.
     *
     * @param value Actual value
     * @param oldValue Old value
     * @param newValue New value
     */
    /*private void focusedPropertyChangedPasswordConfirm(Observable value, Boolean oldValue, Boolean newValue) {
        if (oldValue) {
            if (!passwordFieldConfirm.isFocused() && !textFieldConfirmPassword.isFocused()) {
                try {
                    if (!passwordFieldConfirm.getText().toString().equalsIgnoreCase(passwordField.getText().toString())) {
                        throw new InvalidConfirmPasswordValueException("These passwords don’t match");
                    }
                    imageViewConfirmPassword.setImage(new Image(getClass().getResourceAsStream("/resources/iconPassword.png")));
                    lineConfirmPassword.setStroke(Color.GREY);
                    labelInvalidConfirmPassword.setText("");
                } catch (InvalidConfirmPasswordValueException e) {
                    imageViewConfirmPassword.setImage(new Image(getClass().getResourceAsStream("/resources/iconPasswordRedIncorrect.png")));
                    lineConfirmPassword.setStroke(Color.RED);
                    labelInvalidConfirmPassword.setText(e.getMessage());
                }
            }
        }
    }*/

    /**
     * Check if the password is under focus and validate if it is correct, if
     * its length is greater than or equal to 8 and that it does not contain
     * spaces.
     *
     * @param value Actual value
     * @param oldValue Old value
     * @param newValue New value
     */
    /*private void focusedPropertyChangedPassword(Observable value, Boolean oldValue, Boolean newValue) {
        if (oldValue) {
            if (!passwordField.isFocused() && !textFieldPassword.isFocused()) {
                try {
                    if (passwordField.getText().contains(" ") || passwordField.getText().length() < 8 || passwordField.getText().isEmpty()) {
                        throw new InvalidPasswordValueException("Password can't be empty nor contain an empty space or his lenght is less than 8.");
                    }
                    imageViewPassword.setImage(new Image(getClass().getResourceAsStream("/resources/iconPassword.png")));
                    linePassword.setStroke(Color.GREY);
                    labelInvalidPassword.setText("");
                } catch (InvalidPasswordValueException e) {
                    imageViewPassword.setImage(new Image(getClass().getResourceAsStream("/resources/iconPasswordRedIncorrect.png")));
                    linePassword.setStroke(Color.RED);
                    labelInvalidPassword.setText(e.getMessage());
                }
            }
        }
    }*/

    /**
     * The method that logs in and opens the signIn window.
     *
     * @param event an ActionEvent.ACTION event type for when the button is
     * pressed
     */
    private void signIn(ActionEvent event) {
        try {
            stage.close();
            LOGGER.info("SignUp window closed");
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ui/views/SignInView.fxml"));
            Parent root = (Parent) loader.load();
            SignInController controller = ((SignInController) loader.getController());
            controller.setStage(new Stage());
            controller.initStage(root);
            LOGGER.info("SignIn window opened");
        } catch (IOException ex) {
            Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * The method of registration.
     *
     * @param event an ActionEvent.ACTION event type for when the button is
     * pressed
     */
    private void signUp(ActionEvent event) {
        LOGGER.info("Signing Up user");
        //boolean usernameIsValidOrNot = usernameIsValid();
        //boolean emailIsValidOrNot = emailIsValid();
        //boolean nameIsValidOrNot = nameIsEmptyOrNo();
        //boolean passwordIsValidOrNot = passwordIsValid();
        //boolean confirmPasswordIsValidOrNot = ConfirmPasswordIsValid();
        
        
        try {
            ClientOBJ client = new ClientOBJ(0, textFieldUsername.getText(), textFieldEmail.getText(), textFieldName.getText(), StatusEnum.ENABLED, PrivilegeEnum.USER, passwordField.getText(), new Date(System.currentTimeMillis()), "20", Float.parseFloat("1.80"), GenreEnum.NON_BINARY, GoalEnum.MAINTAIN);
            byte[] passwordBytes = new Asymmetric().cipher(passwordField.getText());
            client.setPassword(HashMD5.hexadecimal(passwordBytes));
            ClientFactory.getModel().create(client);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "User created succesfully");
            alert.show();
        } catch (BusinessLogicException ex) {
            Logger.getLogger(SignUpController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Method that makes the password visible and not visible
     *
     * @param event an ActionEvent.ACTION event type for when the button is
     * pressed
     */
    /*private void showHide(ActionEvent event) {
        if (ButtonShowHide.isSelected()) {
            IconEye.setImage(new Image(getClass().getResourceAsStream("/resources/iconEye2.png")));
            passwordField.setVisible(false);
            textFieldPassword.setVisible(true);
        } else {
            IconEye.setImage(new Image(getClass().getResourceAsStream("/resources/iconEye.png")));
            passwordField.setVisible(true);
            textFieldPassword.setVisible(false);
        }
    }*/

    /**
     * Method that makes the password confirmation visible and not visible.
     *
     * @param event an ActionEvent.ACTION event type for when the button is
     * pressed
     */
    /*private void showHideConfirm(ActionEvent event) {
        if (ButtonShowHideConfirm.isSelected()) {
            IconEye2.setImage(new Image(getClass().getResourceAsStream("/resources/iconEye2.png")));
            passwordFieldConfirm.setVisible(false);
            textFieldConfirmPassword.setVisible(true);
        } else {
            IconEye2.setImage(new Image(getClass().getResourceAsStream("/resources/iconEye.png")));
            passwordFieldConfirm.setVisible(true);
            textFieldConfirmPassword.setVisible(false);
        }
    }*/

    /**
     * Method that makes the password visible and not visible and verify if the confirmPassword is same as password.
     *
     * @param KEY_RELEASED
     */
    /*private void textChangedPressed(KeyEvent KEY_RELEASED) {
        if (passwordField.isVisible()) {
            textFieldPassword.setText(passwordField.getText());
        } else if (textFieldPassword.isVisible()) {
            passwordField.setText(textFieldPassword.getText());
        }
        if (passwordFieldConfirm.isVisible()) {
            textFieldConfirmPassword.setText(passwordFieldConfirm.getText());
        } else if (textFieldConfirmPassword.isVisible()) {
            passwordFieldConfirm.setText(textFieldConfirmPassword.getText());
        }
        
        try {
            if (!passwordFieldConfirm.getText().toString().equalsIgnoreCase(passwordField.getText().toString())) {
                throw new InvalidConfirmPasswordValueException("These passwords don’t match");
            }
            imageViewConfirmPassword.setImage(new Image(getClass().getResourceAsStream("/resources/iconPassword.png")));
            lineConfirmPassword.setStroke(Color.GREY);
            labelInvalidConfirmPassword.setText("");
        } catch (InvalidConfirmPasswordValueException e) {
            imageViewConfirmPassword.setImage(new Image(getClass().getResourceAsStream("/resources/iconPasswordRedIncorrect.png")));
            lineConfirmPassword.setStroke(Color.RED);
            labelInvalidConfirmPassword.setText(e.getMessage());
        }
    }*/
    
    /**
     * Method that makes you exit the application by going through an alert.
     *
     * @param event an ActionEvent.ACTION event type for when the button is
     * pressed
     */
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
}
