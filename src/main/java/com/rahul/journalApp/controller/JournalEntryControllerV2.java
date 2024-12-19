package com.rahul.journalApp.controller;

import com.rahul.journalApp.entity.JournalEntries;
import com.rahul.journalApp.entity.User;
import com.rahul.journalApp.service.JournalEntryService;
import com.rahul.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(path = "/journal")
public class JournalEntryControllerV2 {

    private static final Logger logger = LoggerFactory.getLogger(JournalEntryControllerV2.class);

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping("/{userName}")
    public ResponseEntity<List<JournalEntries>> getAllJournalsEntriesOfUser(@PathVariable String userName){
        User user = userService.findByUserName(userName);
        logger.info("> user name: {}", user.getUserName());

        logger.info("Fetching all journals");
        List<JournalEntries> allJournal= user.getJournalEntities();
        if(allJournal !=null && !allJournal.isEmpty()){
            logger.info("> No of journals: {}", allJournal.size());
            return new ResponseEntity<>(allJournal, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{userName}")
    public ResponseEntity<JournalEntries> createJournal(@RequestBody JournalEntries myJournal, @PathVariable String userName){
        try{
            JournalEntries savedJournalEntry=journalEntryService.saveJournalEntryOfUser(myJournal, userName);
            return new ResponseEntity<>(savedJournalEntry, HttpStatus.CREATED);
        }catch (Exception e){
            logger.info("Exception occur while creating a journal entry, message: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/id/{id}")
    public ResponseEntity<JournalEntries> getJournalById(@PathVariable("id") ObjectId id){
        logger.info("Fetching journal with id: {}", id);
        Optional<JournalEntries> optionalJournalEntity = journalEntryService.findJournalById(id);
        if(optionalJournalEntity.isPresent()){
            return new ResponseEntity<>(optionalJournalEntity.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{userName}/{id}")
    public ResponseEntity<?> deleteJournal(@PathVariable("id") ObjectId id,
                                           @PathVariable("userName") String userName){
        journalEntryService.deleteJournalByIdOfUser(userName, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/id/{userName}/{id}")
    public ResponseEntity<JournalEntries> updateJournal(@PathVariable("id") ObjectId id,
                                                        @PathVariable("userName") String userName,
                                                        @RequestBody JournalEntries newJournal)
    {
        Optional<JournalEntries> optionalJournalEntity = journalEntryService.findJournalById(id);
        JournalEntries oldJournal = optionalJournalEntity.orElse(null);
        if(oldJournal!=null){
            oldJournal.setTitle(newJournal.getTitle()!=null && !newJournal.getTitle().equals("")? newJournal.getTitle() : oldJournal.getTitle());
            oldJournal.setContent(newJournal.getContent()!=null && !newJournal.getContent().equals("")? newJournal.getContent() : oldJournal.getContent());
            journalEntryService.saveJournalEntry(oldJournal);
            return new ResponseEntity<>(oldJournal, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
