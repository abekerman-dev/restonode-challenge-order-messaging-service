package com.truenorth.restonode.receiver;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class EventConsumer {

	public void receiveMessage(String message) {
		log.info("Received <" + message + ">");
		// TODO send sms (write on log) for NOTIFICATIONs and send an email for CONFIRMATIONs 
	}

}