package com.bridgelabz.fundoonotes.user.exceptions;

public class EmailNotFoundException extends Exception
{
	
	private static final long serialVersionUID = 1L;

	public EmailNotFoundException(String id) {
		super("NOT A AUTHORISED USER FOR LOGIN WITH ID: "+id);//write some valid msg as journaldev custom exception
		
	}
}
