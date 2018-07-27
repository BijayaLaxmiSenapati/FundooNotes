package com.bridgelabz.fundoonotes.user.services;

import java.util.Optional;
import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.bridgelabz.fundoonotes.user.exceptions.LoginException;
import com.bridgelabz.fundoonotes.user.exceptions.RegistrationException;
import com.bridgelabz.fundoonotes.user.models.Email;
import com.bridgelabz.fundoonotes.user.models.LoginDTO;
import com.bridgelabz.fundoonotes.user.models.RegistrationDTO;
import com.bridgelabz.fundoonotes.user.models.ResetPasswordDTO;
import com.bridgelabz.fundoonotes.user.models.User;
import com.bridgelabz.fundoonotes.user.rabbitmq.Producer;
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
	private Producer producer;

	@Override
	public String login(LoginDTO loginDTO) throws LoginException {
		Utility.validateUserWhileLogin(loginDTO);

		Optional<User> optional = userRepository.findByEmail(loginDTO.getEmail());

		if (!optional.isPresent()) {

			throw new LoginException("Email id not present");

		}
		User dbUser = optional.get();

		if (!passwordEncoder.matches(loginDTO.getPassword(), dbUser.getPassword())) {
			throw new LoginException("Wrong Password given");
		}
		if (!dbUser.isActivationStatus()) {
			throw new LoginException(
					"given account is not yet activated. First activate your account from the inbox, with mail SUBJECT \"Activation Link\"");
		}
		
		TokenProvider tokenProvider = new TokenProvider();
		String token = tokenProvider.generateToken(dbUser.getId());
		return token;

	}

	@Override
	public void register(RegistrationDTO registrationDTO) throws RegistrationException, MessagingException {

		Utility.validateUserWhileRegistering(registrationDTO);

		Optional<User> optional = userRepository.findByEmail(registrationDTO.getEmail());

		if (optional.isPresent()) {

			throw new RegistrationException("Email id already exists");
		}

		User user = new User();
		user.setName(registrationDTO.getName());
		user.setPassword(passwordEncoder.encode(registrationDTO.getConfirmPassword()));
		user.setContactNumber(registrationDTO.getContactNumber());
		user.setEmail(registrationDTO.getEmail());

		userRepository.insert(user);

		optional = userRepository.findByEmail(registrationDTO.getEmail());
		String userId = optional.get().getId();

		TokenProvider tokenProvider = new TokenProvider();
		String token = tokenProvider.generateToken(userId);

		Email email = new Email();
		email.setTo(registrationDTO.getEmail());
		email.setSubject("Activation Link");
		email.setText("http://localhost:8080/fundoo/activate/?token=" + token);

		producer.produceMsg(email);

	}
	

	@Override
	public void activateUser(String token) {

		TokenProvider tokenProvider = new TokenProvider();
		String idFromToken = tokenProvider.parseToken(token);

		Optional<User> optional = userRepository.findById(idFromToken);

		User user = optional.get();

		user.setActivationStatus(true);

		userRepository.save(user);

	}

	@Override
	public void forgotPassword(String emailId) throws MessagingException, LoginException {

		Utility.validateWhileForgotPassword(emailId);

		Optional<User> optional = userRepository.findByEmail(emailId);
		String userId = optional.get().getId();

		TokenProvider tokenProvider = new TokenProvider();
		String token = tokenProvider.generateToken(userId);

		Email email = new Email();
		email.setTo(emailId);
		email.setSubject("Link for forgot password");
		email.setText("http://localhost:8080/fundoo/resetPassword/?token=" + token);

		producer.produceMsg(email);

	}

	@Override
	public void resetPassword(String token, ResetPasswordDTO resetPasswordDTO) throws LoginException {

		Utility.validateWhileResetPassword(resetPasswordDTO);

		TokenProvider tokenProvider = new TokenProvider();
		String userIdFromToken = tokenProvider.parseToken(token);

		Optional<User> optional = userRepository.findById(userIdFromToken);

		User user = optional.get();
		user.setPassword(passwordEncoder.encode(resetPasswordDTO.getNewPassword()));
		userRepository.save(user);
	}

}
