package com.echowire.articlestreamprocessor;

import com.echowire.articlestreamprocessor.config.KafkaConfig;
import com.echowire.articlestreamprocessor.deserializer.ArticleKafkaDeserializer;
import com.echowire.articlestreamprocessor.processor.EnrichmentFunction;
import com.echowire.articlestreamprocessor.processor.FilteringFunction;
import com.echowire.articlestreamprocessor.sink.MongoArticleSink;
import com.echowire.core.model.Article;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArticleStreamJob {

    private static final Logger logger = LoggerFactory.getLogger(ArticleStreamJob.class);

    public static void main(String[] args) throws Exception {
        logger.info("Starting EchoWire Article Stream Processor...");

        var env = StreamExecutionEnvironment.getExecutionEnvironment();
        env
                .fromSource(KafkaConfig.articleKafkaSource(new ArticleKafkaDeserializer()), WatermarkStrategy.noWatermarks(), "Kafka Source")
                .filter(new FilteringFunction())
                .map(new EnrichmentFunction())
                .addSink(new MongoArticleSink());

        logger.info("Job configuration completed, executing...");
        env.execute("EchoWire - Article Stream Processor");
    }

}
