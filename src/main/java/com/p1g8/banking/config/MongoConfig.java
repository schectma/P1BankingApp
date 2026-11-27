package com.p1g8.banking.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import java.util.Properties;

public class MongoConfig {

    private final MongoClient mongoClient;
    private final MongoDatabase database;

    public MongoConfig(Properties properties) {

        mongoClient = MongoClients.create(
                properties.getProperty("mongo.connection.uri"));

        database = mongoClient.getDatabase(
                properties.getProperty("mongo.database.name"));
    }

    public MongoDatabase getDatabase() {
        return database;
    }
}