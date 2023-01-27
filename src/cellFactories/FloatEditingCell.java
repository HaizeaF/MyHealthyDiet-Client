package cellFactories;

import java.util.regex.Pattern;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import objects.Plate;

/**
 *
 * @author haize
 */
public class FloatEditingCell extends TableCell<Plate, Float> {

    private final TextField textField = new TextField();
    private final Pattern floatPattern = Pattern.compile("\\d*\\.\\d+");
    
    public FloatEditingCell() {
        textField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused) {
                processEdit();
            }
        });
        textField.setOnAction(event -> processEdit());
    }

    private void processEdit() {
        String text = textField.getText();
        if (floatPattern.matcher(text).matches()) {
            commitEdit(Float.parseFloat(text));
        } else {
            cancelEdit();
        }
    }

    // Se llama al método al iniciar la edición de una celda (doble click o F2).
    @Override
    public void startEdit() {
        // getItem() equivale al valor de la celda.
        if (getItem() != null) {
            super.startEdit();
            // Establece el valor inicial en el textField.
            textField.setText(getItem().toString());
            // Establece el textField como elemento gráfico.
            setGraphic(textField);
            // Establece el texto a null porque si no se mostraría el textField y a su lado un String con el mismo valor.
            setText(null);
            // Establece el foco en el textField y selecciona todo el texto para facilitar la edición.
            textField.requestFocus();
            textField.selectAll();
        }
    }

    // Se llama al método al actualizar una celda (agregar un nuevo elemento, editarlo o eliminarlo)
    @Override
    public void updateItem(Float value, boolean empty) {
        
        super.updateItem(value, empty);
        // En caso de que la celda esté vacía (al eliminar un plato o al filtrarlos) se pondrán a nulos tanto el String como el elemento gráfico.
        if (empty) {
            setText(null);
            setGraphic(null);
        // En caso de que la celda no esté vacía se mostrará el valor en el String y se pondrá a nulo el elemento gráfico.
        } else {
            setText(value.toString());
            setGraphic(null);
        }
    }
    
    // Se llama al método al cancelar la edición de una celda
    @Override
    public void cancelEdit() {
        super.cancelEdit();
        // Establece el valor inicial en el String.
        setText(getItem().toString());
        // Establece a nulo el elemento gráfico.
        setGraphic(null);
    }
}
