package com.bridgelabz.fundoonotes.user.exceptions;

public class InvalidEmailException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidEmailException(String id) {
		super("EMAIL FORMAT IS NOT CORRECT, GIVEN ID IS: "+id);//write some valid msg as journaldev custom exception
	}

}
