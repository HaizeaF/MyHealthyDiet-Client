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
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.stage.Stage;
import objects.Diet;
import objects.Tip;
import ui.controllers.DietsControlVController;
import ui.controllers.TipsControlVController;

/**
 * This class inserts a button in the Tips cell of the table from the
 * DietsControlVController.
 *
 * @author JulenB
 */
public class TipsCell extends TableCell<Diet, List<Tip>> {

    /**
     * The button that is going to be in the cell.
     */
    private final Button button = new Button("Tips");
    /**
     * Log that gives more info when app is running.
     */
    private static final Logger LOGGER = Logger.getLogger(TipsCell.class.getName());

    /**
     * Constructor of the class that makes the button move you to another
     * window.
     *
     * @param stage The current stage of the app.
     */
    public TipsCell(Stage stage) {
        //When the button is pressed, it is going to move you to another window.
        button.setOnAction((ActionEvent t) -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ui/views/TipsControlWindow.fxml"));
                Parent root = (Parent) loader.load();

                TipsControlVController controller = ((TipsControlVController) loader.getController());

                controller.setStage(stage);
                controller.setStage(stage);
                Diet diet = (Diet) getTableRow().getItem();
                controller.setData((ObservableList<Tip>) diet.getTips());
                //The actual stage is closed and the new one is initialized.
                stage.close();
                controller.initStage(root);
            } catch (IOException | IllegalStateException ex) {
                //If the new window not opens or theres an error, shows you an error alert.
                Alert alert = new Alert(Alert.AlertType.ERROR, "Failed loading Tips window", ButtonType.OK);
                alert.showAndWait();
                LOGGER.log(Level.SEVERE, "TipsCell: Load of TipsControlWindow failed, {0}", ex.getMessage());
            }
        });
    }

    /**
     * This method sets the button in the cell.
     *
     * @param t cell that you want to update.
     * @param empty If the cell is empty or not.
     */
    @Override
    protected void updateItem(List<Tip> t, boolean empty) {
        super.updateItem(t, empty);
        //If the row is not empty, is goint to put the button, if not, set it to null.
        if (!empty) {
            setText(null);
            setAlignment(Pos.CENTER);
            setGraphic(button);
        } else {
            setGraphic(null);
        }
    }
}
