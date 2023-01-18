/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import java.io.Serializable;
import java.util.List;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Sendoa
 */
@XmlRootElement
public class Tip implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private SimpleIntegerProperty tip_id;

    private SimpleStringProperty tipText;
    
    private SimpleObjectProperty<TipTypeEnum> type;
    
    private SimpleObjectProperty diet;

    public Tip(Integer tip_id, String tipText, TipTypeEnum type, Diet diet) {
        this.tip_id = new SimpleIntegerProperty(tip_id);
        this.tipText = new SimpleStringProperty(tipText);
        this.type = new SimpleObjectProperty<>(type);
        this.diet = new SimpleObjectProperty<>(diet);
    }
    
    public Tip() {
    }


    public void setTip_id(Integer tip_id) {
        this.tip_id.set(tip_id);
    }

    public Integer getTip_id() {
        return tip_id.get();
    }

    public void setTipText(String tipText) {
        this.tipText.set(tipText);
    }

    public String getTipText() {
        return tipText.get();
    }

    public void setType(TipTypeEnum type) {
        this.type.set(type);
    }

    public TipTypeEnum getType() {
        return type.get();
    }
    
    public Diet getDiet() {
        return diet.get();
    }

    public void setDiet(Diet diet) {
        this.diet.set(diet);
    }

    
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tip)) {
            return false;
        }
        Tip other = (Tip) object;
        if ((this.tip_id == null && other.tip_id != null) || (this.tip_id != null && !this.tip_id.equals(other.tip_id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return super.toString();
    }
    
}
