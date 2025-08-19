package net.engineeringdigest.journalApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Builder
public class EmailService {

	@Autowired
	JavaMailSender javaMailSender;

	public void sendMail(String to, String subject, String body) {
		try {

			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setTo(to);
			mailMessage.setSubject(subject);
			mailMessage.setText(body);

			javaMailSender.send(mailMessage);
		} catch (Exception e) {
			log.error("Exception occurred>>>" + e);
		}
	}
}
