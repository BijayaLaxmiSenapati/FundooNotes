package com.bridgelabz.fundoonotes.user.exceptions;

/**
*
* @author Bijaya Laxmi Senapati
* @since 10/07/2018
* @version 1.0
*
*/
public class PasswordNotMatchingException extends Exception
{
	
	private static final long serialVersionUID = 1L;

	public PasswordNotMatchingException(String id) {
		super("'PASSWORD' AND 'CONFIRM PASSWORD' FIELD SHOULD MATCH, GIVEN BY ID:"+id);
	}
}
