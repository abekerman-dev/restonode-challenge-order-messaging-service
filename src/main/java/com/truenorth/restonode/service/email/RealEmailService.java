package com.truenorth.restonode.service.email;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Personalization;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RealEmailService implements EmailService {

	@Value("${sendgrid.apiKey}")
	private String apiKey;

	@Value("${sendgrid.sendEndpoint}")
	private String sendEndpoint;

	@Value("${sendgrid.from.name}")
	private String fromName;

	@Value("${sendgrid.from.email}")
	private String fromEmail;

	@Value("${sendgrid.subject}")
	private String subject;

	@Value("${sendgrid.content.type}")
	private String contentType;

	@Value("${sendgrid.content.value}")
	private String contentValue;
	
	private SendGrid sendGrid;

	public void send(String toEmail, String body) throws IOException {
		sendGrid = new SendGrid(apiKey);
		Request request = new Request();
		request.setMethod(Method.POST);
		request.setEndpoint(sendEndpoint);
		request.setBody(getMailBody(toEmail, body).build());
		log.debug("Executing sendgrid API call with");
		log.debug("toEmail=" + toEmail);
		log.debug("body=" + body);
		Response response = sendGrid.api(request);
		log.debug("Status Code from call to sendgrid API -> " + response.getStatusCode());
	}

	private Mail getMailBody(String toEmail, String body) {
		Mail mail = new Mail();
	    Email from = new Email();
	    from.setName(fromName);
	    from.setEmail(fromEmail);
	    mail.setFrom(from);

	    Personalization personalization = new Personalization();
	    personalization.addTo(new Email(toEmail));
	    personalization.setSubject(subject);
	    mail.addPersonalization(personalization);
		mail.addContent(new Content(contentType, body));
	    
	    return mail;
	}

}
