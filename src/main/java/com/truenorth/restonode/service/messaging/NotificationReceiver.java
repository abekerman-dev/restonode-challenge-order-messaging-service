package com.truenorth.restonode.service.messaging;

import org.springframework.stereotype.Component;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class NotificationReceiver extends AbstractRabbitMQReceiver {

	@Override
	protected void handleMessage(JsonElement jsonTree) {
		// In real app, this should send an SMS to the customer
		log.info("[SMS TO CUSTOMER]");
		log.info(createSMSText(jsonTree.getAsJsonObject()));
	}

	private String createSMSText(JsonObject jsonObject) {
		StringBuilder sb = new StringBuilder();

		sb.append("Delivery Order has been processed! ");
		sb.append("ETA: " + jsonObject.get("inSeconds"));
		sb.append(" seconds ");
		sb.append(" (" + jsonObject.get("humanReadable") + ")");

		return sb.toString();
	}

}
