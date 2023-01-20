/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import services.DietFacadeREST;

/**
 *
 * @author JulenB
 */
public class DietFactory {
      private static DietInterface model;
    
    public static DietInterface getModel() {
        if (model == null) {
            model = new DietFacadeREST();
        }
        return model;
    }
}
