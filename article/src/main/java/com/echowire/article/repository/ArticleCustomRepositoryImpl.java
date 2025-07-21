package com.echowire.article.repository;

import com.echowire.article.dto.request.ArticleRequest;
import com.echowire.article.model.ArticleEntity;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ArticleCustomRepositoryImpl implements ArticleCustomRepository {

    private final MongoTemplate mongoTemplate;

    public ArticleCustomRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<ArticleEntity> getArticles(ArticleRequest request) {
        List<Criteria> conditions = new ArrayList<>();
        if (request.title() != null && !request.title().isBlank()) {
            conditions.add(Criteria.where("title").regex(request.title(), "i"));
        }
        if (request.source() != null && !request.source().isBlank()) {
            conditions.add(Criteria.where("source").is(request.source()));
        }
        if (request.category() != null && !request.category().isBlank())  {
            conditions.add(Criteria.where("category").is(request.category()));
        }

        var combined = new Criteria();
        if (!conditions.isEmpty()) {
            combined.andOperator(conditions);
        }
        Query query = new Query(combined);
        query.skip( (request.page() - 1) * request.limit()).limit(request.limit());
        return mongoTemplate.find(query, ArticleEntity.class);
    }
}
