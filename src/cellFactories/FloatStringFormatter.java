/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cellFactories;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.util.StringConverter;

/**
 *
 * @author haize
 */
public class FloatStringFormatter extends StringConverter<Float> {

    private final NumberFormat nf;
    /**
     * Log that gives more info when app is running.
     */
    private static final Logger LOGGER = Logger.getLogger("FloatStringFormatter.class");

    public FloatStringFormatter() {
        nf = NumberFormat.getInstance(Locale.getDefault());
    }

    @Override
    public String toString(Float value) {
        try{
            return nf.format(value);
        }catch(IllegalArgumentException ex){
            return null;
        }
        
    }

    @Override
    public Float fromString(String string) {
        try {
            return nf.parse(string).floatValue();
        } catch (ParseException ex) {
            return null;
        }
    }
}
