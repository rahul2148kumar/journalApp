package com.rahul.journal_app.service;

import com.rahul.journal_app.model.SentimentalData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SentimentConsumerService {

    @Autowired
    private EmailService emailService;


    @KafkaListener(topics = "weekly-sentiments", groupId = "weekly-sentiment-group")
    public void consume(SentimentalData sentimentalData){
        log.info("Message consume from kafka server");
        sendEmail(sentimentalData);
    }

    private void sendEmail(SentimentalData sentimentalData) {
        log.info("Sending Email to user: {}", sentimentalData.getEmail());
        emailService.sendMail(sentimentalData.getEmail(), "Sentiment for previous week", sentimentalData.getSentiment());
    }
}
