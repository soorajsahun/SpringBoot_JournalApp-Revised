package net.engineeringdigest.journalApp.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import net.engineeringdigest.journalApp.entity.User;

@Component
public class UserRepositoryCriteria {// for complex queries->Use Criteria Api

	@Autowired
	MongoTemplate mongoTemplate;

	public List<User> getSentimentAnalysisUser() {
		Query query = new Query();

//		query.addCriteria(Criteria.where("email").exists(true));
//		query.addCriteria(Criteria.where("sentimentAnalysis").is(true));

		// OR

		Criteria criteria = new Criteria();
		query.addCriteria(criteria.andOperator(
				Criteria.where("email").regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"),
				Criteria.where("sentimentAnalysis").is(true)));
		List<User> user = mongoTemplate.find(query, User.class);
		return user;
	}
}
