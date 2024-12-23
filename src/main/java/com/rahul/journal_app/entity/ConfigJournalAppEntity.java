package com.rahul.journal_app.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@NoArgsConstructor
@Document(collection = "config_journal_app")
public class ConfigJournalAppEntity {

    @Id
    private ObjectId id;
    private String key;
    private String value;
}
