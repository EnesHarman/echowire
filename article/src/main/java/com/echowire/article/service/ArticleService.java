package com.echowire.article.service;

import com.echowire.core.model.Article;

import java.util.List;

public interface ArticleService {

    List<Article> getArticlesByPreference();
}
