package com.bridgelabz.fundoonotes.user.rabbitmq;

import javax.mail.MessagingException;

import com.bridgelabz.fundoonotes.user.models.Email;

public interface Consumer {
	public void recievedMessage(Email email) throws MessagingException ;
}
