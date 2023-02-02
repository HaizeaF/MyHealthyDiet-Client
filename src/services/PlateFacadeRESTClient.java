package services;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import objects.Plate;
import businessLogic.PlateInterface;
import exceptions.BussinessLogicException;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.ws.rs.core.GenericType;

/**
 * Jersey REST client generated for REST resource:PlateFacadeREST
 * [entities.plate]<br>
 * USAGE:
 * <pre>
 *        PlateFacadeREST client = new PlateFacadeREST();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author haize
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

    @Override
    public void edit_XML(Plate requestEntity) throws BussinessLogicException {
        try {
            LOGGER.info("Editing plate.");
            webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), Plate.class);
        } catch (Exception e) {
            throw new BussinessLogicException("An error occurred while trying to edit the plate: " + e.getMessage());
        }
    }

    @Override
    public <T> T findPlatesByMealType_XML(GenericType<T> responseType, String mealType) throws BussinessLogicException {
        try {
            LOGGER.info("Finding plates by meal type.");
            WebTarget resource = webTarget;
            resource = resource.path(java.text.MessageFormat.format("findByMealType/{0}", new Object[]{mealType}));
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
        } catch (Exception e) {
            throw new BussinessLogicException("An error occurred while trying to find the plates by meal type: " + e.getMessage());
        }
    }

    @Override
    public <T> T find_XML(Class<T> responseType, Integer id) throws BussinessLogicException {
        try {
            LOGGER.info("Finding plate by id.");
            WebTarget resource = webTarget;
            resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
        } catch (Exception e) {
            throw new BussinessLogicException("An error occurred while trying to find a plate by id: " + e.getMessage());
        }
    }

    @Override
    public <T> T findPlatesByName_XML(GenericType<T> responseType, String plateName) throws BussinessLogicException {
        try {
            LOGGER.info("Finding plates by name.");
            WebTarget resource = webTarget;
            resource = resource.path(java.text.MessageFormat.format("findByName/{0}", new Object[]{plateName}));
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
        } catch (Exception e) {
            throw new BussinessLogicException("An error occurred while trying to find the plates by name: " + e.getMessage());
        }
    }

    @Override
    public void create_XML(Plate requestEntity) throws BussinessLogicException {
        try {
            LOGGER.info("Creating plate.");
            webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), Plate.class);
        } catch (Exception e) {
            throw new BussinessLogicException("An error occurred while trying to create a new plate: " + e.getMessage());
        }
    }

    @Override
    public <T> T findPlatesIfVegetarian_XML(GenericType<T> responseType) throws BussinessLogicException {
        try {
            LOGGER.info("Finding vegetarian plates.");
            WebTarget resource = webTarget;
            resource = resource.path("findVegetarians");
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
        } catch (Exception e) {
            throw new BussinessLogicException("An error occurred while trying to find the vegetarian plates: " + e.getMessage());
        }
    }

    @Override
    public <T> T findAll_XML(GenericType<T> responseType) throws BussinessLogicException {
        try {
            LOGGER.info("Finding all plates.");
            WebTarget resource = webTarget;
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
        } catch (Exception e) {
            throw new BussinessLogicException("An error occurred while trying to find all the plates: " + e.getMessage());
        }
    }

    @Override
    public void remove(Integer id) throws BussinessLogicException {
        try {
            LOGGER.info("Removing plate.");
            webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request().delete(Plate.class);
        } catch (Exception e) {
            throw new BussinessLogicException("An error occurred while trying to remove the plate: " + e.getMessage());
        }
    }

    public void close() {
        client.close();
    }

}
