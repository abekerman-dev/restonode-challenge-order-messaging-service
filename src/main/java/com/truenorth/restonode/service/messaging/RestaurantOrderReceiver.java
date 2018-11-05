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
//	@Qualifier("mockEmailService")
	@Qualifier("sendGridService")
	private EmailService emailService;

	@Override
	protected void handleMessage(JsonElement restaurantOrderMessage) throws Exception {
		final JsonObject jsonObject = restaurantOrderMessage.getAsJsonObject();
		emailService.send(jsonObject.get("toEmail").getAsString(), createEmailBody(jsonObject));
	}

	private String createEmailBody(JsonObject restaurantOrderMessage) {
		final StringBuilder sb = new StringBuilder();
		sb.append("<b>Order #:</b>&nbsp;" + restaurantOrderMessage.get("id"));
		sb.append("<br><br>");
		sb.append("<b>Date & time placed:</b>&nbsp;" + restaurantOrderMessage.get("timestamp").getAsString().replaceAll("\"", ""));
		sb.append("<br><br>");
		sb.append("<b>Meals ordered:</b>");
		sb.append("<br><br>");
		final JsonArray meals = restaurantOrderMessage.get("meals").getAsJsonArray();
		for (JsonElement mealElement : meals) {
			sb.append("&nbsp;&nbsp;&nbsp;* ");
			sb.append("<i>");
			sb.append(mealElement.getAsString());
			sb.append("</i><br>");
		}
		sb.append("<br>");
		sb.append("<b>Deliver to:</b>&nbsp;");
		sb.append(restaurantOrderMessage.get("address").getAsString());

		return sb.toString();
	}

}