package com.echowire.article.repository;

import com.echowire.article.model.ArticleEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends MongoRepository<ArticleEntity, String> {
    List<ArticleEntity> findByCategoryIn(List<String> categories);
}
