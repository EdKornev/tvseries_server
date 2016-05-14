package com.ek.serialsserver.configuration;

import com.mongodb.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Arrays;

/**
 * Created by Eduard on 04.05.2016.
 */
@Configuration
@EnableMongoRepositories
public class MongoConfiguration extends AbstractMongoConfiguration {
    private static final String DATABASE_NAME = "tvshow";
    private static final String DATABASE_HOST = "127.4.58.130";
//    private static final String DATABASE_HOST = "127.0.0.1";
//    private static final String DATABASE_NAME = "serials_server";

    @Override
    protected String getDatabaseName() {
        return DATABASE_NAME;
    }

    @Override
    public Mongo mongo() throws Exception {
        ServerAddress serverAddress = new ServerAddress(DATABASE_HOST, 27017);
        MongoCredential mongoCredential = MongoCredential.createMongoCRCredential("admin", getDatabaseName(), "NTvcU2jj_KdB"/*"Fender1990"*/.toCharArray());
        return new MongoClient(serverAddress, Arrays.asList(mongoCredential));
    }

    @Override
    protected String getMappingBasePackage() {
        return "com.ek.serialsserver";
    }

    @Override
    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        MongoTemplate mongoTemplate = new MongoTemplate(mongo(), getDatabaseName());
        return mongoTemplate;
    }
}
