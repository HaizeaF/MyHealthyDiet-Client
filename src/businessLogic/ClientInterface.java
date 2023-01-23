/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;
import objects.ClientOBJ;

/**
 *
 * @author Sendoa
 */
public interface ClientInterface {
    
    public <T> T findClientById(Class<T> responseType, String id) throws ClientErrorException;
    
    public <T> T findClientByStatus(Class<T> responseType, String status) throws ClientErrorException;
    
    public void editPassword(ClientOBJ requestEntity) throws ClientErrorException;
    
    public void edit(ClientOBJ requestEntity) throws ClientErrorException;
    
    public <T> T findClientByLogin(Class<T> responseType, String usrLogin) throws ClientErrorException;
    
    public void create(ClientOBJ requestEntity) throws ClientErrorException;
    
    public <T> T findClientBySearch(Class<T> responseType, String usrValue) throws ClientErrorException;
    
    public <T> T findAll(GenericType<T> responseType) throws ClientErrorException;
    
    public void remove(Integer id) throws ClientErrorException;
    
}
