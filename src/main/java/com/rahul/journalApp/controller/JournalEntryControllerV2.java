package com.rahul.journalApp.controller;

import com.rahul.journalApp.entity.JournalEntity;
import com.rahul.journalApp.service.JournalEntryService;
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

    @GetMapping
    public ResponseEntity<List<JournalEntity>> getAllJournals(){
        logger.info("Fetching all journals");
        List<JournalEntity> allJournal= journalEntryService.getAllJournal();
        if(allJournal !=null && !allJournal.isEmpty()){
            logger.info("> No of journals: {}", allJournal.size());
            return new ResponseEntity<>(allJournal, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<JournalEntity> createJournal(@RequestBody JournalEntity myJournal){
        try{
            JournalEntity savedJournalEntry=journalEntryService.saveJournalEntry(myJournal);
            return new ResponseEntity<>(savedJournalEntry, HttpStatus.CREATED);
        }catch (Exception e){
            logger.info("Exception occur while creating a journal entry, message: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/id/{id}")
    public ResponseEntity<JournalEntity> getJournalById(@PathVariable("id") ObjectId id){
        logger.info("Fetching journal with id: {}", id);
        Optional<JournalEntity> optionalJournalEntity = journalEntryService.findJournalById(id);
        if(optionalJournalEntity.isPresent()){
            return new ResponseEntity<>(optionalJournalEntity.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteJournal(@PathVariable("id") ObjectId id){
        journalEntryService.deleteJournalById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<JournalEntity> updateJournal(@PathVariable("id") ObjectId id, @RequestBody JournalEntity newJournal){
        Optional<JournalEntity> optionalJournalEntity = journalEntryService.findJournalById(id);
        JournalEntity oldJournal = optionalJournalEntity.orElse(null);
        if(oldJournal!=null){
            oldJournal.setTitle(newJournal.getTitle()!=null && !newJournal.getTitle().equals("")? newJournal.getTitle() : oldJournal.getTitle());
            oldJournal.setContent(newJournal.getContent()!=null && !newJournal.getContent().equals("")? newJournal.getContent() : oldJournal.getContent());
            journalEntryService.saveJournalEntry(oldJournal);
            return new ResponseEntity<>(oldJournal, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
