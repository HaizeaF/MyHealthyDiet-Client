/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import java.util.Date;
import java.util.List;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


/**
 *
 * @author Sendoa
 */
@XmlRootElement(name="client")
public class Client extends User{

    private static final long serialVersionUID = 1L;
    private SimpleIntegerProperty age;
    
    private SimpleFloatProperty height;
    
    private SimpleObjectProperty<GenreEnum> genre;
    
    private SimpleObjectProperty<GoalEnum> goal;
    /**
     * @associates <{entities.Weight}>
     */
    private SimpleListProperty<Weight> weights;


    public Client(Integer user_id, String login, String email, String fullName, StatusEnum status,
                  PrivilegeEnum privilege, String password, Date lastPasswordChange, Integer age, Float height, 
                  GenreEnum genre, GoalEnum goal, List<Weight> weights) {
        // TODO Implement this method
        super(user_id, login, email, fullName, status, privilege, password, lastPasswordChange);
        this.age = new SimpleIntegerProperty(age);
        this.height = new SimpleFloatProperty(height);
        this.genre = new SimpleObjectProperty<>(genre);
        this.goal = new SimpleObjectProperty<>(goal);
        this.weights = new SimpleListProperty<>(FXCollections.observableList(weights));
    }


    public Client() {
    }

    public void setAge(Integer age) {
        this.age.set(age);
    }

    public Integer getAge() {
        return age.get();
    }

    public void setHeight(Float height) {
        this.height.set(height);
    }

    public Float getHeight() {
        return height.get();
    }

    public void setGenre(GenreEnum genre) {
        this.genre.set(genre);
    }

    public GenreEnum getGenre() {
        return genre.get();
    }

    public void setGoal(GoalEnum goal) {
        this.goal.set(goal);
    }

    public GoalEnum getGoal() {
        return goal.get();
    }

    public void setWeights(List<Weight> weights) {
        this.weights.set(FXCollections.observableList(weights));
    }
    
    public List<Weight> getWeights() {
        return weights.get();
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (super.getUser_id() != null ? getUser_id().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Client)) {
            return false;
        }
        Client other = (Client) object;
        if ((super.getUser_id() == null && other.getUser_id() != null) || (super.getUser_id() != null && !super.getUser_id().equals(other.getUser_id()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return super.toString();
    }
    
}
