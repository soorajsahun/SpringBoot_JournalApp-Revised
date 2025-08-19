package net.engineeringdigest.journalApp.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "config_journal_app")
@Getter
@Setter
public class ConfigJournalApp {

	String key;
	String value;

}
