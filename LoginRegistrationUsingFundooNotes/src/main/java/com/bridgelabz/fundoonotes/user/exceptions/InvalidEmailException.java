package com.bridgelabz.fundoonotes.user.exceptions;

/**
*
* @author Bijaya Laxmi Senapati
* @since 10/07/2018
* @version 1.0
*
*/
public class InvalidEmailException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidEmailException(String id) {
		super("EMAIL FORMAT IS NOT CORRECT, GIVEN ID IS: "+id);
	}

}
