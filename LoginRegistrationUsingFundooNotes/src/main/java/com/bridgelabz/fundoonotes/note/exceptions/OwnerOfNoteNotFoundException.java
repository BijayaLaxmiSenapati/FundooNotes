package com.bridgelabz.fundoonotes.note.exceptions;

public class OwnerOfNoteNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * @param message
	 */
	public OwnerOfNoteNotFoundException(String message) {
		super(message);
	}

}
