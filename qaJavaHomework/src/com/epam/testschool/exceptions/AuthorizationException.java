package com.epam.testschool.exceptions;

/**
 * Thrown to indicate that a user can not be authorized
 *
 */
public class AuthorizationException extends RuntimeException {
	private String key;
	public AuthorizationException(String key) {
		this.key = key;
	}
	public AuthorizationException() {
	}
	@Override
	public String getMessage() {
		if (key == null){
			return "Authorize please.";			
		} else {
			return "Wrong key: "+ key + ". Check that you have picked up right key.";			
		}
	}
}
