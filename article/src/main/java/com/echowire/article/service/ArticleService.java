package com.echowire.article.service;

import com.echowire.article.core.threadlocal.UserInfo;
import com.echowire.article.dto.request.ArticleRequest;
import com.echowire.article.dto.request.PaginatedRequest;
import com.echowire.article.dto.response.ArticleResponse;
import com.echowire.article.model.ArticleEntity;

import java.util.List;

public interface ArticleService {
    List<ArticleEntity> getArticlesByPreference(UserInfo userInfo, PaginatedRequest pagination);

    List<ArticleResponse> getArticles(ArticleRequest request);

    ArticleResponse getArticleByLink(String link);
}
