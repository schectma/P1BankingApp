package com.p1g8.banking.dao.mongo;

import java.util.List;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.p1g8.banking.dao.AccountNoteDao;
import com.p1g8.banking.model.AccountNote;
import com.p1g8.banking.model.NoteFilter;
import java.util.Date;
import org.bson.Document;

public class MongoAccountNoteDao implements AccountNoteDao {

    private final MongoCollection<Document> collection;

    public MongoAccountNoteDao(MongoDatabase database) {
        this.collection =
            database.getCollection("account_notes");
    }

    Document toDocument(AccountNote note) {

    return new Document()
        .append("id", note.getId())
        .append("accountId", note.getAccountId())
        .append("employeeId", note.getEmployeeId())
        .append("text", note.getText())
        .append("tags", note.getTags())
        .append("relatedTransactionId", note.getRelatedTransactionId())
        .append("createdAt", Date.from(note.getCreatedAt()));
    }

    AccountNote fromDocument(Document document) {

        Date createdAt = document.getDate("createdAt");

        return new AccountNote(
            document.getLong("id"),
            document.getLong("accountId"),
            document.getLong("employeeId"),
            document.getString("text"),
            document.getList("tags", String.class),
            document.getLong("relatedTransactionId"),
            createdAt.toInstant());
    }

    @Override 
    public AccountNote create(AccountNote note){
        throw new UnsupportedOperationException();
    }
    @Override
    public List<AccountNote> findByAccountId(long accountId){
        throw new UnsupportedOperationException();
    }
    @Override 
    public List<AccountNote> findByFilter(NoteFilter filter){
        throw new UnsupportedOperationException();
    }
}