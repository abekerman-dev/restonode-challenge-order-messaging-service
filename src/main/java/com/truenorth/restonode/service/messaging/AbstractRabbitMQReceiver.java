package com.truenorth.restonode.service.messaging;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * Abstract class which parses incoming RabbitMQ stringified json messages and
 * delegates their handling to its suclasses
 * 
 * @author andres
 *
 */
public abstract class AbstractRabbitMQReceiver implements RabbitMQReceiver {

	@Override
	public void receive(String stringifiedJson) throws Exception {
		final JsonElement jsonTree = new JsonParser().parse(stringifiedJson);
		handleMessage(jsonTree);
	}

	protected abstract void handleMessage(JsonElement jsonTree) throws Exception;

}
