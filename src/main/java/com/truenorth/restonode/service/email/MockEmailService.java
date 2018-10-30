package com.truenorth.restonode.service.email;

import java.io.IOException;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MockEmailService implements EmailService {

	@Override
	public void send(String toEmail, String body) throws IOException {
		log.debug("Mock sending email to " + toEmail);
		log.debug("with body:");
		log.debug(body);
	}

}
