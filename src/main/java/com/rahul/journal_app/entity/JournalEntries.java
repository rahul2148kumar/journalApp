package com.rahul.journal_app.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@NoArgsConstructor
@Document(collection = "journal_entries")
public class JournalEntries {

    @Id
    private ObjectId id;
    private String title;
    private String content;
    @CreatedDate
    private Date date;

}
