package com.echowire.articlestreamprocessor.sink;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.echowire.core.model.Article;
import org.apache.flink.streaming.api.functions.sink.legacy.SinkFunction;
import org.bson.Document;

public class MongoArticleSink implements SinkFunction<Article> {

    private transient MongoClient mongoClient;
    private transient MongoCollection<Document> collection;

    @Override
    public void invoke(Article article, Context context) {
        if (mongoClient == null) {
            mongoClient = MongoClients.create("mongodb://localhost:27017");
            MongoDatabase db = mongoClient.getDatabase("echowire");
            collection = db.getCollection("articles");
        }

        Document doc = new Document()
                .append("title", article.title())
                .append("link", article.link())
                .append("description", article.description())
                .append("source", article.source())
                .append("publishedAt", article.publishedDate().toString())
                .append("category", article.category());

        collection.insertOne(doc);
    }
}