package net.engineeringdigest.journalApp.scheduler;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.cache.AppCache;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.enums.Sentiment;
import net.engineeringdigest.journalApp.repository.UserRepositoryCriteria;
import net.engineeringdigest.journalApp.service.EmailService;

@Slf4j
@Component
public class UserScheduler {

	@Autowired
	UserRepositoryCriteria userRepositoryCriteria;

	@Autowired
	EmailService emailService;

	@Autowired
	AppCache appCache;

	@Scheduled(cron = "0 0 9 * * SUN")
	public void fetchUserAndSendSentimentAnalysisMail() {
		List<User> users = userRepositoryCriteria.getSentimentAnalysisUser();
		for (User user : users) {
			log.info("UserName:: {}", user.getUserName());
			List<JournalEntry> journalEntries = user.getJournalEntries();
			List<Sentiment> sentiments = journalEntries.stream()
					.filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS)))
					.map(x -> x.getSentiment()).collect(Collectors.toList());
			Map<Sentiment, Integer> sentimentCount = new HashMap<>();
			for (Sentiment sentiment : sentiments) {
				if (sentiment != null) {
					sentimentCount.put(sentiment, sentimentCount.getOrDefault(sentiment, 0) + 1);
				}
			}

			Sentiment mostFrequestSentiment = null;
			int maxCount = 0;
			for (Map.Entry<Sentiment, Integer> entry : sentimentCount.entrySet()) {
				if (entry.getValue() > maxCount) {
					maxCount = entry.getValue();
					mostFrequestSentiment = entry.getKey();
				}
			}

			log.info("SENTIMENT>>>>" + mostFrequestSentiment.toString() + " Email()" + user.getEmail());
			if (mostFrequestSentiment != null) {
				emailService.sendMail(user.getEmail(), "Sentiment For Last 7 Days", mostFrequestSentiment.toString());
			}

		}
	}

	@Scheduled(cron = "0 0 9 * * SUN")
	public void reloadAppCache() {
		appCache.init();
	}
}
