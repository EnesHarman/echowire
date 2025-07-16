package com.echowire.newsingest.infrastructure;

import com.echowire.core.model.Article;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class RssFieldClient {
    public List<Article> fetchArticles(String rssUrl) {
        List<Article> articles = new ArrayList<>();
        try (var reader = new XmlReader(new URL(rssUrl))) {
            var feed = new SyndFeedInput().build(reader);
            for (SyndEntry entry : feed.getEntries()) {
                var article = new Article(
                        entry.getTitle(),
                        entry.getLink(),
                        entry.getDescription() != null ? entry.getDescription().getValue() : "",
                        feed.getTitle(),
                        entry.getPublishedDate() != null ?
                                ZonedDateTime.ofInstant(entry.getPublishedDate().toInstant(), ZoneId.systemDefault()) :
                                ZonedDateTime.now(),
                        null
                );
                articles.add(article);
            }
        } catch (Exception e) {
            System.err.println("Failed to fetch or parse feed: " + rssUrl + " - " + e.getMessage());
        }

        return articles;
    }

}
