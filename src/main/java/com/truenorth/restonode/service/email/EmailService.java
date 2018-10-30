package com.truenorth.restonode.service.email;

import java.io.IOException;

public interface EmailService {

	public void send(String toEmail, String body) throws IOException;
}
