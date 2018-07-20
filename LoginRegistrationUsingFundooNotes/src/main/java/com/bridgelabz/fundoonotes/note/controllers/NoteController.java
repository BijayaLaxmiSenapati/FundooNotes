package com.bridgelabz.fundoonotes.note.controllers;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.note.exceptions.EmptyNoteException;
import com.bridgelabz.fundoonotes.note.exceptions.NoteAuthorisationException;
import com.bridgelabz.fundoonotes.note.exceptions.NoteException;
import com.bridgelabz.fundoonotes.note.exceptions.NoteNotFoundException;
import com.bridgelabz.fundoonotes.note.exceptions.OwnerOfNoteNotFoundException;
import com.bridgelabz.fundoonotes.note.models.NoteCreateDTO;
import com.bridgelabz.fundoonotes.note.models.NoteUpdateDTO;
import com.bridgelabz.fundoonotes.note.models.NoteViewDTO;
import com.bridgelabz.fundoonotes.note.services.NoteService;
import com.bridgelabz.fundoonotes.user.models.ResponseDTO;//using from user

@RestController
@RequestMapping("/fundoo")
public class NoteController {

	@Autowired
	private NoteService noteService;

	@RequestMapping(value = "/createNote", method = RequestMethod.POST)
	public ResponseEntity<NoteViewDTO> createNote(@RequestHeader(value = "token") String token,
			@RequestBody NoteCreateDTO noteDTO, HttpServletResponse response) throws NoteException, EmptyNoteException {

		NoteViewDTO noteViewDTO = noteService.createNote(token, noteDTO);

		/*
		 * ResponseDTO responseDTO = new ResponseDTO();
		 * responseDTO.setMessage("Note Successfully created");
		 * responseDTO.setStatus(1);
		 */
		return new ResponseEntity<>(noteViewDTO, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/updateNote", method = RequestMethod.POST)
	public ResponseEntity<ResponseDTO> updateNote(@RequestHeader(value = "token") String token,
			@RequestBody NoteUpdateDTO noteCreateDTO)
			throws NoteException, OwnerOfNoteNotFoundException, NoteNotFoundException, NoteAuthorisationException {

		noteService.updateNote(token, noteCreateDTO);

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage("Note Successfully updated");
		responseDTO.setStatus(1);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/viewAllNotes", method = RequestMethod.GET)
	public ResponseEntity<List<NoteViewDTO>> viewAllNotes(@RequestHeader(value = "token") String token) {

		List<NoteViewDTO> list = noteService.viewAllNotes(token);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@RequestMapping(value = "/deleteNote/{id}", method = RequestMethod.POST)
	public ResponseEntity<ResponseDTO> deleteNote(@RequestHeader(value = "token") String token,
			@PathVariable(value = "id") String id)
			throws OwnerOfNoteNotFoundException, NoteException, NoteNotFoundException, NoteAuthorisationException {

		noteService.deleteNote(token, id);

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage("Note added to trash");
		responseDTO.setStatus(1);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/permanentlyDeleteNote/{id}", method = RequestMethod.POST)
	public ResponseEntity<ResponseDTO> permanentlyDeleteNote(@RequestHeader(value = "token") String token,
			@PathVariable(value = "id") String id) throws NoteException, OwnerOfNoteNotFoundException, NoteNotFoundException, NoteAuthorisationException {
		noteService.permanentlyDeleteNote(token,id);
		
		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage("Note Successfully deleted");
		responseDTO.setStatus(1);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

}
