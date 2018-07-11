package com.bridgelabz.fundoonotes.user.exceptions;

/**
*
* @author Bijaya Laxmi Senapati
* @since 10/07/2018
* @version 1.0
*
*/
public class PasswordNotFoundException extends Exception
{
	
	private static final long serialVersionUID = 1L;

	public PasswordNotFoundException(String id) {
		super("GIVEN PASSWORD IS AN INVALID ONE, GIVEN BY ID:"+id);
	}
}
