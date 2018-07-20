package com.bridgelabz.fundoonotes.note.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.note.exceptions.EmptyNoteException;
import com.bridgelabz.fundoonotes.note.exceptions.NoteAuthorisationException;
import com.bridgelabz.fundoonotes.note.exceptions.NoteException;
import com.bridgelabz.fundoonotes.note.exceptions.NoteNotFoundException;
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

	@Override
	public NoteViewDTO createNote(String token, NoteCreateDTO noteCreateDTO) throws NoteException, EmptyNoteException {

		Utility.validateNoteWhileCreating(noteCreateDTO);

		Note note = new Note();
		note.setTitle(noteCreateDTO.getTitle());
		note.setDescription(noteCreateDTO.getDescription());
		note.setReminder(noteCreateDTO.getReminder());
		note.setColor(noteCreateDTO.getColor());

		note.setCreatedAt(new Date());

		TokenProvider tokenProvider = new TokenProvider();
		String userIdFromToken = tokenProvider.parseToken(token);

		note.setUserId(userIdFromToken);

		noteRepository.insert(note);

		ModelMapper modelMapper = new ModelMapper();
		NoteViewDTO noteViewDTO = modelMapper.map(note, NoteViewDTO.class);
		return noteViewDTO;
	}

	@Override
	public void updateNote(String token, NoteUpdateDTO noteUpdateDTO)
			throws NoteException, OwnerOfNoteNotFoundException, NoteNotFoundException, NoteAuthorisationException {

		/*
		 * if (noteUpdateDTO.getId() == null) { throw new
		 * NoteException("For updation of note \"id\" was needed"); }
		 * 
		 * TokenProvider tokenProvider = new TokenProvider(); String userIdFromToken =
		 * tokenProvider.parseToken(token); Optional<User>
		 * optionalUser=userRepsitory.findById(userIdFromToken);
		 * if(!optionalUser.isPresent()) { throw new
		 * OwnerOfNoteNotFoundException("Note owner not present");//have doubt }
		 * 
		 * Optional<Note> optionalnote = noteRepository.findById(noteUpdateDTO.getId());
		 * if(!optionalnote.isPresent()) { throw new
		 * NoteNotFoundException("given note-id is not present"); }
		 * 
		 * if(!userIdFromToken.equals(optionalnote.get().getUserId())) { throw new
		 * NoteAuthorisationException("Unauthorised access of note"); }
		 */

		Optional<Note> optionalnote = noteRepository.findById(noteUpdateDTO.getId());

		validate(token, optionalnote.get().getUserId());

		Note note = optionalnote.get();
		if (noteUpdateDTO.getTitle() != null) {
			note.setTitle(noteUpdateDTO.getTitle());
		}
		if (noteUpdateDTO.getDescription() != null) {
			note.setDescription(noteUpdateDTO.getDescription());
		}
		if (noteUpdateDTO.getColor() != null) {
			note.setColor(noteUpdateDTO.getColor());
		}
		if (noteUpdateDTO.getReminder() != null) {
			note.setReminder(noteUpdateDTO.getReminder());
		}
		note.setUpdatedAt(new Date());

		noteRepository.save(note);
	}

	@Override
	public List<NoteViewDTO> viewAllNotes(String token) {

		TokenProvider tokenProvider = new TokenProvider();
		String userIdFromToken = tokenProvider.parseToken(token);
		Optional<User> optionalUser = userRepsitory.findById(userIdFromToken);
		List<NoteViewDTO> noteList = noteRepository.findAllByuserId(optionalUser.get().getId());

		List<NoteViewDTO> finalNoteList = new ArrayList<NoteViewDTO>();
		for (NoteViewDTO note : noteList) {
			if (!note.isTrashed())
				finalNoteList.add(note);
		}
		return finalNoteList;
	}

	@Override
	public void deleteNote(String token, String noteId)
			throws OwnerOfNoteNotFoundException, NoteException, NoteNotFoundException, NoteAuthorisationException {

		/*
		 * if (noteId == null) { throw new
		 * NoteException("For deletion of note \"id\" is needed"); }
		 * 
		 * TokenProvider tokenProvider = new TokenProvider(); String userIdFromToken =
		 * tokenProvider.parseToken(token); Optional<User>
		 * optionalUser=userRepsitory.findById(userIdFromToken);
		 * if(!optionalUser.isPresent()) { throw new
		 * OwnerOfNoteNotFoundException("Note owner not present");//have doubt }
		 * 
		 * Optional<Note> optionalnote = noteRepository.findById(noteId);
		 * if(!optionalnote.isPresent()) { throw new
		 * NoteNotFoundException("given note-id to delete is not present"); }
		 * 
		 * if(!userIdFromToken.equals(optionalnote.get().getUserId())) { throw new
		 * NoteAuthorisationException("Unauthorised access of note to delete"); }
		 */

		Optional<Note> optionalnote = noteRepository.findById(noteId);

		Note note = optionalnote.get();
		note.setTrashed(true);

		noteRepository.save(note);
	}

	@Override
	public void permanentlyDeleteNote(String token, String id)
			throws NoteException, OwnerOfNoteNotFoundException, NoteNotFoundException, NoteAuthorisationException {

		/*
		 * if (id == null) { throw new
		 * NoteException("For deletion of note \"id\" is needed"); }
		 * 
		 * TokenProvider tokenProvider = new TokenProvider(); String userIdFromToken =
		 * tokenProvider.parseToken(token); Optional<User>
		 * optionalUser=userRepsitory.findById(userIdFromToken);
		 * if(!optionalUser.isPresent()) { throw new
		 * OwnerOfNoteNotFoundException("Note owner not present");//have doubt }
		 * 
		 * Optional<Note> optionalnote = noteRepository.findById(id);
		 * if(!optionalnote.isPresent()) { throw new
		 * NoteNotFoundException("given note-id to delete is not present"); }
		 * 
		 * if(!userIdFromToken.equals(optionalnote.get().getUserId())) { throw new
		 * NoteAuthorisationException("Unauthorised access of note to delete"); }
		 */

		Optional<Note> optionalnote = noteRepository.findById(id);

		if (!optionalnote.get().isTrashed()) {
			throw new NoteNotFoundException("Note not trashed yet!");
		}
		noteRepository.deleteById(optionalnote.get().getId());
	}

	public void validate(String token, String noteId)
			throws NoteException, OwnerOfNoteNotFoundException, NoteNotFoundException, NoteAuthorisationException {

		if (noteId == null) {
			throw new NoteException("For deletion of note \"id\" is needed");
		}

		TokenProvider tokenProvider = new TokenProvider();
		String userIdFromToken = tokenProvider.parseToken(token);
		Optional<User> optionalUser = userRepsitory.findById(userIdFromToken);
		if (!optionalUser.isPresent()) {
			throw new OwnerOfNoteNotFoundException("Note owner not present");// have doubt
		}

		Optional<Note> optionalnote = noteRepository.findById(noteId);
		if (!optionalnote.isPresent()) {
			throw new NoteNotFoundException("given note-id to delete is not present");
		}

		if (!userIdFromToken.equals(optionalnote.get().getUserId())) {
			throw new NoteAuthorisationException("Unauthorised access of note to delete");
		}

	}
}
