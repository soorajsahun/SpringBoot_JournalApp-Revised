package net.engineeringdigest.journalApp.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepositoryCriteria;

@SpringBootTest
@Slf4j
public class UserRepositoryCriteriaTests {

	@Autowired
	UserRepositoryCriteria userRepositoryCriteria;

	@Test
	public void getSentimentAnalysisUser() {
		List<User> user = userRepositoryCriteria.getSentimentAnalysisUser();
		log.info("user>>>>>>>>>>>." + user.toString());
		assertNotNull(user);
	}
}
