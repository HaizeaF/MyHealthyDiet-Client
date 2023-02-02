package businessLogic;

import services.PlateFacadeRESTClient;

/**
 * Plate factory for PlateInterface
 * @author HaizeaF
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
