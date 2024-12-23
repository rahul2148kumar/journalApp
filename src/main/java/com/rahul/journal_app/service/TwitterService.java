package com.rahul.journal_app.service;

import com.rahul.journal_app.api.response.TwitterUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Component
@Service
@Slf4j
public class TwitterService {

    private static final String rapidapi="4b75012982mshb827a1b5a697400p122c5ajsn2";

    private static final String baseUrl="https://twitter-api45.p.rapidapi.com/tweet.php?id=USER_ID";
    @Autowired
    private RestTemplate restTemplate;

    public TwitterUser getTweet(String id) {
        String url=baseUrl.replace("USER_ID", id);
        try {
            // Set the headers
            HttpHeaders headers = new HttpHeaders();
            headers.set("x-rapidapi-host", "twitter-api45.p.rapidapi.com");
            headers.set("x-rapidapi-key", "4b75012982mshb827a1b5a697400p122c5ajsn25206f41ec7f");

            // Create the HttpEntity with headers
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<TwitterUser> response = restTemplate.exchange(url, HttpMethod.GET, entity, TwitterUser.class);
            if(response.getStatusCode().equals(200)){
                log.info("Successfully fetched");
            }
            log.info("Successfully fetched user data for user id: {}", id);
            return response.getBody();
        }catch (Exception e){
            log.error("Error occurred while fetching user data Error: {}", e.getMessage(), e);
        }
        return null;
    }
}
