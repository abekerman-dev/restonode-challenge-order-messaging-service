package com.truenorth.restonode.service.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.truenorth.restonode.service.email.EmailService;

@Component
public class RestaurantOrderReceiver extends AbstractRabbitMQReceiver {

	// FIXME switcht to sendGridService
	@Autowired
	@Qualifier("mockEmailService")
//	@Qualifier("sendGridService")
	private EmailService emailService;

	@Override
	protected void handleMessage(JsonElement restaurantOrderMessage) throws Exception {
		final JsonObject jsonObject = restaurantOrderMessage.getAsJsonObject();
		emailService.send(jsonObject.get("toEmail").getAsString(), createEmailBody(jsonObject));
	}

	private String createEmailBody(JsonObject restaurantOrderMessage) {
		final StringBuilder sb = new StringBuilder();
		sb.append("Order #: " + restaurantOrderMessage.get("id"));
		sb.append(System.getProperty("line.separator"));
		sb.append("Placed: " + restaurantOrderMessage.get("timestamp"));
		sb.append(System.getProperty("line.separator"));
		final JsonArray meals = restaurantOrderMessage.get("meals").getAsJsonArray();
		for (JsonElement mealElement : meals) {
			sb.append(mealElement.getAsString());
			sb.append(System.getProperty("line.separator"));
		}
		sb.append("Deliver to: ");
		sb.append(restaurantOrderMessage.get("address").getAsString());

		return sb.toString();
	}

}