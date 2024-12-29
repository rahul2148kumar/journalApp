package com.rahul.journal_app.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.rahul.journal_app.entity.JournalEntries;
import com.rahul.journal_app.serializer.ObjectIdToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    @JsonSerialize(using = ObjectIdToStringSerializer.class)
    private ObjectId id;
    private String userName;
    private String email;
    private String city;
    private boolean sentimentAnalysis;
    private List<JournalEntries> journalEntities= new ArrayList<>();
    private List<String> roles;
    private LocalDateTime userCreatedDate;
    private LocalDateTime userUpdatedDate;
}
