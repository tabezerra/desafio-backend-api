package com.usercase.api.infrastructure.exception;

public class GenericException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public GenericException(String message) {
		super(message);
	}

}
