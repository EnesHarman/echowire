package com.echowire.articlestreamprocessor.deserializer;

import com.echowire.core.model.Article;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.api.common.serialization.AbstractDeserializationSchema;

public class ArticleKafkaDeserializer extends AbstractDeserializationSchema<Article> {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Article deserialize(byte[] message) {
        try {
            return mapper.readValue(message, Article.class);
        } catch (Exception e) {
            System.err.println("Failed to deserialize article: " + e.getMessage());
            return null;
        }
    }
}
