package com.epam.exception;

public class InvalidAccessException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InvalidAccessException(String message)
	{
		super(message);
	}

}