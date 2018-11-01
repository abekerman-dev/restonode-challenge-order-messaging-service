package com.truenorth.restonode.service.messaging;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public abstract class AbstractRabbitMQReceiver implements RabbitMQReceiver {

	@Override
	public void receive(String jsonMessage) throws Exception {
		JsonElement jsonTree = new JsonParser().parse(jsonMessage);
		handleMessage(jsonTree);
	}

	protected abstract void handleMessage(JsonElement jsonTree) throws Exception;

}
