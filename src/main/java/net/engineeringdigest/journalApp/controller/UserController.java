package net.engineeringdigest.journalApp.controller;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.api.response.WeatherResponse;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import net.engineeringdigest.journalApp.service.UserService;
import net.engineeringdigest.journalApp.service.WeatherService;

@RestController
@RequestMapping("/user")
@Slf4j
@Tag(name = "User APIs", description = "Read, Update & Delete User")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	UserRepository userRepository;

	@Autowired
	WeatherService weatherService;

	@PutMapping
    @Operation(summary = "Update user")
	public ResponseEntity<?> updateUser(@RequestBody User user) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		User user2 = userService.findByUserName(userName);
		user2.setUserName(user.getUserName());
		user2.setPassword(user.getPassword());
		userService.saveNewEntry(user2);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping
    @Operation(summary = "Delete user")
	public ResponseEntity<?> deleteUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		userRepository.deleteByUserName(authentication.getName());

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/get-weather/{city}")
    @Operation(summary = "Get weather info of a city")
	public ResponseEntity<?> greetings(@PathVariable String city) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		WeatherResponse weather;

		try {
			weather = weatherService.getWeather(city);
			if (weather != null) {
				Map<String, Object> response = new LinkedHashMap<>();
				response.put("message", "Hi " + authentication.getName() + " 👋");
				response.put("city", city);
				response.put("temperature", weather.getCurrent().getTemperature() + "°C");
				response.put("feelsLike", weather.getCurrent().getFeelslike() + "°C");
				response.put("description", weather.getCurrent().getWeatherDescriptions().get(0));
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
		} catch (Exception e) {
			log.error("Exception occurred", e);
			return new ResponseEntity<>(
					Collections.singletonMap("error", "Something went wrong while fetching weather."),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(Collections.singletonMap("message", "Weather data not found for city: " + city),
				HttpStatus.NOT_FOUND);
	}

}
