package com.truenorth.restonode.receiver;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.truenorth.restonode.service.messaging.NotificationReceiver;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NotificationReceiverTest {

	@Autowired
	private NotificationReceiver notificationReceiver;

	private static String jsonMessage;

	@BeforeClass
	public static void setup() {
		jsonMessage = "{\"inSeconds\":1800,\"humanReadable\":\"30 minutes\"}";
	}

	@Test
	public void test() {
		notificationReceiver.receive(jsonMessage);
	}

}
