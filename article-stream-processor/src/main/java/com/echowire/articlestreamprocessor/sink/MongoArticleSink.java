package com.echowire.articlestreamprocessor.sink;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.echowire.core.model.Article;
import org.apache.flink.streaming.api.functions.sink.legacy.SinkFunction;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MongoArticleSink implements SinkFunction<Article> {

    private transient MongoClient mongoClient;
    private transient MongoCollection<Document> collection;
    private final Logger logger = LoggerFactory.getLogger(MongoArticleSink.class);
    
    private final String mongoHost;
    private final int mongoPort;
    private final String database;
    private final String collectionName;

    public MongoArticleSink() {
        this("localhost", 27017, "echowire", "articles");
    }

    public MongoArticleSink(String mongoHost, int mongoPort, String database, String collectionName) {
        this.mongoHost = mongoHost;
        this.mongoPort = mongoPort;
        this.database = database;
        this.collectionName = collectionName;
    }

    @Override
    public void invoke(Article article, Context context) {
        try {
            if (article == null) {
                logger.warn("Received null article, skipping");
                return;
            }

            if (mongoClient == null) {
                logger.info("Initializing MongoDB connection to {}:{}...", mongoHost, mongoPort);
                String connectionString = String.format("mongodb://%s:%d", mongoHost, mongoPort);
                mongoClient = MongoClients.create(connectionString);
                MongoDatabase db = mongoClient.getDatabase(database);
                collection = db.getCollection(collectionName);
                logger.info("MongoDB connection established successfully to database: {}, collection: {}", database, collectionName);
            }

            logger.info("Processing article: {}", article.title());

            Document doc = new Document()
                    .append("title", article.title())
                    .append("link", article.link())
                    .append("description", article.description())
                    .append("source", article.source())
                    .append("publishedAt", article.publishedDate().toString())
                    .append("category", article.category());

            collection.insertOne(doc);
            logger.info("Successfully inserted article: {} to MongoDB", article.title());

        } catch (Exception e) {
            logger.error("Error inserting article to MongoDB: {}", e.getMessage(), e);
        }
    }

    public void close() {
        if (mongoClient != null) {
            logger.info("Closing MongoDB connection");
            mongoClient.close();
        }
    }
}