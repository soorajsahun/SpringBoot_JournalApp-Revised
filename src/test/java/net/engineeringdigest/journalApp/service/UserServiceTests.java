package net.engineeringdigest.journalApp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import net.engineeringdigest.journalApp.repository.UserRepository;

//@SpringBootTest
public class UserServiceTests {

	@Autowired
	UserRepository userRepository;
	
	@Disabled
	@Test
	public void testFindByUserName() {
		assertEquals(4, 2+2);
		assertNotNull(userRepository.findByUserName("Ram"));
	}
	
	@ParameterizedTest
	@CsvSource({
		"1,2,3",
		"2,2,3",
		""
	})
	public void test(int a,int b,int expected) {
		assertEquals(expected, a+b);
	}
	
}
