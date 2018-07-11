package com.bridgelabz.fundoonotes.user.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bridgelabz.fundoonotes.user.exceptions.EmailNotFoundException;
import com.bridgelabz.fundoonotes.user.exceptions.InvalidContactNumberException;
import com.bridgelabz.fundoonotes.user.exceptions.InvalidEmailException;
import com.bridgelabz.fundoonotes.user.exceptions.InvalidNameException;
import com.bridgelabz.fundoonotes.user.exceptions.InvalidPasswordException;
import com.bridgelabz.fundoonotes.user.exceptions.PasswordNotFoundException;
import com.bridgelabz.fundoonotes.user.exceptions.PasswordNotMatchingException;
import com.bridgelabz.fundoonotes.user.models.ResponseDTO;

@ControllerAdvice
public class GlobalExceptionHandler 
{
	
	@ExceptionHandler(InvalidContactNumberException.class)
	public ResponseEntity<ResponseDTO> handleInvalidContactNumberException(InvalidContactNumberException e)
	{
		ResponseDTO responseDTO=new ResponseDTO();
		responseDTO.setMessage("Given Contact number for registration is not allowed");
		responseDTO.setStatus(-6);
		System.out.println(e);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.BAD_REQUEST);
	}
	
	/*@ExceptionHandler(InvalidEmailException.class)
	public ResponseEntity<ResponseDTO> handleInvalidEmailException(InvalidEmailException e)
	{
		ResponseDTO responseDTO=new ResponseDTO();
		responseDTO.setMessage("Given Email id for registration is not allowed");
		responseDTO.setStatus(-3);
		System.out.println(e);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.BAD_REQUEST);
	}*/
	
	@ExceptionHandler(InvalidNameException.class)
	public ResponseEntity<ResponseDTO> handleInvalidNameException(InvalidNameException e)
	{
		ResponseDTO responseDTO=new ResponseDTO();
		responseDTO.setMessage("Given Email id for registration is not allowed");
		responseDTO.setStatus(-7);
		System.out.println(e);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidPasswordException.class)
	public ResponseEntity<ResponseDTO> handleInvalidPasswordException(InvalidPasswordException e)
	{
		ResponseDTO responseDTO=new ResponseDTO();
		responseDTO.setMessage("Given password for registration is not allowed");
		responseDTO.setStatus(-5);
		System.out.println(e);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(EmailNotFoundException.class)
	public ResponseEntity<ResponseDTO> handleEmailNotFoundException(EmailNotFoundException e)
	{
		ResponseDTO responseDTO=new ResponseDTO();
		responseDTO.setMessage("Given email id for login is not a uthorised one");
		responseDTO.setStatus(-1);
		System.out.println(e);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(PasswordNotMatchingException.class)
	public ResponseEntity<ResponseDTO> handlePasswordNotMatchingException(PasswordNotMatchingException e)
	{
		ResponseDTO responseDTO=new ResponseDTO();
		responseDTO.setMessage("Password Fields are not matching(while registering)");
		responseDTO.setStatus(-4);
		System.out.println(e);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(PasswordNotFoundException.class)
	public ResponseEntity<ResponseDTO> handlePasswordNotFoundException(PasswordNotFoundException e)
	{
		ResponseDTO responseDTO=new ResponseDTO();
		responseDTO.setMessage("Given Password is not matching with password on database(while logging in)");
		responseDTO.setStatus(-2);
		System.out.println(e);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.BAD_REQUEST);
	}
}
