/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler;

/**
 *
 * @author haler
 */
public class WarningException extends Exception{
    
    String message;

    public WarningException() {
    }
    
    public WarningException(String message) {
        this.message = message;
    }
    
    public String getMessage() {
        return this.message;
    }
}
