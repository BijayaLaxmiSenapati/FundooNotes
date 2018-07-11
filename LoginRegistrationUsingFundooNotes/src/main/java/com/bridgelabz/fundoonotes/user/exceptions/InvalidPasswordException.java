package com.bridgelabz.fundoonotes.user.exceptions;

/**
*
* @author Bijaya Laxmi Senapati
* @since 10/07/2018
* @version 1.0
*
*/
public class InvalidPasswordException extends Exception
{
	
	private static final long serialVersionUID = 1L;
	
	public InvalidPasswordException(String id) {
		super("A PASSWORD SHOULD HAVE ATLEAST 8 CHARECTERS, GIVEN BY ID: "+id);
	}

}
