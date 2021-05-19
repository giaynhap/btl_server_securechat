package com.giaynhap.securechat.config;

import com.giaynhap.securechat.utils.CascadingMongoEventListener;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Collection;

@EnableMongoRepositories(basePackages = "com.giaynhap.securechat.repository")
@Configuration
public class MongoConfig  extends AbstractMongoClientConfiguration {


    @Value("${spring.data.mongodb.uri}")
    private String mongoDB;
    @Value("${spring.data.mongodb.database}")
    private String dbName;

    @Bean
    public MongoClient mongo() {
        ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017"+"/"+dbName);
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        return MongoClients.create();
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        MongoTemplate mongoTemplate = new MongoTemplate(mongo(),dbName);

        return mongoTemplate;
    }

    @Bean
    public CascadingMongoEventListener cascadingMongoEventListener() {
        return new CascadingMongoEventListener();
    }

    @Override
    protected String getDatabaseName() {
        return dbName;
    }
    @Override
    public boolean autoIndexCreation() {
        return true;
    }
}


