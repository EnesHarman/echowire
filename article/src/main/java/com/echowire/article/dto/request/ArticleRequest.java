package com.echowire.article.dto.request;

import com.echowire.article.model.ArticleEntity;

public record ArticleRequest(String title, String source, String category, Integer limit, Integer page, String publishDate) {
}
