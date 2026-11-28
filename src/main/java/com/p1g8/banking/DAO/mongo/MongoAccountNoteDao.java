package com.p1g8.banking.dao.mongo;

import java.util.List;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.p1g8.banking.dao.AccountNoteDao;
import com.p1g8.banking.model.AccountNote;
import com.p1g8.banking.model.NoteFilter;

import java.util.ArrayList;
import java.util.Date;
import org.bson.Document;
import org.bson.conversions.Bson;

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
    public AccountNote create(AccountNote note) {
        collection.insertOne(toDocument(note));
        return note;
    }
    @Override
    public List<AccountNote> findByAccountId(long accountId) {

        List<AccountNote> notes =
            new ArrayList<>();

        FindIterable<Document> documents =
            collection.find(Filters.eq("accountId",accountId));

        for (Document document : documents) {
            notes.add(
                fromDocument(document));
        }

        return notes;
    }

    @Override
    public List<AccountNote> findByFilter(NoteFilter filter) {

        List<Bson> criteria = new ArrayList<>();

        if (filter.getAccountId() != null) {
            criteria.add(
                Filters.eq(
                    "accountId",
                    filter.getAccountId()));
        }

        if (filter.getEmployeeId() != null) {
            criteria.add(
                Filters.eq(
                    "employeeId",
                    filter.getEmployeeId()));
        }

        if (!filter.getTags().isEmpty()) {

            if (filter.isMatchAll()) {
                criteria.add(
                    Filters.all(
                        "tags",
                        filter.getTags()));
            } else {
                criteria.add(
                    Filters.in(
                        "tags",
                        filter.getTags()));
            }
        }

        if (filter.getFrom() != null) {
            criteria.add(
                Filters.gte(
                    "createdAt",
                    Date.from(
                        filter.getFrom())));
        }

        if (filter.getToExclusive() != null) {
            criteria.add(
                Filters.lt(
                    "createdAt",
                    Date.from(
                        filter.getToExclusive())));
        }

        Bson query =
            criteria.isEmpty()
                ? new Document()
                : Filters.and(criteria);

        List<AccountNote> notes =
                new ArrayList<>();

        FindIterable<Document> documents =
                collection.find(query);

        for (Document document : documents) {
            notes.add(
                fromDocument(document));
        }

        return notes;
    }
}