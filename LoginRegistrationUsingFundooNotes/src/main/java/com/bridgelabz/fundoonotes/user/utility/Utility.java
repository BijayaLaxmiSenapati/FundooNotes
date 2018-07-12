package com.bridgelabz.fundoonotes.user.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bridgelabz.fundoonotes.user.exceptions.RegistrationException;
import com.bridgelabz.fundoonotes.user.models.RegistrationDTO;

/**
 *
 * @author Bijaya Laxmi Senapati
 * @since 10/07/2018
 * @version 1.0
 *
 */

// @Component//remove componenent from here and remove creating object of
// utility from service
public class Utility {
	
	//private static int workload = 12;		

	private static final String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";

	// static Pattern object, since pattern is fixed

	private static Pattern pattern;

	// non-static Matcher object because it's created from the input String

	private static Matcher matcher;

	public static boolean validateEmail(String email) {
		pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
		matcher = pattern.matcher(email);
		return matcher.matches();
	}

	public static void validateUserWhileRegistering(RegistrationDTO registrationDTO) throws RegistrationException {
		if (registrationDTO.getName().length() <= 3) {
			throw new RegistrationException("Name should have atleast 3 charecters");
		} else if (registrationDTO.getContactNumber().length() != 10) {
			throw new RegistrationException("Contact number should have 10 digits ");
		} else if (registrationDTO.getPassword().length() < 8) {
			throw new RegistrationException("Password should have atleast 8 charecters");
		} else if (!registrationDTO.getPassword().equals(registrationDTO.getConfirmPassword())) {
			throw new RegistrationException("Both 'password' and 'confirmPassword' field should have same value");
		} /*else if (validateEmail(registrationDTO.getEmail())) {
			throw new RegistrationException("Email Format not correct");
		}*/

	}
	
	/*public static String hashPassword(String password_plaintext) {
		String salt = BCrypt.gensalt(workload);
		String hashed_password = BCrypt.hashpw(password_plaintext, salt);

		return (hashed_password);
	}*/

}
