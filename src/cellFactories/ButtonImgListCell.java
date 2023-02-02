package cellFactories;

import businessLogic.DietFactory;
import exceptions.BusinessLogicException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import objects.Diet;

/**
 * This class inserts a button in the Image cell of the table from the DietsControlVController.
 * The button can choose an image from the pc.
 * Open a fileChooser to choose an image.
 * @author JulenB
 */
public class ButtonImgListCell extends TableCell<Diet, byte[]> {
    /**
     * The button that is going to be in the cell.
     */
    private final Button button = new Button("Select");
    /**
     * Log that gives more info when app is running.
     */
    private static final Logger LOGGER = Logger.getLogger(ButtonImgListCell.class.getName());
    
    /**
     * Constructor of the class that makes the button pick images from pc.
     * @param stage The current stage of the app.
     */
    public ButtonImgListCell(Stage stage) {
        button.setOnAction((ActionEvent t) -> {
            try {
                //The image extensions that are allow for the FileChoose
                FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", ".jpg", "*.png");
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().add(imageFilter);
                fileChooser.setTitle("Select Image");
                //Opens the file chooser to choose an image
                File image = fileChooser.showOpenDialog(stage);
                //Convert the image into bytes so later can be updated.
                byte[] imgBytes = Files.readAllBytes(image.toPath());
                Diet diet = (Diet) getTableRow().getItem();
                diet.setDietImg(imgBytes);
                //sends the image bytes to the DietFacadeRESTClient to be updated.
                DietFactory.getModel().edit_XML(diet);
                LOGGER.info("ButtonImgListCell: Edit done.");
            } catch (IOException | NullPointerException ex) {
                //If theres no image selected this exception is thrown.
                LOGGER.log(Level.INFO, "ButtonImgListCell: Image path equals, {0}", ex.getMessage());
            } catch (BusinessLogicException ex) {
                //If the is any problem during the update, this exception is thrown.
                Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
                alert.showAndWait();
            }
        });
    }
    
    /**
     * This method sets the button in the cell.
     * @param t cell that you want to update.
     * @param empty If the cell is empty or not.
     */
    @Override
    protected void updateItem(byte[] t, boolean empty) {
        super.updateItem(t, empty);
        setAlignment(Pos.CENTER);
        //If the row is not empty, is goint to put the button, if not, set it to null.
        if (!empty) {
            setGraphic(button);
        } else {
            setGraphic(null);
        }
    }
}
