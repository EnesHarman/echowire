package com.echowire.core.model;

import java.time.ZonedDateTime;

public record Article(String title, String link, String source, String description, ZonedDateTime publishedDate, String category) {
    public Article withCategory(String category) {
        return new Article(title, link, description, source, publishedDate, category);

    }
}
