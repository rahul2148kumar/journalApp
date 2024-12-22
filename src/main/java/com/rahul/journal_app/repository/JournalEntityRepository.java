package com.rahul.journal_app.repository;

import com.rahul.journal_app.entity.JournalEntries;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalEntityRepository extends MongoRepository<JournalEntries, ObjectId> {
}
