/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cellFactories;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import javafx.util.StringConverter;

/**
 * Class to make a format fot waterIndex in IngredientControlVController.
 * @author mikel
 */
public class FloatStringFormatterIngredient extends StringConverter<Float> {

    private final NumberFormat nf;
    
    /**
     * Formater of the waterIndex value
     */
    public FloatStringFormatterIngredient() {
        nf = NumberFormat.getInstance(Locale.getDefault());
    }
    
    /**
     * This method converts a Float into a String to show it in the table.
     * @param value Float value that will be send into the table.
     * @return String converted from Float to be in the table.
     */
    @Override
    public String toString(Float value) {
        try{
            return nf.format(value);
        } catch (IllegalArgumentException ex){
            return null;
        }
    }
    
    /**
     * This method converts a String from the table into a Float.
     * @param string char sequence from the table that will be converted into Float
     * @return Value of the Float.
     */
    @Override
    public Float fromString(String string) {
        try {
            return nf.parse(string).floatValue();
        } catch (ParseException e) {
            return null;
        }
    }
}
