package com.bridgelabz.fundoonotes.note.controllers;

import java.text.ParseException;
import java.util.ArrayList;
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
import com.bridgelabz.fundoonotes.note.configurations.MessagePropertyConfig;
import com.bridgelabz.fundoonotes.note.exceptions.ArchieveException;
import com.bridgelabz.fundoonotes.note.exceptions.EmptyNoteException;
import com.bridgelabz.fundoonotes.note.exceptions.InvalidDateException;
import com.bridgelabz.fundoonotes.note.exceptions.LabelException;
import com.bridgelabz.fundoonotes.note.exceptions.NoteAuthorisationException;
import com.bridgelabz.fundoonotes.note.exceptions.NoteException;
import com.bridgelabz.fundoonotes.note.exceptions.NoteNotFoundException;
import com.bridgelabz.fundoonotes.note.exceptions.NoteTrashException;
import com.bridgelabz.fundoonotes.note.exceptions.OwnerOfNoteNotFoundException;
import com.bridgelabz.fundoonotes.note.models.ColorDTO;
import com.bridgelabz.fundoonotes.note.models.Label;
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
	MessagePropertyConfig messagePropertyConfig;
	
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
			@RequestBody NoteCreateDTO noteCreateDTO, HttpServletResponse response)
			throws NoteException, EmptyNoteException, InvalidDateException {

		NoteViewDTO noteViewDTO = noteService.createNote(token, noteCreateDTO);
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
			@RequestBody NoteUpdateDTO noteUpdateDTO)
			throws NoteException, OwnerOfNoteNotFoundException, NoteNotFoundException, NoteAuthorisationException {
		noteService.updateNote(token, noteUpdateDTO);

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage(messagePropertyConfig.getUpdateNoteMsg());
		responseDTO.setStatus(messagePropertyConfig.getSuccessfulStatus());
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	/**
	 * @param token
	 * @return
	 */
	@GetMapping(value = "/get-all-notes")
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
	@PutMapping(value = "/trash-note/{id}")
	public ResponseEntity<ResponseDTO> trashNote(@RequestHeader(value = "token") String token,
			@PathVariable(value = "id") String id)
			throws OwnerOfNoteNotFoundException, NoteException, NoteNotFoundException, NoteAuthorisationException {
		noteService.trashNote(token, id);

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage(messagePropertyConfig.getTrashNoteMsg());
		responseDTO.setStatus(messagePropertyConfig.getSuccessfulStatus());
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
			responseDTO.setMessage(messagePropertyConfig.getPermanentlyDeleteNoteMsgDLT());
			responseDTO.setStatus(messagePropertyConfig.getSuccessfulStatus());
		} else {
			responseDTO.setMessage(messagePropertyConfig.getPermanentlyDeleteNoteMsgRestore());
			responseDTO.setStatus(messagePropertyConfig.getSuccessfulStatus());
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
		responseDTO.setMessage(messagePropertyConfig.getAddReminderMsg());
		responseDTO.setStatus(messagePropertyConfig.getSuccessfulStatus());
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
	@PutMapping(value = "/remove-reminder/{noteId}")
	public ResponseEntity<ResponseDTO> removeReminder(@RequestHeader(value = "token") String token,
			@PathVariable(value = "noteId") String noteId) throws ParseException, InvalidDateException, NoteException,
			OwnerOfNoteNotFoundException, NoteNotFoundException, NoteAuthorisationException {

		noteService.removeReminder(token, noteId);

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage(messagePropertyConfig.getRemoveReminderMsg());
		responseDTO.setStatus(messagePropertyConfig.getSuccessfulStatus());
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}
	
	@PutMapping(value="/add-color/{noteId}")
	public ResponseEntity<ResponseDTO> addColor(@RequestHeader(value = "token") String token,
			@PathVariable(value = "noteId") String noteId,@RequestBody ColorDTO colorDTO) throws OwnerOfNoteNotFoundException, NoteNotFoundException{
		
		noteService.addColor(token,noteId,colorDTO);		
		
		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage("Successfully color is added");//add to properties
		responseDTO.setStatus(messagePropertyConfig.getSuccessfulStatus());
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);	}

	@PutMapping(value = "/add-pin/{id}")
	public ResponseEntity<ResponseDTO> addPin(@RequestHeader(value = "token") String token,
			@PathVariable(value = "id") String id) throws NoteException, OwnerOfNoteNotFoundException,
			NoteNotFoundException, NoteAuthorisationException, PinException {

		noteService.addPin(token, id);

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage(messagePropertyConfig.getAddPinMsg());
		responseDTO.setStatus(messagePropertyConfig.getSuccessfulStatus());
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@PutMapping(value = "/remove-pin/{id}")
	public ResponseEntity<ResponseDTO> removePin(@RequestHeader(value = "token") String token,
			@PathVariable(value = "id") String id) throws NoteException, OwnerOfNoteNotFoundException,
			NoteNotFoundException, NoteAuthorisationException, PinException {

		noteService.removePin(token, id);

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage(messagePropertyConfig.getRemovePinMsg());
		responseDTO.setStatus(messagePropertyConfig.getSuccessfulStatus());
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);

	}

	@PutMapping(value = "/add-to-archive/{id}")
	public ResponseEntity<ResponseDTO> addToArchieve(@RequestHeader(value = "token") String token,
			@PathVariable(value = "id") String id) throws NoteException, OwnerOfNoteNotFoundException,
			NoteNotFoundException, NoteAuthorisationException, ArchieveException {

		noteService.addToArchive(token, id);

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage(messagePropertyConfig.getAddToArchiveMsg());
		responseDTO.setStatus(messagePropertyConfig.getSuccessfulStatus());
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@PutMapping(value = "/remove-from-archive/{id}")
	public ResponseEntity<ResponseDTO> removeFromArchieve(@RequestHeader(value = "token") String token,
			@PathVariable(value = "id") String id) throws NoteException, OwnerOfNoteNotFoundException,
			NoteNotFoundException, NoteAuthorisationException, ArchieveException {

		noteService.removeFromArchive(token, id);

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage(messagePropertyConfig.getRemoveFromArchive());
		responseDTO.setStatus(messagePropertyConfig.getSuccessfulStatus());
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@PostMapping(value = "/create-label")
	public ResponseEntity<ResponseDTO> createLabel(@RequestHeader(value = "token") String token,
			@RequestBody String labelName) throws LabelException {

		noteService.createLabel(token, labelName);

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage(messagePropertyConfig.getCreateLabelMsg());
		responseDTO.setStatus(messagePropertyConfig.getSuccessfulStatus());
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@PostMapping(value = "/add-label/{noteId}")
	public ResponseEntity<ResponseDTO> addLabel(@RequestHeader(value = "token") String token,
			@PathVariable(value = "noteId") String noteId, @RequestBody List<String> labels)
			throws OwnerOfNoteNotFoundException, NoteNotFoundException {

		noteService.addLabel(token, noteId, labels);

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage(messagePropertyConfig.getAddLabelMsg());
		responseDTO.setStatus(messagePropertyConfig.getSuccessfulStatus());
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@PutMapping(value = "/edit-label")
	public ResponseEntity<ResponseDTO> editLabel(@RequestHeader(value = "token") String token,
			@RequestParam String currentLabelName, @RequestParam String newLabelName)
			throws OwnerOfNoteNotFoundException, LabelException {

		noteService.editLabel(token, currentLabelName, newLabelName);

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage(messagePropertyConfig.getEditLabelMsg());
		responseDTO.setStatus(messagePropertyConfig.getSuccessfulStatus());
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@DeleteMapping(value = "/delete-label")
	public ResponseEntity<ResponseDTO> deleteLabel(@RequestHeader(value = "token") String token,
			@RequestParam String labelName) throws OwnerOfNoteNotFoundException, LabelException {

		noteService.deleteLabel(token, labelName);

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage(messagePropertyConfig.getDeleteLabelMsg());
		responseDTO.setStatus(messagePropertyConfig.getSuccessfulStatus());
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/get-all-label")
	public ResponseEntity<List<Label>> viewAllLabel(@RequestHeader(value = "token") String token)
			throws OwnerOfNoteNotFoundException, LabelException {

		List<Label> labelList = noteService.getAllLabel(token);

		return new ResponseEntity<>(labelList, HttpStatus.OK);
	}

	@GetMapping(value = "/notes-by-label-name/{labelName}")
	public ResponseEntity<List<Note>> notesByLabelName(@RequestHeader(value = "token") String token,
			@RequestParam String labelName) throws OwnerOfNoteNotFoundException, LabelException {

		List<Note> noteList = noteService.notesByLabelName(token, labelName);

		return new ResponseEntity<>(noteList, HttpStatus.OK);

	}
	
	@GetMapping(value = "/view-all-trashed-note")
	public ResponseEntity<List<Note>> viewAllTrashedNote(@RequestHeader(value = "token") String token)
			throws OwnerOfNoteNotFoundException, LabelException {

		List<Note> trashedNoteList = noteService.getAllTrashedNote(token);

		return new ResponseEntity<>(trashedNoteList, HttpStatus.OK);
	}
	
	@GetMapping(value = "/get-all-archived-note")
	public ResponseEntity<List<Note>> getAllArchivedNote(@RequestHeader(value = "token") String token)
			throws OwnerOfNoteNotFoundException, LabelException {

		List<Note> pinnedNoteList = noteService.getAllArchivedNote(token);

		return new ResponseEntity<>(pinnedNoteList, HttpStatus.OK);
	}
	
	@DeleteMapping(value="/empty-trash")
	public ResponseEntity<ResponseDTO> emptyTrash(@RequestHeader(value = "token") String token) throws OwnerOfNoteNotFoundException{
		
		noteService.emptyTrash(token);
		
		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage(messagePropertyConfig.getEmptyTrashMsg());
		responseDTO.setStatus(messagePropertyConfig.getSuccessfulStatus());
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);		
	}
	
	@PutMapping(value="/remove-label-from-note/{noteId}")
	public ResponseEntity<ResponseDTO> removeLabelFromNote(@RequestHeader(value = "token") String token, @PathVariable(value="noteId")String noteId,@RequestBody String labelName) throws OwnerOfNoteNotFoundException, NoteNotFoundException, LabelException{

		noteService.removeLabelFromNote(token,noteId,labelName);
		
		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage(messagePropertyConfig.getRemoveLabelFromNoteMsg());
		responseDTO.setStatus(messagePropertyConfig.getSuccessfulStatus());
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

}
