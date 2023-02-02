/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import businessLogic.IngredientInterface;
import java.util.ResourceBundle;
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
     * @throws WebApplicationException Exception to be catched.
     */
    @Override
    public void edit_XML(Ingredient requestEntity) throws WebApplicationException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML),
                Ingredient.class);
    }
    
    /**
     * This method sends an ingredient to find it in the database by id.
     * @param <T> type of the class.
     * @param responseType type of the class expected.
     * @param id id of the object to find.
     * @return the object finded.
     * @throws WebApplicationException  Exception to be catched..
     */
    @Override
    public <T> T find_XML(Class<T> responseType, Integer id) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("ingredient/{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * This method creates Ingredients.
     * @param requestEntity Ingredient that is sending to the server
     * @throws WebApplicationException Exception to be catched.
     */
    @Override
    public void create_XML(Ingredient requestEntity) throws WebApplicationException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML),
                Ingredient.class);
    }

    /**
     * This method shows all ingredients.
     * @param <T> type of the class.
     * @param responseType type of the class expected.
     * @return the object finded.
     * @throws WebApplicationException Exception to be catched.
     */
    @Override
    public <T> T findAll_XML(GenericType<T> responseType) throws WebApplicationException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * This method deletes an ingredient from the server.
     * @param id Id of the ingredient to be deleted.
     * @throws WebApplicationException Exception to be catched.
     */
    @Override
    public void remove(Integer id) throws WebApplicationException {
        webTarget.path(java.text.MessageFormat.format("ingredient/{0}", new Object[]{id})).request().delete(Ingredient.class);
    }
    
    /**
     * This method find all ingredients with the name.
     * @param <T> type of the class.
     * @param responseType type of the class expected.
     * @param ingredientName name to search
     * @return the object finded.
     * @throws WebApplicationException Exception to be catched.
     */
    @Override
    public <T> T findIngredientsByName_XML(GenericType<T> responseType, String ingredientName) throws WebApplicationException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{ingredientName}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }
    
    /**
     * This method close Restful connection.
     */
    public void close() {
        client.close();
    }

}
