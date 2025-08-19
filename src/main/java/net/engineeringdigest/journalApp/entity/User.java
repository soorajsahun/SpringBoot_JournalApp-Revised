package net.engineeringdigest.journalApp.entity;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "users")
@Data //all required annotations
@NoArgsConstructor
//@Builder
public class User {

	@Id
	private ObjectId id;

	@Indexed(unique = true)//also need to configure in application properties file
	@NonNull
	private String userName;
	
	@NonNull
	private String password;
	
	private String email;
	
	private boolean sentimentAnalysis;
	
	@DBRef
	private List<JournalEntry> journalEntries = new ArrayList<JournalEntry>();
	
	private List<String> roles;
}
