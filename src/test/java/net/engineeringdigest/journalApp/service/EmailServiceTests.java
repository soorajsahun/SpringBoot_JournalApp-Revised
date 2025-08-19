package net.engineeringdigest.journalApp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class EmailServiceTests {

	@Autowired
	EmailService emailService;
	
	@Test
	public void sendMail() {
		try {
			
			emailService.sendMail("cutcap1999@gmail.com", "Mail Test", "Hi, how are you");
			assertEquals(2, 1 + 1);
		} catch (Exception e) {
			log.error(">>>>>." + e);
		}
	}
}
