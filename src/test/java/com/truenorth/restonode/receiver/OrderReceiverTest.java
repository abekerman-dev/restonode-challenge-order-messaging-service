package com.truenorth.restonode.receiver;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.truenorth.restonode.service.messaging.OrderReceiver;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderReceiverTest {

	@Autowired
	private OrderReceiver orderReceiver;
	
	private static String jsonMessage;

	@BeforeClass
	public static void setup() {
		jsonMessage = "{\"toEmail\":\"abekerman@gmail.com\",\"order\":{\"meals\":[{\"name\":\"Noodles\",\"qty\":1},{\"name\":\"Cheeseburger\",\"qty\":2}],\"totalAmount\":123.45,\"address\":\"Fake St. 123\",\"destination\":{\"lat\":-34.598284,\"lng\":-58.4175797}}}";
	}

	@Test
	public void test() {
		orderReceiver.receive(jsonMessage);
	}

}
