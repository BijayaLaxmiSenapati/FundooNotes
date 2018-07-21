
package com.bridgelabz.fundoonotes.user.exceptionhandlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 *
 */

@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	/**
	 * @param e
	 * @return
	 */
	@ExceptionHandler(RegistrationException.class)
	public ResponseEntity<ResponseDTO> handleRegistrationException(RegistrationException e) {

		LOGGER.error(e.getMessage());

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage(e.getMessage());
		responseDTO.setStatus(-3);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.BAD_REQUEST);
	}

	/**
	 * @param e
	 * @return
	 */
	@ExceptionHandler(LoginException.class)
	public ResponseEntity<ResponseDTO> handleLoginException(LoginException e) {

		LOGGER.error(e.getMessage());

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage(e.getMessage());
		responseDTO.setStatus(-2);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.BAD_REQUEST);
	}

	/**
	 * @param e
	 * @return
	 */
	/*@ExceptionHandler(Exception.class)
	public ResponseEntity<ResponseDTO> handleGenericException(Exception e) {

		LOGGER.error(e.getMessage());

		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage("Something went wrong");
		responseDTO.setStatus(-1);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
	}*/
}
