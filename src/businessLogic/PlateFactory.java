package businessLogic;

import services.PlateFacadeRESTClient;

/**
 *
 * @author haize
 */
public class PlateFactory {
    private static PlateInterface model;
    
    public static PlateInterface getModel(){
        if(model==null){
            model = new PlateFacadeRESTClient();
        }
        return model;
    }
}
