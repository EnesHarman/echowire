package com.echowire.newsingest.scheduler;

import com.echowire.core.model.Article;
import com.echowire.newsingest.domain.service.ArticleService;
import com.echowire.newsingest.infrastructure.ArticlePublisher;
import com.echowire.newsingest.infrastructure.RssFieldClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Component
public class RssIngestScheduler {

    @Value("${echowire.rss.sources}")
    private List<String> urls;

    private static final Logger log = LoggerFactory.getLogger(RssIngestScheduler.class);

    private final RssFieldClient rssFieldClient;
    private final ArticleService articleService;
    private final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

    public RssIngestScheduler(RssFieldClient rssFieldClient, ArticleService articleService) {
        this.rssFieldClient = rssFieldClient;
        this.articleService = articleService;
    }

    @Scheduled(fixedRate = 5 * 60 * 1000)
    public void ingestFeeds() {
        log.info("Starting scheduled RSS ingestion");
        for (String url : urls) {
            executor.submit(() -> {
                Thread.currentThread().setName("rss-fetcher-" + url.hashCode());
                try {
                    List<Article> articles = rssFieldClient.fetchArticles(url);

                    if (articles.isEmpty()) {
                        log.info("No new articles found");
                        return;
                    }

                    log.info("Fetched {} articles. Publishing to Kafka…", articles.size());
                    for (Article article : articles) {
                        articleService.publish(article);
                    }

                    log.info("All articles published successfully");

                } catch (Exception e) {
                    log.error("Error during RSS ingestion: {}", e.getMessage(), e);
                }
            });
        }
    }
}
