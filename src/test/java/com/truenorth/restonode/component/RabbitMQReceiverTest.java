/*
 * Copyright 2012-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.truenorth.restonode.component;

import static com.truenorth.restonode.util.TestUtils.createJsonNotification;
import static com.truenorth.restonode.util.TestUtils.createJsonOrder;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.truenorth.restonode.service.messaging.RabbitMQReceiver;

@RunWith(SpringRunner.class)
@SpringBootTest
// FIXME transform into unit test (i.e. don't expect actual rabbitmq message receiving)
public class RabbitMQReceiverTest {

	@Autowired
	@Qualifier("notificationReceiver")
	private RabbitMQReceiver notificationReceiver;

	@Autowired
	@Qualifier("orderReceiver")
	private RabbitMQReceiver orderReceiver;
	
	@Test
	public void testNotificationReceiver() throws Exception {
		notificationReceiver.receive(createJsonNotification());
	}

	@Test
	public void testOrderReceiver() throws Exception {
		orderReceiver.receive(createJsonOrder());
	}

}
