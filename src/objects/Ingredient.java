/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import java.io.Serializable;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Mikel
 */

public class Ingredient implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /**
     * Id of the entity Ingredient
     */
    private SimpleIntegerProperty ingredient_id;
    
    /**
     * Name of the ingredient
     */
    private SimpleStringProperty ingredientName;
    
    /**
     * Enumeration for the type of food
     */
    private SimpleObjectProperty<FoodTypeEnum> foodType;
    
    /**
     * List to join plate with ingredient
     */
    private SimpleListProperty<Plate> plates;
    
    /**
     * Boolean that shows if it is in season or if is not
     */
    private SimpleBooleanProperty isInSeason;
    
    private SimpleFloatProperty waterIndex;
    
    public Ingredient(SimpleIntegerProperty ingredient_id, SimpleStringProperty ingredientName, SimpleObjectProperty<FoodTypeEnum> foodType, SimpleListProperty<Plate> plates, SimpleBooleanProperty isInSeason, SimpleFloatProperty waterIndex) {
        this.ingredient_id = ingredient_id;
        this.ingredientName = ingredientName;
        this.foodType = foodType;
        this.plates = plates;
        this.isInSeason = isInSeason;
        this.waterIndex = waterIndex;
    }
    
    public Ingredient() {
    }

    public SimpleIntegerProperty getIngredient_id() {
        return ingredient_id;
    }

    public void setIngredient_id(SimpleIntegerProperty ingredient_id) {
        this.ingredient_id = ingredient_id;
    }

    public SimpleStringProperty getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(SimpleStringProperty ingredientName) {
        this.ingredientName = ingredientName;
    }

    public SimpleObjectProperty<FoodTypeEnum> getFoodType() {
        return foodType;
    }

    public void setFoodType(SimpleObjectProperty<FoodTypeEnum> foodType) {
        this.foodType = foodType;
    }

    public SimpleListProperty<Plate> getPlates() {
        return plates;
    }

    public void setPlates(SimpleListProperty<Plate> plates) {
        this.plates = plates;
    }

    public SimpleBooleanProperty getIsInSeason() {
        return isInSeason;
    }

    public void setIsInSeason(SimpleBooleanProperty isInSeason) {
        this.isInSeason = isInSeason;
    }

    public SimpleFloatProperty getWaterIndex() {
        return waterIndex;
    }

    public void setWaterIndex(SimpleFloatProperty waterIndex) {
        this.waterIndex = waterIndex;
    }
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ingredient_id != null ? ingredient_id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ingredient)) {
            return false;
        }
        Ingredient other = (Ingredient) object;
        if ((this.ingredient_id == null && other.ingredient_id != null) || (this.ingredient_id != null && !this.ingredient_id.equals(other.ingredient_id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return super.toString();
    }
    
}
