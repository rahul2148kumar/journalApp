package com.rahul.journal_app.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.rahul.journal_app.enums.Sentiment;
import com.rahul.journal_app.serializer.ObjectIdToStringSerializer;
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
    @JsonSerialize(using = ObjectIdToStringSerializer.class)
    private ObjectId id;
    private String title;
    private String content;
    @CreatedDate
    private Date date;
    private Sentiment sentiment;

}
