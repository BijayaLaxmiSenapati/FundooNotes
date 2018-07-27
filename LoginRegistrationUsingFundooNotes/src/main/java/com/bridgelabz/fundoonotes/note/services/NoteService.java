package com.bridgelabz.fundoonotes.note.services;

import java.text.ParseException;
import java.util.List;
import com.bridgelabz.fundoonotes.note.exceptions.PinException;
import com.bridgelabz.fundoonotes.note.exceptions.ArchieveException;
import com.bridgelabz.fundoonotes.note.exceptions.EmptyNoteException;
import com.bridgelabz.fundoonotes.note.exceptions.InvalidDateException;
import com.bridgelabz.fundoonotes.note.exceptions.LabelException;
import com.bridgelabz.fundoonotes.note.exceptions.NoteAuthorisationException;
import com.bridgelabz.fundoonotes.note.exceptions.NoteException;
import com.bridgelabz.fundoonotes.note.exceptions.NoteNotFoundException;
import com.bridgelabz.fundoonotes.note.exceptions.NoteTrashException;
import com.bridgelabz.fundoonotes.note.exceptions.OwnerOfNoteNotFoundException;
import com.bridgelabz.fundoonotes.note.models.Label;
import com.bridgelabz.fundoonotes.note.models.Note;
import com.bridgelabz.fundoonotes.note.models.NoteCreateDTO;
import com.bridgelabz.fundoonotes.note.models.NoteUpdateDTO;
import com.bridgelabz.fundoonotes.note.models.NoteViewDTO;

public interface NoteService {

	/**
	 * @param token
	 * @param noteCreateDTO
	 * @return
	 * @throws NoteException
	 * @throws EmptyNoteException
	 * @throws InvalidDateException
	 */
	NoteViewDTO createNote(String token, NoteCreateDTO noteCreateDTO)
			throws NoteException, EmptyNoteException, InvalidDateException;

	/**
	 * @param token
	 * @param noteCreateDTO
	 * @throws NoteException
	 * @throws OwnerOfNoteNotFoundException
	 * @throws NoteNotFoundException
	 * @throws NoteAuthorisationException
	 */
	void updateNote(String token, NoteUpdateDTO noteCreateDTO)
			throws NoteException, OwnerOfNoteNotFoundException, NoteNotFoundException, NoteAuthorisationException;

	/**
	 * @param token
	 * @return
	 */
	List<Note> getAllNotes(String token);

	/**
	 * @param token
	 * @param noteId
	 * @throws OwnerOfNoteNotFoundException
	 * @throws NoteException
	 * @throws NoteNotFoundException
	 * @throws NoteAuthorisationException
	 */
	void trashNote(String token, String noteId)
			throws OwnerOfNoteNotFoundException, NoteException, NoteNotFoundException, NoteAuthorisationException;

	/**
	 * @param token
	 * @param id
	 * @throws NoteException
	 * @throws OwnerOfNoteNotFoundException
	 * @throws NoteNotFoundException
	 * @throws NoteAuthorisationException
	 * @throws NoteTrashException 
	 */
	void permanentlyDeleteNote(String token, String id, boolean deleteOrRestore)
			throws NoteException, OwnerOfNoteNotFoundException, NoteNotFoundException, NoteAuthorisationException, NoteTrashException;

	/**
	 * @param token
	 * @param id
	 * @param remindDate
	 * @throws ParseException
	 * @throws InvalidDateException
	 * @throws NoteException
	 * @throws OwnerOfNoteNotFoundException
	 * @throws NoteNotFoundException
	 * @throws NoteAuthorisationException
	 */
	void addReminder(String token, String id, String remindDate) throws ParseException, InvalidDateException,
			NoteException, OwnerOfNoteNotFoundException, NoteNotFoundException, NoteAuthorisationException;

	void removeReminder(String token, String id)
			throws NoteException, OwnerOfNoteNotFoundException, NoteNotFoundException, NoteAuthorisationException;

	void addPin(String token, String id) throws NoteException, OwnerOfNoteNotFoundException, NoteNotFoundException, NoteAuthorisationException, PinException;

	void removePin(String token, String id) throws NoteException, OwnerOfNoteNotFoundException, NoteNotFoundException, NoteAuthorisationException, PinException;

	void addToArchive(String token, String id) throws NoteException, OwnerOfNoteNotFoundException, NoteNotFoundException, NoteAuthorisationException, ArchieveException;

	void removeFromArchive(String token, String id) throws NoteException, OwnerOfNoteNotFoundException, NoteNotFoundException, NoteAuthorisationException, ArchieveException;

	void createLabel(String token, String labelName) throws LabelException;

	void addLabel(String token, String noteId, List<String> labels) throws OwnerOfNoteNotFoundException, NoteNotFoundException;

	void editLabel(String token, String currentLabelName, String newLabelName) throws OwnerOfNoteNotFoundException, LabelException ;

	void deleteLabel(String token, String labelName) throws OwnerOfNoteNotFoundException, LabelException;

	List<Note> notesByLabelName(String token, String labelName) throws OwnerOfNoteNotFoundException, LabelException;

	List<Label> getAllLabel(String token) throws OwnerOfNoteNotFoundException, LabelException;

	List<Note> getAllTrashedNote(String token) throws OwnerOfNoteNotFoundException;

	List<Note> getAllArchivedNote(String token) throws OwnerOfNoteNotFoundException;

	void emptyTrash(String token) throws OwnerOfNoteNotFoundException;

	void removeLabelFromNote(String token, String noteId,String labelName) throws OwnerOfNoteNotFoundException, NoteNotFoundException, LabelException;

}
