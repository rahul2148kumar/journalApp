package com.rahul.journalApp.service;

import com.rahul.journalApp.controller.JournalEntryControllerV2;
import com.rahul.journalApp.entity.JournalEntries;
import com.rahul.journalApp.entity.User;
import com.rahul.journalApp.repository.JournalEntityRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Component
public class JournalEntryService {

    private static final Logger logger = LoggerFactory.getLogger(JournalEntryControllerV2.class);
    @Autowired
    private JournalEntityRepository journalEntityRepository;

    @Autowired
    private UserService userService;

    public JournalEntries saveJournalEntry(JournalEntries journalEntity){
        if(journalEntity.getDate() ==null){
            LocalDateTime now = LocalDateTime.now();
            Date localDate = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
            journalEntity.setDate(localDate);
        }
        JournalEntries savedJournal = journalEntityRepository.save(journalEntity);
        return savedJournal;
    }

    public List<JournalEntries> getAllJournal(){
        List<JournalEntries> allJournals= journalEntityRepository.findAll();
        return allJournals;
    }

    public Optional<JournalEntries> findJournalById(ObjectId id) {
        return journalEntityRepository.findById(id);
    }

    public void deleteJournalByIdOfUser(String userName, ObjectId id) {
        User savedUser = userService.findByUserName(userName);
        logger.info("> userName : {}, User Object :{}",userName, savedUser);
        // Delete the journal into journal_entries database
        JournalEntries journalEntries = findJournalById(id).get();
        journalEntityRepository.deleteById(id);

        // Delete the journal id into user's journal list
        List<JournalEntries> userJournalEntities=savedUser.getJournalEntities();
        userJournalEntities.removeIf(journal-> journal.equals(journalEntries));
        savedUser.setJournalEntities(userJournalEntities);

        User updatedUser=userService.saveUserEntry(savedUser);
        logger.info("> updated journal into user dataDB: {}", updatedUser);
    }

    public JournalEntries saveJournalEntryOfUser(JournalEntries myJournal, String userName) {
        User savedUser = userService.findByUserName(userName);
        logger.info("> userName : {}, User Object :{}",userName, savedUser);
        // save the journal into journal_entries database
        JournalEntries savedJournalEntry = saveJournalEntry(myJournal);
        logger.info("> updated journal into Journal dataDB: {}", savedJournalEntry);

        // save the journal id into user's journal list
        List<JournalEntries> userJournalEntities=savedUser.getJournalEntities();
        userJournalEntities.add(savedJournalEntry);
        User updatedUser=userService.saveUserEntry(savedUser);
        logger.info("> updated journal into user dataDB: {}", updatedUser);

        return savedJournalEntry;
    }
}


// controller --> service --> repository