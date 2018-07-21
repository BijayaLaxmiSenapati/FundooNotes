package com.bridgelabz.fundoonotes.note.services;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.note.exceptions.EmptyNoteException;
import com.bridgelabz.fundoonotes.note.exceptions.InvalidDateException;
import com.bridgelabz.fundoonotes.note.exceptions.NoteAuthorisationException;
import com.bridgelabz.fundoonotes.note.exceptions.NoteException;
import com.bridgelabz.fundoonotes.note.exceptions.NoteNotFoundException;
import com.bridgelabz.fundoonotes.note.exceptions.NoteTrashException;
import com.bridgelabz.fundoonotes.note.exceptions.OwnerOfNoteNotFoundException;
import com.bridgelabz.fundoonotes.note.models.Note;
import com.bridgelabz.fundoonotes.note.models.NoteCreateDTO;
import com.bridgelabz.fundoonotes.note.models.NoteUpdateDTO;
import com.bridgelabz.fundoonotes.note.models.NoteViewDTO;
import com.bridgelabz.fundoonotes.note.repositories.NoteRepository;
import com.bridgelabz.fundoonotes.note.utility.Utility;
import com.bridgelabz.fundoonotes.user.models.User;//using from user
import com.bridgelabz.fundoonotes.user.repositories.UserRepository;//using from user
import com.bridgelabz.fundoonotes.user.security.TokenProvider;//using from user

@Service
public class NoteServiceImpl implements NoteService {

	@Autowired
	private NoteRepository noteRepository;

	@Autowired
	private UserRepository userRepsitory;

	@Autowired
	private TokenProvider tokenProvider;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	//write string userId insteadof token
	public NoteViewDTO createNote(String token, NoteCreateDTO noteCreateDTO)
			throws NoteException, EmptyNoteException, InvalidDateException {

		// TokenProvider tokenProvider = new TokenProvider();
		String userIdFromToken = tokenProvider.parseToken(token);//no need of parsing token because we have interceptor

		Utility.validateNoteWhileCreating(noteCreateDTO);

		Note note = new Note();
		note.setTitle(noteCreateDTO.getTitle());
		note.setDescription(noteCreateDTO.getDescription());
		if (noteCreateDTO.getReminder() != null) {
			if (!noteCreateDTO.getReminder().before(new Date())) {
				throw new InvalidDateException("reminder should not be earlier from now");
			}
		}
		note.setReminder(noteCreateDTO.getReminder());

		if (noteCreateDTO.getColor() != null)
			note.setColor(noteCreateDTO.getColor());
		else
			note.setColor("white");

		note.setCreatedAt(new Date());

		note.setUserId(userIdFromToken);//put "userId"

		noteRepository.insert(note);

		NoteViewDTO noteViewDTO = modelMapper.map(note, NoteViewDTO.class);
		return noteViewDTO;
	}

	@Override
	//write string userId insteadof token
	public void updateNote(String token, NoteUpdateDTO noteUpdateDTO)
			throws NoteException, OwnerOfNoteNotFoundException, NoteNotFoundException, NoteAuthorisationException {

		Note note = validate(token, noteUpdateDTO.getId());// return note
		if (noteUpdateDTO.getTitle() != null) {
			note.setTitle(noteUpdateDTO.getTitle());
		}
		if (noteUpdateDTO.getDescription() != null) {
			note.setDescription(noteUpdateDTO.getDescription());
		}
		/*********************************************
		 * create different api for setting color and
		 * reminder*************************************** /*if
		 * (noteUpdateDTO.getColor() != null) { note.setColor(noteUpdateDTO.getColor());
		 * } if (noteUpdateDTO.getReminder() != null) {
		 * note.setReminder(noteUpdateDTO.getReminder()); }
		 */
		note.setUpdatedAt(new Date());

		noteRepository.save(note);
	}

	@Override
	//write string userId insteadof token
	public List<NoteViewDTO> getAllNotes(String token) {

		// TokenProvider tokenProvider = new TokenProvider();
		String userIdFromToken = tokenProvider.parseToken(token);//no need of parsing token because we have interceptor
		List<NoteViewDTO> noteList = noteRepository.findAllByuserId(userIdFromToken);//put "userId"

		List<NoteViewDTO> finalNoteList = new ArrayList<NoteViewDTO>();
		for (NoteViewDTO note : noteList) {
			if (!note.isTrashed())
				finalNoteList.add(note);
		}
		return finalNoteList;
	}

	@Override
	//write string userId insteadof token
	public void trashNote(String token, String noteId)// take transh
			throws OwnerOfNoteNotFoundException, NoteException, NoteNotFoundException, NoteAuthorisationException {

		Note note = validate(token, noteId);

		note.setTrashed(true);

		noteRepository.save(note);
	}

	@Override
	//write string userId insteadof token
	public void permanentlyDeleteNote(String token, String id, boolean isDelete)
			throws NoteException, OwnerOfNoteNotFoundException, NoteNotFoundException, NoteAuthorisationException, NoteTrashException {

		Note note = validate(token, id);

		if (!note.isTrashed()) {
			throw new NoteTrashException("Note not trashed yet!");// change exception
		}
		
		if (isDelete) {
			noteRepository.deleteById(note.getId());
		} else {
			note.setTrashed(false);
			noteRepository.save(note);
		}
	}

	private Note validate(String token, String noteId)
			throws NoteException, OwnerOfNoteNotFoundException, NoteNotFoundException, NoteAuthorisationException {

		if (noteId == null) {
			throw new NoteException("For deletion of note \"id\" is needed");
		}

		// TokenProvider tokenProvider = new TokenProvider();
		String userIdFromToken = tokenProvider.parseToken(token);//no need of parsing token because we have interceptor
		Optional<User> optionalUser = userRepsitory.findById(userIdFromToken);
		if (!optionalUser.isPresent()) {
			throw new OwnerOfNoteNotFoundException("Note owner not present");// have doubt
		}//delete parsing token and checking isPresent

		Optional<Note> optionalnote = noteRepository.findById(noteId);
		if (!optionalnote.isPresent()) {
			throw new NoteNotFoundException("given note-id to delete is not present");
		}

		//replace userIdFromToken by userId
		if (!userIdFromToken.equals(optionalnote.get().getUserId())) {
			throw new NoteAuthorisationException("Unauthorised access of note to delete");
		}
		return optionalnote.get();
	}

	@Override//not taking date(json parsing exception)see once
	public void addReminder(String token, String id, Date remindDate) throws ParseException, InvalidDateException,
			NoteException, OwnerOfNoteNotFoundException, NoteNotFoundException, NoteAuthorisationException {

		Note note = validate(token, id);

		if (!remindDate.before(new Date())) {
			throw new InvalidDateException("reminder should not be earlier from now");
		}
		note.setReminder(remindDate);

		noteRepository.save(note);
	}

	@Override
	public void removeReminder(String token, String id)
			throws NoteException, OwnerOfNoteNotFoundException, NoteNotFoundException, NoteAuthorisationException {

		Note note = validate(token, id);

		note.setReminder(null);

		noteRepository.save(note);
	}
}
