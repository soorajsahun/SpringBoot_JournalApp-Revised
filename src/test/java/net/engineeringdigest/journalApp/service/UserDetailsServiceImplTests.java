package net.engineeringdigest.journalApp.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import net.engineeringdigest.journalApp.repository.UserRepository;

//@SpringBootTest
public class UserDetailsServiceImplTests {

//	@Autowired
	@InjectMocks
	UserDetailsServiceImpl userDetailsService;

//	@MockBean
	@Mock
	UserRepository userRepository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void loadUserByUsernameTests() {
		/*
		 * User mockUser = new User();
		 *  But only if:
		 * You have imported your custom User class, not Spring Security’s.
		 * There’s no conflict with org.springframework.security.core.userdetails.User.
		 * 
		 */
		net.engineeringdigest.journalApp.entity.User mockUser = new net.engineeringdigest.journalApp.entity.User();
		mockUser.setUserName("Ram");
		mockUser.setPassword("jkbsd");
		mockUser.setRoles(Arrays.asList("USER"));

		when(userRepository.findByUserName(ArgumentMatchers.anyString())).thenReturn(mockUser);

//		when(userRepository.findByUserName(ArgumentMatchers.anyString()))
//				.thenReturn((net.engineeringdigest.journalApp.entity.User) User.builder().username("Ram")

//						.password("jkbsd").roles(new ArrayList<String>().toArray(new String[0])).build());
		UserDetails user = userDetailsService.loadUserByUsername("Rnam");
		assertNotNull(user);
	}

}
