/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Sendoa
 */
@XmlRootElement
public class Tip implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Integer tip_id;

    private String tipText;
    
    private TipTypeEnum type;
    
    private Diet diet;

    public Tip(Integer tip_id, String tipText, TipTypeEnum type) {
        this.tip_id = tip_id;
        this.tipText = tipText;
        this.type = type;
    }
    
    public Tip() {
    }


    public void setTip_id(Integer tip_id) {
        this.tip_id = tip_id;
    }

    public Integer getTip_id() {
        return tip_id;
    }

    public void setTipText(String tipText) {
        this.tipText = tipText;
    }

    public String getTipText() {
        return tipText;
    }

    public void setType(TipTypeEnum type) {
        this.type = type;
    }

    public TipTypeEnum getType() {
        return type;
    }
    
    public Diet getDiet() {
        return diet;
    }

    public void setDiet(Diet diet) {
        this.diet = diet;
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
