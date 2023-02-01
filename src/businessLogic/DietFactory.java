package businessLogic;

import services.DietFacadeRESTClient;

/**
 *
 * @author JulenB
 * This class is a factory for DietInterface implementing objects.
 */
public class DietFactory {
    /**
     * The model thats returned
     */
      private static DietInterface model;
    
      /**
       * Factory method that returns DietInteface implementing objects.
       * @return An objects implemeting DietInterface methods.
       */
    public static DietInterface getModel() {
        if (model == null) {
            model = new DietFacadeRESTClient();
        }
        return model;
    }
}
