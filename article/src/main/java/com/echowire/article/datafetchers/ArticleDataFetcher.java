package com.echowire.article.datafetchers;

import com.echowire.article.dto.request.ArticleRequest;
import com.echowire.article.dto.response.ArticleResponse;
import com.echowire.article.model.ArticleEntity;
import com.echowire.article.service.ArticleService;
import com.echowire.core.model.Article;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@DgsComponent
public class ArticleDataFetcher {
    private final ArticleService articleService;

    public ArticleDataFetcher(ArticleService articleService) {
        this.articleService = articleService;
    }

    @DgsQuery
    public List<ArticleResponse> articles(@InputArgument ArticleRequest request) {
        return articleService.getArticles(request);
    }

    @DgsQuery
    public ArticleResponse articleByLink(@InputArgument String link) {
        return articleService.getArticleByLink(link);
    }

    @DgsQuery
    @PreAuthorize("hasRole('USER')")
    public List<ArticleEntity> articlesByPreference() {
        return articleService.getArticlesByPreference();
    }


}
