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
import com.bridgelabz.fundoonotes.note.configurations.MessagePropertyConfig;
import com.bridgelabz.fundoonotes.user.exceptions.LoginException;
import com.bridgelabz.fundoonotes.user.exceptions.RegistrationException;
import com.bridgelabz.fundoonotes.user.models.LoginDTO;
import com.bridgelabz.fundoonotes.user.models.RegistrationDTO;
import com.bridgelabz.fundoonotes.user.models.ResetPasswordDTO;
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
@RequestMapping("/fundoo")
public class UserController {

	@Autowired
	MessagePropertyConfig messagePropertyConfig;
	
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
		dto.setMessage(messagePropertyConfig.getLoginMsg());
		dto.setStatus(messagePropertyConfig.getSuccessfulStatus());

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
		dto.setMessage(messagePropertyConfig.getRegisterMsg());
		dto.setStatus(messagePropertyConfig.getSuccessfulStatus());

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
		dto.setMessage(messagePropertyConfig.getActivateMsg());
		dto.setStatus(messagePropertyConfig.getSuccessfulStatus());

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
		dto.setMessage(messagePropertyConfig.getForgotPasswordMsg());
		dto.setStatus(messagePropertyConfig.getSuccessfulStatus());

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
		dto.setMessage(messagePropertyConfig.getResetPasswordMsg());
		dto.setStatus(messagePropertyConfig.getSuccessfulStatus());

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

}
