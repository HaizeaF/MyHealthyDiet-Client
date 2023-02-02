/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import objects.Ingredient;

/**
 * Interface of the ingredient restful
 * @author Mikel
 */
public interface IngredientInterface {
    
    /**
     * This method sends an Ingredient to the server to be modified in XML format.
     * @param requestEntity entity to be modified.
     * @throws WebApplicationException Exception to be catched.
     */
    public void edit_XML(Ingredient requestEntity) throws WebApplicationException;
   
    /**
     * This method sends an ingredient to find it in the database by id.
     * @param <T> type of the class.
     * @param responseType type of the class expected.
     * @param id id of the object to find.
     * @return the object finded.
     * @throws WebApplicationException  Exception to be catched..
     */
    public <T> T find_XML(Class<T> responseType, Integer id) throws WebApplicationException;
    
    /**
     * This method creates Ingredients.
     * @param requestEntity Ingredient that is sending to the server
     * @throws WebApplicationException Exception to be catched.
     */
    public void create_XML(Ingredient requestEntity) throws WebApplicationException;
    
    /**
     * This method shows all ingredients.
     * @param <T> type of the class.
     * @param responseType type of the class expected.
     * @return the object finded.
     * @throws WebApplicationException Exception to be catched.
     */
    public <T> T findAll_XML(GenericType<T> responseType) throws WebApplicationException;
    
    /**
     * This method deletes an ingredient from the server.
     * @param id Id of the ingredient to be deleted.
     * @throws WebApplicationException Exception to be catched.
     */
    public void remove(Integer id) throws WebApplicationException;
    
    /**
     * This method find all ingredients with the name.
     * @param <T> type of the class.
     * @param responseType type of the class expected.
     * @param ingredientName name to search
     * @return the object finded.
     * @throws WebApplicationException Exception to be catched.
     */
    public <T> T findIngredientsByName_XML(GenericType<T> responseType, String ingredientName) throws WebApplicationException;
}
