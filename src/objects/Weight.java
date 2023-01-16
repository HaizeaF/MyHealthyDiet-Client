/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Mikel
 */
public class Weight implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer weight_id;
    
    /**
     * Weight of the client in kg
     */
    private Float weight;
    
    /**
     * Date that the client saved his weight
     */   
    private Date date;
    private Client client;

    public Weight(Float weight, Date date, Integer weight_id) {
        this.weight_id = weight_id;
        this.weight = weight;
        this.date = date;
    }

    public Weight() {

    }
    
    public void setWeight_id(Integer weight_id) {
        this.weight_id = weight_id;
    }

    public Integer getWeight_id() {
        return weight_id;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Float getWeight() {
        return weight;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
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
