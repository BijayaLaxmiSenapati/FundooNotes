package com.bridgelabz.fundoonotes.user.exceptions;

/**
 * @author adminstrato
 *
 */
public class LoginException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * @param message
	 */
	public LoginException(String message) {
		super(message);
	}
}
