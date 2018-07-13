package com.bridgelabz.fundoonotes.user.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bridgelabz.fundoonotes.user.models.User;
import com.bridgelabz.fundoonotes.user.services.EmailService;
import com.bridgelabz.fundoonotes.user.services.EmailServiceImplementation;

@Configuration
public class UserConfig {
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();                                                   	
	}
	
	/*@Bean
	public User user() {
		return new User();
	}*/
	
	@Bean
	public EmailService emailService() {
		return new EmailServiceImplementation();
	}

}
