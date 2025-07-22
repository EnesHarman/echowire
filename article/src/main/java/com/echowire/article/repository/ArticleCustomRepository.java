package com.echowire.article.repository;

import com.echowire.article.dto.request.ArticleRequest;
import com.echowire.article.model.ArticleEntity;

import java.util.List;

public interface ArticleCustomRepository {
    List<ArticleEntity> getArticles(ArticleRequest request);
    List<ArticleEntity> findPreferences(List<String> categories, Integer limit, Integer page);
}
