package objects;

import java.io.Serializable;
import java.util.List;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author HaizeaF
 * Class that contains the information of a plate.
 */
public class Plate implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /**
     * Identifier of the plate.
     */
    private SimpleIntegerProperty plate_id;

    /**
     * Name of the plate.
     */
    private SimpleStringProperty plateName;

    /**
     * KiloCalories in the plate.
     */
    private SimpleFloatProperty calories;

    /**
     * Carbohydrates in the plate.
     */
    private SimpleFloatProperty carbohydrates;

    /**
     * Lipids in the plate.
     */
    private SimpleFloatProperty lipids;

    /**
     * Proteins in the plate.
     */
    private SimpleFloatProperty proteins;

    /**
     * What meal of the day is.
     */
    private SimpleObjectProperty<MealEnum> mealType;
    
    /**
     * Defines if the plate is vegetarian or not.
     */

    private SimpleBooleanProperty isVegetarian;
    
    /**
     * List of the ingredients the plate has.
     */
    private SimpleListProperty<Ingredient> ingredients;
    
    /**
     * List of the diets the plate is on.
     */
    private SimpleListProperty<Diet> diets;
    
    /**
     * Image of the plate.
     */
    private SimpleObjectProperty<byte[]> plateImg;

    public Plate(String plateName, Float calories, Float carbohydrates, Float lipids, Float proteins,
            MealEnum mealType, List<Ingredient> ingredients, Boolean isVegetarian, List<Diet> diets, byte[] plateImg) {
        this.plateName = new SimpleStringProperty(plateName);
        this.calories = new SimpleFloatProperty(calories);
        this.carbohydrates = new SimpleFloatProperty(carbohydrates);
        this.lipids = new SimpleFloatProperty(lipids);
        this.proteins = new SimpleFloatProperty(proteins);
        this.mealType = new SimpleObjectProperty<MealEnum>(mealType);
        this.ingredients = new SimpleListProperty(ingredients);
        this.isVegetarian = new SimpleBooleanProperty(isVegetarian);
        this.diets = new SimpleListProperty(diets);
        this.plateImg = new SimpleObjectProperty(plateImg);
    }

    public Plate() {
    }

    public void setPlate_id(Integer plate_id) {
        this.plate_id.set(plate_id);
    }

    public Integer getPlate_id() {
        return plate_id.get();
    }

    public void setPlateName(String plateName) {
        this.plateName.set(plateName);
    }

    public String getPlateName() {
        return plateName.get();
    }

    public void setCalories(Float calories) {
        this.calories.set(calories);
    }

    public Float getCalories() {
        return calories.get();
    }

    public void setCarbohydrates(Float carbohydrates) {
        this.carbohydrates.set(carbohydrates);
    }

    public Float getCarbohydrates() {
        return carbohydrates.get();
    }

    public void setLipids(Float lipids) {
        this.lipids.set(lipids);
    }

    public Boolean getIsVegetarian() {
        return isVegetarian.get();
    }

    public void setIsVegetarian(Boolean isVegetarian) {
        this.isVegetarian.set(isVegetarian);
    }

    public List<Diet> getDiets() {
        return diets.get;
    }

    public void setDiets(List<Diet> diets) {
        this.diets.set(diets);
    }

    public Float getLipids() {
        return lipids.get();
    }

    public void setProteins(Float proteins) {
        this.proteins.set(proteins);
    }

    public Float getProteins() {
        return proteins.get();
    }

    public void setMealType(MealEnum mealType) {
        this.mealType.set(mealType);
    }

    public MealEnum getMealType() {
        return mealType.get();
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients.set(ingredients);
    }

    public List<Ingredient> getIngredients() {
        return ingredients.get();
    }

    public byte[] getPlateImg() {
        return (byte[]) plateImg.get();
    }

    public void setPlateImg(byte[] plateImg) {
        this.plateImg.set(plateImg);
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (plate_id != null ? plate_id.hashCode() : 0);
        return hash;
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Plate)) {
            return false;
        }
        Plate other = (Plate) object;
        if ((this.plate_id == null && other.plate_id != null) || (this.plate_id != null && !this.plate_id.equals(other.plate_id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return super.toString();
    }

}