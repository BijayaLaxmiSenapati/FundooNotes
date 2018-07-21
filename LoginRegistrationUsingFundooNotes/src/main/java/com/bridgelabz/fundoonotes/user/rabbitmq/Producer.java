package com.bridgelabz.fundoonotes.user.rabbitmq;

import com.bridgelabz.fundoonotes.user.models.Email;

/**
 * @author adminstrato
 *
 */
public interface Producer {
	/**
	 * @param email
	 */
	public void produceMsg(Email email);
}
