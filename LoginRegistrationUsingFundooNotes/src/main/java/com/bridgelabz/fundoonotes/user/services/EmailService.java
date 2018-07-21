package com.bridgelabz.fundoonotes.user.services;

import javax.mail.MessagingException;

import com.bridgelabz.fundoonotes.user.models.Email;

/**
 * @author adminstrato
 *
 */
public interface EmailService {

	/**
	 * @param email
	 * @throws MessagingException
	 */
	public void sendEmail(Email email) throws MessagingException;

}
