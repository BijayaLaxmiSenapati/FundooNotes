package com.bridgelabz.fundoonotes.user.services;

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

	int login(LoginDTO loginDTO) throws LoginException;

	int register(RegistrationDTO registrationDTO) throws RegistrationException;

}
