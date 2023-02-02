package cellFactories;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import objects.Ingredient;
import objects.Plate;
import ui.controllers.IngredientControlVController;

/**
 * Cell factory for button that opens the ingredient window with the current ingredients of the selected plate.
 * @author HaizeaF
 */
public class IngredientsButtonCell extends TableCell<Plate, List<Ingredient>> {

    final Button cellButton = new Button("See ingredients");
    private static final Logger LOGGER = Logger.getLogger(IngredientsButtonCell.class.getName());
    
    /**
     * This method sets the event for the button. It opens the ingredient window setting the ingredients of the plate.
     * @param stage The stage of the current window.
     * @param tablePlates The table of the current window.
     */
    public IngredientsButtonCell(Stage stage, TableView tablePlates) {
        cellButton.setOnAction((ActionEvent t) -> {
            try {
                Plate plate = (Plate) getTableRow().getItem();
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ui/views/IngredientControlWindow.fxml"));
                Parent root = (Parent) loader.load();

                IngredientControlVController controller = ((IngredientControlVController) loader.getController());

                controller.setData((ObservableList<Ingredient>) plate.getIngredients());
                controller.setStage(stage);
                stage.close();
                controller.initStage(root);
            } catch (IOException | IllegalStateException ex) {
                String msg = "Failed loading Ingredient window: " + ex.getMessage();
                Alert alert = new Alert(Alert.AlertType.ERROR, msg);
                alert.showAndWait();
                LOGGER.log(Level.SEVERE, msg);
            }
        });
    }

    @Override
    protected void updateItem(List<Ingredient> t, boolean empty) {
        super.updateItem(t, empty);
        if (!empty) {
            setText(null);
            setAlignment(Pos.CENTER);
            setGraphic(cellButton);
        } else {
            setGraphic(null);
        }
    }

}
