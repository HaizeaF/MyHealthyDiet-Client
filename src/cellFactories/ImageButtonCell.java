/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cellFactories;

import businessLogic.PlateFactory;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import objects.Plate;

/**
 *
 * @author haize
 */
public class ImageButtonCell extends TableCell<Plate, byte[]> {

    final Button cellButton = new Button("Choose image");
    private static final Logger LOGGER = Logger.getLogger(ImageButtonCell.class.getName());

    public ImageButtonCell(Stage stage) {
        FileChooser.ExtensionFilter imageFilter
                = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(imageFilter);
        fileChooser.setTitle("Select Image");
        cellButton.setOnAction((ActionEvent t) -> {
            File image = fileChooser.showOpenDialog(stage);
            if (image != null) {
                try {
                    byte[] imgBytes = Files.readAllBytes(image.toPath());
                    Plate plate = (Plate) getTableRow().getItem();
                    plate.setPlateImg(imgBytes);
                    PlateFactory.getModel().edit_XML(plate);
                } catch (IOException ex) {
                    Logger.getLogger(ImageButtonCell.class.getName()).log(Level.SEVERE, null, ex);
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
