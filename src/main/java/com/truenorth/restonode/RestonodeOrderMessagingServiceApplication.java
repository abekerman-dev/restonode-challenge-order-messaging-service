package com.truenorth.restonode;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.truenorth.restonode.service.messaging.CustomerNotificationReceiver;
import com.truenorth.restonode.service.messaging.RestaurantOrderReceiver;

@SpringBootApplication
public class RestonodeOrderMessagingServiceApplication {

	@Value("${spring.rabbitmq.queue.notification}")
	private String notificationQueue;

	@Value("${spring.rabbitmq.queue.order}")
	private String orderQueue;

	@Value("${spring.rabbitmq.exchange.name}")
	private String exchangeName;

	@Value("${spring.rabbitmq.routingKey.notification}")
	private String notificationRoutingKey;

	@Value("${spring.rabbitmq.routingKey.order}")
	private String orderRoutingKey;

	@Bean
	Queue notificationQueue() {
		return new Queue(notificationQueue, false);
	}

	@Bean
	Queue orderQueue() {
		return new Queue(orderQueue, false);
	}

	@Bean
	TopicExchange exchange() {
		return new TopicExchange(exchangeName);
	}

	@Bean
	Binding bindNotification(Queue notificationQueue, TopicExchange exchange) {
		return BindingBuilder.bind(notificationQueue).to(exchange).with(notificationRoutingKey);
	}

	@Bean
	Binding bindOrder(Queue orderQueue, TopicExchange exchange) {
		return BindingBuilder.bind(orderQueue).to(exchange).with(orderRoutingKey);
	}

	@Bean
	SimpleMessageListenerContainer notificationContainer(ConnectionFactory connectionFactory,
			MessageListenerAdapter notificationListenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(notificationQueue);
		container.setMessageListener(notificationListenerAdapter);

		return container;
	}

	@Bean
	SimpleMessageListenerContainer orderContainer(ConnectionFactory connectionFactory,
			MessageListenerAdapter orderListenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(orderQueue);
		container.setMessageListener(orderListenerAdapter);

		return container;
	}

	@Bean
	MessageListenerAdapter notificationListenerAdapter(CustomerNotificationReceiver receiver) {
		return new MessageListenerAdapter(receiver, "receive");
	}

	@Bean
	MessageListenerAdapter orderListenerAdapter(RestaurantOrderReceiver receiver) {
		return new MessageListenerAdapter(receiver, "receive");
	}

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(RestonodeOrderMessagingServiceApplication.class, args);
	}

}
