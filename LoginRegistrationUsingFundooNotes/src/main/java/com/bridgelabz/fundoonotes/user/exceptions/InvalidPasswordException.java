package com.bridgelabz.fundoonotes.user.exceptions;

public class InvalidPasswordException extends Exception
{
	
	private static final long serialVersionUID = 1L;
	
	public InvalidPasswordException(String id) {
		super("A PASSWORD SHOULD HAVE ATLEAST 8 CHARECTERS, GIVEN BY ID: "+id);//write some valid msg as journaldev custom exception
	}

}
