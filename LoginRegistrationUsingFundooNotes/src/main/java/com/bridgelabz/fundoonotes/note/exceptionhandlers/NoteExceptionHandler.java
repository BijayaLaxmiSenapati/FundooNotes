package com.bridgelabz.fundoonotes.note.exceptionhandlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bridgelabz.fundoonotes.note.exceptions.EmptyNoteException;
import com.bridgelabz.fundoonotes.note.exceptions.InvalidDateException;
import com.bridgelabz.fundoonotes.note.exceptions.NoteAuthorisationException;
import com.bridgelabz.fundoonotes.note.exceptions.NoteException;
import com.bridgelabz.fundoonotes.note.exceptions.NoteNotFoundException;
import com.bridgelabz.fundoonotes.note.exceptions.OwnerOfNoteNotFoundException;
import com.bridgelabz.fundoonotes.user.models.ResponseDTO;

@ControllerAdvice
public class NoteExceptionHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(NoteExceptionHandler.class);

	/**
	 * @param e
	 * @return
	 */
	@ExceptionHandler(NoteException.class)
	public ResponseEntity<ResponseDTO> handleNoteException(NoteException e) {
		
		LOGGER.error(e.getMessage());

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage(e.getMessage());
		responseDTO.setStatus(-10);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * @param e
	 * @return
	 */
	@ExceptionHandler(EmptyNoteException.class)
	public ResponseEntity<ResponseDTO> handleEmptyNoteException(EmptyNoteException e) {
		
		LOGGER.error(e.getMessage());

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage(e.getMessage());
		responseDTO.setStatus(-20);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * @param e
	 * @return
	 */
	@ExceptionHandler(NoteAuthorisationException.class)
	public ResponseEntity<ResponseDTO> handleNoteAuthorisationException(NoteAuthorisationException e) {
		
		LOGGER.error(e.getMessage());

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage(e.getMessage());
		responseDTO.setStatus(-30);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * @param e
	 * @return
	 */
	@ExceptionHandler(NoteNotFoundException.class)
	public ResponseEntity<ResponseDTO> handleNoteNotFoundException(NoteNotFoundException e) {
		
		LOGGER.error(e.getMessage());

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage(e.getMessage());
		responseDTO.setStatus(-40);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * @param e
	 * @return
	 */
	@ExceptionHandler(OwnerOfNoteNotFoundException.class)
	public ResponseEntity<ResponseDTO> handleOwnerOfNoteNotFoundException(OwnerOfNoteNotFoundException e) {
		
		LOGGER.error(e.getMessage());

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage(e.getMessage());
		responseDTO.setStatus(-50);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidDateException.class)
	public ResponseEntity<ResponseDTO> handleInvalidDateException(InvalidDateException e) {
		
		LOGGER.error(e.getMessage());

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage(e.getMessage());
		responseDTO.setStatus(-50);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.BAD_REQUEST);
	}

}
