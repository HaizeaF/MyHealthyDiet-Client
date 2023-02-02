package ui.controllers;

import businessLogic.PlateFactory;
import businessLogic.PlateInterface;
import exceptions.BusinessLogicException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.ws.rs.core.GenericType;
import objects.Plate;

/**
 *
 * @author HaizeaF
 */
public class ListPlatesVController {

    private Stage stage;
    private ObservableList<Plate> platesData = null;
    private PlateInterface plateModel;
    private Pane contentPane;
    private Pane imgPane;
    private Label labelTitle;
    private ImageView imgPlate;
    private Button buttonInfo;
    private Label labelMeal;
    private ImageView imgVegetarian;
    private static final Logger LOGGER = Logger.getLogger("ListPlatesVController.class");

    @FXML
    private FlowPane flowPane;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initStage(Parent root) throws IOException {
        try {
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("List Plates");
            stage.setResizable(false);
            stage.show();
            plateModel = PlateFactory.getModel();
            platesData = FXCollections.observableArrayList(plateModel.findAll_XML(new GenericType<List<Plate>>() {
            }));
            ArrayList<Node> nodes = new ArrayList<>();
            for (Plate plate : platesData) {
                contentPane = new Pane();
                contentPane.setPrefHeight(225);
                contentPane.setPrefWidth(878);
                contentPane.setStyle("-fx-background-color: #eeeeee; -fx-border-width: 2px");
                nodes.add(contentPane);
                imgPane = new Pane();
                imgPane.setPrefWidth(320);
                imgPane.setPrefHeight(225);
                imgPane.setStyle("-fx-effect: dropshadow(three-pass-box, #000000, 10, 0, 0, 0)");
                byte[] imgBytes = plate.getPlateImg();
                Image image = new Image(new ByteArrayInputStream(imgBytes));
                imgPlate = new ImageView();
                imgPlate.setImage(image);
                imgPlate.setFitWidth(320);
                imgPlate.setFitHeight(225);
                imgPane.getChildren().add(imgPlate);
                buttonInfo = new Button("i");
                buttonInfo.setStyle("-fx-background-color: #15B7B9; -fx-background-radius: 50px; -fx-padding: 0px; -fx-effect: dropshadow(three-pass-box, #000000, 10, 0, 0, 0);");
                buttonInfo.setFont(Font.font("Rockwell", 20.0));
                buttonInfo.setTextFill(Color.web("#FFFFFF"));
                buttonInfo.setPrefWidth(40);
                buttonInfo.setPrefHeight(40);
                buttonInfo.setLayoutX(265);
                buttonInfo.setLayoutY(170);
                buttonInfo.setOnAction(this::handleButtonInfo);
                imgPane.getChildren().add(buttonInfo);
                contentPane.getChildren().add(imgPane);
                labelTitle = new Label(plate.getPlateName());
                labelTitle.setLayoutX(343);
                labelTitle.setLayoutY(13);
                labelTitle.setPrefHeight(139);
                labelTitle.setPrefWidth(540);
                labelTitle.setTextFill(Color.web("#686868"));
                labelTitle.setWrapText(true);
                labelTitle.setFont(Font.font("Circular Std Book", 40.0));
                contentPane.getChildren().add(labelTitle);
                String mealType = plate.getMealType().toString().toLowerCase();
                String mealName = mealType.substring(0, 1).toUpperCase() + mealType.substring(1);
                labelMeal = new Label(mealName);
                labelMeal.setLayoutX(360);
                labelMeal.setLayoutY(167);
                labelMeal.setPrefHeight(38);
                labelMeal.setPrefWidth(240);
                labelMeal.setTextFill(Color.web("#9e9e9e"));
                labelMeal.setWrapText(true);
                labelMeal.setFont(Font.font("Circular Std Book", 20.0));
                contentPane.getChildren().add(labelMeal);
            }

            flowPane.getChildren().addAll(nodes);
        } catch (BusinessLogicException ex) {
            Logger.getLogger(ListPlatesVController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void handleButtonInfo(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "WIP");
        alert.show();
    }

    @FXML
    private void handleHelp(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "WIP");
        alert.show();
    }

    @FXML
    private void handleFilter(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "WIP");
        alert.show();
    }

    @FXML
    private void handleButtonDiets(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "WIP");
        alert.show();
    }

    @FXML
    private void handleButtonSearch(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "WIP");
        alert.show();
    }

    @FXML
    private void handleButtonLogOut(ActionEvent event) {
        try {
            stage.close();
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ui/views/SignInView.fxml"));
            Parent root = (Parent) loader.load();

            SignInController controller = ((SignInController) loader.getController());

            controller.setStage(stage);

            controller.initStage(root);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage());
            alert.show();
        }
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
}
