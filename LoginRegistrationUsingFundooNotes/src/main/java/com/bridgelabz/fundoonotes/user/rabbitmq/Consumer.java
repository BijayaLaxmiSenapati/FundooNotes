package com.bridgelabz.fundoonotes.user.rabbitmq;

import javax.mail.MessagingException;

import com.bridgelabz.fundoonotes.user.models.Email;

public interface Consumer {
	/**
	 * @param email
	 * @throws MessagingException
	 */
	public void recievedMessage(Email email) throws MessagingException ;
}
