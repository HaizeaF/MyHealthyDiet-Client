package cellFactories;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.stage.Stage;
import objects.Ingredient;
import objects.Plate;
import ui.controllers.PlateControlVController;

/**
 *
 * @author haize
 */
public class IngredientsButtonCell extends TableCell<Plate, List<Ingredient>> {

    final Button cellButton = new Button("See ingredients");
    private static final Logger LOGGER = Logger.getLogger(IngredientsButtonCell.class.getName());

    public IngredientsButtonCell(Stage stage) {
        cellButton.setOnAction((ActionEvent t) -> {
            try {
                stage.close();
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ui/views/IngredientsControlWindow.fxml"));
                Parent root;
                root = (Parent) loader.load();
                PlateControlVController controller = ((PlateControlVController) loader.getController());

                controller.setStage(stage);

                controller.initStage(root);
            } catch (IOException ex) {
                Logger.getLogger(IngredientsButtonCell.class.getName()).log(Level.SEVERE, null, ex);
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
