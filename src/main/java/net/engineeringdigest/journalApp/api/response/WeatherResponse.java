package net.engineeringdigest.journalApp.api.response;

import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class WeatherResponse {
	public Current current;

	@Getter
	@Setter
	public class Current {
		public int temperature;

		@JsonProperty("weather_descriptions")
		public List<String> weatherDescriptions;
		public int feelslike;
	}

}
