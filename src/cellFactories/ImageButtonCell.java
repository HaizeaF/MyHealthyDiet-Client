package cellFactories;

import businessLogic.PlateFactory;
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
import javafx.scene.control.TableCell;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import objects.Plate;

/**
 * Cell factory for button that open a filechooser to put a image on a plate.
 * @author haize
 */
public class ImageButtonCell extends TableCell<Plate, byte[]> {

    final Button cellButton = new Button("Choose image");
    private static final Logger LOGGER = Logger.getLogger(ImageButtonCell.class.getName());
    
    /**
     * This method sets the event for the button. It opens the filechooser and then it saves the image on the current plate.
     * @param stage 
     */
    public ImageButtonCell(Stage stage) {
        // Open a fileChooser to choose an image. 
        FileChooser.ExtensionFilter imageFilter
                = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(imageFilter);
        fileChooser.setTitle("Select Image");
        // Sends a request to the server to update the plate with the entered data. If there is any error with the server a popup window is shown informing about it.
        cellButton.setOnAction((ActionEvent t) -> {
            File image = fileChooser.showOpenDialog(stage);
            if (image != null) {
                try {
                    byte[] imgBytes = Files.readAllBytes(image.toPath());
                    Plate plate = (Plate) getTableRow().getItem();
                    plate.setPlateImg(imgBytes);
                    PlateFactory.getModel().edit_XML(plate);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Image added correctly.");
                    alert.show();
                } catch (IOException | BusinessLogicException ex) {
                    String msg = "Error saving the image: " + ex.getMessage();
                    Alert alert = new Alert(Alert.AlertType.ERROR, msg);
                    alert.show();
                    LOGGER.log(Level.SEVERE, msg);
                }
            }

        });
    }

    @Override
    protected void updateItem(byte[] t, boolean empty) {
        super.updateItem(t, empty);
        setAlignment(Pos.CENTER);
        if (!empty) {
            setText(null);
            setGraphic(cellButton);
        } else {
            setGraphic(null);
        }
    }
}
