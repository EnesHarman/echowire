package com.echowire.article.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Document("articles")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleEntity implements Serializable {
    @Id
    String id;
    String title;
    String link;
    String source;
    String description;
    ZonedDateTime publishedDate;
    String category;
}
