/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import services.ClientFacadeREST;

/**
 *
 * @author Sendoa
 */
public class ClientFactory {
    public static ClientInterface model;
    
    public static ClientInterface getModel(){
        if(model==null){
            model = new ClientFacadeREST();
        }
        return model;
    }
}
