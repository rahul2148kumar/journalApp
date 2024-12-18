package com.rahul.journalApp.controller;

import com.rahul.journalApp.entity.JournalEntity;
import com.rahul.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(path = "/journal")
public class JournalEntryControllerV2 {

    private static final Logger logger = LoggerFactory.getLogger(JournalEntryControllerV2.class);

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public List<JournalEntity> getAllJournals(){
        logger.info("Fetching all journals");
        return journalEntryService.getAllJournal();
    }

    @PostMapping
    public JournalEntity createJournal(@RequestBody JournalEntity myJournal){
        return journalEntryService.saveJournalEntry(myJournal);
    }

    @GetMapping("/id/{id}")
    public JournalEntity getJournalById(@PathVariable("id") ObjectId id){
        logger.info("Fetching journal with id: {}", id);
        Optional<JournalEntity> optionalJournalEntity = journalEntryService.findJournalById(id);
        return optionalJournalEntity.orElse(null);
    }

    @DeleteMapping("/id/{id}")
    public String deleteJournal(@PathVariable("id") ObjectId id){
        journalEntryService.deleteJournalById(id);
        return "deleted";
    }

    @PutMapping("/id/{id}")
    public JournalEntity updateJournal(@PathVariable("id") ObjectId id, @RequestBody JournalEntity newJournal){
        Optional<JournalEntity> optionalJournalEntity = journalEntryService.findJournalById(id);
        JournalEntity oldJournal = optionalJournalEntity.orElse(null);
        if(oldJournal!=null){
            oldJournal.setTitle(newJournal.getTitle()!=null && !newJournal.getTitle().equals("")? newJournal.getTitle() : oldJournal.getTitle());
            oldJournal.setContent(newJournal.getContent()!=null && !newJournal.getContent().equals("")? newJournal.getContent() : oldJournal.getContent());
        }
        journalEntryService.saveJournalEntry(oldJournal);
        return oldJournal;
    }
}
