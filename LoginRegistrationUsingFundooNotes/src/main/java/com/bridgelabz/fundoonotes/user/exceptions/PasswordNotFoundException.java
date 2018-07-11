package com.bridgelabz.fundoonotes.user.exceptions;

public class PasswordNotFoundException extends Exception
{
	
	private static final long serialVersionUID = 1L;

	public PasswordNotFoundException(String id) {
		super("GIVEN PASSWORD IS AN INVALID ONE, GIVEN BY ID:"+id);
	}
}
