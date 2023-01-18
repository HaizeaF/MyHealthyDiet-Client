package objects;

import java.io.Serializable;
import java.util.List;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *@author Julen
 * Entity that saves the information about a Diet.
 */
public class Diet implements Serializable {

    //VARIABLES
    private static final long serialVersionUID = 1L;

    /**
     * Identifier of the diet
     */
    private SimpleIntegerProperty diet_id;

    /**
     * Name of the diet.
     */
    private SimpleStringProperty dietName;

    /**
     * Description of the diet.
     */
    private SimpleStringProperty description;

    /**
     * Calories of the diet.
     */
    private SimpleFloatProperty calories;

    /**
     * Proteins of the diet.
     */
    private SimpleFloatProperty proteins;

    /**
     * Lipids of the diet.
     */
    private SimpleFloatProperty lipids;

    /**
     * GoalType of the diet (INCREASE,DECREASE,MAINTAIN).
     */
    private SimpleObjectProperty<GoalEnum> type;

    /**
     * Carbohydrates of the diet.
     */
    private SimpleFloatProperty carbohydrates;

    //RELATIONS
    /**
     * @associates <{entities.Plate}>
     */
    //List with plates that we want to get it fast.
    private SimpleListProperty<Plate> plates;

    /**
     * @associates <{entities.Tip}>
     */
    //List with tips in one diet.
    private SimpleListProperty<Tip> tips;
    
    /**
     * Img of the diet.
     */
    private SimpleObjectProperty<byte[]> dietImg;

    //CONSTRUCTORS
    public Diet(Integer diet_id, String dietName, String description, Float calories, Float proteins, Float lipids,
            GoalEnum type, Float carbohydrates, List<Plate> plates, List<Tip> tips, byte[] dietImg) {
        this.diet_id = new SimpleIntegerProperty(diet_id);
        this.dietName = new SimpleStringProperty(dietName);
        this.description = new SimpleStringProperty(description);
        this.calories = new SimpleFloatProperty(calories);
        this.proteins = new SimpleFloatProperty(calories);
        this.lipids = new SimpleFloatProperty(calories);
        this.carbohydrates = new SimpleFloatProperty(calories);
        this.type = new SimpleObjectProperty(type);
        this.plates = new SimpleListProperty(plates);
        this.tips = new SimpleListProperty(plates);
        this.dietImg = new SimpleObjectProperty(dietImg);
    }

    public Diet() {
    }

    
    //GETTERS AND SETTERS


    /**
     * Set the diet ID for this diet.
     * @param diet_id the diet ID to set.
     */
    public void setDiet_id(Integer diet_id) {
        this.diet_id.set(diet_id);
    }

    
    /**
     * Get the diet ID for this diet.
     * @return the diet ID for this diet.
     */
    public Integer getDiet_id() {
        return diet_id.get();
    }

    /**
     * Set the name of this diet.
     * @param dietName the name to set of this diet.
     */
    public void setDietName(String dietName) {
        this.dietName.set(dietName);
    }

    
    /**
     * Get the name of this diet.
     * @return the name for this diet.
     */
    public String getDietName() {
        return dietName.get();
    }

    
    /**
     * Set the description for this diet.
     * @param description the description to set of this diet.
     */
    public void setDescription(String description) {
        this.description.set(description);
    }
    
    
    /**
     * Get the description for this diet.
     * @return the description for this diet.
     */
    public String getDescription() {
        return description.get();
    }

    
    /**
     * Set the calories count for this diet.
     * @param calories the calories count to set of this diet.
     */
    public void setCalories(Float calories) {
        this.calories.set(calories);
    }
    
    
    /**
     * Get the calories count for this diet.
     * @return the calories count for this diet.
     */
    public Float getCalories() {
        return calories.get();
    }

    
    /**
     * Set the proteins count for this diet.
     * @param proteins the proteins count to set of this diet.
     */
    public void setProteins(Float proteins) {
        this.proteins.set(proteins);
    }
    
    
    /**
     * Get the proteins count for this diet.
     * @return the proteins count for this diet.
     */
    public Float getProteins() {
        return proteins.get();
    }

    
    /**
     * Set the lipids count for this diet.
     * @param lipids the lipids count to set of this diet.
     */
    public void setLipids(Float lipids) {
        this.lipids.set(lipids);
    }
    
    
    /**
     * Get the lipids count for this diet.
     * @return the lipids count for this diet.
     */
    public Float getLipids() {
        return lipids.get();
    }

    
    /**
     * Set the goal type for this diet.
     * @param type the goal type to set for this diet.
     */
    public void setType(GoalEnum type) {
        this.type.set(type);
    }
    
    
    /**
     * Get the goal type for this diet.
     * @return the goal type for this diet.
     */
    public GoalEnum getType() {
        return type.get();
    }

    
    /**
     * Set the carbohydrates count of this diet.
     * @param carbohydrates the carbohydrates count to set of this diet.
     */
    public void setCarbohydrates(Float carbohydrates) {
        this.carbohydrates.set(carbohydrates);
    }
    
    
    /**
     * Get the carbohydrates count for this diet.
     * @return the carbohydrates count for this diet.
     */
    public Float getCarbohydrates() {
        return carbohydrates.get();
    }
    
    
    /**
     * Get the tips list for this diet.
     * @return the tips list for this diet.
     */
    public List<Tip> getTips() {
        return tips.get();
    }
    
    
    /**
     * Set the tips list of this diet.
     * @param tips the tips list to set of this diet.
     */
    public void setTips(List<Tip> tips) {
        this.tips.set(tips);
    }

    
    /**
     * Set the list of plates for this diet.
     * @param plates the list of plates to set for this.
     */
    public void setPlates(List<Plate> plates) {
        this.plates.set(plates);
    }
    
    /**
     * Get the list of plates for this diet.
     * @return the list of plates for this diet.
     */
    
    public List<Plate> getPlates() {
        return plates.get();
    }
    
    /**
     * Get the image of the diet.
     * @return the image for this diet.
     */
    public byte[] getDietImg() {
        return (byte[]) dietImg.get();
    }
    
    /**
     * Set the image for this diet.
     *
     * @param dietImg the image for this diet.
     */
    public void setDietImg(byte[] dietImg) {
        this.dietImg.set(dietImg);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (diet_id != null ? diet_id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Diet)) {
            return false;
        }
        Diet other = (Diet) object;
        if ((this.diet_id == null && other.diet_id != null) || (this.diet_id != null && !this.diet_id.equals(other.diet_id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
