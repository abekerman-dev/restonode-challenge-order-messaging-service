package com.truenorth.restonode.service.messaging;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.truenorth.restonode.service.email.EmailService;

@Component
public class OrderReceiver extends AbstractRabbitMQReceiver {

	@Autowired
	private EmailService emailService;
	
	@Override
	protected void handleMessage(JsonElement jsonTree) {
		JsonObject jsonObject = jsonTree.getAsJsonObject();
		String toEmail = (String) jsonObject.get("toEmail").getAsString();
		try {
			emailService.send(toEmail, createEmailBody(jsonObject.getAsJsonObject("order")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String createEmailBody(JsonObject jsonOrder) {
		StringBuilder sb = new StringBuilder();
		
		JsonArray meals = jsonOrder.getAsJsonArray("meals");
		for (JsonElement mealElement : meals) {
			JsonObject mealObject = mealElement.getAsJsonObject();
			sb.append(mealObject.get("name"));
			sb.append(" x ");
			sb.append(mealObject.get("qty"));
			sb.append(System.getProperty("line.separator"));
		}
		sb.append("Total amount: $");
		sb.append(jsonOrder.get("totalAmount"));
		sb.append(System.getProperty("line.separator"));
		sb.append("Deliver to: ");
		sb.append(jsonOrder.get("address"));
		
		return sb.toString();
	}

}