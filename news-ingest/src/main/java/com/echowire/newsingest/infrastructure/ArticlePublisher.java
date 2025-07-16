package com.echowire.newsingest.infrastructure;

import com.echowire.core.model.Article;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ArticlePublisher {
    private static final String ARTICLE_TOPIC = "articles";
    private final KafkaTemplate<String, Article> producer;

    public ArticlePublisher(KafkaTemplate<String, Article> producer) {
        this.producer = producer;
    }

    public void publish(Article article) {
        producer.send(ARTICLE_TOPIC, article);
    }
}
