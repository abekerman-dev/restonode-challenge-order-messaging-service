package com.truenorth.restonode.service.messaging;

import org.springframework.stereotype.Component;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CustomerNotificationReceiver extends AbstractRabbitMQReceiver {

	/**
	 * In a real-world app, this should send an SMS to the customer! 
	 */
	@Override
	protected void handleMessage(JsonElement customerNotificationMessage) throws Exception {
		log.info("[SMS TO CUSTOMER] " + createSMSText(customerNotificationMessage.getAsJsonObject()));
	}
	
	private String createSMSText(JsonObject customerNotificationMessage) {
		final StringBuilder sb = new StringBuilder();
		sb.append("Delivery Order is being cooked up!");
		sb.append(System.getProperty("line.separator"));
		sb.append("Order #: " + customerNotificationMessage.get("id"));
		sb.append(System.getProperty("line.separator"));
		sb.append("Placed: " + customerNotificationMessage.get("timestamp"));
		sb.append(System.getProperty("line.separator"));
		sb.append("ETA: " + customerNotificationMessage.get("eta"));
		sb.append(System.getProperty("line.separator"));
		sb.append("Total amount: $");
		sb.append(customerNotificationMessage.get("totalAmount"));

		return sb.toString();
	}

}
