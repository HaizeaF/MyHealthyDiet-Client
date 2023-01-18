/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import java.io.Serializable;
import java.util.Date;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Sendoa
 */
@XmlRootElement
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private SimpleIntegerProperty user_id;

    private SimpleStringProperty login;

    private SimpleStringProperty email;

    private SimpleStringProperty fullName;
    
    private SimpleObjectProperty<StatusEnum> status;
    
    private SimpleObjectProperty<PrivilegeEnum> privilege;

    private SimpleStringProperty password;
    
    private SimpleObjectProperty lastPasswordChange;

    public User(Integer user_id, String login, String email, String fullName, StatusEnum status,
                PrivilegeEnum privilege, String password, Date lastPasswordChange) {
        this.user_id = new SimpleIntegerProperty(user_id);
        this.login = new SimpleStringProperty(login);
        this.email = new SimpleStringProperty(email);
        this.fullName = new SimpleStringProperty(fullName);
        this.status = new SimpleObjectProperty<>(status);
        this.privilege = new SimpleObjectProperty<>(privilege);
        this.password = new SimpleStringProperty(password);
        this.lastPasswordChange = new SimpleObjectProperty(lastPasswordChange);
    }
    
    public User() {
    }


    public void setUser_id(Integer user_id) {
        this.user_id.set(user_id);
    }

    public Integer getUser_id() {
        return user_id.get();
    }

    public void setLogin(String login) {
        this.login.set(login); 
    }

    public String getLogin() {
        return login.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getEmail() {
        return email.get();
    }

    public void setFullName(String fullName) {
        this.fullName.set(fullName);
    }

    public String getFullName() {
        return fullName.get();
    }

    public void setStatus(StatusEnum status) {
        this.status.set(status);
    }

    public StatusEnum getStatus() {
        return status.get();
    }

    public void setPrivilege(PrivilegeEnum privilege) {
        this.privilege.set(privilege);
    }

    public PrivilegeEnum getPrivilege() {
        return privilege.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public String getPassword() {
        return password.get();
    }

    public void setLastPasswordChange(Date lastPasswordChange) {
        this.lastPasswordChange.set(lastPasswordChange);
    }

    public Date getLastPasswordChange() {
        return (Date) lastPasswordChange.get();
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (user_id != null ? user_id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.user_id == null && other.user_id != null) || (this.user_id != null && !this.user_id.equals(other.user_id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return super.toString();
    }
    
}
