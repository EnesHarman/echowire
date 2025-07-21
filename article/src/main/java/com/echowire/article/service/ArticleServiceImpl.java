package com.echowire.article.service;

import com.echowire.article.core.threadlocal.ECThreadLocal;
import com.echowire.article.dto.request.ArticleRequest;
import com.echowire.article.dto.response.ArticleResponse;
import com.echowire.article.model.ArticleEntity;
import com.echowire.article.repository.ArticleRepository;
import com.echowire.core.model.Article;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public List<ArticleEntity> getArticlesByPreference() {
        var userInfo = ECThreadLocal.get();
        var preference = userServiceClient.getPreferences(userInfo.userId());
        return articleRepository.findByCategoryIn(preference.categories());
    }

    @Override
    public List<ArticleResponse> getArticles(ArticleRequest request) {
        var articles = articleRepository.getArticles(request);
        return articles.stream().map(ArticleResponse::fromArticle).collect(Collectors.toList());
    }

    @Override
    public ArticleResponse getArticleByLink(String link) {
        var articleOpt = articleRepository.findByLink(link);
        if (articleOpt.isPresent()) {
            return ArticleResponse.fromArticle(articleOpt.get());
        }
        return null;
    }
}
