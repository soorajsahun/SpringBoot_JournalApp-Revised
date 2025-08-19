package net.engineeringdigest.journalApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.engineeringdigest.journalApp.cache.AppCache;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;

@RestController
@RequestMapping("/admin")
@Tag(name = "Admin APIs")
public class AdminController {

	@Autowired
	AppCache appCache;

	@Autowired
	UserService userService;

	@Autowired
	JournalEntryService journalEntryService;

	@GetMapping("/all-users")
    @Operation(summary = "Get users list")
	public ResponseEntity<?> getAllUsers() {
		List<User> all = userService.getAll();
		if (all != null && !all.isEmpty()) {
			return new ResponseEntity<>(all, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/create-admin-user")
    @Operation(summary = "Create admin user")
	public void saveUser(@RequestBody User user) {
		userService.saveAdminEntry(user);
	}

	@GetMapping("/clear-app-cache")
    @Operation(summary = "Caching weather api url in app cache map to avoid downtime in case of url change in database")
	public void clearAppCache() {
		appCache.init();
	}

}
