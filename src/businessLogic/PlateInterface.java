package businessLogic;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;
import objects.Plate;

/**
 *
 * @author haize
 */
public interface PlateInterface {
    public void edit_XML(Plate requestEntity) throws ClientErrorException;

    public <T> T findPlatesByMealType_XML(GenericType<T> responseType, String mealType) throws ClientErrorException;

    public <T> T find_XML(Class<T> responseType, Integer id) throws ClientErrorException;

    public <T> T findPlatesByName_XML(GenericType<T> responseType, String plateName) throws ClientErrorException;

    public void create_XML(Plate requestEntity) throws ClientErrorException;

    public <T> T findPlatesIfVegetarian_XML(GenericType<T> responseType) throws ClientErrorException;

    public <T> T findAll_XML(GenericType<T> responseType) throws ClientErrorException;

    public void remove(Integer id) throws ClientErrorException;
}
