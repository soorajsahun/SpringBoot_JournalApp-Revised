package net.engineeringdigest.journalApp.controller;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;

@RestController
@RequestMapping("/journal")
@Tag(name = "Journal APIs")
public class JournalEntryControllerUpdated {
	
	@Autowired
	JournalEntryService journalEntryService;

	@Autowired
	UserService userService;

	@GetMapping
    @Operation(summary = "Get all journal entries of a user")
	public ResponseEntity<?> getAll() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		User user = userService.findByUserName(authentication.getName());
		List<JournalEntry> all = user.getJournalEntries();

		if (all != null && !all.isEmpty()) {
			return new ResponseEntity<>(all, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping
    @Operation(summary = "Create journal entry")
	public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry) {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			journalEntryService.saveEntry(myEntry, authentication.getName());

			return new ResponseEntity<JournalEntry>(myEntry, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<JournalEntry>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/id/{myId}")
    @Operation(summary = "Get journal entey by id")
	public ResponseEntity<JournalEntry> getById(@PathVariable String myId) {
		ObjectId objectId = new ObjectId(myId);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findByUserName(authentication.getName());
		List<JournalEntry> journalEntries = user.getJournalEntries();
		boolean found = false;
		for (int i = 0; i < journalEntries.size(); i++) {
			if (journalEntries.get(i).getId().equals(objectId)) {
				found = true;
			}
		}

		if (found) {
			Optional<JournalEntry> entryById = journalEntryService.getEntryById(objectId);
			return new ResponseEntity<JournalEntry>(entryById.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<JournalEntry>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/id/{myId}")
    @Operation(summary = "Delete journal entry by id")
	public ResponseEntity<?> deletById(@PathVariable String myId) {
		ObjectId objectId = new ObjectId(myId);

		// ?=>wildcard pattern(any class)
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findByUserName(authentication.getName());
		boolean removeIf = user.getJournalEntries().removeIf(x -> x.getId().equals(objectId));
		if (removeIf) {
			journalEntryService.deleteById(objectId);
			userService.saveEntry(user);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/id/{myId}")
    @Operation(summary = "Update journal entry by id")
	public ResponseEntity<JournalEntry> updateById(@PathVariable String myId, @RequestBody JournalEntry newEntry) {
		ObjectId objectId = new ObjectId(myId);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		User user = userService.findByUserName(authentication.getName());
		List<JournalEntry> journalEntries = user.getJournalEntries();
		boolean found = false;
		for (int i = 0; i < journalEntries.size(); i++) {
			if (journalEntries.get(i).getId().equals(objectId)) {
				found = true;
			}
		}

		if (found) {
			Optional<JournalEntry> entryById = journalEntryService.getEntryById(objectId);
			JournalEntry old = entryById.get();

			old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle()
					: old.getTitle());

			old.setDescription(newEntry.getDescription() != null && !newEntry.getDescription().equals("")
					? newEntry.getDescription()
					: old.getDescription());

			journalEntryService.saveEntry(old);	

			return new ResponseEntity<JournalEntry>(old, HttpStatus.OK);

		} else {
			return new ResponseEntity<JournalEntry>(HttpStatus.NOT_FOUND);
		}

	}

}
