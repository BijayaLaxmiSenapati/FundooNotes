package com.bridgelabz.fundoonotes.user.exceptions;

/**
*
* @author Bijaya Laxmi Senapati
* @since 10/07/2018
* @version 1.0
*
*/
public class InvalidNameException extends Exception
{
	
	private static final long serialVersionUID = 1L;
	
	public InvalidNameException(String id) {
		super("A NAME SHOULD HAVE ATLEAST 3 CHARECTERS, GIVEN BY ID: "+id);
	}
	
}
