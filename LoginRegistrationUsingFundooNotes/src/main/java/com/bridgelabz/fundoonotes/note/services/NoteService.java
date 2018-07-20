package com.bridgelabz.fundoonotes.note.services;

import java.util.List;

import com.bridgelabz.fundoonotes.note.exceptions.EmptyNoteException;
import com.bridgelabz.fundoonotes.note.exceptions.NoteAuthorisationException;
import com.bridgelabz.fundoonotes.note.exceptions.NoteException;
import com.bridgelabz.fundoonotes.note.exceptions.NoteNotFoundException;
import com.bridgelabz.fundoonotes.note.exceptions.OwnerOfNoteNotFoundException;
import com.bridgelabz.fundoonotes.note.models.NoteCreateDTO;
import com.bridgelabz.fundoonotes.note.models.NoteUpdateDTO;
import com.bridgelabz.fundoonotes.note.models.NoteViewDTO;

public interface NoteService {

	NoteViewDTO createNote(String token, NoteCreateDTO noteCreateDTO) throws NoteException, EmptyNoteException;

	void updateNote(String token, NoteUpdateDTO noteCreateDTO) throws NoteException, OwnerOfNoteNotFoundException, NoteNotFoundException, NoteAuthorisationException;

	List<NoteViewDTO> viewAllNotes(String token);

	void deleteNote(String token, String noteId) throws OwnerOfNoteNotFoundException, NoteException, NoteNotFoundException, NoteAuthorisationException;

	void permanentlyDeleteNote(String token, String id) throws NoteException, OwnerOfNoteNotFoundException, NoteNotFoundException, NoteAuthorisationException;

}
