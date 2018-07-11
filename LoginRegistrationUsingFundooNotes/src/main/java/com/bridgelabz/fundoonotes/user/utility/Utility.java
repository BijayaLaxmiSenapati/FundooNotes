package com.bridgelabz.fundoonotes.user.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bridgelabz.fundoonotes.user.models.RegistrationDTO;
import com.bridgelabz.fundoonotes.user.regex.EmailValidator;

@Component
public class Utility {
	@Autowired
	EmailValidator emailValidator;

	public int validateUserWhileRegistering(RegistrationDTO registrationDTO) 
	{
		if(registrationDTO.getName().length()<=3)
		{
			return -7;
		}
		else if(registrationDTO.getContactNumber().length()!=10)
		{
			return -6;
		}
		else if(registrationDTO.getPassword().length()<8)
		{
			return -5;
		}
		else if(!registrationDTO.getPassword().equals(registrationDTO.getConfirmPassword()))
		{
			return -4;
		}
		/*else if(emailValidator.validateEmail(registrationDTO.getEmail()))
		{
			//create exception for email not valid(-2)
			return -3;
		}*/
		return 1;
	}


	public int validateUserWhileLoggingIn(String givenPassword, String dbPassword)
	{
		if(givenPassword!=dbPassword)
		{
			return -2;
		}
		return 1;
	}

}
