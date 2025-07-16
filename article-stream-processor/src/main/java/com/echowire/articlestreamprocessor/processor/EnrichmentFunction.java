package com.echowire.articlestreamprocessor.processor;


import com.echowire.articlestreamprocessor.util.CategoryResolver;
import com.echowire.core.model.Article;
import org.apache.flink.api.common.functions.MapFunction;

public class EnrichmentFunction implements MapFunction<Article, Article> {
    @Override
    public Article map(Article article) {
        String category = CategoryResolver.resolve(article.title(), article.description());
        return article.withCategory(category);
    }
}
