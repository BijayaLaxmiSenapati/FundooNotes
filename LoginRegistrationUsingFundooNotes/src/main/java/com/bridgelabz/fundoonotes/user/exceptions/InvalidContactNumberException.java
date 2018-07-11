package com.bridgelabz.fundoonotes.user.exceptions;

public class InvalidContactNumberException extends Exception
{
	
	private static final long serialVersionUID = 1L;
	
	public InvalidContactNumberException(String id) {
		super("A CONTACT NUMBER SHOULD HAVE 10 DIGITS, WHICH ENTERED A USER WITH ID: "+id);
		
	}

}
