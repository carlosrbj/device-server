package br.com.hsob.scannerserver.configurations;

import br.com.hsob.scannerserver.repository.ScannerServerMongoDb;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * @author carlos
 */
@Configuration
public class MongoConfig {
    private static final String MONGODB_URI = System.getenv("mongo_uri");
    private static final String MONGODB_DATABASE = System.getenv("mongo_database");

    @Bean
    public MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString(MONGODB_URI);
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder().applyConnectionString(connectionString).build();
        return MongoClients.create(mongoClientSettings);
    }

    @Bean(ScannerServerMongoDb.BASE_MONGO_CONECTION)
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), MONGODB_DATABASE);
    }
}

