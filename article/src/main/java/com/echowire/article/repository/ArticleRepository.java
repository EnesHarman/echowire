package com.echowire.article.repository;

import com.echowire.article.model.ArticleEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends MongoRepository<ArticleEntity, String>, ArticleCustomRepository {

    Optional<ArticleEntity> findByLink(String link);
}
