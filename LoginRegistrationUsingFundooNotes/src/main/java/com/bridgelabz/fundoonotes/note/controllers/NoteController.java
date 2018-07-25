package com.bridgelabz.fundoonotes.note.controllers;

import java.text.ParseException;
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
import com.bridgelabz.fundoonotes.note.models.NoteCreateDTO;
import com.bridgelabz.fundoonotes.note.models.NoteUpdateDTO;
import com.bridgelabz.fundoonotes.note.models.NoteViewDTO;
import com.bridgelabz.fundoonotes.note.services.NoteService;
import com.bridgelabz.fundoonotes.user.models.ResponseDTO;

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
	@RequestMapping(value = "/create-note", method = RequestMethod.POST)
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
	@RequestMapping(value = "/update-note", method = RequestMethod.PUT)
	public ResponseEntity<ResponseDTO> updateNote(@RequestHeader(value = "token") String token,
			@RequestBody NoteUpdateDTO noteCreateDTO)
			throws NoteException, OwnerOfNoteNotFoundException, NoteNotFoundException, NoteAuthorisationException {
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
	@RequestMapping(value = "/get-all-notes", method = RequestMethod.GET)
	public ResponseEntity<List<NoteViewDTO>> getAllNotes(@RequestHeader(value = "token") String token) {
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
	@RequestMapping(value = "/trash-note/{id}", method = RequestMethod.PUT)
	public ResponseEntity<ResponseDTO> trashNote(@RequestHeader(value = "token") String token,
			@PathVariable(value = "id") String id)
			throws OwnerOfNoteNotFoundException, NoteException, NoteNotFoundException, NoteAuthorisationException {
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
	@RequestMapping(value = "/permanently-delete-note/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<ResponseDTO> permanentlyDeleteNote(@RequestHeader(value = "token") String token,
			@PathVariable(value = "id") String id, @RequestBody boolean deleteOrRestore) throws NoteException,
			OwnerOfNoteNotFoundException, NoteNotFoundException, NoteAuthorisationException, NoteTrashException {
		noteService.permanentlyDeleteNote(token, id, deleteOrRestore);

		ResponseDTO responseDTO = new ResponseDTO();
		if (deleteOrRestore) {
			responseDTO.setMessage("Note Successfully deleted");
			responseDTO.setStatus(1);
		} else {
			responseDTO.setMessage("Note Successfully restored");
			responseDTO.setStatus(1);
		}
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
	public ResponseEntity<ResponseDTO> addReminder(@RequestHeader(value = "token") String token,
			@PathVariable(value = "id") String id, @RequestBody String remindDate)
			throws ParseException, InvalidDateException, NoteException, OwnerOfNoteNotFoundException,
			NoteNotFoundException, NoteAuthorisationException {
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
	public ResponseEntity<ResponseDTO> removeReminder(@RequestHeader(value = "token") String token,
			@PathVariable(value = "id") String id) throws ParseException, InvalidDateException, NoteException,
			OwnerOfNoteNotFoundException, NoteNotFoundException, NoteAuthorisationException {

		noteService.removeReminder(token, id);

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage("Reminder successfully added to the note");
		responseDTO.setStatus(1);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/add-pin/{id}", method = RequestMethod.PUT)
	public ResponseEntity<ResponseDTO> addPin(@RequestHeader(value = "token") String token,
			@PathVariable(value = "id") String id) throws NoteException, OwnerOfNoteNotFoundException,
			NoteNotFoundException, NoteAuthorisationException, PinException {

		noteService.addPin(token, id);

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage("Successfully the pin is added");
		responseDTO.setStatus(1);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/remove-pin/{id}", method = RequestMethod.PUT)
	public ResponseEntity<ResponseDTO> removePin(@RequestHeader(value = "token") String token,
			@PathVariable(value = "id") String id) throws NoteException, OwnerOfNoteNotFoundException,
			NoteNotFoundException, NoteAuthorisationException, PinException {

		noteService.removePin(token, id);

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage("Successfully the pin is removed");
		responseDTO.setStatus(1);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);

	}

	@RequestMapping(value = "/add-to-archive/{id}", method = RequestMethod.PUT)
	public ResponseEntity<ResponseDTO> addToArchieve(@RequestHeader(value = "token") String token,
			@PathVariable(value = "id") String id) throws NoteException, OwnerOfNoteNotFoundException,
			NoteNotFoundException, NoteAuthorisationException, ArchieveException {

		noteService.addToArchive(token, id);

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage("Successfully added to Archieve");
		responseDTO.setStatus(1);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/remove-from-archive/{id}", method = RequestMethod.PUT)
	public ResponseEntity<ResponseDTO> removeFromArchieve(@RequestHeader(value = "token") String token,
			@PathVariable(value = "id") String id) throws NoteException, OwnerOfNoteNotFoundException,
			NoteNotFoundException, NoteAuthorisationException, ArchieveException {

		noteService.removeFromArchive(token, id);

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage("Successfully removed from Archieve");
		responseDTO.setStatus(1);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}
	
	@RequestMapping(value="/create-label", method= RequestMethod.POST)
	public ResponseEntity<ResponseDTO> createLabel(@RequestHeader(value="token")String token, @RequestBody String labelName) throws LabelException{
		
		noteService.createLabel(token,labelName);
		
		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage("Label created successfully");
		responseDTO.setStatus(1);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@RequestMapping(value="/add-label/{noteId}", method=RequestMethod.POST)
	public ResponseEntity<ResponseDTO> addLabel(@RequestHeader(value="token")String token,@PathVariable(value="noteId")String noteId,@RequestBody List<String> labels) throws OwnerOfNoteNotFoundException, NoteNotFoundException {
		
		noteService.addLabel(token,noteId,labels);
		
		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage("Labels added successfully");
		responseDTO.setStatus(1); 
		return new ResponseEntity<>(responseDTO,HttpStatus.OK);
	}
	//addlabel to createNote
	//delete label
	//edit label
	//view all trashed note
	//view all pinned note
	//view add archived note
	
}
