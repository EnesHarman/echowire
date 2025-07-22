package com.echowire.article.service;

import com.echowire.article.core.threadlocal.UserInfo;
import com.echowire.article.dto.request.ArticleRequest;
import com.echowire.article.dto.request.PaginatedRequest;
import com.echowire.article.dto.response.ArticleResponse;
import com.echowire.article.model.ArticleEntity;
import com.echowire.article.repository.ArticleRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {


    private final ArticleRepository articleRepository;

    private final UserServiceClient userServiceClient;

    public ArticleServiceImpl(ArticleRepository articleRepository, UserServiceClient userServiceClient) {
        this.articleRepository = articleRepository;
        this.userServiceClient = userServiceClient;
    }

    @Override
    @Cacheable(value = "preferences", key = "#userInfo.userId() + #pagination.hashCode()")
    public List<ArticleEntity> getArticlesByPreference(UserInfo userInfo, PaginatedRequest pagination) {;
        var preference = userServiceClient.getPreferences(userInfo.userId());
        return articleRepository.findPreferences(preference.categories(), pagination.limit(), pagination.page());
    }

    @Override
    @Cacheable(value = "articles", key = "#request.hashCode()")
    public List<ArticleResponse> getArticles(ArticleRequest request) {
        var articles = articleRepository.getArticles(request);
        return articles.stream().map(ArticleResponse::fromArticle).collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "article", key = "#link")
    public ArticleResponse getArticleByLink(String link) {
        var articleOpt = articleRepository.findByLink(link);
        if (articleOpt.isPresent()) {
            return ArticleResponse.fromArticle(articleOpt.get());
        }
        return null;
    }
}
