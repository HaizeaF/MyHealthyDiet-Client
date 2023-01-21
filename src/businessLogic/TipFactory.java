/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import services.TipFacadeREST;

/**
 *
 * @author Sendoa
 */
public class TipFactory {
    private static TipInterface model;
    
    public static TipInterface getModel(){
        if(model==null){
            model = new TipFacadeREST();
        }
        return model;
    }
}
