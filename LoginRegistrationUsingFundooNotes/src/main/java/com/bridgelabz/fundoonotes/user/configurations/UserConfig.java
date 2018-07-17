package com.bridgelabz.fundoonotes.user.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bridgelabz.fundoonotes.user.models.User;
import com.bridgelabz.fundoonotes.user.rabbitmq.Producer;
import com.bridgelabz.fundoonotes.user.rabbitmq.ProducerImpl;
import com.bridgelabz.fundoonotes.user.services.EmailService;
import com.bridgelabz.fundoonotes.user.services.EmailServiceImplementation;

@Configuration
public class UserConfig {
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();                                                   	
	}
	
	@Bean
	@Scope("prototype")
	public User user() {
		return new User();
	}
	
	@Bean
	@Scope("prototype")
	public EmailService emailService() {
		return new EmailServiceImplementation();
	}

	@Bean
	@Scope("prototype")
	public Producer producer() {
		return new ProducerImpl();
	}
}
