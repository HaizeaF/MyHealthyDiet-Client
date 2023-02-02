package exceptions;

/**
 * Exception associated to an error produced in the RESTfull services.
 * @author JulenB, MikelB, SendoaB and HaizeaF
 */
public class BusinessLogicException extends Exception {
    public BusinessLogicException(String msg){
        super(msg);
    }
}