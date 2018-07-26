package com.bridgelabz.fundoonotes.note.controllers;

import java.text.ParseException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
import com.bridgelabz.fundoonotes.note.models.Note;
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
	@PostMapping(value = "/create-note")
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
	@PutMapping(value = "/update-note")
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
	@GetMapping(value = "/get-all-notes")
	public ResponseEntity<List<Note>> getAllNotes(@RequestHeader(value = "token") String token) {
		List<Note> list = noteService.getAllNotes(token);
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
	@PutMapping(value = "/trash-note/{id}")
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
	@DeleteMapping(value = "/permanently-delete-note/{id}")
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
	@PutMapping(value = "/add-reminder/{id}")
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
	@PutMapping(value = "/remove-reminder/{id}")
	public ResponseEntity<ResponseDTO> removeReminder(@RequestHeader(value = "token") String token,
			@PathVariable(value = "id") String id) throws ParseException, InvalidDateException, NoteException,
			OwnerOfNoteNotFoundException, NoteNotFoundException, NoteAuthorisationException {

		noteService.removeReminder(token, id);

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage("Reminder successfully added to the note");
		responseDTO.setStatus(1);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@PutMapping(value = "/add-pin/{id}")
	public ResponseEntity<ResponseDTO> addPin(@RequestHeader(value = "token") String token,
			@PathVariable(value = "id") String id) throws NoteException, OwnerOfNoteNotFoundException,
			NoteNotFoundException, NoteAuthorisationException, PinException {

		noteService.addPin(token, id);

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage("Successfully the pin is added");
		responseDTO.setStatus(1);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@PutMapping(value = "/remove-pin/{id}")
	public ResponseEntity<ResponseDTO> removePin(@RequestHeader(value = "token") String token,
			@PathVariable(value = "id") String id) throws NoteException, OwnerOfNoteNotFoundException,
			NoteNotFoundException, NoteAuthorisationException, PinException {

		noteService.removePin(token, id);

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage("Successfully the pin is removed");
		responseDTO.setStatus(1);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);

	}

	@PutMapping(value = "/add-to-archive/{id}")
	public ResponseEntity<ResponseDTO> addToArchieve(@RequestHeader(value = "token") String token,
			@PathVariable(value = "id") String id) throws NoteException, OwnerOfNoteNotFoundException,
			NoteNotFoundException, NoteAuthorisationException, ArchieveException {

		noteService.addToArchive(token, id);

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage("Successfully added to Archieve");
		responseDTO.setStatus(1);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@PutMapping(value = "/remove-from-archive/{id}")
	public ResponseEntity<ResponseDTO> removeFromArchieve(@RequestHeader(value = "token") String token,
			@PathVariable(value = "id") String id) throws NoteException, OwnerOfNoteNotFoundException,
			NoteNotFoundException, NoteAuthorisationException, ArchieveException {

		noteService.removeFromArchive(token, id);

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage("Successfully removed from Archieve");
		responseDTO.setStatus(1);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}
	
	@PostMapping(value="/create-label")
	public ResponseEntity<ResponseDTO> createLabel(@RequestHeader(value="token")String token, @RequestBody String labelName) throws LabelException{
		
		noteService.createLabel(token,labelName);
		
		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage("Label created successfully");
		responseDTO.setStatus(1);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@PostMapping(value="/add-label/{noteId}")
	public ResponseEntity<ResponseDTO> addLabel(@RequestHeader(value="token")String token,@PathVariable(value="noteId")String noteId,@RequestBody List<String> labels) throws OwnerOfNoteNotFoundException, NoteNotFoundException {
		
		noteService.addLabel(token,noteId,labels);
		
		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage("Labels added successfully");
		responseDTO.setStatus(1); 
		return new ResponseEntity<>(responseDTO,HttpStatus.OK);
	}
	
	@PutMapping(value="/edit-label")
	public ResponseEntity<ResponseDTO> editLabel(@RequestHeader(value="token")String token,@RequestParam String currentLabelName,@RequestParam String newLabelName) throws OwnerOfNoteNotFoundException, LabelException{
		
		noteService.editLabel(token,currentLabelName,newLabelName);
		
		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage("Label edited successfully");
		responseDTO.setStatus(1); 
		return new ResponseEntity<>(responseDTO,HttpStatus.OK);
	}
	
	@DeleteMapping(value="/delete-label")
	public ResponseEntity<ResponseDTO> deleteLabel(@RequestHeader(value="token")String token,@RequestParam String labelName) throws OwnerOfNoteNotFoundException, LabelException{
		
		noteService.deleteLabel(token,labelName);
		
		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage("Label deleted successfully");
		responseDTO.setStatus(1); 
		return new ResponseEntity<>(responseDTO,HttpStatus.OK);		
	}
	//view all label
	@GetMapping(value="/view-all-label")
	public ResponseEntity<ResponseDTO> viewAllLabel(@RequestHeader(value="token")String token){
		
		return null;		
	}
	//view all the notes in which label is applied
	@GetMapping(value="/notes-by-label-name")
	public ResponseEntity<ResponseDTO> notesByLabelName(@RequestHeader(value="token")String token){
		return null;
		
	}
	//view all trashed note
	//view all pinned note
	//view add archived note
	
}
