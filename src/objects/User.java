/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Sendoa
 */
@XmlRootElement
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Integer user_id;

    private String login;

    private String email;

    private String fullName;
    
    private StatusEnum status;
    
    private PrivilegeEnum privilege;

    private String password;
    
    private Date lastPasswordChange;

    public User(Integer user_id, String login, String email, String fullName, StatusEnum status,
                PrivilegeEnum privilege, String password, Date lastPasswordChange) {
        this.user_id = user_id;
        this.login = login;
        this.email = email;
        this.fullName = fullName;
        this.status = status;
        this.privilege = privilege;
        this.password = password;
        this.lastPasswordChange = lastPasswordChange;
    }
    
    public User() {
    }


    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setPrivilege(PrivilegeEnum privilege) {
        this.privilege = privilege;
    }

    public PrivilegeEnum getPrivilege() {
        return privilege;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setLastPasswordChange(Date lastPasswordChange) {
        this.lastPasswordChange = lastPasswordChange;
    }

    public Date getLastPasswordChange() {
        return lastPasswordChange;
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
