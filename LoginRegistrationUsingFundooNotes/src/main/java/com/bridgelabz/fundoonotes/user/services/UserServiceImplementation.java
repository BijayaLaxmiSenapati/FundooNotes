package com.bridgelabz.fundoonotes.user.services;

import java.util.Optional;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import com.bridgelabz.fundoonotes.user.configurations.UserConfig;
import com.bridgelabz.fundoonotes.user.exceptions.LoginException;
import com.bridgelabz.fundoonotes.user.exceptions.RegistrationException;
import com.bridgelabz.fundoonotes.user.models.Email;
import com.bridgelabz.fundoonotes.user.models.LoginDTO;
import com.bridgelabz.fundoonotes.user.models.RegistrationDTO;
import com.bridgelabz.fundoonotes.user.models.User;
import com.bridgelabz.fundoonotes.user.repositories.UserRepository;
import com.bridgelabz.fundoonotes.user.security.TokenProvider;
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
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired 
	private WebApplicationContext context;

	@Override
	public void login(LoginDTO loginDTO) throws LoginException {

		Optional<User> optional = userRepository.findByEmail(loginDTO.getEmail());

		if (!optional.isPresent()) {

			throw new LoginException("Email id not present");

		}
		User dbUser = optional.get();

		if (!passwordEncoder.matches(loginDTO.getPassword(),dbUser.getPassword())) {
			throw new LoginException("Wrong Password given");
		}

	}

	@Override
	public void register(RegistrationDTO registrationDTO) throws RegistrationException, MessagingException {

		Utility.validateUserWhileRegistering(registrationDTO);
		
		Optional<User> optional = userRepository.findByEmail(registrationDTO.getEmail());
		
		if(optional.isPresent()) {
			
			throw new RegistrationException("Email id already exists");
		}

		User user = new User();
		user.setName(registrationDTO.getName());
		user.setPassword(passwordEncoder.encode(registrationDTO.getConfirmPassword()));
		user.setContactNumber(registrationDTO.getContactNumber());
		user.setEmail(registrationDTO.getEmail());

		userRepository.insert(user);
		
		TokenProvider tokenProvider=new TokenProvider();
		String token=tokenProvider.generateToken(user);
		System.out.println(token);
		
		EmailService emailService=context.getBean(EmailService.class);//have doubt for wiring
		
		Email email=new Email();
		email.setTo(registrationDTO.getEmail());
		email.setSubject("Activation Link");
		email.setText("http:lacalhost:8080/activation/?"+token);
		
		emailService.sendEmail(email);
		
		

	}

	@Override
	public void activateUser(String token) {
		
		TokenProvider tokenProvider=new TokenProvider();
		String emailFromToken=tokenProvider.parseToken(token);
		
		Optional<User> optional = userRepository.findByEmail(emailFromToken);
		
		User user=optional.get();
		
		user.setActivationStatus(true);//it may give some exception....so create one exception
	}

}
