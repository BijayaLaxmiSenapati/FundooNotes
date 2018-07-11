package com.bridgelabz.fundoonotes.user.services;

import com.bridgelabz.fundoonotes.user.models.LoginDTO;
import com.bridgelabz.fundoonotes.user.models.RegistrationDTO;

public interface UserService {

	int login(LoginDTO loginDTO);

	int register(RegistrationDTO registrationDTO);

}
