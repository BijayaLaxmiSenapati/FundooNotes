package com.bridgelabz.fundoonotes.user.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.user.exceptions.LoginException;
import com.bridgelabz.fundoonotes.user.exceptions.RegistrationException;
import com.bridgelabz.fundoonotes.user.models.LoginDTO;
import com.bridgelabz.fundoonotes.user.models.RegistrationDTO;
import com.bridgelabz.fundoonotes.user.models.User;
import com.bridgelabz.fundoonotes.user.repositories.UserRepository;
import com.bridgelabz.fundoonotes.user.utility.Utility;

/**
 *
 * @author Bijaya Laxmi Senapati
 * @since 10/07/2018
 * @version 1.0
 *
 */

@Service
public class UserServiceImplementation implements UserService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	User user;
	@Override
	public int login(LoginDTO loginDTO) throws LoginException {
		Optional<User> optional = userRepository.findByEmail(loginDTO.getEmail());
		// write query in place of findById
		if (!optional.isPresent()) {
			throw new LoginException("Email id not present");
		}
		User dbUser = optional.get();
		int statusCode = Utility.validateUserWhileLoggingIn(loginDTO.getPassword(), dbUser.getPassword());
		return statusCode;
	}

	@Override
	public int register(RegistrationDTO registrationDTO) throws RegistrationException {
		int statusCode = Utility.validateUserWhileRegistering(registrationDTO);
		if (statusCode == 1) {
			user.setName(registrationDTO.getName());
			user.setPassword(registrationDTO.getConfirmPassword());
			user.setContactNumber(registrationDTO.getContactNumber());
			user.setEmail(registrationDTO.getEmail());
			userRepository.insert(user);
		}
		return statusCode;
	}

}
