package com.rahul.journal_app.service;

import com.rahul.journal_app.api.response.WeatherResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Component
@Service
@Slf4j
public class WeatherService {

    private static final String apiKey="ef0096c229c46ce925ad9b29005d0044";
    private static final String baseUrl="http://api.weatherstack.com/current?access_key=API_KEY&query=CITI";
    @Autowired
    private RestTemplate restTemplate;


    public WeatherResponse getWeather(String citi) {
        log.info("Fetching weather data for city: {}", citi);
        String url = baseUrl.replace("CITI", citi).replace("API_KEY", apiKey);
        log.debug("Constructed URL: {}", url);

        try {
            ResponseEntity<WeatherResponse> response = restTemplate.exchange(url, HttpMethod.GET, null, WeatherResponse.class);
            log.info("Successfully fetched weather data for city: {}", citi);
            return response.getBody();
        } catch (Exception e) {
            log.error("Error occurred while fetching weather data for city: {}. Error: {}", citi, e.getMessage(), e);
        }

        log.warn("Returning null for city: {} as no weather data could be fetched.", citi);
        return null;
    }
}