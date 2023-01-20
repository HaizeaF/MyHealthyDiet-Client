package businessLogic;

import services.PlateFacadeREST;

/**
 *
 * @author haize
 */
public class PlateFactory {
    private static PlateInterface model;
    
    public static PlateInterface getModel(){
        if(model==null){
            model = new PlateFacadeREST();
        }
        return model;
    }
}
