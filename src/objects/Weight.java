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
 * Class Object Weight.
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

    public Weight(Float weight, Date date, Client client) {
        this.weight = new SimpleFloatProperty(weight);
        this.date = new SimpleObjectProperty<>(date);
        this.client = new SimpleObjectProperty<>(client);
    }

    public Weight() {

    }

    public Integer getWeight_id() {
        return weight_id.get();
    }

    public void setWeight_id(Integer weight_id) {
        this.weight_id.set(weight_id);
    }

    public Float getWeight() {
        return weight.get();
    }

    public void setWeight(Float weight) {
        this.weight.set(weight);
    }

    public Date getDate() {
        return date.get();
    }

    public void setDate(Date date) {
        this.date.set(date);
    }

    public Client getClient() {
        return client.get();
    }

    public void setClient(Client client) {
        this.client.set(client);
    }
    
    /**
     * This method hashes the id.
     * @return id hashed 
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (weight_id != null ? weight_id.hashCode() : 0);
        return hash;
    }
    
    /**
     * This method compares two wieghts.
     * @param object Weight to compare
     * @return Boolean value depending on the result of the comparision.
     */
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
    
    /**
     * This methods gives a weight object.
     * @return String with weight data.
     */
    @Override
    public String toString() {
        return super.toString();
    }
    
}
