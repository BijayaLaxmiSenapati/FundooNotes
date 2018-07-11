package com.bridgelabz.fundoonotes.user.exceptions;

public class PasswordNotMatchingException extends Exception
{
	
	private static final long serialVersionUID = 1L;

	public PasswordNotMatchingException(String id) {
		super("'PASSWORD' AND 'CONFIRM PASSWORD' FIELD SHOULD MATCH, GIVEN BY ID:"+id);
	}
}
