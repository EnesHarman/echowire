package com.echowire.articlestreamprocessor.util;


import java.util.Map;

public class CategoryResolver {

    private static final Map<String, String> keywordToCategory = Map.ofEntries(
            Map.entry("AI", "technology"),
            Map.entry("politics", "politics"),
            Map.entry("economy", "business"),
            Map.entry("startup", "business"),
            Map.entry("Java", "programming"),
            Map.entry("space", "science"),
            Map.entry("climate", "environment"),
            Map.entry("health", "health"),
            Map.entry("sports", "sports"),
            Map.entry("music", "entertainment"),
            Map.entry("movie", "entertainment"),
            Map.entry("education", "education"),
            Map.entry("finance", "business"),
            Map.entry("travel", "lifestyle"),
            Map.entry("food", "lifestyle"),
            Map.entry("history", "history"),
            Map.entry("art", "art")
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