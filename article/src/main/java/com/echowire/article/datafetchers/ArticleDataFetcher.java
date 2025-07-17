package com.echowire.article.datafetchers;

import com.echowire.core.model.Article;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

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
        return mongoTemplate.find(query, Article.class, "articles");
    }

    @DgsQuery
    public Article articleByLink(@InputArgument String link) {
        return mongoTemplate.findOne(Query.query(Criteria.where("link").is(link)), Article.class, "articles");
    }


}
