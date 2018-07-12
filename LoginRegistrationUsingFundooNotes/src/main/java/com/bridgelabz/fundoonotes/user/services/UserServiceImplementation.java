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
	private UserRepository userRepository;

	@Override
	public void login(LoginDTO loginDTO) throws LoginException {

		Optional<User> optional = userRepository.findByEmail(loginDTO.getEmail());

		if (!optional.isPresent()) {

			throw new LoginException("Email id not present");

		}
		User dbUser = optional.get();

		if (!loginDTO.getPassword().equals(dbUser.getPassword())) {
			throw new LoginException("Wrong Password given");
		}

	}

	@Override
	public void register(RegistrationDTO registrationDTO) throws RegistrationException {

		Utility.validateUserWhileRegistering(registrationDTO);
		
		Optional<User> optional = userRepository.findByEmail(registrationDTO.getEmail());
		
		if(optional!=null) {
			
			throw new RegistrationException("Email id already exists");
		}

		User user = new User();
		user.setName(registrationDTO.getName());
		
	/*	//private static int workload = 12;*/		
		
		/*public static String hashPassword(String password_plaintext) {
			String salt = BCrypt.gensalt(workload);
			String hashed_password = BCrypt.hashpw(password_plaintext, salt);

			return(hashed_password);
	}*/
		
		
		/*public static boolean checkPassword(String password_plaintext, String stored_hash) {
			boolean password_verified = false;

			if(null == stored_hash || !stored_hash.startsWith("$2a$"))
				throw new java.lang.IllegalArgumentException("Invalid hash provided for comparison");

			password_verified = BCrypt.checkpw(password_plaintext, stored_hash);

			return(password_verified);
	}*/
		user.setPassword(registrationDTO.getConfirmPassword());
		user.setContactNumber(registrationDTO.getContactNumber());
		user.setEmail(registrationDTO.getEmail());

		userRepository.insert(user);

	}

}
