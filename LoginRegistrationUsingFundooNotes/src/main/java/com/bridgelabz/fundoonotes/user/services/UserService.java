package com.bridgelabz.fundoonotes.user.services;

import javax.mail.MessagingException;

import com.bridgelabz.fundoonotes.user.exceptions.LoginException;
import com.bridgelabz.fundoonotes.user.exceptions.RegistrationException;
import com.bridgelabz.fundoonotes.user.models.LoginDTO;
import com.bridgelabz.fundoonotes.user.models.RegistrationDTO;
import com.bridgelabz.fundoonotes.user.models.ResetPasswordDTO;

/**
 *
 * @author Bijaya Laxmi Senapati
 * @since 10/07/2018
 * @version 1.0
 *
 */

public interface UserService {

	/**
	 * @param loginDTO
	 * @return
	 * @throws LoginException
	 */
	String login(LoginDTO loginDTO) throws LoginException;

	/**
	 * @param registrationDTO
	 * @throws RegistrationException
	 * @throws MessagingException
	 */
	void register(RegistrationDTO registrationDTO) throws RegistrationException, MessagingException;

	/**
	 * @param token
	 */
	void activateUser(String token);

	/**
	 * @param emailId
	 * @throws MessagingException
	 * @throws LoginException
	 */
	void forgotPassword(String emailId) throws MessagingException, LoginException;

	/**
	 * @param token
	 * @param resetPasswordDTO
	 * @throws LoginException
	 */
	void resetPassword(String token, ResetPasswordDTO resetPasswordDTO) throws LoginException;

}
