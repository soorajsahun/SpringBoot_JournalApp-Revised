package net.engineeringdigest.journalApp.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import net.engineeringdigest.journalApp.entity.User;

public interface UserRepository extends MongoRepository<User, ObjectId>{//for simpe queries->Use Query Method DSL 

	public User findByUserName(String userName);  
	
	public void deleteByUserName(String userName);

}
