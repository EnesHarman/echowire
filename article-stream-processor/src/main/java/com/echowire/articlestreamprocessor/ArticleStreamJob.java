package com.echowire.articlestreamprocessor;

import com.echowire.articlestreamprocessor.config.KafkaConfig;
import com.echowire.articlestreamprocessor.deserializer.ArticleKafkaDeserializer;
import com.echowire.articlestreamprocessor.processor.EnrichmentFunction;
import com.echowire.articlestreamprocessor.processor.FilteringFunction;
import com.echowire.articlestreamprocessor.sink.MongoArticleSink;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;


public class ArticleStreamJob {

    public static void main(String[] args) throws Exception {
        var env = StreamExecutionEnvironment.getExecutionEnvironment();

        env
                .fromSource(KafkaConfig.articleKafkaSource(new ArticleKafkaDeserializer()), WatermarkStrategy.noWatermarks(), "Kafka Source")
                .filter(new FilteringFunction())
                .map(new EnrichmentFunction())
                .addSink(new MongoArticleSink());

        env.execute("EchoWire - Article Stream Processor");
    }

}
