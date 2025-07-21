package com.echowire.article.dto.response;


import com.echowire.article.model.ArticleEntity;

import java.time.ZonedDateTime;


public record ArticleResponse(String title, String link, String source, String description, ZonedDateTime publishDate, String category) {

    public static ArticleResponse fromArticle(ArticleEntity article) {
        return new ArticleResponse(article.getTitle(), article.getLink(), article.getSource(), article.getDescription(), article.getPublishedDate(), article.getCategory());
    }
}
