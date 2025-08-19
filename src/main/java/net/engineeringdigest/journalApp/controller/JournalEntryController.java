//package net.engineeringdigest.journalApp.controller;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import net.engineeringdigest.journalApp.journalEntry.JournalEntry;
//
//@RestController
//@RequestMapping("/_journal")
//public class JournalEntryController {
//	HashMap<Long, JournalEntry> map = new HashMap<Long, JournalEntry>();
//
//	@GetMapping
//	public List<JournalEntry> getAll() {
//		return new ArrayList<JournalEntry>(map.values());
//	}
//
//	@PostMapping
//	public boolean createEntry(@RequestBody JournalEntry myEntry) {
//		map.put(myEntry.getId(), myEntry);
//		return true;
//	}
//
//	@GetMapping("/id/{myId}")
//	public JournalEntry getById(@PathVariable Long myId) {
//		return map.get(myId);
//	}
//
//	@PutMapping()
//	public JournalEntry updateById(@RequestBody JournalEntry myEntry) {
//		map.put(myEntry.getId(), myEntry);
//		return myEntry;
//	}
//
//	@DeleteMapping("/id/{myId}")
//	public boolean deletById(@PathVariable Long myId) {
//		map.remove(myId);
//		return true;
//	}
//
//}
