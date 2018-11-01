package com.truenorth.restonode.service.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.truenorth.restonode.service.email.EmailService;

@Component
public class OrderReceiver extends AbstractRabbitMQReceiver {

	@Autowired
	// FIXME remove qualifiers below
//	@Qualifier("mockEmailService")
	@Qualifier("sendGridService")
	private EmailService emailService;

	@Override
	protected void handleMessage(JsonElement jsonTree) throws Exception {
		final JsonObject jsonObject = jsonTree.getAsJsonObject();
		final String toEmail = jsonObject.getAsJsonObject("restaurant").get("email").getAsString();
		final JsonArray meals = jsonObject.get("meals").getAsJsonArray();
		final double totalAmount = jsonObject.get("totalAmount").getAsDouble();
		final String string = jsonObject.get("address").getAsString();
		emailService.send(toEmail, createEmailBody(meals, totalAmount, string));
	}

	private String createEmailBody(JsonArray meals, double totalAmount, String address) {
		StringBuilder sb = new StringBuilder();
		for (JsonElement mealElement : meals) {
			JsonObject mealObject = mealElement.getAsJsonObject();
			sb.append(mealObject.get("name"));
			sb.append(" x ");
			sb.append(mealObject.get("qty"));
			sb.append(System.getProperty("line.separator"));
		}
		sb.append("Total amount: $");
		sb.append(totalAmount);
		sb.append(System.getProperty("line.separator"));
		sb.append("Deliver to: ");
		sb.append(address);

		return sb.toString();
	}

}