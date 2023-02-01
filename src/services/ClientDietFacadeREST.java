/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import exceptions.BusinessLogicException;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

/**
 * Jersey REST client generated for REST resource:ClientDietFacadeREST
 * [clientdiet]<br>
 * USAGE:
 * <pre>
 *        ClientDietFacadeREST client = new ClientDietFacadeREST();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author JulenB
 */
public class ClientDietFacadeREST {

    private final WebTarget webTarget;
    private final Client client;
    //Files where URL to access is.
    private final ResourceBundle bundle = ResourceBundle.getBundle("files/URLCredentials");
    //URL of the resource to access.
    private final String BASE_URI = bundle.getString("BASE_URI");
    /**
     * Log that gives more info when app is running.
     */
    private static final Logger LOGGER = Logger.getLogger("DietFacadeRESTClient.class");

    public ClientDietFacadeREST() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("clientdiet");
    }
    
    /**
     * This method updates an existing clientdiet relation.
     * @param requestEntity Any type of object.
     * @throws BusinessLogicException Exception thrown if any error occurs while processing.
     */
    public void edit_XML(Object requestEntity) throws BusinessLogicException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
        
    }
    
    /**
     * This method return an generic type param with an active diet asigned to a client.
     * @param <T> A generic type return.
     * @param responseType A generic respose type that indicates in which type of data you want to save the active diet asigned to a client.
     * @param user_id The user_id to search it active diet.
     * @return A generic type return with the active diet asigned to a client.
     * @throws BusinessLogicException Exception thrown if any error occurs while processing.
     */
    public <T> T findClientDietRelationIsActive_XML(GenericType< T> responseType, String user_id) throws BusinessLogicException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("findClientDietRelationIsActive/{0}", new Object[]{user_id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }
    
    /**
     * This method return an generic type param with all client diets.
     * @param <T> A generic type return.
     * @param responseType A generic respose type that indicates in which type of data you want to save all client diets.
     * @param user_id The user_id to search all client diets.
     * @return A generic type return with all client diets.
     * @throws BusinessLogicException Exception thrown if any error occurs while processing.
     */
    public <T> T find_XML(GenericType< T> responseType, String user_id) throws BusinessLogicException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("findAllClientDiets/{0}", new Object[]{user_id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * This method creates a ClientDiet relation.
     * @param requestEntity Any type of object.
     * @throws BusinessLogicException Exception thrown if any error occurs while processing.
     */
    public void create_XML(Object requestEntity) throws BusinessLogicException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }
    
    /**
     * This method return an generic type param with all clients diets.
     * @param <T> A generic type return.
     * @param responseType A generic respose type that indicates in which type of data you want to save all clients diets.
     * @return A generic type return with all clients diets.
     * @throws BusinessLogicException Exception thrown if any error occurs while processing.
     */
    public <T> T findAll_C_D_XML(GenericType< T> responseType) throws BusinessLogicException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }
    
    /**
     * This method removes a ClientDiet relation.
     * @param user_id The user_id to search the client diet relation.
     * @throws BusinessLogicException Exception thrown if any error occurs while processing.
     */
    public void remove(String user_id) throws BusinessLogicException {
        webTarget.path(java.text.MessageFormat.format("findClientDietsRelation/{0}", new Object[]{user_id})).request().delete();
    }
    
    /**
     * This method return an generic type param with all disable diets asigned to a client.
     * @param <T> A generic type return.
     * @param responseType A generic respose type that indicates in which type of data you want to save all disable diets asigned to a client.
     * @param user_id The user_id to search all disable diets asigned to a client.
     * @return A generic type return with all disable diets asigned to a client.
     * @throws BusinessLogicException Exception thrown if any error occurs while processing.
     */
    public <T> T findClientDietsRelation_XML(GenericType< T> responseType, String user_id) throws BusinessLogicException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("findClientDietsRelation/{0}", new Object[]{user_id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    public void close() {
        client.close();
    }

}
