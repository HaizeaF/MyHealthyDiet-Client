/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import services.IngredientFacadeREST;

/**
 * Ingredient factory of the ingredient restful
 * @author Mikel
 */
public class IngredientFactory {
    private static IngredientInterface model;
    
    public static IngredientInterface getModel(){
        if(model==null){
            model = new IngredientFacadeREST();
        }
        return model;
    }
}
