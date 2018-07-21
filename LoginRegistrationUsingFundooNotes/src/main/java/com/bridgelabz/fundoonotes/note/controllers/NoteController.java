package com.bridgelabz.fundoonotes.note.controllers;

import java.text.ParseException;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.note.exceptions.EmptyNoteException;
import com.bridgelabz.fundoonotes.note.exceptions.InvalidDateException;
import com.bridgelabz.fundoonotes.note.exceptions.NoteAuthorisationException;
import com.bridgelabz.fundoonotes.note.exceptions.NoteException;
import com.bridgelabz.fundoonotes.note.exceptions.NoteNotFoundException;
import com.bridgelabz.fundoonotes.note.exceptions.NoteTrashException;
import com.bridgelabz.fundoonotes.note.exceptions.OwnerOfNoteNotFoundException;
import com.bridgelabz.fundoonotes.note.models.NoteCreateDTO;
import com.bridgelabz.fundoonotes.note.models.NoteUpdateDTO;
import com.bridgelabz.fundoonotes.note.models.NoteViewDTO;
import com.bridgelabz.fundoonotes.note.services.NoteService;
import com.bridgelabz.fundoonotes.user.models.ResponseDTO;//using from user

@RestController
@RequestMapping("/notes")
public class NoteController {

	@Autowired
	private NoteService noteService;

	/**
	 * @param token
	 * @param noteDTO
	 * @param response
	 * @return
	 * @throws NoteException
	 * @throws EmptyNoteException
	 * @throws InvalidDateException
	 */
	@RequestMapping(value = "/createNote", method = RequestMethod.POST)//add pin and archieve
	//remove request header
	//take HttpServletRequest
	public ResponseEntity<NoteViewDTO> createNote(@RequestHeader(value = "token") String token,
			@RequestBody NoteCreateDTO noteDTO, HttpServletResponse response)
			throws NoteException, EmptyNoteException, InvalidDateException {

		NoteViewDTO noteViewDTO = noteService.createNote(token, noteDTO);
		return new ResponseEntity<>(noteViewDTO, HttpStatus.CREATED);
	}

	/**
	 * @param token
	 * @param noteCreateDTO
	 * @return
	 * @throws NoteException
	 * @throws OwnerOfNoteNotFoundException
	 * @throws NoteNotFoundException
	 * @throws NoteAuthorisationException
	 */
	@RequestMapping(value = "/updateNote", method = RequestMethod.PUT) // put
	//remove request header
	//take HttpServletRequest request
	public ResponseEntity<ResponseDTO> updateNote(@RequestHeader(value = "token") String token,
			@RequestBody NoteUpdateDTO noteCreateDTO)
			throws NoteException, OwnerOfNoteNotFoundException, NoteNotFoundException, NoteAuthorisationException {
//write String userid=request.getAttribute(userId); and then pass in the method
		noteService.updateNote(token, noteCreateDTO);

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage("Note Successfully updated");
		responseDTO.setStatus(1);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	/**
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/viewAllNotes", method = RequestMethod.GET)
	//remove request header
	//take HttpServletRequest
	public ResponseEntity<List<NoteViewDTO>> getAllNotes(@RequestHeader(value = "token") String token) {
//write String userid=request.getAttribute(userId); and then pass in the method
		List<NoteViewDTO> list = noteService.getAllNotes(token);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	/**
	 * @param token
	 * @param id
	 * @return
	 * @throws OwnerOfNoteNotFoundException
	 * @throws NoteException
	 * @throws NoteNotFoundException
	 * @throws NoteAuthorisationException
	 */
	@RequestMapping(value = "/deleteNote/{id}", method = RequestMethod.PUT)
	//remove request header
	//take HttpServletRequest
	public ResponseEntity<ResponseDTO> trashNote(@RequestHeader(value = "token") String token,
			@PathVariable(value = "id") String id)
			throws OwnerOfNoteNotFoundException, NoteException, NoteNotFoundException, NoteAuthorisationException {
//write String userid=request.getAttribute("userId"); and then pass in the method
		noteService.trashNote(token, id);

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage("Note added to trash");
		responseDTO.setStatus(1);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	/**
	 * @param token
	 * @param id
	 * @return
	 * @throws NoteException
	 * @throws OwnerOfNoteNotFoundException
	 * @throws NoteNotFoundException
	 * @throws NoteAuthorisationException
	 * @throws NoteTrashException 
	 */
	@RequestMapping(value = "/permanentlyDeleteNote/{id}", method = RequestMethod.DELETE)
	//remove request header
	//take HttpServletRequest
	public ResponseEntity<ResponseDTO> permanentlyDeleteNote(@RequestHeader(value = "token") String token,
			@PathVariable(value = "id") String id, @RequestBody boolean deleteOrRestore)
			throws NoteException, OwnerOfNoteNotFoundException, NoteNotFoundException, NoteAuthorisationException, NoteTrashException {
//write String userid=request.getAttribute(userId); and then pass in the method
		noteService.permanentlyDeleteNote(token, id, deleteOrRestore);

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage("Note Successfully deleted");
		responseDTO.setStatus(1);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	/**
	 * @param token
	 * @param id
	 * @param remindDate
	 * @return
	 * @throws ParseException
	 * @throws InvalidDateException
	 * @throws NoteException
	 * @throws OwnerOfNoteNotFoundException
	 * @throws NoteNotFoundException
	 * @throws NoteAuthorisationException
	 */
	@RequestMapping(value = "/add-reminder/{id}", method = RequestMethod.PUT)
	//remove request header
	//take HttpServletRequest
	public ResponseEntity<ResponseDTO> addReminder(@RequestHeader(value = "token") String token,
			@PathVariable(value = "id") String id, @RequestBody Date remindDate)
			throws ParseException, InvalidDateException, NoteException, OwnerOfNoteNotFoundException,
			NoteNotFoundException, NoteAuthorisationException {
//write String userid=request.getAttribute(userId); and then pass in the method
		noteService.addReminder(token, id, remindDate);

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage("Reminder successfully added to the note");
		responseDTO.setStatus(1);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	/**
	 * @param token
	 * @param id
	 * @return
	 * @throws ParseException
	 * @throws InvalidDateException
	 * @throws NoteException
	 * @throws OwnerOfNoteNotFoundException
	 * @throws NoteNotFoundException
	 * @throws NoteAuthorisationException
	 */
	@RequestMapping(value = "/remove-reminder/{id}", method = RequestMethod.PUT)
	//remove request header
	//take HttpServletRequest
	public ResponseEntity<ResponseDTO> removeReminder(@RequestHeader(value = "token") String token,
			@PathVariable(value = "id") String id) throws ParseException, InvalidDateException, NoteException,
			OwnerOfNoteNotFoundException, NoteNotFoundException, NoteAuthorisationException {
//write String userid=request.getAttribute(userId); and then pass in the method
		noteService.removeReminder(token, id);

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage("Reminder successfully added to the note");
		responseDTO.setStatus(1);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

}
