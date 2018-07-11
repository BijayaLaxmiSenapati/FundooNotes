package com.bridgelabz.fundoonotes.user.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.user.exceptions.EmailNotFoundException;
import com.bridgelabz.fundoonotes.user.exceptions.InvalidContactNumberException;
import com.bridgelabz.fundoonotes.user.exceptions.InvalidEmailException;
import com.bridgelabz.fundoonotes.user.exceptions.InvalidNameException;
import com.bridgelabz.fundoonotes.user.exceptions.InvalidPasswordException;
import com.bridgelabz.fundoonotes.user.exceptions.PasswordNotFoundException;
import com.bridgelabz.fundoonotes.user.exceptions.PasswordNotMatchingException;
import com.bridgelabz.fundoonotes.user.models.LoginDTO;
import com.bridgelabz.fundoonotes.user.models.RegistrationDTO;
import com.bridgelabz.fundoonotes.user.models.ResponseDTO;
import com.bridgelabz.fundoonotes.user.services.UserService;
/**
 * @purpose validates login and registration using mongodb,global exception handling concept
 * @author Bijaya Laxmi Senapati
 * @since 10/07/2018
 * @version 1.0
 *
 */
@RestController
public class UserController 
{
	@Autowired
	private UserService traineeService;
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public ResponseEntity<ResponseDTO> login(@RequestBody LoginDTO loginDTO) throws EmailNotFoundException, PasswordNotFoundException
	{
		ResponseDTO dto = new ResponseDTO();
		int statusCode=traineeService.login(loginDTO);
		
		if(statusCode==-1)
		{
			throw new EmailNotFoundException(loginDTO.getEmail());
		}
		else if(statusCode==-2)
		{
			throw new PasswordNotFoundException(loginDTO.getEmail());
		}
		else
		{
			dto.setMessage("successfully logged in with email:"+loginDTO.getEmail());
			dto.setStatus(1);
			return new ResponseEntity<>(dto, HttpStatus.OK);
		}
		
	}
	
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public ResponseEntity<ResponseDTO> register(@RequestBody RegistrationDTO registrationDTO) throws InvalidPasswordException, PasswordNotMatchingException, InvalidContactNumberException, InvalidEmailException, InvalidNameException
	{
		ResponseDTO dto = new ResponseDTO();
		int statusCode=traineeService.register(registrationDTO);
		if(statusCode==-6)
		{
			throw new InvalidContactNumberException(registrationDTO.getEmail());
		}
		else if(statusCode==-3)
		{
			throw new InvalidEmailException(registrationDTO.getEmail());
		}
		else if(statusCode==-7)
		{
			throw new InvalidNameException(registrationDTO.getEmail());
		}
		else if(statusCode==-5)
		{
			throw new InvalidPasswordException(registrationDTO.getEmail());
		}
		else if(statusCode==-4)
		{
			throw new PasswordNotMatchingException(registrationDTO.getEmail());
		}
		else
		{
		dto.setMessage("successfully registered with email-id:"+registrationDTO.getEmail());
		dto.setStatus(1);
		return new ResponseEntity<>(dto, HttpStatus.CREATED);
		}
		
	}
}
