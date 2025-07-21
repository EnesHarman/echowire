package com.echowire.article.service;

import com.echowire.article.dto.request.ArticleRequest;
import com.echowire.article.dto.response.ArticleResponse;
import com.echowire.article.model.ArticleEntity;

import java.util.List;

public interface ArticleService {
    List<ArticleEntity> getArticlesByPreference();

    List<ArticleResponse> getArticles(ArticleRequest request);

    ArticleResponse getArticleByLink(String link);
}
