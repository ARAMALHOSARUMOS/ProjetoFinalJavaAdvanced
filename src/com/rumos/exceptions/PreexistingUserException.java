/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rumos.exceptions;

/**
 *
 * @author parodutt
 */
public class PreexistingUserException extends Exception {
	
	 private static final long serialVersionUID = 1L;
	
     public PreexistingUserException(String message) {
        
     super(message);
    }
}
