package com.bridgelabz.fundoonotes.user.rabbitmq;

import com.bridgelabz.fundoonotes.user.models.Email;

public interface Producer {
	public void produceMsg(Email email);
}
