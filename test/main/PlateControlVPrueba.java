package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import ui.controllers.PlateControlVController;

/**
 * The controller used to test the Plate management window controller.
 * @author HaizeaF
 */
public class PlateControlVPrueba extends Application {
    
    /**
     * This method initializes the Plate Control window.
     */
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ui/views/PlateControlView.fxml"));
        Parent root = (Parent) loader.load();

        PlateControlVController controller = ((PlateControlVController) loader.getController());

        controller.setStage(stage);

        controller.initStage(root);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
