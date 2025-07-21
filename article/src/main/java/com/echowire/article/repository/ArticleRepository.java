package com.echowire.article.repository;

import com.echowire.core.model.Article;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends MongoRepository<Article, String> {
    List<Article> findByCategoryIn(List<String> categories);
}
