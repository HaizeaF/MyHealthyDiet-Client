/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import services.UserFacadeREST;

/**
 *
 * @author Sendoa
 */
public class UserFactory {
    public static UserInterface model;
    
    public static UserInterface getModel(){
        if(model==null){
            model = new UserFacadeREST();
        }
        return model;
    }
}
