package com.bridgelabz.fundoonotes.user.exceptions;

public class InvalidNameException extends Exception
{
	
	private static final long serialVersionUID = 1L;
	
	public InvalidNameException(String id) {
		super("A NAME SHOULD HAVE ATLEAST 3 CHARECTERS, GIVEN BY ID: "+id);//write some valid msg as journaldev custom exception
	}
	
}
