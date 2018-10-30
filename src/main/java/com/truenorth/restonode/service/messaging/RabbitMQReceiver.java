package com.truenorth.restonode.service.messaging;

public interface RabbitMQReceiver {

	public void receive(String message);
	
}
