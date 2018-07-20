package com.bridgelabz.fundoonotes.note.utility;

import com.bridgelabz.fundoonotes.note.exceptions.EmptyNoteException;
import com.bridgelabz.fundoonotes.note.exceptions.NoteException;
import com.bridgelabz.fundoonotes.note.models.NoteCreateDTO;
public class Utility {

	public static void validateNoteWhileCreating(NoteCreateDTO noteCreateDTO) throws NoteException, EmptyNoteException {
		
		if(noteCreateDTO.getTitle()==null && noteCreateDTO.getDescription()==null) {
			throw new EmptyNoteException("All fields should be filled");
		}
	}
}
