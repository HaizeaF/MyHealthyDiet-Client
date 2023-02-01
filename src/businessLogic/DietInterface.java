package businessLogic;

import exceptions.BusinessLogicException;
import javax.ws.rs.core.GenericType;
import objects.Diet;

/**
 * @author JulenB
 * This class is an inteface that has the methods that are implemented by DietFacadeRESTClient.
 */
public interface DietInterface {
    
    /**
     * This method return an generic type param with all diets data.
     * @param <T> A generic type return.
     * @param responseType A generic respose type that indicates in which type of data you want to save all the diets.
     * @return A generic type return with all diets data.
     * @throws BusinessLogicException Exception thrown if any error occurs while processing.
     */
    public <T> T findAllDiets_XML(GenericType<T> responseType) throws BusinessLogicException ;

    /**
     * This method return an generic type param with all diets data filtered by its name.
     * @param <T> A generic type return.
     * @param responseType A generic respose type that indicates in which type of data you want to save all the diets filtered by its name.
     * @param name The name to filter the diets.
     * @return A generic type return with all diets data filtered by its name.
     * @throws BusinessLogicException Exception thrown if any error occurs while processing.
     */
    public <T> T findAllDietsByName_XML(GenericType<T> responseType, String name) throws BusinessLogicException ;

    /**
     * This method updates data from an existing diet.
     * @param diet The diet to be updated.
     * @throws BusinessLogicException Exception thrown if any error occurs while processing.
     */
    public void edit_XML(Diet diet) throws BusinessLogicException ;

    /**
     * This method return an generic type param with all diets data filtered by its id.
     * @param <T> A generic type return.
     * @param responseType A generic respose type that indicates in which type of data you want to save all the diets filtered by its id.
     * @param id The id used to filter the diets.
     * @return A generic type return with all diets data filtered by its id.
     * @throws BusinessLogicException Exception thrown if any error occurs while processing.
     */
    public <T> T findById_XML(Class<T> responseType, String id) throws BusinessLogicException ;

    /**
     * This method return an generic type param with all diets data filtered by its goal.
     * @param <T> A generic type return.
     * @param responseType A generic respose type that indicates in which type of data you want to save all the diets filtered by its goal.
     * @param goal The goal used to filter the diets.
     * @return A generic type return with all diets data filtered by its goal.
     * @throws BusinessLogicException Exception thrown if any error occurs while processing.
     */
    public <T> T findAllDietsByGoal_XML(GenericType<T> responseType, String goal) throws BusinessLogicException ;
    
    /**
     * This method creates a diet.
     * @param diet The diet to be created.
     * @throws BusinessLogicException Exception thrown if any error occurs while processing.
     */
    public void create_XML(Diet diet) throws BusinessLogicException ;
    
    /**
     * This method removes a diet.
     * @param id The id of the diet be remove.
     * @throws BusinessLogicException Exception thrown if any error occurs while processing.
     */
    public void remove(Integer id) throws BusinessLogicException ;
}
