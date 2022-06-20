package com.example.exercise1.app.domain;

public class ServiceException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	public ServiceException(String message) {
		super(message);
	}


	public ServiceException(String message, Exception cause) {
		super(message, cause);
	}

}
