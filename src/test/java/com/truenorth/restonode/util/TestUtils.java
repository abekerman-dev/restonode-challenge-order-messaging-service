package com.truenorth.restonode.util;

public class TestUtils {

	public static String createJsonNotification() {
		return "{\"inSeconds\":1800,\"humanReadable\":\"30 minutes\"}";
	}

	public static String createJsonOrder() {
		return "{\"restaurant\":{\"id\":null,\"name\":null,\"location\":null,\"email\":\"abekerman@gmail.com\",\"ratings\":[]},\"meals\":[{\"name\":\"Noodles\",\"qty\":1},{\"name\":\"Cheeseburger\",\"qty\":2}],\"totalAmount\":123.45,\"address\":\"Fake St. 123\",\"destination\":{\"lat\":-34.598284,\"lng\":-58.4175797}}";
	}

}
