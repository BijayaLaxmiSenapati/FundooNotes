package com.bridgelabz.fundoonotes.user.controllers;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.bridgelabz.fundoonotes.user.exceptions.LoginException;
import com.bridgelabz.fundoonotes.user.exceptions.RegistrationException;
import com.bridgelabz.fundoonotes.user.models.LoginDTO;
import com.bridgelabz.fundoonotes.user.models.RegistrationDTO;
import com.bridgelabz.fundoonotes.user.models.ResetPasswordDTO;
import com.bridgelabz.fundoonotes.user.models.ResponseDTO;
import com.bridgelabz.fundoonotes.user.services.UserService;

//import io.swagger.annotations.ApiOperation;
/**
 * @purpose validates login and registration using mongodb,global exception
 *          handling concept
 * @author Bijaya Laxmi Senapati
 * @since 10/07/2018
 * @version 1.0
 *
 */
@RestController
@RequestMapping("/fundoo")
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * @param loginDTO
	 * @param response
	 * @return
	 * @throws LoginException
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<ResponseDTO> login(@RequestBody LoginDTO loginDTO, HttpServletResponse response) throws LoginException {

		String tokenValue=userService.login(loginDTO);
		ResponseDTO dto = new ResponseDTO();
		dto.setMessage("successfully logged in with email:" + loginDTO.getEmail());
		dto.setStatus(1);

		response.setHeader("token", tokenValue);
		return new ResponseEntity<>(dto, HttpStatus.OK);

	}

	/**
	 * @param registrationDTO
	 * @return
	 * @throws RegistrationException
	 * @throws MessagingException
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<ResponseDTO> register(@RequestBody RegistrationDTO registrationDTO)
			throws RegistrationException, MessagingException {

		userService.register(registrationDTO);
		ResponseDTO dto = new ResponseDTO();
		dto.setMessage("successfully registered with email-id");
		dto.setStatus(1);

		return new ResponseEntity<>(dto, HttpStatus.CREATED);

	}

	/**
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/activate", method = RequestMethod.GET)
	public ResponseEntity<ResponseDTO> activateUser(@RequestParam(value = "token") String token) {

		userService.activateUser(token);
		ResponseDTO dto = new ResponseDTO();
		dto.setMessage("user successfully activated");
		dto.setStatus(1);

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	/**
	 * @param emailId
	 * @return
	 * @throws MessagingException
	 * @throws LoginException
	 */
	@RequestMapping(value = "/forgotpassword", method = RequestMethod.POST)
	public ResponseEntity<ResponseDTO> forgotPassword(@RequestBody String emailId)
			throws MessagingException, LoginException {

		userService.forgotPassword(emailId);
		ResponseDTO dto = new ResponseDTO();
		dto.setMessage("email sent for changing password");
		dto.setStatus(1);

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	/**
	 * @param token
	 * @param resetPasswordDTO
	 * @return
	 * @throws LoginException
	 */
	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	public ResponseEntity<ResponseDTO> resetPassword(@RequestParam(value = "token") String token,
			@RequestBody ResetPasswordDTO resetPasswordDTO) throws LoginException {

		userService.resetPassword(token, resetPasswordDTO);
		ResponseDTO dto = new ResponseDTO();
		dto.setMessage("password of your account has been successfully changed");
		dto.setStatus(1);

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

}
