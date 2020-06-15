package com.videos.exceptions;

//Es dispara quan es detecta un camp buit al intentar crear un usuari o un video
public class EmptyFieldException extends Exception{ 
	
	public EmptyFieldException() {
		super();
	}
	
	public EmptyFieldException(String message) {
		super(message);
	}
	
}
