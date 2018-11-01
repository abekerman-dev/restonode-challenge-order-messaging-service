package com.truenorth.restonode.component;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.truenorth.restonode.service.email.SendGridService;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SendGridMailSendTestSpringBoot {

	@Autowired
    private SendGridService emailService;
    
    @Test
    public void testSend() throws IOException {
    	log.debug("About to send email...");
    	emailService.send("abekerman@gmail.com", "some email body");
    	log.debug("email sent!");
    }
}
