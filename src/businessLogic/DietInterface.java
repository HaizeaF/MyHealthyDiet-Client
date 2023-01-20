package businessLogic;

import javax.ws.rs.WebApplicationException;
import objects.Diet;

/**
 *
 * @author JulenB
 */
public interface DietInterface {

    public <T> T findAllDiets_XML(Class<T> responseType) throws WebApplicationException;

    public <T> T findAllDiets_JSON(Class<T> responseType) throws WebApplicationException;

    public <T> T findAllDietsByName_XML(Class<T> responseType, String name) throws WebApplicationException;

    public <T> T findAllDietsByName_JSON(Class<T> responseType, String name) throws WebApplicationException;

    public void edit_XML(Diet diet) throws WebApplicationException;

    public void edit_JSON(Diet diet) throws WebApplicationException;

    public <T> T findById_XML(Class<T> responseType, String id) throws WebApplicationException;

    public <T> T findById_JSON(Class<T> responseType, String id) throws WebApplicationException;

    public <T> T findAllDietsByGoal_XML(Class<T> responseType, String goal) throws WebApplicationException;

    public <T> T findAllDietsByGoal_JSON(Class<T> responseType, String goal) throws WebApplicationException;

    public void create_XML(Diet diet) throws WebApplicationException;

    public void create_JSON(Diet diet) throws WebApplicationException;

    public void remove(String id) throws WebApplicationException;
}
