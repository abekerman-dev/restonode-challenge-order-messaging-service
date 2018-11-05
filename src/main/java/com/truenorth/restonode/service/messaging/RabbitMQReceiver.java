package com.truenorth.restonode.service.messaging;

/**
 * Entry point for messages coming from a RabbitMQ queue
 * 
 * @author andres
 *
 */
public interface RabbitMQReceiver {

	public void receive(String stringifiedJson) throws Exception;
	
}
