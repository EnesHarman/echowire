package com.echowire.articlestreamprocessor.processor;

import com.echowire.core.model.Article;
import org.apache.flink.api.common.functions.FilterFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FilteringFunction implements FilterFunction<Article> {
    Logger logger = LoggerFactory.getLogger(FilteringFunction.class);
    @Override
    public boolean filter(Article article) {

        logger.info("Article filter:" + article.title());
        return article != null &&
                article.title() != null &&
                !article.title().isBlank() &&
                article.link() != null &&
                !article.link().isBlank();
    }
}
