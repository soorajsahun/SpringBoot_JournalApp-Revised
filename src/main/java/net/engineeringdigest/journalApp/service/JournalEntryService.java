package net.engineeringdigest.journalApp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;

@Component
@Slf4j
public class JournalEntryService {

	@Autowired
	JournalEntryRepository journalEntryRepository;

	@Autowired
	UserService userService;
	
//	public static final Logger logger = LoggerFactory.getLogger(JournalEntryService.class);

	@Transactional
	public void saveEntry(JournalEntry journalEntry, String userName) {
		try {
			User user = userService.findByUserName(userName);
			journalEntry.setDate(LocalDateTime.now());
			JournalEntry entry = journalEntryRepository.save(journalEntry);
			user.getJournalEntries().add(entry);
//			user.setUserName(null);
			userService.saveEntry(user);
		} catch (Exception e) {
			log.error("An exception occurred", e);
			e.printStackTrace();
			throw new RuntimeException("An exception occurred", e);
		}
	}

	public void saveEntry(JournalEntry journalEntry) {
		journalEntryRepository.save(journalEntry);
	}

	public List<JournalEntry> getAll() {
		return journalEntryRepository.findAll();
	}

	public Optional<JournalEntry> getEntryById(ObjectId myId) {
		return journalEntryRepository.findById(myId);
	}

	public void deleteById(ObjectId myId) {
		journalEntryRepository.deleteById(myId);
	}
	
//	public List<JournalEntry> findByUserName(String userName) {
//		User user = userService.findByUserName(userName);
//		user.getJournalEntries().stream().filter(x->x.getId().equals())
//		return null;
//	}

}
