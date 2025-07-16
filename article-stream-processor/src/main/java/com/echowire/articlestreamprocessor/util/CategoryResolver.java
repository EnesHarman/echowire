package com.echowire.articlestreamprocessor.util;


import java.util.Map;

public class CategoryResolver {

    private static final Map<String, String> keywordToCategory = Map.of(
            "AI", "technology",
            "politics", "politics",
            "economy", "business",
            "startup", "business",
            "Java", "programming",
            "space", "science",
            "climate", "environment"
    );

    public static String resolve(String title, String desc) {
        String content = (title + " " + desc).toLowerCase();

        return keywordToCategory.entrySet().stream()
                .filter(e -> content.contains(e.getKey().toLowerCase()))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse("general");
    }
}