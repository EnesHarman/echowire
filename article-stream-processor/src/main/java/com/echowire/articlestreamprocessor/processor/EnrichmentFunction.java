package com.echowire.articlestreamprocessor.processor;

import com.echowire.articlestreamprocessor.util.CategoryResolver;
import com.echowire.core.model.Article;
import org.apache.flink.api.common.functions.MapFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnrichmentFunction implements MapFunction<Article, Article> {
    private final Logger logger = LoggerFactory.getLogger(EnrichmentFunction.class);
    
    @Override
    public Article map(Article article) {
        logger.debug("Enriching article: {}", article.title());
        String category = CategoryResolver.resolve(article.title(), article.description());
        Article enrichedArticle = article.withCategory(category);
        logger.debug("Enriched article: {} with category: {}", enrichedArticle.title(), category);
        return enrichedArticle;
    }
}
