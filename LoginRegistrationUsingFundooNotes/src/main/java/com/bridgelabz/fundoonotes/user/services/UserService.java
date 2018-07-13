package com.bridgelabz.fundoonotes.user.services;

import javax.mail.MessagingException;

import com.bridgelabz.fundoonotes.user.exceptions.LoginException;
import com.bridgelabz.fundoonotes.user.exceptions.RegistrationException;
import com.bridgelabz.fundoonotes.user.models.LoginDTO;
import com.bridgelabz.fundoonotes.user.models.RegistrationDTO;

/**
 *
 * @author Bijaya Laxmi Senapati
 * @since 10/07/2018
 * @version 1.0
 *
 */

public interface UserService {

	void login(LoginDTO loginDTO) throws LoginException;

	void register(RegistrationDTO registrationDTO) throws RegistrationException, MessagingException;

	void activateUser(String token);

}
