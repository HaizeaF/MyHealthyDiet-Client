/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import exceptions.BusinessLogicException;
import javax.ws.rs.ClientErrorException;

/**
 *
 * @author Sendoa
 */
public interface UserInterface {
    
    public <T> T logIn(Class<T> responseType, String login, String password) throws BusinessLogicException;
}
