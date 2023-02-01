package businessLogic;

import exceptions.BussinessLogicException;
import javax.ws.rs.core.GenericType;
import objects.Plate;

/**
 *
 * @author haize
 */
public interface PlateInterface {
    public void edit_XML(Plate requestEntity) throws BussinessLogicException;

    public <T> T findPlatesByMealType_XML(GenericType<T> responseType, String mealType) throws BussinessLogicException;

    public <T> T find_XML(Class<T> responseType, Integer id) throws BussinessLogicException;

    public <T> T findPlatesByName_XML(GenericType<T> responseType, String plateName) throws BussinessLogicException;

    public void create_XML(Plate requestEntity) throws BussinessLogicException;

    public <T> T findPlatesIfVegetarian_XML(GenericType<T> responseType) throws BussinessLogicException;

    public <T> T findAll_XML(GenericType<T> responseType) throws BussinessLogicException;

    public void remove(Integer id) throws BussinessLogicException;
}
