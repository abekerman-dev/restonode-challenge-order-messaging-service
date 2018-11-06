package com.truenorth.restonode.service.messaging;

import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.truenorth.restonode.service.email.EmailService;

@Component
public class RestaurantOrderReceiver extends AbstractRabbitMQReceiver {

	private static final String SPACE = "&nbsp;";
	private static final String DOUBLE_LINE = "<br><br>";

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
		sb.append(DOUBLE_LINE);
		sb.append("<b>Date & time placed:</b>" + SPACE);
		sb.append(restaurantOrderMessage.get("timestamp").getAsString().replaceAll("\"", ""));
		sb.append(DOUBLE_LINE);
		sb.append("<b>Order:</b>");
		sb.append(DOUBLE_LINE);
		final JsonArray meals = restaurantOrderMessage.get("meals").getAsJsonArray();
		for (JsonElement mealElement : meals) {
			sb.append(Collections.nCopies(3, SPACE).stream().collect(Collectors.joining("")));
			sb.append("<i>*" + SPACE);
			sb.append(mealElement.getAsString());
			sb.append("</i><br>");
		}
		sb.append("<br>");
		sb.append("<b>Deliver to:</b>&nbsp;");
		sb.append(restaurantOrderMessage.get("address").getAsString());

		return sb.toString();
	}

}