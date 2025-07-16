package com.echowire.articlestreamprocessor.deserializer;

import com.echowire.core.model.Article;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.flink.api.common.serialization.AbstractDeserializationSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArticleKafkaDeserializer extends AbstractDeserializationSchema<Article> {
    private transient ObjectMapper mapper;
    private final Logger logger = LoggerFactory.getLogger(ArticleKafkaDeserializer.class);

    private ObjectMapper getMapper() {
        if (mapper == null) {
            mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
        }
        return mapper;
    }

    @Override
    public Article deserialize(byte[] message) {
        try {
            if (message == null || message.length == 0) {
                logger.warn("Received null or empty message");
                return null;
            }
            
            Article article = getMapper().readValue(message, Article.class);
            logger.debug("Successfully deserialized article: {}", article.title());
            return article;
        } catch (Exception e) {
            logger.error("Failed to deserialize article: {}", e.getMessage(), e);
            return null;
        }
    }
}
