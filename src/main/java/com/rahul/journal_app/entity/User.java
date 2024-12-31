package com.rahul.journal_app.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.rahul.journal_app.serializer.ObjectIdToStringSerializer;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = "users")
@AllArgsConstructor
public class User {

    @Id
    @JsonSerialize(using = ObjectIdToStringSerializer.class)
    private ObjectId id;

    @Indexed(unique = true)
    @NotBlank(message = "User cannot be blank")
    private String userName;
    @Indexed(unique = true)
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String email;
    private String city;
    private boolean sentimentAnalysis;
    @NotBlank(message = "Password cannot be blank")
    private String password;
    @DBRef
    private List<JournalEntries> journalEntities= new ArrayList<>();
    private List<String> roles;
    private boolean verified;
    @CreatedDate
    private LocalDateTime userCreatedDate;
    @LastModifiedDate
    private LocalDateTime UserUpdatedDate;


}
