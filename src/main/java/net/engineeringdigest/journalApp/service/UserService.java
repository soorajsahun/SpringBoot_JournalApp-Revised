package net.engineeringdigest.journalApp.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;

@Component
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public void saveEntry(User user) {
		userRepository.save(user);
	}
	
	public void saveNewEntry(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRoles(Arrays.asList("USER"));
		userRepository.save(user);
	}
	
	public void saveAdminEntry(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRoles(Arrays.asList("USER","ADMIN"));
		userRepository.save(user);
	}

	public List<User> getAll() {
		return userRepository.findAll();
	}

	public Optional<User> getEntryById(ObjectId myId) {
		return userRepository.findById(myId);
	}

	public void deleteById(ObjectId myId) {
		userRepository.deleteById(myId);
	}
	
	public User findByUserName(String userName) {
		return userRepository.findByUserName(userName);
	}

}
