package com.bridgelabz.fundoonotes.user.controllers;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.user.exceptions.LoginException;
import com.bridgelabz.fundoonotes.user.exceptions.RegistrationException;
import com.bridgelabz.fundoonotes.user.models.LoginDTO;
import com.bridgelabz.fundoonotes.user.models.RegistrationDTO;
import com.bridgelabz.fundoonotes.user.models.ResponseDTO;
import com.bridgelabz.fundoonotes.user.services.UserService;

/**
 * @purpose validates login and registration using mongodb,global exception
 *          handling concept
 * @author Bijaya Laxmi Senapati
 * @since 10/07/2018
 * @version 1.0
 *
 */
@RestController
@RequestMapping("fundoo/")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<ResponseDTO> login(@RequestBody LoginDTO loginDTO) throws LoginException {
		userService.login(loginDTO);
		ResponseDTO dto = new ResponseDTO();
		dto.setMessage("successfully logged in with email:" + loginDTO.getEmail());
		dto.setStatus(1);
		return new ResponseEntity<>(dto, HttpStatus.OK);

	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<ResponseDTO> register(@RequestBody RegistrationDTO registrationDTO)
			throws RegistrationException, MessagingException {
		userService.register(registrationDTO);
		ResponseDTO dto = new ResponseDTO();
		dto.setMessage("successfully registered with email-id:" + registrationDTO.getEmail());
		dto.setStatus(1);
		return new ResponseEntity<>(dto, HttpStatus.CREATED);

	}
	
	@RequestMapping(value="/activate",method=RequestMethod.GET)
	public ResponseEntity<ResponseDTO> activateUser(@RequestParam(value="token") String token) {
		System.out.println("Inside Activation");
		System.out.println(token);
		userService.activateUser(token);
		ResponseDTO dto=new ResponseDTO();
		dto.setMessage("user successfully activated");
		dto.setStatus(1);
		return new ResponseEntity<>(dto,HttpStatus.OK);
	}
}
