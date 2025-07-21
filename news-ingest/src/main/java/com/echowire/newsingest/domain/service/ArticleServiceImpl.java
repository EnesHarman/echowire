package com.echowire.newsingest.domain.service;

import com.echowire.core.model.Article;
import com.echowire.newsingest.infrastructure.ArticlePublisher;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticlePublisher articlePublisher;

    public ArticleServiceImpl(ArticlePublisher articlePublisher) {
        this.articlePublisher = articlePublisher;
    }

    @Override
    public void publish(Article article) {
        articlePublisher.publish(article);
    }
}
