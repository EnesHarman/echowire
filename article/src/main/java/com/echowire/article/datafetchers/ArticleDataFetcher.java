package com.echowire.article.datafetchers;

import com.echowire.article.core.threadlocal.ECThreadLocal;
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


    private final MongoTemplate mongoTemplate;

    public ArticleDataFetcher(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @DgsQuery
    public List<Article> articles(@InputArgument Integer limit) {
        Query query = new Query().limit(limit != null ? limit : 10);
        ECThreadLocal.get().userId();
        return mongoTemplate.find(query, Article.class, "articles");
    }

    @DgsQuery
    @PreAuthorize("hasRole('USER')")
    public Article articleByLink(@InputArgument String link) {
        return mongoTemplate.findOne(Query.query(Criteria.where("link").is(link)), Article.class, "articles");
    }


}
