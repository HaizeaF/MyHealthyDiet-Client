/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import cryptography.Asymmetric;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Sendoa
 */
@XmlRootElement(name = "client")
public class ClientOBJ extends User {

    private static final long serialVersionUID = 1L;
    // Age is supposed to be an Integer, due to some errors I had with the table it had to be changed to String 
    // The functionality with the Server is still the same
    private SimpleStringProperty age;

    private SimpleFloatProperty height;

    private SimpleObjectProperty<GenreEnum> genre;

    private SimpleObjectProperty<GoalEnum> goal;

    public ClientOBJ(Integer user_id, String login, String email, String fullName, StatusEnum status,
            PrivilegeEnum privilege, String password, Date lastPasswordChange, String age, Float height,
            GenreEnum genre, GoalEnum goal) {
        super(user_id, login, email, fullName, status, privilege, password, lastPasswordChange);
        this.age = new SimpleStringProperty(age);
        this.height = new SimpleFloatProperty(height);
        this.genre = new SimpleObjectProperty<>(genre);
        this.goal = new SimpleObjectProperty<>(goal);
    }

    public ClientOBJ() {
        super(0, "login", "example@mail.com", "Example Name", StatusEnum.DISABLED, PrivilegeEnum.USER, "abcd*1234", new Date(System.currentTimeMillis()));
        this.age = new SimpleStringProperty("20");
        this.height = new SimpleFloatProperty(Float.parseFloat("1.80"));
        this.genre = new SimpleObjectProperty<>(GenreEnum.NON_BINARY);
        this.goal = new SimpleObjectProperty<>(GoalEnum.MAINTAIN);
    }

    public void setAge(String age) {
        this.age.set(age);
    }

    public String getAge() {
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
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (super.getUser_id() != null ? getUser_id().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClientOBJ)) {
            return false;
        }
        ClientOBJ other = (ClientOBJ) object;
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
