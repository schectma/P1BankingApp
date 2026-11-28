package com.p1g8.banking.dao.mongo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Disabled;

import java.io.InputStream;
import java.time.Instant;

import org.junit.jupiter.api.Test;

import com.p1g8.banking.config.MongoConfig;
import com.p1g8.banking.model.AccountNote;
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
}
