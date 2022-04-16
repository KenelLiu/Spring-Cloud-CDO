package com.cdo.cloud.exception;

public class BussinessException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -674410701129851443L;

	public BussinessException(String message) {
        super(message);
    }
	
    public BussinessException(String message, Throwable cause) {
        super(message, cause);
    }	
}
