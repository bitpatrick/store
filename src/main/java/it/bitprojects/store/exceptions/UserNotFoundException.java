package it.bitprojects.store.exceptions;

public class UserNotFoundException extends RuntimeException {
	public UserNotFoundException() {
	}

	public UserNotFoundException(String message) {
		super(message);
	}
	
}
