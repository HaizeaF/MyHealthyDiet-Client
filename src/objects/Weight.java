/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import java.io.Serializable;
import java.util.Date;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 *
 * @author Mikel
 */
public class Weight implements Serializable {

    private static final long serialVersionUID = 1L;
    private SimpleIntegerProperty weight_id;
    
    /**
     * Weight of the client in kg
     */
    private SimpleFloatProperty weight;
    
    /**
     * Date that the client saved his weight
     */   
    private SimpleObjectProperty<Date> date;
    private SimpleObjectProperty<Client> client;

    public Weight(SimpleFloatProperty weight, SimpleObjectProperty<Date> date, SimpleIntegerProperty weight_id) {
        this.weight_id = weight_id;
        this.weight = weight;
        this.date = date;
    }

    public Weight() {

    }

    public SimpleIntegerProperty getWeight_id() {
        return weight_id;
    }

    public void setWeight_id(SimpleIntegerProperty weight_id) {
        this.weight_id = weight_id;
    }

    public SimpleFloatProperty getWeight() {
        return weight;
    }

    public void setWeight(SimpleFloatProperty weight) {
        this.weight = weight;
    }

    public SimpleObjectProperty<Date> getDate() {
        return date;
    }

    public void setDate(SimpleObjectProperty<Date> date) {
        this.date = date;
    }

    public SimpleObjectProperty<Client> getClient() {
        return client;
    }

    public void setClient(SimpleObjectProperty<Client> client) {
        this.client = client;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (weight_id != null ? weight_id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Weight)) {
            return false;
        }
        Weight other = (Weight) object;
        if ((this.weight_id == null && other.weight_id != null) || (this.weight_id != null && !this.weight_id.equals(other.weight_id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return super.toString();
    }
    
}
