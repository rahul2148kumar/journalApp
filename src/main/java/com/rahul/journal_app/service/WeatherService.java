package com.rahul.journal_app.service;

import com.rahul.journal_app.api.response.WeatherResponse;
import com.rahul.journal_app.cache.AppCache;
import com.rahul.journal_app.constants.Placeholder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@Slf4j
public class WeatherService {

//    @Value("${weather.api.key}")
//    private String apiKey;
//    @Value("${weather.api.url}")
//    private String baseUrl;

    @Autowired
    private AppCache appCache;
    private final RestTemplate restTemplate;

    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public WeatherResponse getWeather(String citi) {
        log.info("Fetching weather data for city: {}", citi);
        String url=appCache.propertiesMap.get(Placeholder.WEATHER_API_URL);
        String apiKey=appCache.propertiesMap.get(Placeholder.WEATHER_API_KEY);

        String finalUrl = url.replace(Placeholder.CITI, citi).replace(Placeholder.WEATHER_API_KEY, apiKey);
        log.debug("Constructed URL: {}", finalUrl);

        try {
            ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalUrl, HttpMethod.GET, null, WeatherResponse.class);
            log.info("Successfully fetched weather data for city: {}", citi);
            return response.getBody();
        } catch (Exception e) {
            log.error("Error occurred while fetching weather data for city: {}. Error: {}", citi, e.getMessage(), e);
        }

        log.warn("Returning null for city: {} as no weather data could be fetched.", citi);
        return null;
    }
}
