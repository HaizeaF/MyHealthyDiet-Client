/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author Sendoa
 */
public interface TipInterface {
    
    public void edit(Object requestEntity) throws ClientErrorException;
    
    public <T> T find(Class<T> responseType, String id) throws ClientErrorException;
    
    public void create(Object requestEntity) throws ClientErrorException;
    
    public <T> T findAll(GenericType<T> responseType) throws ClientErrorException;
    
    public void remove(String id) throws ClientErrorException;
    
    public <T> T findByType(Class<T> responseType, String type) throws ClientErrorException;
    
}
