/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author haize
 */
public class BussinessLogicException extends Exception {
    public BussinessLogicException() {
        super();
    }
    
    public BussinessLogicException(String msg) {
        super(msg);
    }
}
