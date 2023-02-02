/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import objects.Ingredient;

/**
 *
 * @author Mikel
 */
public interface IngredientInterface {
    
    public void edit_XML(Ingredient requestEntity) throws WebApplicationException;

    public void edit_JSON(Ingredient requestEntity) throws WebApplicationException;

    public <T> T find_XML(Class<T> responseType, Integer id) throws WebApplicationException;

    public <T> T find_JSON(Class<T> responseType, Integer id) throws WebApplicationException;

    public void create_XML(Ingredient requestEntity) throws WebApplicationException;

    public void create_JSON(Ingredient requestEntity) throws WebApplicationException;

    public <T> T findAll_XML(GenericType<T> responseType) throws WebApplicationException;

    public <T> T findAll_JSON(GenericType<T> responseType) throws WebApplicationException;

    public void remove(Integer id) throws WebApplicationException;

    public <T> T findIngredientsByName_XML(GenericType<T> responseType, String ingredientName) throws WebApplicationException;

    public <T> T findIngredientsByName_JSON(Class<T> responseType, String ingredientName) throws WebApplicationException;
}
