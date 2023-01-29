/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 * @author Sendoa
 * Exception that is thrown when the username format is not correct
 */
public class InvalidUserValueException extends Exception {
    public InvalidUserValueException() {
        super();
    }
    
    public InvalidUserValueException(String msg) {
        super(msg);
    }
}
