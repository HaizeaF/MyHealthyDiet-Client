package services;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import objects.Plate;
import businessLogic.PlateInterface;
import exceptions.BusinessLogicException;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.ws.rs.core.GenericType;

/**
 * This class manages the connection with the server.
 * @author HaizeaF
 */
public class PlateFacadeRESTClient implements PlateInterface {

    private WebTarget webTarget;
    private Client client;

    private final ResourceBundle bundle = ResourceBundle.getBundle("files.URLCredentials");
    private final String BASE_URI = bundle.getString("BASE_URI");

    private static final Logger LOGGER = Logger.getLogger(PlateFacadeRESTClient.class.getName());

    public PlateFacadeRESTClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("plate");
    }
    
    /**
     * This method sends an edit request to the server.
     * @param requestEntity The plate to modify.
     * @throws BusinessLogicException This exception is thrown when there is an error with the server.
     */
    @Override
    public void edit_XML(Plate requestEntity) throws BusinessLogicException {
        try {
            LOGGER.info("Editing plate.");
            webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), Plate.class);
        } catch (Exception e) {
            throw new BusinessLogicException("An error occurred while trying to edit the plate: " + e.getMessage());
        }
    }
    
    /**
     * This method sends a request to the server to find plates by meal type.
     * @param responseType The type of object we want on response.
     * @param mealType The meal type we want to search.
     * @return The plates with the selected meal type.
     * @throws BusinessLogicException This exception is thrown when there is an error with the server.
     */
    @Override
    public <T> T findPlatesByMealType_XML(GenericType<T> responseType, String mealType) throws BusinessLogicException {
        try {
            LOGGER.info("Finding plates by meal type.");
            WebTarget resource = webTarget;
            resource = resource.path(java.text.MessageFormat.format("findByMealType/{0}", new Object[]{mealType}));
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
        } catch (Exception e) {
            throw new BusinessLogicException("An error occurred while trying to find the plates by meal type: " + e.getMessage());
        }
    }

    /**
     * This method sends a request to find a plate by id.
     * @param responseType The type of object we want on response.
     * @param id The id we want to search.
     * @return The plate with the id.
     * @throws BusinessLogicException This exception is thrown when there is an error with the server.
     */
    @Override
    public <T> T find_XML(Class<T> responseType, Integer id) throws BusinessLogicException {
        try {
            LOGGER.info("Finding plate by id.");
            WebTarget resource = webTarget;
            resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
        } catch (Exception e) {
            throw new BusinessLogicException("An error occurred while trying to find a plate by id: " + e.getMessage());
        }
    }
    
    /**
     * This method sends a request to find a plate by name.
     * @param responseType The type of object we want on response.
     * @param plateName The name we want to search.
     * @return The plate with the introduced name.
     * @throws BusinessLogicException This exception is thrown when there is an error with the server.
     */
    @Override
    public <T> T findPlatesByName_XML(GenericType<T> responseType, String plateName) throws BusinessLogicException {
        try {
            LOGGER.info("Finding plates by name.");
            WebTarget resource = webTarget;
            resource = resource.path(java.text.MessageFormat.format("findByName/{0}", new Object[]{plateName}));
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
        } catch (Exception e) {
            throw new BusinessLogicException("An error occurred while trying to find the plates by name: " + e.getMessage());
        }
    }
    
    /**
     * This method sends a request to create a plate.
     * @param requestEntity The plate we want to create.
     * @throws BusinessLogicException This exception is thrown when there is an error with the server.
     */
    @Override
    public void create_XML(Plate requestEntity) throws BusinessLogicException {
        try {
            LOGGER.info("Creating plate.");
            webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), Plate.class);
        } catch (Exception e) {
            throw new BusinessLogicException("An error occurred while trying to create a new plate: " + e.getMessage());
        }
    }
    
    /**
     * This method sends a request to find vegetarians plate.
     * @param responseType The type of object we want on response.
     * @return The vegetarian plates.
     * @throws BusinessLogicException This exception is thrown when there is an error with the server.
     */
    @Override
    public <T> T findPlatesIfVegetarian_XML(GenericType<T> responseType) throws BusinessLogicException {
        try {
            LOGGER.info("Finding vegetarian plates.");
            WebTarget resource = webTarget;
            resource = resource.path("findVegetarians");
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
        } catch (Exception e) {
            throw new BusinessLogicException("An error occurred while trying to find the vegetarian plates: " + e.getMessage());
        }
    }
    
    /**
     * This method sends a request to find all the plates.
     * @param responseType The type of object we want on response.
     * @return All the plates.
     * @throws BusinessLogicException This exception is thrown when there is an error with the server.
     */
    @Override
    public <T> T findAll_XML(GenericType<T> responseType) throws BusinessLogicException {
        try {
            LOGGER.info("Finding all plates.");
            WebTarget resource = webTarget;
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
        } catch (Exception e) {
            throw new BusinessLogicException("An error occurred while trying to find all the plates: " + e.getMessage());
        }
    }
    
    /**
     * This method sends a request to delete a plate by id.
     * @param id The id of the plate we want to delete.
     * @throws BusinessLogicException This exception is thrown when there is an error with the server.
     */
    @Override
    public void remove(Integer id) throws BusinessLogicException {
        try {
            LOGGER.info("Removing plate.");
            webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request().delete(Plate.class);
        } catch (Exception e) {
            throw new BusinessLogicException("An error occurred while trying to remove the plate: " + e.getMessage());
        }
    }

    public void close() {
        client.close();
    }

}
