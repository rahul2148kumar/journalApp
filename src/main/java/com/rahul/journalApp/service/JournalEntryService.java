package com.rahul.journalApp.service;

import com.rahul.journalApp.entity.JournalEntity;
import com.rahul.journalApp.repository.JournalEntityRepository;
import org.bson.types.ObjectId;
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

    @Autowired
    private JournalEntityRepository journalEntityRepository;

    public JournalEntity saveJournalEntry(JournalEntity journalEntity){
        if(journalEntity.getDate() ==null){
            LocalDateTime now = LocalDateTime.now();
            Date localDate = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
            journalEntity.setDate(localDate);
        }
        JournalEntity savedJournal = journalEntityRepository.save(journalEntity);
        return savedJournal;
    }

    public List<JournalEntity> getAllJournal(){
        List<JournalEntity> allJournals= journalEntityRepository.findAll();
        return allJournals;
    }

    public Optional<JournalEntity> findJournalById(ObjectId id) {
        return journalEntityRepository.findById(id);
    }

    public void deleteJournalById(ObjectId id) {
        journalEntityRepository.deleteById(id);
    }
}


// controller --> service --> repository