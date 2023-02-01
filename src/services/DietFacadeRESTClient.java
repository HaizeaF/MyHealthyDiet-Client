package services;

import businessLogic.DietInterface;
import exceptions.BusinessLogicException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import objects.Diet;

/**
 * @author JulenB
 * This class implements the DietInteface methods.
 */
public class DietFacadeRESTClient implements DietInterface {

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

    public DietFacadeRESTClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("diet");
    }

    /**
     * This method return an generic type param with all diets data.
     * @param <T> A generic type return.
     * @param responseType A generic respose type that indicates in which type of data you want to save all the diets.
     * @return A generic type return with all diets data.
     * @throws BusinessLogicException Exception thrown if any error occurs while processing.
     */
    @Override
    public <T> T findAllDiets_XML(GenericType<T> responseType) throws BusinessLogicException {
        try {
            WebTarget resource = webTarget;
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE,"Diet: Exception finding diets, {0}",ex.getMessage());
            throw new BusinessLogicException("Error finding diets:\n" + ex.getMessage());
        }
    }

    /**
     * This method return an generic type param with all diets data filtered by its name.
     * @param <T> A generic type return.
     * @param responseType A generic respose type that indicates in which type of data you want to save all the diets filtered by its name.
     * @param name The name to filter the diets.
     * @return A generic type return with all diets data filtered by its name.
     * @throws BusinessLogicException Exception thrown if any error occurs while processing.
     */
    @Override
    public <T> T findAllDietsByName_XML(GenericType< T> responseType, String name) throws BusinessLogicException {
        try {
            WebTarget resource = webTarget;
            resource = resource.path(java.text.MessageFormat.format("findAllByName/{0}", new Object[]{name}));
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE,"Diet: Exception finding diets by name, {0}",ex.getMessage());
            throw new BusinessLogicException("Error finding diets by name:\n" + ex.getMessage());
        }
    }

    /**
     * This method updates data from an existing diet.
     * @param diet The diet to be updated.
     * @throws BusinessLogicException Exception thrown if any error occurs while processing.
     */
    @Override
    public void edit_XML(Diet diet) throws BusinessLogicException {
        try {
            webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML)
                    .put(javax.ws.rs.client.Entity.entity(diet, javax.ws.rs.core.MediaType.APPLICATION_XML),
                            diet.getClass());
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE,"Diet: Exception updating diet, {0}",ex.getMessage());
            throw new BusinessLogicException("Error updating diet:\n" + ex.getMessage());
        }
    }

    /**
     * This method return an generic type param with all diets data filtered by its id.
     * @param <T> A generic type return.
     * @param responseType A generic respose type that indicates in which type of data you want to save all the diets filtered by its id.
     * @param id The id used to filter the diets.
     * @return A generic type return with all diets data filtered by its id.
     * @throws BusinessLogicException Exception thrown if any error occurs while processing.
     */
    @Override
    public <T> T findById_XML(Class< T> responseType, String id) throws BusinessLogicException {
        try {
            WebTarget resource = webTarget;
            resource = resource.path(java.text.MessageFormat.format("findDietById/{0}", new Object[]{id}));
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE,"Diet: Exception finding diets by id, {0}",ex.getMessage());
            throw new BusinessLogicException("Error finding diets by id:\n" + ex.getMessage());
        }
    }
    
    /**
     * This method return an generic type param with all diets data filtered by its goal.
     * @param <T> A generic type return.
     * @param responseType A generic respose type that indicates in which type of data you want to save all the diets filtered by its goal.
     * @param goal The goal used to filter the diets.
     * @return A generic type return with all diets data filtered by its goal.
     * @throws BusinessLogicException Exception thrown if any error occurs while processing.
     */
    @Override
    public <T> T findAllDietsByGoal_XML(GenericType< T> responseType, String goal) throws BusinessLogicException {
        try {
            WebTarget resource = webTarget;
            resource = resource.path(java.text.MessageFormat.format("findAllByGoal/{0}", new Object[]{goal}));
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE,"Diet: Exception finding diets by goal, {0}",ex.getMessage());
            throw new BusinessLogicException("Error finding diets by goal:\n" + ex.getMessage());
        }
    }
    
    /**
     * This method creates a diet.
     * @param diet The diet to be created.
     * @throws BusinessLogicException Exception thrown if any error occurs while processing.
     */
    @Override
    public void create_XML(Diet diet) throws BusinessLogicException {
        try {
            webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(diet, javax.ws.rs.core.MediaType.APPLICATION_XML),
                    Diet.class);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE,"Diet: Exception creating a diet, {0}",ex.getMessage());
            throw new BusinessLogicException("Error creating a diet:\n" + ex.getMessage());
        }
    }
    
    /**
     * This method removes a diet.
     * @param id The id of the diet be remove.
     * @throws BusinessLogicException Exception thrown if any error occurs while processing.
     */
    @Override
    public void remove(Integer id) throws BusinessLogicException {
        try {
            webTarget.path(java.text.MessageFormat.format("findDietById/{0}", new Object[]{id})).request().delete(Diet.class);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE,"Diet: Exception removing a diet, {0}",ex.getMessage());
            throw new BusinessLogicException("Error removing a diet:\n" + ex.getMessage());
        }
    }

    public void close() {
        client.close();
    }

}
