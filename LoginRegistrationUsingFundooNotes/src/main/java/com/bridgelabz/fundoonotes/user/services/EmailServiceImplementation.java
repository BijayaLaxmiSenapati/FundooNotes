package com.bridgelabz.fundoonotes.user.services;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.bridgelabz.fundoonotes.user.models.Email;

/**
 * @author adminstrato
 *
 */
public class EmailServiceImplementation implements EmailService {

	@Autowired
	public JavaMailSender emailSender;

	public void sendEmail(Email email) throws MessagingException {

		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		helper.setTo(email.getTo());
		helper.setSubject(email.getSubject());
		helper.setText(email.getText());

		emailSender.send(message);

	}

}
