package ui.controllers;

import businessLogic.DietFactory;
import exceptions.BusinessLogicException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Random;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.ws.rs.core.GenericType;
import objects.ClientOBJ;
import objects.Diet;
import objects.Plate;

/**
 * @author JulenB
 */
public class DietWindowController {

    private Stage stage;
    /**
     * Log that gives more info when app is running.
     */
    private static final Logger LOGGER = Logger.getLogger("DietWindowController.class");

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
     * Sends you to the overview view.
     */
    @FXML
    private Button buttonOverview;
    /**
     * Logout from app.
     */
    @FXML
    private MenuItem menuItemProfile;
    /**
     * Logout from app.
     */
    @FXML
    private MenuItem menuItemLogout;
    /**
     * Help view showing button.
     */
    @FXML
    private Button buttonHelp;
    /**
     * Diet name.
     */
    @FXML
    private Label labelDietName;
    /**
     * Diet desc.
     */
    @FXML
    private Label labelDesc;
    /**
     * Diet type.
     */
    @FXML
    private Label labelType;
    /**
     * Diet calories.
     */
    @FXML
    private Label labelCalories;
    /**
     * Diet carbohydrates.
     */
    @FXML
    private Label labelCarbohydrates;
    /**
     * Diet lipids.
     */
    @FXML
    private Label labelLipids;
    /**
     * Diet proteins.
     */
    @FXML
    private Label labelProteins;
    /**
     * Diet tip.
     */
    @FXML
    private Label labelTip;
    /**
     * Diet plate list.
     */
    @FXML
    private ListView<Plate> listPlates;
    /**
     * Diet img.
     */
    @FXML
    private ImageView imgDiet;
    /**
     * Diet has no plates.
     */
    @FXML
    private Label labelNoDiets;
    /**
     * User inital.
     */
    @FXML
    private Label userInitial;
    /**
     * User name.
     */
    @FXML
    private MenuButton buttonUser;
    /**
     * ObservableList that saves diets data.
     */
    private ObservableList<Diet> dietsData;
    /**
     * Random number for tip.
     */
    private final Random random = new Random();

    Diet diet = new Diet();
    
    ClientOBJ client = new ClientOBJ();

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setDiet(Diet diet) {
        this.diet = diet;
    }
    
    public void setClient(ClientOBJ client){
        this.client = client;
    }

