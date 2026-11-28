package com.p1g8.banking.dao.mongo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.InputStream;
import java.time.Instant;

import org.junit.jupiter.api.Test;

import com.p1g8.banking.config.MongoConfig;
import com.p1g8.banking.model.AccountNote;
import com.p1g8.banking.model.NoteFilter;

import java.util.List;
import java.util.Properties;

public class MongoAccountNoteDaoTest {
    @Test
    void createPersistsNote() throws Exception {

        Properties properties = new Properties();

        try (InputStream in =
                getClass()
                    .getClassLoader()
                    .getResourceAsStream(
                            "application.properties")) {

            assertNotNull(in);

            properties.load(in);
        }

        MongoConfig config =
                new MongoConfig(properties);

        MongoAccountNoteDao dao =
                new MongoAccountNoteDao(
                        config.getDatabase());

        AccountNote note =
                new AccountNote(
                        999999L,
                        10L,
                        20L,
                        "DAO create test",
                        List.of("TEST"),
                        null,
                        Instant.now());

        AccountNote persisted =
                dao.create(note);

        assertEquals(
                note.getId(),
                persisted.getId());
    }

    @Test
    void findByAccountIdReturnsMatchingNotes() throws Exception {

        Properties properties = new Properties();

        try (InputStream in =
            getClass()
            .getClassLoader()
            .getResourceAsStream(
                    "application.properties")) {

            assertNotNull(in);

            properties.load(in);
        }

        MongoConfig config = new MongoConfig(properties);

        MongoAccountNoteDao dao = new MongoAccountNoteDao(config.getDatabase());

        long uniqueId = System.currentTimeMillis();

        long accountId = 424242L;

        AccountNote note =
            new AccountNote(
                uniqueId,
                accountId,
                10L,
                "findByAccountId test",
                List.of("TEST"),
                null,
                Instant.now());

        dao.create(note);

        List<AccountNote> notes =
            dao.findByAccountId(accountId);

        assertFalse(notes.isEmpty());

        boolean found =
            notes.stream()
                .anyMatch(n ->
                    n.getId() == uniqueId);

        assertTrue(found);
    }

    private MongoAccountNoteDao createDao() throws Exception {

        Properties properties = new Properties();

        try (InputStream in =
            getClass()
                .getClassLoader()
                .getResourceAsStream(
                    "application.properties")) {

            assertNotNull(in);

            properties.load(in);
        }

        MongoConfig config = new MongoConfig(properties);

        return new MongoAccountNoteDao(
            config.getDatabase());
    }

    @Test
    void findByFilterReturnsNotesForAccount() throws Exception {

        MongoAccountNoteDao dao = createDao();

        long id = System.currentTimeMillis();

        AccountNote note = new AccountNote(
            id,
            12345L,
            1L,
            "Account filter test",
            List.of("TEST"),
            null,
            Instant.now());

        dao.create(note);

        NoteFilter filter = new NoteFilter(
            12345L,
            null,
            List.of(),
            false,
            null,
            null);

        List<AccountNote> results = dao.findByFilter(filter);

        assertTrue(
            results.stream()
                .anyMatch(
                    n -> n.getId() == id));
    }
}
