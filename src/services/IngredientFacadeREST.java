/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import businessLogic.IngredientInterface;
import exceptions.BusinessLogicException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import objects.Ingredient;

/**
 * Jersey REST client generated for REST resource:IngredientFacadeREST
 * [entities.ingredient]<br>
 * USAGE:
 * <pre>
 *        IngredientFacadeREST client = new IngredientFacadeREST();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author Mikel
 */
public class IngredientFacadeREST implements IngredientInterface {

    private WebTarget webTarget;
    private Client client;
    private final ResourceBundle bundle = ResourceBundle.getBundle("files/URLCredentials");
    private final String BASE_URI = bundle.getString("BASE_URI");
    
    private static final Logger LOGGER = Logger.getLogger("IngredientFacadeREST");
    
    /**
     * Constructor of the restfull ingredient.
     */
    public IngredientFacadeREST() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("ingredient");
    }
    
    /**
     * This method sends an Ingredient to the server to be modified in XML format.
     * @param requestEntity entity to be modified.
     * @throws exceptions.BusinessLogicException Exception to be catched.
     */
    @Override
    public void edit_XML(Ingredient requestEntity) throws BusinessLogicException {
        try{
            webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML),
                Ingredient.class);
        }catch (Exception ex) {
            LOGGER.log(Level.SEVERE,"Ingredient: Exception editing ingredients, {0}",ex.getMessage());
            throw new BusinessLogicException("Error editing ingredients:\n" + ex.getMessage());
        }    
    }
    
    /**
     * This method sends an ingredient to find it in the database by id.
     * @param <T> type of the class.
     * @param responseType type of the class expected.
     * @param id id of the object to find.
     * @return the object finded.
     * @throws BusinessLogicException  Exception to be catched..
     */
    @Override
    public <T> T find_XML(Class<T> responseType, Integer id) throws BusinessLogicException {
        try{
            WebTarget resource = webTarget;
            resource = resource.path(java.text.MessageFormat.format("ingredient/{0}", new Object[]{id}));
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
        }catch (Exception ex) {
            LOGGER.log(Level.SEVERE,"Ingredient: Exception finding ingredients, {0}",ex.getMessage());
            throw new BusinessLogicException("Error finding ingredients:\n" + ex.getMessage());
        }
    }

    /**
     * This method creates Ingredients.
     * @param requestEntity Ingredient that is sending to the server
     * @throws BusinessLogicException Exception to be catched.
     */
    @Override
    public void create_XML(Ingredient requestEntity) throws BusinessLogicException {
        try{
            webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML),
                Ingredient.class);
        }catch (Exception ex) {
            LOGGER.log(Level.SEVERE,"Ingredient: Exception creating ingredients, {0}",ex.getMessage());
            throw new BusinessLogicException("Error creating ingredients:\n" + ex.getMessage());
        }
    }

    /**
     * This method shows all ingredients.
     * @param <T> type of the class.
     * @param responseType type of the class expected.
     * @return the object finded.
     * @throws BusinessLogicException Exception to be catched.
     */
    @Override
    public <T> T findAll_XML(GenericType<T> responseType) throws BusinessLogicException {
        try{
            WebTarget resource = webTarget;
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
        }catch (Exception ex) {
            LOGGER.log(Level.SEVERE,"Ingredient: Exception finding ingredients, {0}",ex.getMessage());
            throw new BusinessLogicException("Error finding ingredients:\n" + ex.getMessage());
        }
    }

    /**
     * This method deletes an ingredient from the server.
     * @param id Id of the ingredient to be deleted.
     * @throws BusinessLogicException Exception to be catched.
     */
    @Override
    public void remove(Integer id) throws BusinessLogicException {
        try{
            webTarget.path(java.text.MessageFormat.format("ingredient/{0}", new Object[]{id})).request().delete(Ingredient.class);
        }catch (Exception ex) {
            LOGGER.log(Level.SEVERE,"Ingredient: Exception removing ingredients, {0}",ex.getMessage());
            throw new BusinessLogicException("Error removing ingredients:\n" + ex.getMessage());
        }
    }
    
    /**
     * This method find all ingredients with the name.
     * @param <T> type of the class.
     * @param responseType type of the class expected.
     * @param ingredientName name to search
     * @return the object finded.
     * @throws BusinessLogicException Exception to be catched.
     */
    @Override
    public <T> T findIngredientsByName_XML(GenericType<T> responseType, String ingredientName) throws BusinessLogicException {
        try{
            WebTarget resource = webTarget;
            resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{ingredientName}));
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
        }catch (Exception ex) {
            LOGGER.log(Level.SEVERE,"Ingredient: Exception finding ingredients by name, {0}",ex.getMessage());
            throw new BusinessLogicException("Error finding ingredients:\n" + ex.getMessage());
        }
    }
    
    /**
     * This method close Restful connection.
     */
    public void close() {
        client.close();
    }

}
