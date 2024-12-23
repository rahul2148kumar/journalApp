package com.rahul.journal_app.cache;

import com.rahul.journal_app.entity.ConfigJournalAppEntity;
import com.rahul.journal_app.repository.ConfigJournalAppRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {

    public enum keys{
        WEATHER_API_KEY
    }

    public Map<String, String> propertiesMap;

    @Autowired
    private ConfigJournalAppRepository configJournalAppRepository;

    @PostConstruct
    public void init(){
        propertiesMap=new HashMap<>();
        List<ConfigJournalAppEntity> allConfigJournalAppEntity =configJournalAppRepository.findAll();
        for(ConfigJournalAppEntity entry: allConfigJournalAppEntity){
            propertiesMap.put(entry.getKey(), entry.getValue());
        }
    }
}
