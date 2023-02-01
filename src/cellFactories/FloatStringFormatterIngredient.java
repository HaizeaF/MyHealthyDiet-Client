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
 *
 * @author mikel
 */
public class FloatStringFormatterIngredient extends StringConverter<Float> {

    private final NumberFormat nf;

    public FloatStringFormatterIngredient() {
        nf = NumberFormat.getInstance(Locale.getDefault());
    }

    @Override
    public String toString(Float value) {
        return nf.format(value);
    }

    @Override
    public Float fromString(String string) {
        try {
            return nf.parse(string).floatValue();
        } catch (ParseException e) {
            return null;
        }
    }
}