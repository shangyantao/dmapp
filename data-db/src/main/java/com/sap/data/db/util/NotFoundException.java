/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sap.data.db.util;

/**
 *
 * @author C5178395
 */
public class NotFoundException extends Exception {
    
	private static final long serialVersionUID = 1L;

	public NotFoundException() {
        super();
    }
    
    public NotFoundException(String message) {
        super(message);
    }
    
    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public NotFoundException(Throwable cause) {
        super(cause);
    }
    
}
