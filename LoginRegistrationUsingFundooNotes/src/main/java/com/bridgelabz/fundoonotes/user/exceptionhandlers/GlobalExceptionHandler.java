
package com.bridgelabz.fundoonotes.user.exceptionhandlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bridgelabz.fundoonotes.user.exceptions.LoginException;
import com.bridgelabz.fundoonotes.user.exceptions.RegistrationException;
import com.bridgelabz.fundoonotes.user.models.ResponseDTO;

/**
 *
 * @author Bijaya Laxmi Senapati
 * @since 10/07/2018
 * @version 1.0
 *
 */

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(RegistrationException.class)
	public ResponseEntity<ResponseDTO> handleRegistrationException(RegistrationException e) {
		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage(e.getMessage());
		responseDTO.setStatus(-3);
		System.out.println(e);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(LoginException.class)
	public ResponseEntity<ResponseDTO> handleLoginException(LoginException e) {
		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage(e.getMessage());
		responseDTO.setStatus(-2);
		System.out.println(e);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ResponseDTO> handleGenericException(Exception e) {
		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage("Something went wrong");
		responseDTO.setStatus(-1);
		System.out.println(e);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
