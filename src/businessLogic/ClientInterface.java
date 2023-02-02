/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import exceptions.BusinessLogicException;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;
import objects.ClientOBJ;
import objects.StatusEnum;

/**
 *
 * @author Sendoa
 */
public interface ClientInterface {
    
    public <T> T findClientById(GenericType<T> responseType, String id) throws BusinessLogicException;
    
    public <T> T findClientByStatus(GenericType<T> responseType, String status) throws BusinessLogicException;
    
    public void editPassword(ClientOBJ requestEntity) throws BusinessLogicException;
    
    public void edit(ClientOBJ requestEntity) throws BusinessLogicException;
    
    public void recoverPassword(ClientOBJ requestEntity) throws BusinessLogicException;
    
    public <T> T findClientByLogin(GenericType<T> responseType, String usrLogin) throws BusinessLogicException;
    
    public void create(ClientOBJ requestEntity) throws BusinessLogicException;
    
    public <T> T findClientBySearch(GenericType<T> responseType, String usrValue) throws BusinessLogicException;
    
    public <T> T findAll(GenericType<T> responseType) throws BusinessLogicException;
    
    public <T> T findClientByEmail(GenericType<T> responseType, String usrEmail) throws BusinessLogicException;
    
    public void remove(Integer id) throws BusinessLogicException;
    
}
