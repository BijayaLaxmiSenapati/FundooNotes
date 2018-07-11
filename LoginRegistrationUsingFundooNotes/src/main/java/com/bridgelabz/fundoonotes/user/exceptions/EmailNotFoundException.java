package com.bridgelabz.fundoonotes.user.exceptions;

/**
*
* @author Bijaya Laxmi Senapati
* @since 10/07/2018
* @version 1.0
*
*/
public class EmailNotFoundException extends Exception
{
	
	private static final long serialVersionUID = 1L;

	public EmailNotFoundException(String id) {
		super("NOT A AUTHORISED USER FOR LOGIN WITH ID: "+id);
		
	}
}
