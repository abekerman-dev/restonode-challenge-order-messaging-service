package com.truenorth.restonode.service.messaging;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractRabbitMQReceiver implements RabbitMQReceiver {

	@Override
	public void receive(String jsonMessage) {
//		log.debug(this.getClass().getSimpleName() + " received message");
//		log.debug("\n\t" + jsonMessage);
		JsonParser parser = new JsonParser();
		JsonElement jsonTree = parser.parse(jsonMessage);
		
		handleMessage(jsonTree); //new GsonJsonParser().parseMap(jsonMessage));
	}

	protected abstract void handleMessage(JsonElement jsonTree);

}
