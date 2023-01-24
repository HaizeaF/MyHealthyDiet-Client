/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 * @author Sendoa
 * Exception that is thrown when the password format is not correct
 */
public class InvalidPasswordValueException extends Exception {
    public InvalidPasswordValueException() {
        super();
    }
    
    public InvalidPasswordValueException(String msg) {
        super(msg);
    }
}