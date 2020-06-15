package com.videos.exceptions;

public class EmptyFieldException extends Exception{

	public EmptyFieldException() {
		super();
	}
	
	public EmptyFieldException(String message) {
		super(message);
	}
	
}
