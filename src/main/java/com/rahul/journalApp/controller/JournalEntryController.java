package com.rahul.journalApp.controller;

import com.rahul.journalApp.entity.JournalEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/journal")
public class JournalEntryController {

    private Map<Long, JournalEntity> journalEntityMap = new HashMap<>();

    @GetMapping
    public List<JournalEntity> getAllJournals(){
        return new ArrayList<>(journalEntityMap.values());
    }

    @PostMapping
    public String createJournal(@RequestBody JournalEntity myJournal){
        journalEntityMap.put(myJournal.getId(), myJournal);
        return "Journal created successfully";
    }

    @DeleteMapping("/id/{id}")
    public String deleteJournal(@PathVariable("id") Long id){
        journalEntityMap.remove(id);
        return "journal id "+id+" deleted successfully";
    }

    @PutMapping("/id/{id}")
    public String updateJournal(@PathVariable("id") Long id, @RequestBody JournalEntity journalEntity){
        journalEntityMap.put(id, journalEntity);
        return "journal id "+id+" updated successfully";
    }
}
