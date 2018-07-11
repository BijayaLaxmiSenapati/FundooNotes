package com.bridgelabz.fundoonotes.user.exceptions;

/**
*
* @author Bijaya Laxmi Senapati
* @since 10/07/2018
* @version 1.0
*
*/
public class InvalidContactNumberException extends Exception
{
	
	private static final long serialVersionUID = 1L;
	
	public InvalidContactNumberException(String id) {
		super("A CONTACT NUMBER SHOULD HAVE 10 DIGITS, WHICH ENTERED A USER WITH ID: "+id);
		
	}

}
