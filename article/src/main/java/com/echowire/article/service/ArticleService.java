package com.echowire.article.service;

import com.echowire.article.model.ArticleEntity;

import java.util.List;

public interface ArticleService {
    List<ArticleEntity> getArticlesByPreference();
}
