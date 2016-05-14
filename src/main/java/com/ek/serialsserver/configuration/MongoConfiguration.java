package com.ek.serialsserver.configuration;

import com.mongodb.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Arrays;

/**
 * Created by Eduard on 04.05.2016.
 * Configure database
 */
@Configuration
@EnableMongoRepositories
@PropertySources({
        @PropertySource("resources/database.properties"),
        @PropertySource("resources/app.properties")
})
public class MongoConfiguration extends AbstractMongoConfiguration {

    @Autowired private Environment env;

    @Value("${app.debug}")
    private Boolean isDebug;

    @Override
    protected String getDatabaseName() {
        if (isDebug) {
            return env.getProperty("mongo.db");
        } else {
            return env.getProperty("mongo.prod.db");
        }
    }

    @Override
    public Mongo mongo() throws Exception {
        if (isDebug) {
            ServerAddress serverAddress = new ServerAddress(env.getProperty("mongo.host"), 27017);
            MongoCredential mongoCredential = MongoCredential.createMongoCRCredential(env.getProperty("mongo.username"), getDatabaseName(), env.getProperty("mongo.password").toCharArray());
            return new MongoClient(serverAddress, Arrays.asList(mongoCredential));
        } else {
            ServerAddress serverAddress = new ServerAddress(env.getProperty("mongo.prod.host"), 27017);
            MongoCredential mongoCredential = MongoCredential.createMongoCRCredential(env.getProperty("mongo.prod.username"), getDatabaseName(), env.getProperty("mongo.prod.password").toCharArray());
            return new MongoClient(serverAddress, Arrays.asList(mongoCredential));
        }
    }

    @Override
    protected String getMappingBasePackage() {
        return "com.ek.serialsserver";
    }

    @Bean
    @Override
    public MongoTemplate mongoTemplate() throws Exception {
        MongoTemplate mongoTemplate = new MongoTemplate(mongo(), getDatabaseName());
        return mongoTemplate;
    }
}
