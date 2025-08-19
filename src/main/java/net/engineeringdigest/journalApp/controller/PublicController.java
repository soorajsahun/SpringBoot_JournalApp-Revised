package net.engineeringdigest.journalApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.dto.UserDTO;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepositoryCriteria;
import net.engineeringdigest.journalApp.service.EmailService;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserDetailsServiceImpl;
import net.engineeringdigest.journalApp.service.UserService;
import net.engineeringdigest.journalApp.utils.JwtUtil;

@Slf4j
@RestController
@RequestMapping("/public")
@Tag(name = "Public APIs")
public class PublicController {

	@Autowired
	UserService userService;

	@Autowired
	EmailService emailService;

	@Autowired
	UserRepositoryCriteria userRepositoryCriteria;

	@Autowired
	JournalEntryService journalEntryService;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UserDetailsServiceImpl userDetailsServiceImpl;
	
	 @Autowired
	 JwtUtil jwtUtil;
	
	@GetMapping("/healthCheck")
    @Operation(summary = "Health check of application status")
	public String healthCheck() {
		return "OK";
	}

	@PostMapping("/sign-up")
    @Operation(summary = "Create new user")
	public void saveUser(@RequestBody UserDTO user) {
		try {
			User newUser = new User();
			newUser.setEmail(user.getEmail());
			newUser.setUserName(user.getUserName());
			newUser.setPassword(user.getPassword());
			newUser.setSentimentAnalysis(user.isSentimentAnalysis());
			userService.saveNewEntry(newUser);
		} catch (Exception e) {
			log.error("Exception occ>>>" + e);
		}
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody UserDTO user) {
		try {
			User newUser = new User();
			newUser.setEmail(user.getEmail());
			newUser.setUserName(user.getUserName());
			newUser.setPassword(user.getPassword());
			newUser.setSentimentAnalysis(user.isSentimentAnalysis());

			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(newUser.getUserName(), newUser.getPassword()));
			UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(newUser.getUserName());
			String jwt = jwtUtil.generateToken(userDetails.getUsername());
			return new ResponseEntity<>(jwt, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Exception occurred while createAuthenticationToken ", e);
			return new ResponseEntity<>("Incorrect username or password", HttpStatus.BAD_REQUEST);
		}
	}
	
}
