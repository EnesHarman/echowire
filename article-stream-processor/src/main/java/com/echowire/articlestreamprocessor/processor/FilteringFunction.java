package com.echowire.articlestreamprocessor.processor;

import com.echowire.core.model.Article;
import org.apache.flink.api.common.functions.FilterFunction;

public class FilteringFunction implements FilterFunction<Article> {
    @Override
    public boolean filter(Article article) {
        return article != null &&
                article.title() != null &&
                !article.title().isBlank() &&
                article.link() != null &&
                !article.link().isBlank();
    }
}