    /**
     * Init stage params.
     *
     * @param root
     */
    public void initStage(Parent root) {
        try {
            Scene scene = new Scene(root); //If any error occurs initializing the window, show an alert.
            //Set stage properties
            stage.setScene(scene);
            stage.setTitle("Diet");
            stage.setResizable(false);

            //ACTIONS FOR CONTROLS
            stage.setOnCloseRequest(this::handleExitAction);
            menuItemLogout.setOnAction(this::handleLogOutAction);
            menuItemProfile.setOnAction(this::handleModifyAction);
            buttonHelp.setOnAction(this::handleButtonHelpAction);
            buttonOverview.setOnAction(this::handleButtonOverviewAction);
            buttonPlates.setOnAction(this::handleButtonPlatesAction);
            buttonDiets.setOnAction(this::handleButtonDietsAction);

            //Create an observable list for diets table.
            dietsData = FXCollections.observableArrayList(DietFactory.getModel().findAllDiets_XML(new GenericType<List<Diet>>() {
            }));
            diet = dietsData.get(0);

            //DIET PARAMS SET
            userInitial.setText(client.getFullName().substring(0,1));
            buttonUser.setText("          " + client.getFullName());
            labelDietName.setText(diet.getDietName());
            labelDesc.setText(diet.getDescription());
            labelType.setText(diet.getType().toString());
            labelCalories.setText(diet.getCalories().toString());
            labelCarbohydrates.setText(diet.getCarbohydrates().toString());
            labelLipids.setText(diet.getLipids().toString());
            labelProteins.setText(diet.getProteins().toString());
            if (diet.getTips() == null) {
                labelTip.setText("A tip");
            } else {
                Integer randomNumber = random.nextInt(diet.getTips().size());
                if (randomNumber == 0) {
                    labelTip.setText("A tip");
                } else {
                    labelTip.setText(diet.getTips().get(randomNumber).getTipText());
                }
            }
            if (diet.getPlates() == null) {
                labelNoDiets.setVisible(true);
            } else {
                listPlates.setItems((ObservableList<Plate>) diet.getPlates());

            }

            byte[] imgBytes = diet.getDietImg();
            Image image = null;
            if (imgBytes != null) {
                image = new Image(new ByteArrayInputStream(imgBytes));
            }else{
                imgDiet = new ImageView();
            }
            imgDiet.setImage(image);

            //Show window.
            stage.show();
            LOGGER.log(Level.INFO, "DietWindowController: Window initialized");
        } catch (BusinessLogicException ex) {
            //If any error occurs initializing the window, show an alert.
            showErrorAlert("Window can not be loaded, " + ex.getMessage());
            LOGGER.log(Level.SEVERE, "DietWindowController: Error initializing window, {0}", ex.getMessage());
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
                LOGGER.log(Level.INFO, "DietWindowController: Window exited");
            }
        } catch (Exception ex) {
            //If , an alert will show.
            showErrorAlert("Error trying to exit app:\n" + ex.getMessage());
            LOGGER.log(Level.SEVERE, "DietWindowController: Error trying to exit app, {0}", ex.getMessage());
        }
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
            //If theres is an error trying to change view, an alert will show.
            showErrorAlert("Failed trying to open Modify window.");
            LOGGER.log(Level.SEVERE, "DietWindowController: Error trying to opening Medify window, {0}", ex.getMessage());
        }
    }

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
            //If theres is an error trying to change view, an alert will show.
            showErrorAlert("Failed trying to open log out.");
            LOGGER.log(Level.SEVERE, "DietWindowController: Error trying to opening log out window, {0}", ex.getMessage());
        }
    }

    /**
     * It will display a help window.
     *
     * @param event The ActionEvent object for the event.
     */
    private void handleButtonHelpAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/views/DietHelpFXMLController.fxml"));
            Parent root = (Parent) loader.load();
            DietHelpController dietHelpController = ((DietHelpController) loader.getController());
            //Initializes and shows help stage
            dietHelpController.initAndShowStage(root);
        } catch (IOException ex) {
            //If theres is an error trying to open the help view, an alert will show. 
            showErrorAlert("Error trying to open help view:\n" + ex.getMessage());
            LOGGER.log(Level.SEVERE, "DietWindowController: Error trying to open help view, {0}", ex.getMessage());
        }
    }

    @FXML
    private void handleButtonOverviewAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ui/views/ClientHomeWindow.fxml"));
            Parent root = (Parent) loader.load();

            //ClientHomeWindowController controller = ((ClientHomeWindowController) loader.getController());
            //controller.setStage(stage);
            stage.close();
            //controller.initStage(root);
        } catch (IOException | IllegalStateException ex) {
            //If theres is an error trying to change view, an alert will show.
            showErrorAlert("Failed trying to open Overview window.");
            LOGGER.log(Level.SEVERE, "DietWindowController: Error trying to open Overview window, {0}", ex.getMessage());
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
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ui/views/ListPlatesWindow.fxml"));
            Parent root = (Parent) loader.load();

            //ListPlatesWindowController controller = ((ListPlatesWindowController) loader.getController());
            //controller.setStage(stage);
            stage.close();
            //controller.initStage(root);
        } catch (IOException | IllegalStateException ex) {
            //If theres is an error trying to change view, an alert will show.
            showErrorAlert("Failed trying to open Plates window.");
            LOGGER.log(Level.SEVERE, "DietWindowController: Error trying to open Plate window, {0}", ex.getMessage());
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
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ui/views/DietWindow.fxml"));
            Parent root = (Parent) loader.load();

            DietWindowController controller = ((DietWindowController) loader.getController());

            controller.setStage(stage);
            stage.close();
            controller.initStage(root);
        } catch (IOException | IllegalStateException ex) {
            //If theres is an error trying to change view, an alert will show.
            showErrorAlert("Failed trying to open Diets window.");
            LOGGER.log(Level.SEVERE, "DietWindowController: Error trying to open Diets window, {0}", ex.getMessage());
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
