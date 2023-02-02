package businessLogic;

import exceptions.BusinessLogicException;
import javax.ws.rs.core.GenericType;
import objects.Plate;

/**
 * PlateInterface that implements the methods for PlateRESTClient
 * @author HaizeaF
 */
public interface PlateInterface {
    public void edit_XML(Plate requestEntity) throws BusinessLogicException;

    public <T> T findPlatesByMealType_XML(GenericType<T> responseType, String mealType) throws BusinessLogicException;

    public <T> T find_XML(Class<T> responseType, Integer id) throws BusinessLogicException;

    public <T> T findPlatesByName_XML(GenericType<T> responseType, String plateName) throws BusinessLogicException;

    public void create_XML(Plate requestEntity) throws BusinessLogicException;

    public <T> T findPlatesIfVegetarian_XML(GenericType<T> responseType) throws BusinessLogicException;

    public <T> T findAll_XML(GenericType<T> responseType) throws BusinessLogicException;

    public void remove(Integer id) throws BusinessLogicException;
}
