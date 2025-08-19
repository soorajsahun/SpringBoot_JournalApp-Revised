package net.engineeringdigest.journalApp.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.ConfigJournalApp;
import net.engineeringdigest.journalApp.repository.ConfigJournalAppRepository;

@Component
@Slf4j
public class AppCache {

	public static enum keys {// keys in config_journal_app collection
		weatherApi
	}

	@Autowired
	ConfigJournalAppRepository configJournalAppRepository;

	public Map<String, String> appCache;

	@PostConstruct
	public void init() {
		appCache = new HashMap<String, String>();
		List<ConfigJournalApp> all = configJournalAppRepository.findAll();
		log.info("all:" + all.toString());
		for (ConfigJournalApp configJournalApp : all) {
			appCache.put(configJournalApp.getKey(), configJournalApp.getValue());
		}
	}
}
