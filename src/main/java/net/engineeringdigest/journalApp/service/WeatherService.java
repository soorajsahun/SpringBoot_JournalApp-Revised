package net.engineeringdigest.journalApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.Constants.Placeholders;
import net.engineeringdigest.journalApp.api.response.WeatherResponse;
import net.engineeringdigest.journalApp.cache.AppCache;

@Service
@Slf4j
public class WeatherService {

	@Autowired
	AppCache appCache;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	RedisService redisService;

	@Value("${weather.api.key}")
	public String apiKey;

//	public static final String API = "http://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

	public WeatherResponse getWeather(String city) {
		//As in office laptop redis connection not happening (response=null) and also comment redisService.set
		WeatherResponse response = null;
//		WeatherResponse response = redisService.get("weather_of_" + city, WeatherResponse.class);
		if (response != null) {
			return response;
		} else {
			String finalApi = appCache.appCache.get(AppCache.keys.weatherApi.toString())
					.replace(Placeholders.API_KEY, apiKey).replace(Placeholders.CITY, city);
			
			log.info("weatherApi>>>" + finalApi);
			ResponseEntity<WeatherResponse> responseEntity = restTemplate.exchange(finalApi, HttpMethod.GET, null,
					WeatherResponse.class);
			WeatherResponse body = responseEntity.getBody();
			if (body != null) {
//				redisService.set("weather_of_" + city, body, 300l);
			}
			return body;
		}
	}
	
	// just for knowledge
	public WeatherResponse postApiCallWeather(String city) {
		String finalApi = appCache.appCache.get("weatherApi").replace("API_KEY", apiKey).replace("CITY", city);

		HttpHeaders headers = new HttpHeaders();
		headers.set("key", "value");
		String requestJson = "{\r\n" + "    \"userName\":\"Suraj\",\r\n" + "    \"password\":\"123\"\r\n" + "}";

		HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);

		ResponseEntity<WeatherResponse> responseEntity = restTemplate.exchange(finalApi, HttpMethod.POST, entity,
				WeatherResponse.class);
		WeatherResponse body = responseEntity.getBody();
		return body;
	}

}
